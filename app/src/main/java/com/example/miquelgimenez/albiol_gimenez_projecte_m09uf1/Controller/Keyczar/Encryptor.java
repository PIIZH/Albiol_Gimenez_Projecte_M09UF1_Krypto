package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.Keyczar;

import org.keyczar.Crypter;
import org.keyczar.exceptions.KeyczarException;


public class Encryptor {

    private String textEnc; // text Encrypted
    private String textDec; // text Decrypted
    //TODO: estudiar com fer aixo!!!!
    private static final String PATH = "path/de/les/nostres/keys";
    Crypter cryptObj;

    public Encryptor() {}

    /**
     * encrypt the message to send
     *
     * @param   {String}    text
     * @return  {String or null}
     */

    public String encryptText(String text) {

        try {
            cryptObj = new Crypter(PATH);
            this.textEnc = cryptObj.encrypt(text);
            return this.textEnc;
        }
        catch(KeyczarException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

    /**
     * decrypt the message received
     *
     * @param   {String}    text
     * @return  {String or null}
     */
    public String decryptText(String text) {

        try {
            cryptObj = new Crypter(PATH);
            this.textDec = cryptObj.decrypt(text);
            return this.textDec;
        }
        catch(KeyczarException e) {
            System.out.println(e.getMessage());
        }

        return null;

    }

}
