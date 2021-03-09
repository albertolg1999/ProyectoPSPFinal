/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import static BaseDatos.ConexionBD.abrirConexion;
import static BaseDatos.ConexionBD.cerrarConexion;
import clases.ConstantesBD;
import clases.ConstantesRoles;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author disen
 */
public class RolesBD {
    private static String sentencia;

    public static boolean ascUser(int id) {
        boolean exito = false;
        Connection conexion = abrirConexion();

        if (conexion != null) {
            try {
                try (Statement st = (Statement) conexion.createStatement()) {
                    sentencia = "UPDATE " + ConstantesBD.TUsuarios_Roles + " SET idRol = " + ConstantesRoles.ROL_ID_ADMIN + " WHERE idUser = '" + id + "'";

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

    public static boolean descUser(int id) {
        boolean exito = false;
        Connection conexion = abrirConexion();

        if (conexion != null) {
            try {
                try (Statement st = (Statement) conexion.createStatement()) {
                    sentencia = "UPDATE " + ConstantesBD.TUsuarios_Roles + " SET idRol = " + ConstantesRoles.ROL_ID_USER + " WHERE idUser = '" + id + "'";

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
}
