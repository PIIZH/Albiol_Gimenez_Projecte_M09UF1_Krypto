package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Asymetric;

import android.util.Base64;
import android.util.Base64DataException;

import com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Hash;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;


public class AsymetricUtil implements Hash{
    private static final String KEYMODE = "RSA/ECB/PKCS1Padding";
    private final static String PASSW_HASH = "SHA-1";
    private KeyPairGenerator kpg;
    private KeyPair kp;
    private PublicKey publicKey;
    private PublicKey publicKeyOther;
    private PrivateKey privateKey;
    private byte [] encryptedBytes,decryptedBytes;
    private Cipher cipher;

    public AsymetricUtil(){
        try{
            cipher = Cipher.getInstance(KEYMODE);
            generateKeys();
        }catch( NoSuchPaddingException| NoSuchAlgorithmException e){
            e.printStackTrace();
        }
    }

    public void setPublicKey(String key) {

        byte[] data = Base64.decode(key, Base64.DEFAULT);
        X509EncodedKeySpec spec = new X509EncodedKeySpec(data);
        KeyFactory fact = null;

        try {
            fact = KeyFactory.getInstance("RSA");
            this.publicKeyOther = fact.generatePublic(spec);

        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }

    }

    public String RSAEncrypt(final String plain) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {

        cipher.init(Cipher.ENCRYPT_MODE, publicKeyOther);
        encryptedBytes = cipher.doFinal(plain.getBytes("Utf-8"));
        return Base64.encodeToString(encryptedBytes,Base64.DEFAULT);
    }


    public String RSADecrypt(final String encryptedStr) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        decryptedBytes = cipher.doFinal(Base64.decode(encryptedStr.getBytes("UTf-8"), Base64.DEFAULT));
        return new String(decryptedBytes);
    }

    public void generateKeys() throws NoSuchAlgorithmException {
        kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(1024);
        kp = kpg.genKeyPair();
        publicKey = kp.getPublic();
        privateKey = kp.getPrivate();
    }


    /**
     * Convert publicKey to String
     *
     * @return  {String}
     */
    public String getKey(){
        return Base64.encodeToString(publicKey.getEncoded(), Base64.DEFAULT);
    }

    @Override
    public SecretKey hashBuildKey(String pass) {
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