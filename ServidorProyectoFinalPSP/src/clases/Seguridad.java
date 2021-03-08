package clases;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;

/**
 *
 * @author disen
 */
public class Seguridad {

    public static Object[] generarClaves() throws NoSuchAlgorithmException {

        Object[] claves = new Object[2];
        KeyPairGenerator KeyGen = KeyPairGenerator.getInstance("RSA");
        SecureRandom numero = SecureRandom.getInstance("SHA1PRNG");
        KeyGen.initialize(4096, numero);
        KeyPair par = KeyGen.generateKeyPair();
        PrivateKey clavepriv = par.getPrivate();
        PublicKey clavepubl = par.getPublic();
        claves[0] = clavepriv;
        claves[1] = clavepubl;

        return claves;
    }
    
    public static SealedObject cifrar(PublicKey claveAjena, Object o) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, IllegalBlockSizeException {

        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.ENCRYPT_MODE, claveAjena);

        return new SealedObject((Serializable) o, c);
    }

    public static Object descifrar(PrivateKey clavePropia, SealedObject so) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IOException, ClassNotFoundException, IllegalBlockSizeException, BadPaddingException {

        Cipher c = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        c.init(Cipher.DECRYPT_MODE, clavePropia);
        Object o = so.getObject(c);

        return o;
    }
    
    public static byte[] resumirPwd(String pwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA1");
        byte datos[] = pwd.getBytes();
        md.update(datos);
        byte[] pwdRes = md.digest();
        System.out.println(pwdRes);
        return pwdRes;
    }
}
