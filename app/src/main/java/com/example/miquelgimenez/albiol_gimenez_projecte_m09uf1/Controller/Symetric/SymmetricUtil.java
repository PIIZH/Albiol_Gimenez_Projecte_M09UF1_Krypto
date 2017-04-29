package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Symetric;

import android.util.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Array;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import static android.util.Base64.DEFAULT;
import static android.util.Base64.NO_WRAP;

/**
 * Created by gerard on 25/04/17.
 */

public class SymmetricUtil {

    private static final String KEYMODE = "DES";
    private final static String PASSW_HASH = "SHA-1";
    private Cipher cripto;
    private SecretKey myKey;
    private byte[] textEncrypted;
    private byte[] textDecrypted;


    public SymmetricUtil() {

        try {
            cripto = Cipher.getInstance(KEYMODE);
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
        //KeyGenerator keyGen = KeyGenerator.getInstance(KEYMODE);
        //myKey = keyGen.generateKey();
        myKey = hashBuildKey("pepe");
    }

    //TODO: prueba
    public String getMyKey() {
        return Base64.encodeToString(myKey.getEncoded(), NO_WRAP);
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

//    public String decrypt(String msgEncrypt) {
    public String decrypt(String msgEncrypt, String key) {

        // decode the base64 encoded string
        byte[] decodedKey = Base64.decode(key, NO_WRAP);
        // rebuild key using SecretKeySpec
        SecretKey originalKey = new SecretKeySpec(decodedKey, 0, decodedKey.length, "DES");

        if(originalKey.equals(myKey)) {
            try {
                cripto.init(Cipher.DECRYPT_MODE, myKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                cripto.init(Cipher.DECRYPT_MODE, originalKey);
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
        }


        try {
//            cripto.init(Cipher.DECRYPT_MODE, myKey);
            textDecrypted = cripto.doFinal(Base64.decode(msgEncrypt, NO_WRAP));
            return new String(textDecrypted);
        }
        catch(BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "decrypt ERROR";

    }


    private SecretKey hashBuildKey(String pass){
        SecretKey secretKey = null;
        try {
            MessageDigest digestiveFontaneda = MessageDigest.getInstance(PASSW_HASH);
            digestiveFontaneda.update(pass.getBytes("UTF-8"));
            byte[] hash = digestiveFontaneda.digest();
            byte[] key = Arrays.copyOf(hash,8);
            secretKey = new SecretKeySpec(key,KEYMODE);
        }catch (NoSuchAlgorithmException|UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return secretKey;
    }
}
