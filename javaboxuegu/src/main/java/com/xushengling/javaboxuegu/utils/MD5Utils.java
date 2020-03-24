package com.xushengling.javaboxuegu.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ProjectName: BoXueGu
 * @Package: com.xushengling.javaboxuegu
 * @ClassName: MD5Utils
 * @Author: 徐圣领
 * @CreateDate: 2020/3/9 15:18
 */
public class MD5Utils {
    public static String md5(String text) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("mu5");
            byte[] result = digest.digest(text.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : result) {
                String hex = Integer.toHexString(b & 0xff);
                if (hex.length()==1){
                    sb.append("0").append(hex);
                }else {
                    sb.append(hex);
                }
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}
