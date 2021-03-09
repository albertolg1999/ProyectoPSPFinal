/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.ConexionBD.abrirConexion;
import static BaseDatos.ConexionBD.cerrarConexion;
import clases.ConstantesBD;
import clases.Usuario;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author disen
 */
public class UsuariosBD {
     private static String sentencia;

    public static boolean activarUser(int id) {
        boolean exito = false;
        Connection conexion = abrirConexion();

        if (conexion != null) {
            try {
                try (Statement st = (Statement) conexion.createStatement()) {
                    sentencia = "UPDATE " + ConstantesBD.TUsuarios + " SET activado = true WHERE id = '" + id + "'";

                    if (st.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                }

            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return exito;
    }

    public static boolean deleteUser(int id) {
        boolean exito = false;
        Connection conexion = abrirConexion();

        if (conexion != null) {
            try {
                try (Statement st = (Statement) conexion.createStatement()) {
                    sentencia = "DELETE FROM " + ConstantesBD.TUsuarios_Roles + " WHERE id_usuario = '" + id + "'";

                    if (st.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                    
                    sentencia = "DELETE FROM " + ConstantesBD.TPerfil + " WHERE id_usuario = '" + id + "'";

                    if (st.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                    
                    sentencia = "DELETE FROM " + ConstantesBD.TUsuarios + " WHERE id_usuario = '" + id + "'";

                    if (st.executeUpdate(sentencia) == 1) {
                        exito = true;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }
        }
        return exito;
    }
    
    public synchronized static ArrayList<Usuario> obtenerUsuarios(int id) {

        ArrayList<Usuario> listaUsers = new ArrayList<Usuario>();

        Connection conexion = abrirConexion();
        if (conexion != null) {
            try {
                try (Statement st = (Statement) conexion.createStatement()) {
                    sentencia = "SELECT * FROM " + ConstantesBD.TUsuarios + " WHERE id != '" + id + "'";
                    ResultSet rs = st.executeQuery(sentencia);
                    while (rs.next()) {
                        Usuario u = new Usuario();
                        u.setId(rs.getInt("id"));
                        u.setName(rs.getString("nombre"));
                        u.setEmail(rs.getString("email"));
                        u.setPwd(rs.getString("password"));
                        u.setActivado(rs.getBoolean("activado"));
                        //String rol = selectTypeUser(u.getId());
                        //u.setRol(rol);
                        System.out.println(u);
                        listaUsers.add(u);
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                cerrarConexion(conexion);
            }

        }
        return listaUsers;
    }
}
