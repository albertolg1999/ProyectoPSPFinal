/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import BaseDatos.ConexionBD;
import BaseDatos.RolesBD;
import BaseDatos.UsuariosBD;
//import static BaseDatos.ConexionBD.abrirConexion;
import clases.CodigosUso;
import clases.Comunicacion;
import clases.ConstantesRoles;
import clases.Perfil;
import clases.Preferencias;
import clases.Seguridad;
import clases.Usuario;
import java.io.IOException;
import java.net.Socket;
import java.net.SocketException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/**
 *
 * @author disen
 */
public class HiloCliente extends Thread {

    private Socket cliente;
    private PublicKey clavePubPropia;
    private PublicKey clavePubAjena;
    private PrivateKey clavePrivPropia;
    private SealedObject so;
    private Usuario userLogueado;
    private Preferencias prefsUserLog;
    private boolean activo;
    ConexionBD cb;

    public HiloCliente(Socket cliente, PublicKey clavePubServidor, PrivateKey clavePrivServidor) {

        this.clavePrivPropia = clavePrivServidor;
        this.clavePubPropia = clavePubServidor;
        this.cliente = cliente;
        this.activo = true;
        this.cb=new ConexionBD();
        
    }

    @Override
    public void run() {

        try {
            //abrirConexion();
            //cb.abrirConexion();
            //recibe la clave publica del cliente actual
            clavePubAjena = (PublicKey) Comunicacion.recibirObjeto(cliente);

            //envia la clave publica del servidor al cliente actual
            Comunicacion.enviarObjeto(cliente, clavePubPropia);

            do {
                //recibe orden
                so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                short orden = (short) Seguridad.descifrar(clavePrivPropia, so);
                System.out.println(orden);
                switch (orden) {
                    //registrar un usuario
                    case CodigosUso.SIGN_UP:
                        System.out.println("ORDEN REGISTRO");
                        if (registrarUsuario()) {
                            
                            enviarRespuesta(CodigosUso.CODE_SIGNUP_CORRECTO);
                        } else {
                            enviarRespuesta(CodigosUso.CODE_SIGNUP_EMAIL);
                        }
                        break;

                    //login usuario   
                    case CodigosUso.LOGIN:
                        System.out.println("ORDEN LOGIN");
                        login();
                        break;
                    case CodigosUso.C_Preferencias:
                        System.out.println("ORDEN CONSULTAR PREFERENCIAS SI EXISTEN");
                        Preferencias p;
                        Usuario u=recibirUsuario();
                        p=ConexionBD.obtenerPreferencias(u.getId());
                        
                        if(p==null){
                            enviarRespuesta(CodigosUso.C_Preferencias_notiene);
                            System.out.println("no tiene");
                        }
                        else{
                            enviarRespuesta(CodigosUso.C_Preferencias_tiene);
                        }
                        break;

                    //crear preferences
                    case CodigosUso.CODE_PREFERENCES_CREATE:
                        System.out.println("ORDEN PREFS");
                        
                        boolean correcto=crearPreferencias();
                        if(correcto){
                            enviarRespuesta(CodigosUso.CODE_PREFERENCES_CORRECTO);
                            System.out.println("respuesta enviada");
                        }
                        break;

                    //actualizar preferences
                    case CodigosUso.CODE_PREFERENCES_UPDATE:
                        modificarPreferencias();
                        break;
                        
                    //seleccionar preferences user
                    case CodigosUso.CODE_PREFERENCES_SELECT:
                        Preferencias pref;
                        Usuario us=recibirUsuario();
                        pref=ConexionBD.obtenerPreferencias(us.getId());
                        System.out.println("Envio preferencias");
                        so = Seguridad.cifrar(clavePubAjena, pref);
                        Comunicacion.enviarObjeto(cliente, so);
                        break;
                    
                    //case CodigosUso.C_obtenerUsuarios:
                        
                    case CodigosUso.C_obtenerPerfil:
                        System.out.println("orden obtener perfil");
                        if(obtenerPerfil()){
                            
                        }
                        break;
                    case CodigosUso.C_actualizarPerfil:
                        System.out.println("mODIFICACN");
                        modificarPerfil();
                        break;
                    case CodigosUso.CODE_USER_ADMIN:
                        System.out.println("aqui estoy");
                        ArrayList<Usuario> lista;
                        lista=ConexionBD.obtenerUsuarios();
                        
                        so = Seguridad.cifrar(clavePubAjena, lista);
                        Comunicacion.enviarObjeto(cliente, so);
                        
                            do {
                                try {

                                    
                
                                    System.out.println("ESPERANDO ORDEN");
                                    //recibe orden
                                    so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                                    orden = (short) Seguridad.descifrar(clavePrivPropia, so);
                                    switch (orden) {
                                        case CodigosUso.CODE_USER_ADMIN:
                                            System.out.println("aqui estoy");
                        
                                            lista=ConexionBD.obtenerUsuarios();
                        
                                            so = Seguridad.cifrar(clavePubAjena, lista);
                                            Comunicacion.enviarObjeto(cliente, so);
                                            break;
                                        case CodigosUso.CODE_ACTIVAR_USER:
                                            System.out.println("ORDEN ACTIVAR");
                                            activarUsuario();
                                            break;

                                        case CodigosUso.CODE_ELIMINAR_USER:
                                            System.out.println("ORDEN ELIMINAR");
                                            eliminarUsuario();
                                            break;

                                        case CodigosUso.CODE_CREAR_USER:
                                            System.out.println("ORDEN CREAR");
                                            if (registrarUsuario()) {
                                                enviarRespuesta(CodigosUso.CODE_SIGNUP_CORRECTO);
                                                System.out.println("REGIS OK");
                                            } else {
                                                enviarRespuesta(CodigosUso.CODE_SIGNUP_EMAIL);
                                            }
                                            break;

                                        case CodigosUso.CODE_CREAR_ADMIN:
                                            System.out.println("ORDEN ASC");
                                            ascenderUsuario();
                                            break;

                                        case CodigosUso.CODE_ELIMINAR_ADMIN:
                                            System.out.println("ORDEN DESC");
                                            DegradarUsuario();
                                            break;
                                    }
                                } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                                        | IllegalBlockSizeException | BadPaddingException ex) {
                                    ex.printStackTrace();
                                } catch (SQLException ex) {
                                    Logger.getLogger(HiloAdmin.class.getName()).log(Level.SEVERE, null, ex);
                                }
                            } while (activo);
                        break;
                        
                    case CodigosUso.SALIR:
                        this.cliente.close();
                        break;
                }
            } while (!this.cliente.isClosed() || activo);

        } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException
                | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(HiloCliente.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    
    
    
    private void activarUsuario(){
        Usuario u = recibirUsuario();
        if (ConexionBD.activarUser(u.getId())) {
            enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("AC OK");
        }else{
            enviarRespuesta(CodigosUso.ERROR);
        }
    }
    
    private void eliminarUsuario() throws SQLException{
        Usuario u = recibirUsuario();
        if (ConexionBD.EliminarUsuario(u.getId())) {
            enviarRespuesta(CodigosUso.CODE_EXITO_ELIMINAR);
            System.out.println("Eliminado Correctamente");
        }else{
            enviarRespuesta(CodigosUso.ERROR);
        }
    }

    private void ascenderUsuario() throws SQLException {
        Usuario u = recibirUsuario();
        System.out.println(u.getId());
        if (ConexionBD.ascUser(u.getId())) {
            //sout
            //enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("ASC OK");
        }else{
            //enviarRespuesta(CodigosUso.ERROR);
        }
    }
    
    private void DegradarUsuario() throws SQLException {
        Usuario u = recibirUsuario();
        if (ConexionBD.DegradarUsuario(u.getId())) {
            //enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("Degradado a usuario normal correctamente");
        }else{
            
            //enviarRespuesta(CodigosUso.ERROR);
        }
    }

    /**
     *
     * @param res
     */
    private void enviarRespuesta(short res) {
        try {
            so = Seguridad.cifrar(clavePubAjena, res);
            Comunicacion.enviarObjeto(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
        }
    }

    /**
     *
     * @return @throws IOException
     * @throws ClassNotFoundException
     */
    private boolean registrarUsuario() throws IOException, ClassNotFoundException, SQLException {
        Usuario u = recibirUsuario();
        System.out.println("RECIBIDO USUARIO OK " + u.getEmail()+" "+u.getPwd());
        return ConexionBD.registrarUsuario(u);
    }

    /**
     *
     * @return
     */
    private Usuario recibirUsuario() {
        Usuario u = null;
        try {
            //recibe el usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }

        return u;
    }

    /**
     *
     * @param email
     * @param pwd
     * @return
     */
    private boolean ComprobarLoginExiste(String email, String pwd) {
        boolean existe = false;
        userLogueado = ConexionBD.comprobarLogin(email, pwd);

        if (userLogueado != null) {
            existe = true;
        }
        return existe;
    }

    /**
     *
     */
    private void login() {
        //recibe el usuario a loguear
        Usuario u = recibirUsuario();
        System.out.println(u.getEmail());
        if (ComprobarLoginExiste(u.getEmail(), u.getPwd())) {
            try {
                //Enviamos el codigo de que el usuario existe en la base de datos
                enviarRespuesta(CodigosUso.CODE_USER_EXISTS);
                int id=ConexionBD.obtenerIdUsuario(userLogueado);
                
                userLogueado.setId(id);
                //Enviamos el usuario que se acaba de logear con todos los datos
                //que existen en la tabla usuario
                so = Seguridad.cifrar(clavePubAjena, userLogueado);
                System.out.println("Usuario Logueado"+userLogueado.getName()+" id:"+userLogueado.getId());
                Comunicacion.enviarObjeto(cliente, so);
                System.out.println("h");
                ComprobarTipoUsuario();

            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
                ex.printStackTrace();
            }

        } else {
            enviarRespuesta(CodigosUso.EMAIL_LOGIN_INCORRECTO);
        }
    }

    /**
     *
     */
    private void ComprobarUsuarioActivado() {
        if (ConexionBD.isActivatedUser(userLogueado)) {
            System.out.println("hh");
            //enviarRespuesta(CodigosUso.LOGIN_CORRECTO);
            ComprobarTipoUsuario();
            //checkPrefsUser();
        } else {
            enviarRespuesta(CodigosUso.CODE_USER_NOT_ACTIVATED);
        }
    }

    /**
     *
     */
    private void ComprobarPreferenciasUsuario() {
        prefsUserLog = ConexionBD.obtenerPreferencias(userLogueado.getId());
        if (prefsUserLog != null) {
            
            
        } else {
            System.out.println("hhh");
            enviarRespuesta(CodigosUso.C_Preferencias_notiene);
        }
    }

    /**
     *
     */
    private void ComprobarTipoUsuario() {
        String rol = ConexionBD.selectTypeUser(userLogueado.getId());

        System.out.println(rol);
        switch (rol) {
            case ConstantesRoles.ROL_USER:
                enviarRespuesta(CodigosUso.CODE_USER_USER);
                ComprobarPreferenciasUsuario();
                break;

            case ConstantesRoles.ROL_ADMIN:
                enviarRespuesta(CodigosUso.CODE_USER_ADMIN);
                break;
        }
    }

    private boolean obtenerPerfil() {
        boolean correcto=false;
        try {
            
            //recibe las preferencias del usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            Usuario u = (Usuario) Seguridad.descifrar(clavePrivPropia, so);

            Perfil p=ConexionBD.obtenerPerfil(u.getId());
            
            so = Seguridad.cifrar(clavePubAjena, p);
            Comunicacion.enviarObjeto(cliente, so);
            
            System.out.println("perfil enviado");
            
            
            

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
        System.out.println(correcto);
       return correcto;
    }
    /**
     *
     */
    private boolean crearPreferencias() {
        boolean correcto=false;
        try {
            
            //recibe las preferencias del usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            Preferencias prefs = (Preferencias) Seguridad.descifrar(clavePrivPropia, so);

            //Introducimos las preferencias en la base de datos
            if (ConexionBD.insertarPreferenciasUsuario(prefs)) {
                
                correcto=true;
                
            }
            

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
        System.out.println(correcto);
       return correcto;
    }
    
    /**
     *
     */
    private void cargarPreferencias(Preferencias pref) {
        try {
            so = Seguridad.cifrar(clavePubAjena, pref);
            Comunicacion.enviarObjeto(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IOException | IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
    }

    private void modificarPreferencias() {
         try {
            //recibe las preferencias del usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            Preferencias prefs = (Preferencias) Seguridad.descifrar(clavePrivPropia, so);

            //Introducimos las preferencias en la base de datos
            if (ConexionBD.modificarPreferencias(prefs)) {
                enviarRespuesta(CodigosUso.CODE_PREFERENCES_CORRECTO);
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
    }
    
    private void modificarPerfil() {
         try {
            //recibe las preferencias del usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            Perfil perfil = (Perfil) Seguridad.descifrar(clavePrivPropia, so);
            
             System.out.println(perfil.getLocalidad()+" "+perfil.getEdad());

            //Introducimos las preferencias en la base de datos
            if (ConexionBD.modificarPerfil(perfil)) {
                System.out.println("Perfil Modificado");
                //enviarRespuesta(CodigosUso.C);
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
    }
}
