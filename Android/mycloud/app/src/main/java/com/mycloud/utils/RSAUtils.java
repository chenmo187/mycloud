package com.mycloud.utils;

import android.util.Base64;


import java.nio.charset.Charset;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * Created by Deson on 2015/8/10.
 */
public class RSAUtils {
    public static final String PUBLIC_KEY_GENERATED = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDgdgwKN1iQDSBG77FXmbNN2q0cm5c8JIwO1Yo/unioFwbZGvbeGFPFoQ2Kw++8+ZOFVx/BXCvU1sL0J7Qv/VBV5h9mkXMyRwgljr5N3zac/4TRkcL7mj4FGTjYCRM1iPlPYaBxRsbLfHBoXgWlbW2DZnQBeozZq/6sZ81ZxS9VOQIDAQAB";




    //    public static final String PUBLIC_KEY_GENERATED = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAMgqBII2QT5-f4_vaZLoB-Pv55QDV8hsQcxD2DOilakOJc2KLi81SoGIL9Djc9siYvJfFZBSIIieQBGP1EvJWQMCAwEAAQ";
    public static final String PRIVATE_KEY_GENERATED = "MIIBVQIBADANBgkqhkiG9w0BAQEFAASCAT8wggE7AgEAAkEAyCoEgjZBPn5_j-9pkugH4-_nlANXyGxBzEPYM6KVqQ4lzYouLzVKgYgv0ONz2yJi8l8VkFIgiJ5AEY_US8lZAwIDAQABAkBICWcp_aCpfxklkgIqzrrYO6TaTgGvrdZYTypmGuNFvaCxXbCC8kQpqXKjNIB7iod1d3n9XekfSXbY7yX5uELRAiEA6jdmmAFieBiQ6YyYa5ekEUxgvQyD-Ek2oFnqHcwf3y0CIQDax9iALp5G6qF7Zn4BbNuFdweIt_3uRl3amzXMoZa27wIgWm2PX8rRENszP-i3hHrkroUVqIfnf_oMbDaq7Fw-RfUCIQCMgFthto6fLH-YKDIz6BzykYadHsxNAZO0mIjsabnIsQIhALgloT6Yj_61qlPXXAIi_UecNdUFABf9k_kXw1J8RfSm";

    public static String decrypt(String base64Encoded) throws GeneralSecurityException {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Base64.decode(PRIVATE_KEY_GENERATED, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] result = cipher.doFinal(Base64.decode(base64Encoded, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING));
        return new String(result, Charset.forName("utf-8"));
    }

    public static String encrypt(String text) throws GeneralSecurityException {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(PUBLIC_KEY_GENERATED, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING));
// X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Base64.decode(PUBLIC_KEY_GENERATED, Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING));
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.encodeToString(cipher.doFinal(text.getBytes(Charset.forName("utf-8"))), Base64.URL_SAFE | Base64.NO_WRAP | Base64.NO_PADDING);
    }
}
