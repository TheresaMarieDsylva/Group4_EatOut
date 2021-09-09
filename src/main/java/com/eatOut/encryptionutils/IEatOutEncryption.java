package com.eatOut.encryptionutils;

public interface IEatOutEncryption {

	String encrypt(String valueToBeEncryptd, String keyValue) ;

	String decrypt(String encryptedDataInput, String keyValue) ;


}
