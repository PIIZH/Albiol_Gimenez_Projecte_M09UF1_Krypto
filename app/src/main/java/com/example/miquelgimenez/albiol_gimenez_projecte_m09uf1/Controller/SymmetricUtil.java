package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller;

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
//            textEncrypted = cripto.doFinal(message.getBytes());
            textEncrypted = cripto.update(message.getBytes());
            textEncrypted = cripto.doFinal();
            return asHex(textEncrypted);
//            return Base64.encodeToString(textEncrypted, NO_WRAP);
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    public String decrypt(String msgEncrypt) {
        try {
//            byte[] a = Base64.decode(msgEncrypt, NO_WRAP);
            cripto.init(Cipher.DECRYPT_MODE, myKey);
//            cripto.update(a);
//            textDecrypted = cripto.doFinal(Base64.decode(msgEncrypt, NO_WRAP));
            textDecrypted = cripto.update(fromHexString(msgEncrypt));
            textDecrypted = cripto.doFinal();
            return new String(textDecrypted);
        }
        catch(InvalidKeyException | BadPaddingException | IllegalBlockSizeException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return "no";

    }

    public static byte[] fromHexString(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4)
                    + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

    public static String asHex(byte buf[]) {
        StringBuffer strbuf = new StringBuffer(buf.length * 2);
        int i;

        for (i = 0; i < buf.length; i++) {
            if (((int) buf[i] & 0xff) < 0x10) {
                strbuf.append("0");
            }

            strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
        }

        return strbuf.toString();
    }
}
