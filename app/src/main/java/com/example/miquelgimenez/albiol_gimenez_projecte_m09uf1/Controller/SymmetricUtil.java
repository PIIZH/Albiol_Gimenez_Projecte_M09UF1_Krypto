package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

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
            this.myKey = keyGen.generateKey();
        }
        catch(NoSuchAlgorithmException e) {
            System.out.println(e.getMessage());
        }

    }

    private byte[] encrypt(String message) {

        try {
            cripto.init(Cipher.ENCRYPT_MODE, this.myKey);
            this.textEncrypted = cripto.doFinal(message.getBytes());
            System.out.println("Text encriptat: " + this.textEncrypted);
            return this.textEncrypted;
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    private byte[] decrypt(String msgEncrypt) {

        try {
            cripto.init(Cipher.DECRYPT_MODE, this.myKey);
            this.textDecrypted = cripto.doFinal(msgEncrypt.getBytes());
            System.out.println("Text desencriptat: " + new String(this.textDecrypted));
            return this.textDecrypted;
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }
}
