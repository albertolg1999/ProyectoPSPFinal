/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clienteproyectofinal;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author disen
 */
public class ClienteProyectoFinal {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws NoSuchAlgorithmException, IOException, ClassNotFoundException {
        IniciarSesion i=new IniciarSesion();
        i.show();
    }
    
}
