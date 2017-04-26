package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Asymetric;
import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class AsymetricUtil {
    private Cipher cipher;
    private String messageEncrypted;
    private String messageDecrypt;

    public AsymetricUtil() throws NoSuchAlgorithmException, NoSuchPaddingException {
        this.cipher = Cipher.getInstance("RSA");
    }

    public PrivateKey getPrivate(String text) throws Exception {
        byte[] keyBytes = text.getBytes();
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    public PublicKey getPublic(String text) throws Exception {
        byte[] keyBytes = text.getBytes();
        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    //Aixo diria que no fa falta
    /**
    public void encryptFile(byte[] input, String output, PrivateKey key) throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);

        //Pot fallar bastant FORT
        messageEncrypted = String.valueOf(this.cipher.doFinal(input));
    }

    public void decryptFile(byte[] input, String output, PublicKey key) throws IOException, GeneralSecurityException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);

        //Pot fallar bastant FORT
        messageDecrypt = String.valueOf(this.cipher.doFinal(input));
    }
     */

    public String encryptText(String msg, PrivateKey key)
            throws NoSuchAlgorithmException, NoSuchPaddingException,
            UnsupportedEncodingException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        this.cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.encodeToString(msg.getBytes("UTF-8"), Base64.DEFAULT);
    }

    public String decryptText(String msg, PublicKey key)
            throws InvalidKeyException, UnsupportedEncodingException,
            IllegalBlockSizeException, BadPaddingException {
        this.cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(Base64.decode(msg, Base64.DEFAULT));
    }



    public static void main(String[] args) throws Exception {
        AsymetricUtil ac = new AsymetricUtil();
        PrivateKey privateKey = ac.getPrivate("KeyPair/privateKey");
        PublicKey publicKey = ac.getPublic("KeyPair/publicKey");

        String msg = "Cryptography is fun!";
        String encrypted_msg = ac.encryptText(msg, privateKey);
        String decrypted_msg = ac.decryptText(encrypted_msg, publicKey);
        System.out.println("Original Message: " + msg +
                "\nEncrypted Message: " + encrypted_msg
                + "\nDecrypted Message: " + decrypted_msg);
        /**
        if (new File("KeyPair/text.txt").exists()) {
            ac.encryptFile(ac.getFileInBytes(new File("KeyPair/text.txt")),
                    new File("KeyPair/text_encrypted.txt"),privateKey);
            ac.decryptFile(ac.getFileInBytes(new File("KeyPair/text_encrypted.txt")),
                    new File("KeyPair/text_decrypted.txt"), publicKey);
        }*/
    }
}

