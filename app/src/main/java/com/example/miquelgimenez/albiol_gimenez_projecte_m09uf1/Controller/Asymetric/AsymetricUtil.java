package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Asymetric;

import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class AsymetricUtil {
    private static final String KEYMODE = "RSA/ECB/PKCS1Padding";
    private KeyPairGenerator kpg;
    private KeyPair kp;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private byte [] encryptedBytes,decryptedBytes;
    private Cipher cipher;

    public AsymetricUtil(){
        try{
            cipher = Cipher.getInstance(KEYMODE);
        }catch( NoSuchPaddingException| NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public String RSAEncrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    //public byte[] RSAEncrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        generateKeys();
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        encryptedBytes = cipher.doFinal(plain.getBytes("Utf-8"));
        return Base64.encodeToString(encryptedBytes,Base64.DEFAULT);
        //return encryptedBytes;
    }


    public String RSADecrypt(final String encryptedStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    //public String RSADecrypt(final byte[] encryptedStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedBytes = cipher.doFinal(Base64.decode(encryptedStr.getBytes("UTf-8"), Base64.DEFAULT));
        //decryptedBytes = cipher.doFinal(encryptedStr);
        return new String(decryptedBytes);
    }

    public void generateKeys() throws NoSuchAlgorithmException {
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }
}