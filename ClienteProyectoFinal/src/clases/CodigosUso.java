/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

/**
 *
 * @author disen
 */
public class CodigosUso {
     public static final short ERROR = -1;   
    
    public static final short LOGIN_CORRECTO = 2;    
    public static final short EMAIL_LOGIN_INCORRECTO = 3;
    public static final short PWD_LOGIN_INCORRECTO = 4;
    
    public static final short LOGIN = 5;    
    public static final short Registro = 6;
    public static final short Preferencias = 7;
    public static final short SALIR = 8;
    
    public static final short C_Registro_CORRECTO = 9;   
    public static final short C_SIGNUP_EMAIL = 10;   
    
    public static final short C_Preferencias_CORRECTO = 11;
    public static final short C_Preferencias_CREATE = 16;
    public static final short C_Preferencias_UPDATE = 17;
    public static final short C_Preferencias_SELECT = 18;
    
    public static final short C_UsuarioInactivo = 12;
     
    public static final short C_UsuarioExistente = 13;
    
    public static final short CODE_USER_USER = 14;
    public static final short CODE_USER_ADMIN = 15;
    
    //ADMIN
    public static final short C_eliminarUsuario = 19;
    public static final short C_crearUsuario = 20;
    public static final short C_modificarUsuario = 34;
    public static final short C_activarUsuario= 28;
    public static final short C_crearAdmin = 22;
    public static final short C_eliminarAdmin = 23;
    public static final short CODE_EXITO_ACTIVAR = 24;
    public static final short CODE_EXITO_ELIMINAR = 25;
    public static final short CODE_EXITO_ASC = 26;
    public static final short CODE_EXITO_DESC = 27;
    
    public static final short C_obtenerUsuarios = 21;
    public static final short C_Preferencias = 29;
    public static final short C_Preferencias_notiene = 30;
    public static final short C_Preferencias_tiene = 31;
    
    public static final short C_obtenerPerfil = 32;
    public static final short C_actualizarPerfil = 33;
    
    public static final short C_obtenerAfines = 34;
    public static final short C_añadirAmigos = 35;
    public static final short C_obtenerAmigos = 36;
    
    public static final short C_enviarMensaje = 37;
    public static final short C_leerMensaje = 38;
}
