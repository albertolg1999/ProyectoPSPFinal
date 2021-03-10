/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servidorproyectofinalpsp;

import clases.Seguridad;
import hilos.HiloCliente;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 *
 * @author disen
 */
public class ServidorProyectoFinalPSP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException, NoSuchAlgorithmException {
        ServerSocket servidor;
        servidor = new ServerSocket(5000);
        Object[] claves = Seguridad.generarClaves();
        PrivateKey clavePrivPropia = (PrivateKey) claves[0];
        PublicKey clavePubPropia = (PublicKey) claves[1];
        
        
        
        while(true){
            Socket cliente = servidor.accept();
            HiloCliente hc = new HiloCliente(cliente, clavePubPropia, clavePrivPropia);
            hc.start();
        }
    }
    
}
