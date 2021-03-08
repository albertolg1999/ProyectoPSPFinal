/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;

/**
 *
 * @author disen
 */
public class Util {

    public Util() {
    }
    
    
    public byte[] resumir(String texto) throws NoSuchAlgorithmException{
        byte[] resumen1=null;
        MessageDigest md = MessageDigest.getInstance("SHA1");
        //MessageDigest md = MessageDigest.getInstance("MD5");
        
        byte datos[] = texto.getBytes(); //Texto en bytes
        md.update(datos);                //Se introduce el texto en bytes a resumir
        resumen1 = md.digest();
        return resumen1;
    }
    
    public byte[] firmar(PrivateKey clavePrivada, String msg) throws Exception {
        byte[] firma = null;
        Signature dsa = Signature.getInstance("SHA1withDSA");
        dsa.initSign(clavePrivada);
        dsa.update(msg.getBytes());
        firma = dsa.sign(); //Mensaje firmado.
        return firma;
    }

    public boolean verifica(PublicKey clavePublica, String msg, byte[] firma) throws Exception {
        Signature verifica_dsa = Signature.getInstance("SHA1withDSA");
        verifica_dsa.initVerify(clavePublica);

        //msg = "Otra cosa";
        verifica_dsa.update(msg.getBytes());
        return verifica_dsa.verify(firma);
    }

    public byte[] cifrar(SecretKey clave, String msg) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.ENCRYPT_MODE, clave);

        byte[] TextoPlano = msg.getBytes();
        byte[] cifrado = c.doFinal(TextoPlano);
        return cifrado;
    }
    public String desencriptar(byte[] cifrado,SecretKey clave) throws Exception {
        Cipher c = Cipher.getInstance("AES/ECB/PKCS5Padding");
        c.init(Cipher.DECRYPT_MODE, clave);
        byte[] desencriptado=c.doFinal(cifrado);
        return new String(desencriptado);
    }
}
