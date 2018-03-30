package com.shch.lz.ssm.util;


import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by Link at 10:23 on 3/30/18.
 */
public class AesUtil {
    private static final String SEED = "lz";
    private static final String MODE = "AES/CBC/PKCS5Padding";
    private static final String ALGORITHM = "AES";
    private static final String NUMBER_GENERATION_ALGORITHM = "SHA1PRNG";
    private static final int KEY_SIZE = 128;
    private static final String ENCODING = "utf-8";

    private static Cipher getCipher(int mode) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom random = SecureRandom.getInstance(NUMBER_GENERATION_ALGORITHM);
            random.setSeed(SEED.getBytes());
            keyGenerator.init(KEY_SIZE, random);
            SecretKeySpec secretKeySpec = new SecretKeySpec(keyGenerator.generateKey().getEncoded(), ALGORITHM);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(keyGenerator.generateKey().getEncoded());
            Cipher cipher = Cipher.getInstance(MODE);
            cipher.init(mode, secretKeySpec, ivParameterSpec);
            return cipher;
        } catch (NoSuchPaddingException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String aesEncode(String plainText) {
        try {
            Cipher encodeCipher = getCipher(Cipher.ENCRYPT_MODE);
            if (null != encodeCipher) {
                return new BASE64Encoder().encode(encodeCipher.doFinal(plainText.getBytes(ENCODING)));
            }
        } catch (BadPaddingException | UnsupportedEncodingException | IllegalBlockSizeException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String aesDecode(String cipherText) {
        try {
            Cipher decodeCipher = getCipher(Cipher.DECRYPT_MODE);
            if (null != decodeCipher) {
                return new String(decodeCipher.doFinal(new BASE64Decoder().decodeBuffer(cipherText)), ENCODING);
            }
        } catch (BadPaddingException | IllegalBlockSizeException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
