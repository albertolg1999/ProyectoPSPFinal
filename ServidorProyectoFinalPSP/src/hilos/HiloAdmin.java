/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hilos;

import BaseDatos.ConexionBD;
import BaseDatos.RolesBD;
import BaseDatos.UsuariosBD;
import clases.CodigosUso;
import clases.Comunicacion;
import clases.Seguridad;
import clases.Usuario;
import java.io.IOException;
import java.net.Socket;
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
public class HiloAdmin extends Thread{
    private Socket cliente;
    private PublicKey clavePubAjena;
    private PrivateKey clavePrivPropia;
    private SealedObject so;
    private boolean activo;
    private Usuario login;

    public HiloAdmin(Socket cliente, PublicKey clavePubAjena, PrivateKey clavePrivPropia, Usuario login) {
        this.cliente = cliente;
        this.clavePubAjena = clavePubAjena;
        this.clavePrivPropia = clavePrivPropia;
        this.so = so;
        this.activo = true;
        this.login = login;
    }

    

    
    
    @Override
    public void run() {
        do {
            try {

                listarUsuarios();
                
                System.out.println("ESPERANDO ORDEN");
                //recibe orden
                so = (SealedObject) Comunicacion.recibirObjeto(cliente);
                short orden = (short) Seguridad.descifrar(clavePrivPropia, so);
                switch (orden) {
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
                        ascenderUser();
                        break;

                    case CodigosUso.CODE_ELIMINAR_ADMIN:
                        System.out.println("ORDEN DESC");
                        eliminarUsuario();
                        break;
                }
            } catch (IOException | ClassNotFoundException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                    | IllegalBlockSizeException | BadPaddingException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(HiloAdmin.class.getName()).log(Level.SEVERE, null, ex);
            }
        } while (activo);
    }

    /**
     *
     * @param res
     */
    private void enviarRespuesta(short res) {
        try {
            so = Seguridad.cifrar(clavePubAjena, res);
            Comunicacion.enviarObjeto(cliente, so);
        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException
                | IOException | IllegalBlockSizeException ex) {
            ex.printStackTrace();
        }
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
     * @return
     *
     */
    private boolean registrarUsuario() throws SQLException {
        Usuario u = recibirUsuario();
        System.out.println("RECIBIDO USUARIO OK " + u.getEmail());
        return ConexionBD.registrarUsuario(u);
    }

    private void listarUsuarios() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException {
        try {
            // try {
            
            ArrayList<Usuario> lista;
            lista=ConexionBD.obtenerUsuarios();
                        
            so = Seguridad.cifrar(clavePubAjena, lista);
            Comunicacion.enviarObjeto(cliente, so);
            System.out.println("ENVIO LISTA USUARIOS SERVIDOR");
            //} catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IOException | IllegalBlockSizeException ex) {
            //    ex.printStackTrace();
            //}
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private void activarUsuario(){
        Usuario u = recibirUsuario();
        if (UsuariosBD.activarUser(u.getId())) {
            enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("AC OK");
        }else{
            enviarRespuesta(CodigosUso.ERROR);
        }
    }
    
    private void eliminarUsuario(){
        Usuario u = recibirUsuario();
        if (UsuariosBD.deleteUser(u.getId())) {
            enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("DEL OK");
        }else{
            enviarRespuesta(CodigosUso.ERROR);
        }
    }

    private void ascenderUser() {
        Usuario u = recibirUsuario();
        if (RolesBD.ascUser(u.getId())) {
            enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("ASC OK");
        }else{
            enviarRespuesta(CodigosUso.ERROR);
        }
    }
    
    private void DegradarUser() {
        Usuario u = recibirUsuario();
        if (RolesBD.descUser(u.getId())) {
            enviarRespuesta(CodigosUso.CODE_EXITO_ACTIVAR);
            System.out.println("ASC OK");
        }else{
            enviarRespuesta(CodigosUso.ERROR);
        }
    }
    
}
