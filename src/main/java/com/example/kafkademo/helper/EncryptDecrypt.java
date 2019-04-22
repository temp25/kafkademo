package com.example.kafkademo.helper;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
 
public class EncryptDecrypt {
    
    private static final String SECRET = "CLOUDKARAFKA";
    
    private static byte[] getKey() {
        
        MessageDigest sha = null;
        byte[] key = null;
        
        try {
            key = SECRET.getBytes("UTF-8");
            sha = MessageDigest.getInstance("SHA-1");
            key = sha.digest(key);
            key = Arrays.copyOf(key, 16);
            
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch(UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch(Exception e) {
        	e.printStackTrace();
        }
        
        return key;
    }
    
    private static SecretKeySpec getSecretKeySpec() {
        return new SecretKeySpec(getKey(), "AES");
    }
    
    public static String encryptString(String message) {
        String encodedMessage = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, getSecretKeySpec());
            encodedMessage = Base64.getEncoder().encodeToString(cipher.doFinal(message.getBytes("UTF-8")));
        } catch (Exception e) {
            System.out.println("Error arised while encrypting: " + e.toString());
        }
        return encodedMessage;
    }
    
    public static String decryptString(String encodedMessage){
        String decodedMessage = null;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            cipher.init(Cipher.DECRYPT_MODE, getSecretKeySpec());
            return new String(cipher.doFinal(Base64.getDecoder().decode(encodedMessage)));
        }
        catch (Exception e)
        {
            System.out.println("Error arised while decrypting: " + e.toString());
        }
        return null;
    }
    
}