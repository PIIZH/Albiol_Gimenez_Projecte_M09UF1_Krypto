package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Symetric;

import android.util.Base64;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import static android.util.Base64.DEFAULT;
import static android.util.Base64.NO_WRAP;

/**
 * Created by gerard on 25/04/17.
 */

public class SymmetricUtil {

    private static final String KEYMODE = "DES";
    private Cipher cripto;
    private SecretKey myKey;
    private byte[] textEncrypted;
    private byte[] textDecrypted;


    public SymmetricUtil() {

        try {
            cripto = Cipher.getInstance("DES/ECB/PKCS5Padding");
        } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
            System.out.println(e.getMessage());
        }

    }

    /**
     * Generate a private key for se session
     *
     * @return  {SecretKey} || null
     */
    public void makeKey() {

        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(KEYMODE);
            myKey = keyGen.generateKey();
        }
        catch(NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

    }

    public String encrypt(String message) {

        try {
            cripto.init(Cipher.ENCRYPT_MODE, myKey);
            textEncrypted = cripto.doFinal(message.getBytes());
            return Base64.encodeToString(textEncrypted, NO_WRAP);
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "encrypt ERROR";

    }

    public String decrypt(String msgEncrypt) {
        try {
            cripto.init(Cipher.DECRYPT_MODE, myKey);
            textDecrypted = cripto.doFinal(Base64.decode(msgEncrypt, NO_WRAP));
            return new String(textDecrypted);
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "decrypt ERROR";

    }

}
