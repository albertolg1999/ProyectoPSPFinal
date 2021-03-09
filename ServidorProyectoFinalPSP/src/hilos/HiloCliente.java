/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import BaseDatos.ConexionBD;
import clases.CodigosUso;
import clases.Comunicacion;
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
    //private Preferences prefsUserLog;
    private boolean activo;

    public HiloCliente(Socket cliente, PublicKey clavePubServidor, PrivateKey clavePrivServidor) {

        this.clavePrivPropia = clavePrivServidor;
        this.clavePubPropia = clavePubServidor;
        this.cliente = cliente;
        this.activo = true;
    }

    @Override
    public void run() {

        try {
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

                    //crear preferences
                    case CodigosUso.CODE_PREFERENCES_CREATE:
                        System.out.println("ORDEN PREFS");
                        //crearPreferencias();
                        break;

                    //actualizar preferences
                    case CodigosUso.CODE_PREFERENCES_UPDATE:
                        //actualizarPreferencias();
                        break;
                        
                    //seleccionar preferences user
                    case CodigosUso.CODE_PREFERENCES_SELECT:
                        //cargarPreferencias();
                        break;
                    
                    case CodigosUso.C_obtenerUsuarios:
                        
                        ArrayList<Usuario> lista;
                        lista=ConexionBD.obtenerUsuarios();
                        
                        so = Seguridad.cifrar(clavePubAjena, lista);
                        Comunicacion.enviarObjeto(cliente, so);
                        break;
                    
                    case CodigosUso.CODE_USER_ADMIN:
                        
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
    private boolean existsLogin(String email, String pwd) {
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

        if (existsLogin(u.getEmail(), u.getPwd())) {
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

                checkStateUser();

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
    private void checkStateUser() {
        if (ConexionBD.isActivatedUser(userLogueado)) {
            enviarRespuesta(CodigosUso.LOGIN_CORRECTO);
            //checkPrefsUser();
        } else {
            enviarRespuesta(CodigosUso.CODE_USER_NOT_ACTIVATED);
        }
    }

    /**
     *
     */
    /*private void checkPrefsUser() {
        prefsUserLog = ConexionBD.obtenerPreferences(userLogueado.getId());
        if (prefsUserLog != null) {
            checkTypeUser();
        } else {
            enviarRespuesta(CodeResponse.PREFERENCES);
        }
    }

    /**
     *
     */
    /*private void checkTypeUser() {
        String rol = ConexionBD.selectTypeUser(userLogueado.getId());

        //Esto lo he hecho con un switch aunque ahora mismo sean dos roles
        //nunca se sabe si en un futuro se incorporan nuevos roles
        switch (rol) {
            case ConstantesRoles.ROL_USER:
                enviarRespuesta(CodeResponse.CODE_USER_USER);
                break;

            case ConstantesRoles.ROL_ADMIN:
                enviarRespuesta(CodeResponse.CODE_USER_ADMIN);
                break;
        }
    }

    /**
     *
     */
    /*private void crearPreferencias() {
        try {
            //recibe las preferencias del usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            Preferences prefs = (Preferences) Seguridad.descifrar(clavePrivPropia, so);

            //Introducimos las preferencias en la base de datos
            if (ConexionBD.insertPreferences(prefs)) {
                enviarRespuesta(CodeResponse.CODE_PREFERENCES_CORRECTO);
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
    }
    
    /**
     *
     */
    /*private void cargarPreferencias() {
        try {
            so = Seguridad.cifrar(clavePubAjena, prefsUserLog);
            Comunicacion.enviarObjeto(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException |
                IOException | IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
    }

    private void actualizarPreferencias() {
         try {
            //recibe las preferencias del usuario
            so = (SealedObject) Comunicacion.recibirObjeto(cliente);
            Preferences prefs = (Preferences) Seguridad.descifrar(clavePrivPropia, so);

            //Introducimos las preferencias en la base de datos
            if (ConexionBD.updatePreferences(prefs)) {
                enviarRespuesta(CodeResponse.CODE_PREFERENCES_CORRECTO);
            }

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException
                | ClassNotFoundException | IllegalBlockSizeException | BadPaddingException ex) {
            ex.printStackTrace();
        }
    }*/
}
