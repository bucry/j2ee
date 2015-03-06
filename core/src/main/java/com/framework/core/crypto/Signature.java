package com.framework.core.crypto;

import java.io.ByteArrayInputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Generate private key:
 * openssl genrsa -out private.pem 1024
 * openssl pkcs8 -topk8 -inform PEM -in private.pem -outform DER -out private.der -nocrypt
 * <p/>
 * Generate cert:
 * openssl req -new -x509 -keyform PEM -key private.pem -outform DER -out cert.der
 *
 * @author bfc
 */
public class Signature {
    private static final String ALGORITHM_RSA = "RSA";
    private static final String ALGORITHM_SHA1_WITH_RSA = "SHA1withRSA";

    private PrivateKey privateKey;
    private PublicKey publicKey;

    public boolean verify(byte[] message, byte[] signatureValue) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(ALGORITHM_SHA1_WITH_RSA);
            signature.initVerify(publicKey);
            signature.update(message);
            return signature.verify(signatureValue);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (SignatureException e) {
            throw new IllegalStateException(e);
        }
    }

    public byte[] sign(byte[] message) {
        try {
            java.security.Signature signature = java.security.Signature.getInstance(ALGORITHM_SHA1_WITH_RSA);
            signature.initSign(privateKey);
            signature.update(message);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeyException e) {
            throw new IllegalArgumentException(e);
        } catch (SignatureException e) {
            throw new IllegalStateException(e);
        }
    }

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(ALGORITHM_RSA);
            generator.initialize(2048);
            return generator.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        }
    }

    public void setPrivateKey(byte[] privateKeyValue) {
        try {
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyValue);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setPublicKey(byte[] publicKeyValue) {
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyValue);
            KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM_RSA);
            publicKey = keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(e);
        } catch (InvalidKeySpecException e) {
            throw new IllegalArgumentException(e);
        }
    }

    public void setCertificate(byte[] certificateValue) {
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X509");
            Certificate certificate = certificateFactory.generateCertificate(new ByteArrayInputStream(certificateValue));
            publicKey = certificate.getPublicKey();
        } catch (CertificateException e) {
            throw new IllegalArgumentException(e);
        }
    }
}
