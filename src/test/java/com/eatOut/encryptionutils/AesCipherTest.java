package com.eatOut.encryptionutils;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.eatOut.encryptionutils.AesCipher;
import com.eatOut.encryptionutils.IEatOutEncryption;

public class AesCipherTest {

	IEatOutEncryption aesCipher=AesCipher.instance();
	
	@Test
    public void encryptDecryptTest(){
		
			String encryptedKey = Hex.encodeHexString("rob".getBytes());
			String encryptedValue=aesCipher.encrypt("group4-eatOut", encryptedKey);
			String decryptedValue=aesCipher.decrypt(encryptedValue, encryptedKey);
			Assert.assertEquals("group4-eatOut", decryptedValue);
		
    }
	
	
}
