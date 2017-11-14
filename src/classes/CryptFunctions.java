package classes;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class CryptFunctions {
	
	private static final String encryptionAlgorithm = "SHA-256";
	private static final Random RANDOM = new SecureRandom();
	

	public static byte[] hash(String text){
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance(encryptionAlgorithm);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return digest.digest(text.getBytes(StandardCharsets.UTF_8));
	}
	
	public String decryptText(byte[] encryptedText, SecretKey key, String algorithm){
        try {
        	Cipher cipher;
			cipher = Cipher.getInstance(algorithm);
			
			cipher.init(Cipher.DECRYPT_MODE, key);
			
            byte[] textDecrypted = cipher.doFinal(encryptedText);
            
            return new String(textDecrypted);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        return "Could not decrypt that shit";
	}
	

	public static SecretKey generateSymmetricKey(String algorithm){
		SecretKey key = null;
		
		try {
			
			KeyGenerator keyGen = KeyGenerator.getInstance(algorithm);
			key = keyGen.generateKey();
			
			
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Error!\n'" + algorithm + "' is not a valid algorithm");
		} 
		
		return key;
	}
	
	
	public static String bytesToString(byte[] bytes) {
	    BigInteger bi = new BigInteger(1, bytes);
	    return String.format("%0" + (bytes.length << 1) + "X", bi);
	}
	
	public static byte[] generateSalt() {
	    byte[] salt = new byte[16];
	    RANDOM.nextBytes(salt);
	    return salt;
	  }
}
