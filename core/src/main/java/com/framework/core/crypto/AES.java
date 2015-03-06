package com.framework.core.crypto;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * @author bfc
 */
public class AES {
    private static final String ALGORITHM_AES = "AES";
    private byte[] key;

    public byte[] encrypt(byte[] plainMessage) {
        try {
            Cipher cipher = createCipher(ENCRYPT_MODE);
            return cipher.doFinal(plainMessage);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        } catch (BadPaddingException e) {
            throw new IllegalStateException(e);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] decrypt(byte[] encryptedMessage) {
        try {
            Cipher cipher = createCipher(DECRYPT_MODE);
            return cipher.doFinal(encryptedMessage);
        } catch (IllegalBlockSizeException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException e) {
            throw new IllegalStateException(e);
        } catch (BadPaddingException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (NoSuchPaddingException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] generateKey() {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(ALGORITHM_AES);
            generator.init(128);
            return generator.generateKey().getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    private Cipher createCipher(int encryptMode) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {
        SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM_AES);
        Cipher cipher = Cipher.getInstance(ALGORITHM_AES);
        cipher.init(encryptMode, keySpec, new SecureRandom());
        return cipher;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}
