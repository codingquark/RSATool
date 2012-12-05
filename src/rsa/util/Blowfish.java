package rsa.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Blowfish {

	public static void encrypt(String passPhrase) throws NoSuchAlgorithmException,
	NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		byte[] pass = passPhrase.getBytes();
		
		byte[] md5data = md5Digest.digest(pass);
		SecretKey blowKey = new SecretKeySpec(md5data, 0, md5data.length, "Blowfish");
				
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, blowKey);
		
		Rsa rsa = new Rsa();
		rsa.generateKeys();
		
		byte[] pub2 = Rsa.publicKey.getEncoded();
		byte[] pub = cipher.doFinal(pub2);
		byte[] pri2 = Rsa.privateKey.getEncoded();
		byte[] pri = cipher.doFinal(pri2);
		
		try {
			
			File homeDir = new File(System.getProperty("user.home"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please choose a key ID: ");
			String ID = reader.readLine().toString();
			
			File fileToWrite = new File(homeDir, "RSApublickey" + ID + ".rsa");
			FileOutputStream fos = new FileOutputStream(fileToWrite);
			
			if(!fileToWrite.exists()) {
				fileToWrite.createNewFile();
			}
			
			fos.write(pub);
			fos.close();
			
			fileToWrite = new File(homeDir, "RSAprivatekey" + ID + ".rsa");
			
				fos = new FileOutputStream(fileToWrite);
			
			
			if(!fileToWrite.exists()) {
				fileToWrite.createNewFile();
			}
			
			fos.write(pri);
			fos.close();
			return;
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file...");
			
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Error while reading the keys...");
			
			System.exit(0);
		}
		
	}

	public static int decrypt() throws NoSuchAlgorithmException, 
							InvalidKeyException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		
		String passPhrase = null;
		byte fileContent[] = null;
		int flag = 1; 
				
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

			System.out.println("Please enter a passphrase: ");
			passPhrase = reader.readLine().toString();
			
		} catch (IOException e) {
			System.out.println("Could not read the pass phrase.");
			e.printStackTrace();
			System.exit(0);
		}
		
		MessageDigest md5Digest = MessageDigest.getInstance("MD5");
		byte[] pass = passPhrase.getBytes();
		
		byte[] md5data = md5Digest.digest(pass);
		SecretKey blowKey = new SecretKeySpec(md5data, 0, md5data.length, "Blowfish");
				
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, blowKey);
		
		try {
			
			File homeDir = new File(System.getProperty("user.home"));
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			System.out.println("Please choose a key ID: ");
			String ID = reader.readLine().toString();
			
			System.out.println("Public / Private ? : ");
			String fileName = reader.readLine().toString();
			
			if(fileName.contains("public")) flag = 0;
			else flag = 1;
			
			File fileToRead = new File(homeDir, "RSA" + fileName + "key" + ID + ".rsa");
			FileInputStream fis = new FileInputStream(fileToRead);
			
			if(!fileToRead.exists()) {
				System.out.println("The key does not exist.");
				System.exit(0);
			}
			
			fileContent = new byte[(int)fileToRead.length()];
		    fis.read(fileContent);
			fis.close();
			
			byte[] key = cipher.doFinal(fileContent);
			Rsa rsa = new Rsa();
			
			if(flag == 0) rsa.setPublicKey(key);
			else if(flag == 1) rsa.setPrivateKey(key);
			
			return flag;
		} catch (FileNotFoundException e) {
			System.out.println("Could not find the file...");
			
			System.exit(0);
		} catch (IOException e) {
			System.out.println("Error while reading the keys...");
			
			System.exit(0);
		}
		return flag;
		
	}
	
}
