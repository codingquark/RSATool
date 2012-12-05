package rsa.user;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import rsa.util.Blowfish;
import rsa.util.Rsa;

public class RSATool {

	/**
	 * @param args
	 * @throws NoSuchPaddingException 
	 * @throws NoSuchAlgorithmException 
	 * @throws InvalidKeyException 
	 * @throws InvalidKeySpecException 
	 * @throws BadPaddingException 
	 * @throws IllegalBlockSizeException 
	 */
	public static void main(String[] args) throws InvalidKeyException, NoSuchAlgorithmException,
						NoSuchPaddingException, InvalidKeySpecException, IllegalBlockSizeException,
						BadPaddingException, FileNotFoundException {
		
		String genArg = "-gen";
		String encArg = "-enc";
		String decArg = "-dec";
		
		if(args[0].contains(genArg)) {
			gen();
		}
		
		else if(args[0].contains(encArg)) {
			
			if(args.length < 2) {
				System.out.println("Please input the file name.");
			}
			
			enc(args);		
			
		}
		
		else if(args[0].contains(decArg)) {
			
			if(args.length < 2) {
				System.out.println("Please input the file name.");
			}
			
			dec(args);
		}
		
		else {
			System.out.println("Incorrect arguement");
			System.exit(0);
		}

	}
	
	private static void dec(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		
		int flag = Blowfish.decrypt();
		BigInteger decrypted = null;
		
		String inputFile = args[1];
		byte[] fileContent = null;
		
		try {
			File originalFile = new File(inputFile);
		
			FileInputStream in = new FileInputStream(originalFile);
			
			if(!originalFile.exists()) {
				System.out.println("The key does not exist.");
				System.exit(0);
			}
			fileContent = new byte[(int) originalFile.length()];
			in.read(fileContent);
			in.close();
			
		} catch(IOException e) {
			System.out.println("Could not read the file");
			System.exit(0);
		}
		
		Rsa r = new Rsa();
		
		if(flag == 0)
			decrypted = r.decryptWithPublic(fileContent);
		else
			decrypted = r.decryptWithPrivate(fileContent);
		
		try {
			inputFile = inputFile.replace(".enc", "");
			
			
			File fileToWrite = new File(inputFile);
			FileOutputStream out = new FileOutputStream(fileToWrite);
			
			fileContent = decrypted.toByteArray();
			
			out.write(fileContent);
			out.close();
			
		} catch(IOException e) {
			System.out.println("Could not create the output file.");
			System.exit(flag);
		}
		
		System.out.println("File generated.");
		System.exit(0);
		
	}

	public static void gen() throws InvalidKeyException, NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, BadPaddingException{
		String passPhrase = null;
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		
			System.out.println("Please enter a passphrase: ");
			passPhrase = reader.readLine().toString();
			
		} catch (IOException e) {
			System.out.println("Could not read the pass phrase.");
			e.printStackTrace();
			System.exit(0);
		}
		
		Blowfish.encrypt(passPhrase);
		System.out.println("Keys created!");
		System.exit(0);
	}
	
	public static void enc(String[] args) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException {
		int flag = Blowfish.decrypt();
		BigInteger encrypted = null;
		
		String inputFile = args[1];
		byte[] fileContent = null;
		
		try {
			File originalFile = new File(inputFile);
		
			FileInputStream in = new FileInputStream(originalFile);
			
			if(!originalFile.exists()) {
				System.out.println("The key does not exist.");
				System.exit(0);
			}
			fileContent = new byte[(int) originalFile.length()];
			in.read(fileContent);
			in.close();
			
		} catch(IOException e) {
			System.out.println("Could not read the file");
			System.exit(0);
		}
		
		Rsa r = new Rsa();
		
		if(flag == 0)
			encrypted = r.encryptWithPublic(fileContent);
		else
			encrypted = r.encryptWithPrivate(fileContent);
		
		try {
			File fileToWrite = new File(inputFile + ".enc");
			FileOutputStream out = new FileOutputStream(fileToWrite);
			
			fileContent = encrypted.toByteArray();
			
			out.write(fileContent);
			out.close();
			
		} catch(IOException e) {
			System.out.println("Could not create the output file.");
			System.exit(flag);
		}
		
		System.out.println("Secure file generated.");
		System.exit(0);
	}

}
