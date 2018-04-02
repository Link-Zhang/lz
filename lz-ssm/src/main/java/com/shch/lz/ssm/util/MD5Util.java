package com.shch.lz.ssm.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by Link at 16:52 on 4/2/18.
 */
public class MD5Util {

    public final static String md5(String content) {
        char[] md5String = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            byte[] byteInput = content.getBytes();
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(byteInput);
            byte[] md = messageDigest.digest();
            // to 0x
            int length = md.length;
            char[] str = new char[length * 2];
            int k = 0;
            for (int i = 0; i < length; i++) {
                byte b0 = md[i]; // 95
                str[k++] = md5String[b0 >>> 4 & 0xf]; // 5
                str[k++] = md5String[b0 & 0xf]; // F

            }
            return new String(str);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
}
