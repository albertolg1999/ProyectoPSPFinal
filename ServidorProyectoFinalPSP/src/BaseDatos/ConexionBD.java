/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import clases.CodigosUso;
import clases.ConstantesBD;
import clases.ConstantesRoles;
import clases.Perfil;
import clases.Preferencias;
import clases.Usuario;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.serial.SerialBlob;
import javax.swing.JOptionPane;
import static sun.security.krb5.Confounder.bytes;

/**
 *
 * @author disen
 */
public class ConexionBD {
    private static Connection conex;
    private static Statement Sentencia_SQL;
    private static ResultSet Conj_Registros;
    private static String sentencia;
    Blob b;

    public ConexionBD() {
    }

    
    
    
    static {
        try {
            try {
                String controlador = "com.mysql.jdbc.Driver";
                Class.forName(controlador);
                String URL_BD = "jdbc:mysql://localhost/" + ConstantesBD.BD;
                conex = (com.mysql.jdbc.Connection) java.sql.DriverManager.getConnection(URL_BD, ConstantesBD.USUARIO, ConstantesBD.PASSWD);
                Sentencia_SQL = (com.mysql.jdbc.Statement) conex.createStatement();
                System.out.println("Conexión realizada con éxito");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    

    public static Connection abrirConexion() {
        try {
            try {
                String controlador = "com.mysql.jdbc.Driver";
                Class.forName(controlador);
                String URL_BD = "jdbc:mysql://localhost/" + ConstantesBD.BD;
                conex = (com.mysql.jdbc.Connection) java.sql.DriverManager.getConnection(URL_BD, ConstantesBD.USUARIO, ConstantesBD.PASSWD);
                Sentencia_SQL = (com.mysql.jdbc.Statement) conex.createStatement();
                System.out.println("Conexión realizada con éxito");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void cerrarConexion(Connection conex) {
        try {
            conex.close();
            conex = null;
            System.out.println("Desconectado de la Base de Datos"); // Opcional para seguridad
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error de Desconexion", JOptionPane.ERROR_MESSAGE);
        }
    }
    
     public static boolean ascUser(int id) throws SQLException {
        boolean exito = false;
        
                    sentencia = "UPDATE " + ConstantesBD.TUsuarios_Roles + " SET id_rol = " + ConstantesRoles.ROL_ID_ADMIN + " WHERE id_usuario = '" + id + "'";
                    
                    if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
        
                
        

        return exito;
    }
     
    public static boolean DegradarUsuario(int id) throws SQLException {
        boolean exito = false;
        
                    sentencia = "UPDATE " + ConstantesBD.TUsuarios_Roles + " SET id_rol = " + ConstantesRoles.ROL_ID_USER + " WHERE id_usuario = '" + id + "'";
                    
                    if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
        
                
        

        return exito;
    }
    /**
     *
     * @param email
     * @return
     */
    private static boolean usuarioExists(String email) {
        boolean exist = false;

        sentencia = "SELECT * FROM " + ConstantesBD.TUsuarios + " WHERE email = '" + email + "'";
        try {
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            if (Conj_Registros.next()) {
                exist = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exist;
    }

    public static boolean activarUser(int id) {
        boolean exito = false;
        

        
        try{
            
            sentencia = "UPDATE " + ConstantesBD.TUsuarios + " SET activado = 1 WHERE id_usuario = '" + id + "'";
            
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                exito = true;
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        
        return exito;
    }
    
    public static boolean EliminarUsuario(int id) throws SQLException {
        boolean exito = false;
       
                System.out.println(id);
        
            sentencia = "DELETE FROM " + ConstantesBD.TUsuarios_Roles + " WHERE id_usuario = " + id + "";

                    if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                    
                    sentencia = "DELETE FROM " + ConstantesBD.TPerfil + " WHERE id_usuario = " + id + "";

                    if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                    
                    sentencia = "DELETE FROM " + ConstantesBD.TPref + " WHERE id_usuario = " + id + "";

                    if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                    
                    sentencia = "DELETE FROM " + ConstantesBD.TUsuarios + " WHERE id_usuario = " + id + "";

                    if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
        
           
        
        return exito;
    }
    
    /**
     *
     * @param u
     * @return
     */
    public synchronized static boolean registrarUsuario(Usuario u) throws SQLException {

        boolean exito = false;

        if (!usuarioExists(u.getEmail())) {
            sentencia = "INSERT INTO " + ConstantesBD.TUsuarios + " ( Usuario, Email, Password,Activado) "
                    + "values('" + u.getName() + "','" + u.getEmail()
                    + "','" + u.getPwd() + "',"+0+")";
            
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                System.out.println("USUARIO REGISTRADO");
                
                exito = true;
            }
        }
        
        
        if(exito){
            int id=obtenerIdUsuario(u);
            if (id!=0) {
            System.out.println(u.getPwd().toString());
            sentencia = "INSERT INTO " + ConstantesBD.TPerfil + " ( id_usuario, usuario) "
                    + "values('" + id +  "','" + u.getName()+ "')";
            
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                System.out.println("USUARIO REGISTRADO en la tabla perfil");
            }
            
            sentencia = "INSERT INTO " + ConstantesBD.TUsuarios_Roles + " ( id_usuario, id_rol) "
                    + "values('" + id +  "',2)";
            
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                System.out.println("USUARIO REGISTRADO en la tabla usuarios roles");
            }
            
        }
        }
        return exito;
    }

    
    
    
    /**
     *
     * @param prefs
     */
    public synchronized static boolean insertarPreferenciasUsuario(Preferencias prefs) {
        boolean exito = false;
        try {
            
            int thijos,qhijos;
            if(prefs.istHijos()==true){
                thijos=1;
            }
            else{
                thijos=0;
            }
            
            if(prefs.isqHijos()==true){
                qhijos=1;
            }
            else{
                qhijos=0;
            }
            
            sentencia = "INSERT INTO " + ConstantesBD.TPref + " (id_usuario, arte, politica, deporte, relaccion, thijos, qhijos, interes) "
                    + "values(" + prefs.getId() + "," + prefs.getArte() + "," + prefs.getPolitica()+ "," + prefs.getDeporte() + ",'" + prefs.getRelacion()
                    + "'," + thijos + "," + qhijos + ",'" + prefs.getInteres() + "')";
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                exito = true;
                System.out.println("PREFERENCIA REGISTRADA");
                
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    public synchronized static boolean modificarPreferencias(Preferencias prefs) {
        boolean exito = false;

        try {
            
            int thijos,qhijos;
            
            if(prefs.istHijos()){
                thijos=1;
            }
            else{
                thijos=0;
            }
            
            if(prefs.isqHijos()){
                qhijos=1;
            }
            else{
                qhijos=0;
            }
            
            sentencia = "UPDATE " + ConstantesBD.TPref + " SET arte = " + prefs.getArte() + ", deporte = "
                    + prefs.getDeporte() + ", politica = " + prefs.getPolitica() + ", thijos = " + thijos
                    + ", qhijos = " + qhijos + ", relaccion = '" + prefs.getRelacion()+ "', interes = '" + prefs.getInteres()+ "' WHERE id_usuario=" +prefs.getId()+"";
            System.out.println(sentencia);

            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                exito = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
    
    public synchronized static boolean modificarPerfil(Perfil p) throws FileNotFoundException {
        boolean exito = false;
        Blob imagen;
        try {
            Blob b;
             FileInputStream fis;
            if(p.getFoto()!=null){
                byte[] bArray = readFileToByteArray(p.getFoto());
                 imagen=new SerialBlob(bArray);
                
                
            }
            else{
                imagen=null;
            }
            
            
            System.out.println(p.getId()+" "+p.getLocalidad());
            
            sentencia = "UPDATE " + ConstantesBD.TPerfil + " SET usuario = '" + p.getName()+ "', foto = '"
                    + imagen + "', edad = " + p.getEdad() + ", localidad = '" + p.getLocalidad()
                    + "', sexo= '"+p.getSexo()+"' WHERE id_usuario=" +p.getId()+"";
            System.out.println(sentencia);

            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                exito = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }
    
    private static byte[] readFileToByteArray(File file){
    FileInputStream fis = null;
    // Creating a byte array using the length of the file
    // file.length returns long which is cast to int
    byte[] bArray = new byte[(int) file.length()];
    try{
      fis = new FileInputStream(file);
      fis.read(bArray);
      fis.close();                   
    }catch(IOException ioExp){
      ioExp.printStackTrace();
    }
    return bArray;
  }

    /**
     *
     * @param idUser
     * @return
     */
    public synchronized static Preferencias obtenerPreferencias(int id) {
        Preferencias prefs = null;
        sentencia = "SELECT * FROM " + ConstantesBD.TPref + " WHERE id_usuario = " + id + "";

        try {
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                prefs = new Preferencias();
                prefs.setId(Conj_Registros.getInt("id_usuario"));
                prefs.setArte(Conj_Registros.getInt("arte"));
                prefs.setDeporte(Conj_Registros.getInt("deporte"));
                prefs.setPolitica(Conj_Registros.getInt("politica"));
                if(Conj_Registros.getInt("thijos")==1){
                    prefs.settHijos(true);
                }
                else{
                   prefs.settHijos(false); 
                }
                
                if(Conj_Registros.getInt("qhijos")==1){
                    prefs.setqHijos(true);
                }
                else{
                   prefs.setqHijos(false); 
                }
                
                
                prefs.setRelacion(Conj_Registros.getString("relaccion"));
                prefs.setInteres(Conj_Registros.getString("interes"));
                
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return prefs;
    }
    
    public synchronized static Perfil obtenerPerfil(int id) {
        Perfil p = null;
        Blob blob;
        sentencia = "SELECT * FROM " + ConstantesBD.TPerfil + " WHERE id_usuario = " + id + "";

        try {
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                p = new Perfil();
                p.setId(id);
                blob=Conj_Registros.getBlob("foto");
                p.setEdad(Conj_Registros.getInt("edad"));
                p.setName(Conj_Registros.getString("usuario"));
                p.setLocalidad(Conj_Registros.getString("Localidad"));
                p.setSexo(Conj_Registros.getString("sexo"));
                if(blob==null){
                    p.setImagen(null);
                }
                else{
                     p.setImagen(blob.getBytes(1, (int) blob.length()));
                     System.out.println(p.getImagen());
                }
               
                
                
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return p;
    }

    /**
     *
     * @param email
     * @param pwd
     * @return
     */
    public synchronized static Usuario comprobarLogin(String email, String pwd) {

        Usuario u = null;
        sentencia = "SELECT * FROM " + ConstantesBD.TUsuarios + " WHERE ( email LIKE '" + email
                +"' or usuario LIKE '"+email+ "' ) and password LIKE '" + pwd + "'";

        try {
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                u = new Usuario();
                u.setId(Conj_Registros.getInt("id_usuario"));
                u.setName(Conj_Registros.getString("usuario"));
                u.setEmail(Conj_Registros.getString("email"));
                u.setPwd(Conj_Registros.getString("password"));
                u.setActivado(Conj_Registros.getBoolean("activado"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return u;

    }
    
    /**
     *
     * @param email
     * @param pwd
     * @return
     */
    public synchronized static int obtenerIdUsuario(Usuario u) {

        int id=0;
        sentencia = "SELECT id_usuario FROM " + ConstantesBD.TUsuarios + " WHERE ( usuario LIKE '"+u.getName()+ "' ) and password LIKE '" + u.getPwd() + "'";

        try {
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                
                id=Conj_Registros.getInt("id_usuario");
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConexionBD.class.getName()).log(Level.SEVERE, null, ex);
        }

        return id;

    }


    /**
     *
     * @param u
     * @return
     */
    public synchronized static boolean isActivatedUser(Usuario u) {
        boolean activado = false;
        sentencia = "SELECT activado FROM " + ConstantesBD.TUsuarios + " where id_usuario = '" + u.getId() + "'";

        try {
            System.out.println( u.getId());
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                int act;
                act = Conj_Registros.getInt(1);
                if(act==0){
                    
                    activado=false;
                }
                else{
                    activado=true;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        
        return activado;
    }
    
    public synchronized static ArrayList<Usuario> obtenerUsuarios(){
        
        ArrayList<Usuario> listaUsers =new ArrayList<Usuario>();
        
        try {
            sentencia = "SELECT * FROM " + ConstantesBD.TUsuarios +"";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                Usuario u = new Usuario();
                
                u.setId(Conj_Registros.getInt("id_usuario"));
                u.setName(Conj_Registros.getString("usuario"));
                u.setEmail(Conj_Registros.getString("email"));
                int act=Conj_Registros.getInt("activado");
                boolean activ;
                if(act==0){
                    activ=false;
                }
                else{
                    activ=true;
                }
                u.setActivado(activ);
                System.out.println(u.getName());
                //String rol = selectTypeUser(u.getId());
                //u.setRol(rol);
                //System.out.println(u.getRol());
                listaUsers.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return listaUsers;
        
    }

    /**
     *
     * @param id
     * @return
     */
    private static int selectIdRol(int id) {
        int idRol =0 ;
        try {
            sentencia = "SELECT id_rol FROM " + ConstantesBD.TUsuarios_Roles + " WHERE id_usuario =" + id + "";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                idRol = Conj_Registros.getInt("id_rol");
                System.out.println(idRol);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return idRol;
    }

    /**
     *
     * @param idUser
     * @return
     */
    public synchronized static String selectTypeUser(int idUsuario) {

        String tipoUser = "";
        int idRol = selectIdRol(idUsuario);

        try {
            sentencia = "SELECT roll FROM " + ConstantesBD.TRoles + " WHERE id_rol = " + idRol + "";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                tipoUser = Conj_Registros.getString("roll");
                System.out.println(tipoUser);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tipoUser;
    }
    
   /* public synchronized static ArrayList<Usuario> obtenerUsuarios(String id){
        
        ArrayList<Usuario> listaUsers = null;
        
        try {
            sentencia = "SELECT * FROM " + ConstantesBD.TUsuarios + " WHERE id != '" + id + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                Usuario u = new Usuario();
                u.setId(Conj_Registros.getInt("id"));
                u.setName(Conj_Registros.getString("nombre"));
                u.setEmail(Conj_Registros.getString("email"));
                u.setPwd(Conj_Registros.getString("password"));
                u.setActivado(Conj_Registros.getBoolean("activado"));
                String rol = selectTypeUser(u.getId());
                u.setRol(rol);
                listaUsers.add(u);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        
        return listaUsers;
        
    }*/
}
