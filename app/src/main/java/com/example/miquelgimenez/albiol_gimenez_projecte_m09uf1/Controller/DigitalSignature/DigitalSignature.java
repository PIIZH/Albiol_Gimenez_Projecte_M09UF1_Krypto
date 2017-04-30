package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.DigitalSignature;


import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;

public class DigitalSignature{

    public byte[] signData(byte[] data, PrivateKey priv) {

        byte[] signature = null;

        try{

            Signature signer = Signature.getInstance("SHA1withRSA");
            signer.initSign(priv);
            signer.update(data);
            signature = signer.sign();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return signature;
    }

}
