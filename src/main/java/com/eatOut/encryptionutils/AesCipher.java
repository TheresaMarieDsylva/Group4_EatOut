package com.eatOut.encryptionutils;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.InputMismatchException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

public class AesCipher implements IEatOutEncryption{

	private static final String ENC_ALGORITHM = "AES/CTR/NoPadding";
	
	private static IEatOutEncryption uniqueInstance=null;
	
	private AesCipher() {
	}
	
	public static IEatOutEncryption instance() {
		if(null==uniqueInstance) {
			return new AesCipher();
		}
		return uniqueInstance;
	}
	
	public  String encrypt(String valueToBeEncryptd, String keyValue)  {
		String encryptedValue = "";
		try {
			SecretKeySpec keySpec = extractKey(keyValue);
			SecureRandom random = new SecureRandom();
			byte[] ivValue = new byte[16];	        
			random.nextBytes(ivValue); 
			IvParameterSpec ivSpec = new IvParameterSpec(ivValue);
			Cipher objCipher = Cipher.getInstance(ENC_ALGORITHM);
			objCipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
			byte[] encryptedTextBytes = objCipher.doFinal(valueToBeEncryptd.getBytes());
			String encodedText = Base64.getEncoder().encodeToString(encryptedTextBytes);
			String encodedIvText = Hex.encodeHexString(ivValue);
			encryptedValue = encodedIvText + "::" + encodedText;
		} catch (Exception e) {
			throw new InputMismatchException("Exception occured while encrypting data");
		}	 
		return encryptedValue;
		
	}

	private static SecretKeySpec extractKey(String keyValue) throws DecoderException {
		String decryptedKey= new String(Hex.decodeHex(keyValue.toCharArray()));
		return new SecretKeySpec(padString(decryptedKey).getBytes(), "AES");
	}
	
	public  String decrypt(String encryptedDataInput, String keyValue)  {
		String decryptedValue = "";
    	try {
			String strIvVal = "";
			String strEncryptedVal = "";
			SecretKeySpec keySpec = extractKey(keyValue);
			if ((encryptedDataInput == null) || (encryptedDataInput.split("::").length < 2)) {
				throw new IllegalArgumentException("Invalid data");
			}
			strIvVal = encryptedDataInput.split("::")[0];
			strEncryptedVal = encryptedDataInput.split("::")[1];	    		
			byte[] ivValue = Hex.decodeHex(strIvVal.toCharArray());	    		
			byte[] encryptedTextBytes = Base64.getDecoder().decode(strEncryptedVal);
		    IvParameterSpec ivSpec = new IvParameterSpec(ivValue);
		    Cipher objCipher = Cipher.getInstance(ENC_ALGORITHM);
		    objCipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);	 
		    byte[] decValueBytes = objCipher.doFinal(encryptedTextBytes);
		    decryptedValue = new String(decValueBytes);	
		} catch (Exception e) {
			throw new InputMismatchException("Exception occured while decrpyting data");
		} 
		
		
		return decryptedValue;
	}
	
	
	private static String padString(String source) {
        char paddingChar = ' ';
        int size = 16;
        int x = source.length() % size;
        int padLength = size - x;
        for (int i = 0; i < padLength; i++)
        {
            source += paddingChar;
        }
        return source;
      }
	
	
}
