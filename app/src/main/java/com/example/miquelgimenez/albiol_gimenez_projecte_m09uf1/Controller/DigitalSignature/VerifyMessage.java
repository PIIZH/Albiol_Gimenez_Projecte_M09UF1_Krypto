package com.example.miquelgimenez.albiol_gimenez_projecte_m09uf1.Controller.DigitalSignature;

import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.X509EncodedKeySpec;
import java.util.List;

//https://www.mkyong.com/java/java-digital-signatures-example/

public class VerifyMessage {
	private List<byte[]> list;

	@SuppressWarnings("unchecked")
	//The constructor of VerifyMessage class retrieves the byte arrays from the File and prints the message only if the signature is verified.
	public VerifyMessage(String data, String keyFile) throws Exception {
		ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(data.getBytes()));
	    this.list = (List<byte[]>) in.readObject();
	    in.close();
		System.out.println(verifySignature(list.get(0), list.get(1), keyFile) ? "VERIFIED MESSAGE" + "\n----------------\n" + new String(list.get(0)) : "Could not verify the signature.");
	}
	
	//Method for signature verification that initializes with the Public Key, updates the data to be verified and then verifies them using the signature
	private boolean verifySignature(byte[] data, byte[] signature, String keyFile) throws Exception {
		Signature sig = Signature.getInstance("SHA1withRSA");
		sig.initVerify(getPublic(keyFile));
		sig.update(data);
		
		return sig.verify(signature);
	}

	//Method to retrieve the Public Key from a file
	public PublicKey getPublic(String data) throws Exception {
		byte[] keyBytes = data.getBytes();
		X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);
		KeyFactory kf = KeyFactory.getInstance("RSA");
		return kf.generatePublic(spec);
	}

	public static void main(String[] args) throws Exception{
		new VerifyMessage("MyData/SignedData.txt", "MyKeys/publicKey");
	}
}
