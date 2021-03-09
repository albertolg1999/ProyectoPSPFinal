/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import clases.ConstantesBD;
import clases.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author disen
 */
public class ConexionBD {
    private static Connection conex;
    private static Statement Sentencia_SQL;
    private static ResultSet Conj_Registros;
    private static String sentencia;

    static {
        try {
            try {
                String controlador = "com.mysql.jdbc.Driver";
                Class.forName(controlador);
                String URL_BD = "jdbc:mysql://localhost/" + ConstantesBD.BD;
                conex = (Connection) java.sql.DriverManager.getConnection(URL_BD, ConstantesBD.USUARIO, ConstantesBD.PASSWD);
                Sentencia_SQL = (Statement) conex.createStatement();
                System.out.println("Conexión realizada con éxito");
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    /**
     *
     * @param u
     * @return
     */
    public synchronized static boolean registrarUsuario(Usuario u) throws SQLException {

        boolean exito = false;

        if (!usuarioExists(u.getEmail())) {
            System.out.println(u.getPwd().toString());
            sentencia = "INSERT INTO " + ConstantesBD.TUsuarios + " ( Usuario, Email, Password) "
                    + "values('" + u.getName() + "','" + u.getEmail()
                    + "','" + u.getPwd() + "')";
            
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                System.out.println("USUARIO REGISTRADO");
                exito = true;
            }
        }
        return exito;
    }

    
    /**
     *
     * @param prefs
     */
    /*public synchronized static boolean insertPreferences(Preferences prefs) {
        boolean exito = false;
        try {
            sentencia = "INSERT INTO " + ConstantesBD.TABLAPREFES + " (idUser, tiporelacion, interes, arte, deporte, politica, thijos, qhijos) "
                    + "values('" + prefs.getIdUser() + "','" + prefs.getRelacion() + "','" + prefs.getInteres()+ "','" + prefs.getArte() + "','" + prefs.getDeporte()
                    + "','" + prefs.getPolitica() + "'," + prefs.istHijos() + "," + prefs.isqHijos() + ")";
            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                exito = true;
                System.out.println("PREFERENCIA REGISTRADA");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    public synchronized static boolean updatePreferences(Preferences prefs) {
        boolean exito = false;

        try {
            sentencia = "UPDATE " + ConstantesBD.TABLAPREFES + " SET arte = '" + prefs.getArte() + "' SET deporte = '"
                    + prefs.getDeporte() + "' SET politica = '" + prefs.getPolitica() + "' SET thijos = '" + prefs.istHijos()
                    + "' SET qhijos = '" + prefs.isqHijos() + "' SET tiporelacion = '" + prefs.getRelacion()+ "' SET interes = '" + prefs.getInteres()+ "'" ;

            if (Sentencia_SQL.executeUpdate(sentencia) == 1) {
                exito = true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return exito;
    }

    /**
     *
     * @param idUser
     * @return
     */
    /*public synchronized static Preferences obtenerPreferences(String idUser) {
        Preferences prefs = null;
        sentencia = "SELECT * FROM " + ConstantesBD.TABLAPREFES + " WHERE idUser like '" + idUser + "'";

        try {
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);

            while (Conj_Registros.next()) {
                prefs = new Preferences();
                prefs.setIdUser(Conj_Registros.getString("idUser"));
                prefs.setArte(Conj_Registros.getInt("arte"));
                prefs.setDeporte(Conj_Registros.getInt("deporte"));
                prefs.setPolitica(Conj_Registros.getInt("politica"));
                prefs.settHijos(Conj_Registros.getBoolean("thijos"));
                prefs.setqHijos(Conj_Registros.getBoolean("qhijos"));
                prefs.setRelacion(Conj_Registros.getString("tiporelacion"));
                prefs.setInteres(Conj_Registros.getString("interes"));
                
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return prefs;
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

    /**
     *
     * @param id
     * @return
     */
   /* private static String selectIdRol(String id) {
        String idRol = "";
        try {
            sentencia = "SELECT idRol FROM " + ConstantesBD.TABLAROLES_USERS + " WHERE idUser LIKE '" + id + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                idRol = Conj_Registros.getString(1);
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
    /*public synchronized static String selectTypeUser(String idUser) {

        String tipoUser = "";
        String idRol = selectIdRol(idUser);

        try {
            sentencia = "SELECT name FROM " + ConstantesBD.TABLAROLES + " WHERE id = '" + idRol + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                tipoUser = Conj_Registros.getString(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return tipoUser;
    }
    
    public synchronized static ArrayList<Usuario> obtenerUsuarios(String id){
        
        ArrayList<Usuario> listaUsers = null;
        
        try {
            sentencia = "SELECT * FROM " + ConstantesBD.TABLAUSUARIOS + " WHERE id != '" + id + "'";
            Conj_Registros = Sentencia_SQL.executeQuery(sentencia);
            while (Conj_Registros.next()) {
                Usuario u = new Usuario();
                u.setId(Conj_Registros.getString("id"));
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
