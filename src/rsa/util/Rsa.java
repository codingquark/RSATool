package rsa.util;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class Rsa {
	
	public static RSAPrivateKey privateKey;
	public static RSAPublicKey publicKey;
	
	public void generateKeys() throws NoSuchAlgorithmException {
		
		SecureRandom random = new SecureRandom();
		random.setSeed(20);
		
		KeyPairGenerator rsaGenerator = KeyPairGenerator.getInstance("RSA");
		rsaGenerator.initialize(1024, random);
		KeyPair rsaPair = rsaGenerator.generateKeyPair();
		
		privateKey = (RSAPrivateKey) rsaPair.getPrivate();
		publicKey = (RSAPublicKey) rsaPair.getPublic();
		
		System.out.println("Keys generated...");
	}
	
	public void setPrivateKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(key);
		
		privateKey = (RSAPrivateKey) kf.generatePrivate(ks);
	}
	
	public void setPublicKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		KeyFactory kf = KeyFactory.getInstance("RSA");
		X509EncodedKeySpec ks = new X509EncodedKeySpec(key);
		
		publicKey = (RSAPublicKey) kf.generatePublic(ks);
	}
	
	public BigInteger encryptWithPublic(byte[] fileContent) {

		BigInteger message = new BigInteger(fileContent);
		return message.modPow(publicKey.getPublicExponent(), publicKey.getModulus());
	}

	public BigInteger encryptWithPrivate(byte[] fileContent) {
		
		BigInteger message = new BigInteger(fileContent);
		return message.modPow(privateKey.getPrivateExponent(), privateKey.getModulus());
	}

	public BigInteger decryptWithPublic(byte[] fileContent) {
		BigInteger message = new BigInteger(fileContent);
		return message.modPow(publicKey.getPublicExponent(), publicKey.getModulus());
	}

	public BigInteger decryptWithPrivate(byte[] fileContent) {
		BigInteger message = new BigInteger(fileContent);
		return message.modPow(privateKey.getPrivateExponent(), privateKey.getModulus());
	}

}
