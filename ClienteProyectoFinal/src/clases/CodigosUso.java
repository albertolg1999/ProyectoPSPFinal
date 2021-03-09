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
    public static final short SIGN_UP = 6;
    public static final short PREFERENCES = 7;
    public static final short SALIR = 8;
    
    public static final short CODE_SIGNUP_CORRECTO = 9;   
    public static final short CODE_SIGNUP_EMAIL = 10;   
    
    public static final short CODE_PREFERENCES_CORRECTO = 11;
    public static final short CODE_PREFERENCES_CREATE = 16;
    public static final short CODE_PREFERENCES_UPDATE = 17;
    public static final short CODE_PREFERENCES_SELECT = 18;
    
    public static final short CODE_USER_NOT_ACTIVATED = 12;
     
    public static final short CODE_USER_EXISTS = 13;
    
    public static final short CODE_USER_USER = 14;
    public static final short CODE_USER_ADMIN = 15;
    
    //ADMIN
    public static final short CODE_ELIMINAR_USER = 19;
    public static final short CODE_CREAR_USER = 20;
    public static final short CODE_ACTIVAR_USER = 28;
    public static final short CODE_CREAR_ADMIN = 22;
    public static final short CODE_ELIMINAR_ADMIN = 23;
    public static final short CODE_EXITO_ACTIVAR = 24;
    public static final short CODE_EXITO_ELIMINAR = 25;
    public static final short CODE_EXITO_ASC = 26;
    public static final short CODE_EXITO_DESC = 27;
    
    public static final short C_obtenerUsuarios = 21;
    public static final short C_Preferencias = 29;
    public static final short C_Preferencias_notiene = 30;
    public static final short C_Preferencias_tiene = 31;
}
