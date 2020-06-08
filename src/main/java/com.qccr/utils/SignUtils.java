package com.qccr.utils;//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

public class SignUtils {
    private static final Logger logger = LoggerFactory.getLogger(SignUtils.class);
    public static final String SIGN_METHOD_MD5 = "MD5";
    public static final String SIGN_METHOD_HMAC = "HMAC";
    public static final String SIGN_METHOD_SHA1 = "SHA1";
    private static final String CHARSET_UTF8 = "utf-8";

    private SignUtils() {
    }

    public static String signParamsMD5(Map<String, String> params, String secret) {
        return signParams(params, secret, "MD5");
    }

    public static String signParamsSHA1(Map<String, String> params, String secret) {
        return signParams(params, secret, "SHA1");
    }

    public static String signParams(Map<String, String> params, String secret, String signMethod) {
        String[] keys = (String[]) params.keySet().toArray(new String[0]);
        Arrays.sort(keys, String.CASE_INSENSITIVE_ORDER);
        StringBuilder query = new StringBuilder();
        String[] var5 = keys;
        int var6 = keys.length;

        for (int var7 = 0; var7 < var6; ++var7) {
            String key = var5[var7];
            if (StringUtils.isNotBlank((CharSequence) params.get(key))) {
                query.append(key).append((String) params.get(key));
            }
        }

        String signStr = "";
        byte[] bytes;
        if ("HMAC".equals(signMethod)) {
            bytes = encryptHMAC(query.toString(), secret);
            signStr = byte2hex(bytes);
        }

        if ("MD5".equals(signMethod)) {
            query.insert(0, secret);
            bytes = encryptMD5(query.toString());
            signStr = byte2hex(bytes);
        }

        if ("SHA1".equals(signMethod)) {
            query.insert(0, secret);
            query.append(secret);
            bytes = encryptSHA1(query.toString());
            signStr = Base64.encodeToString(bytes);
        }

        return signStr;
    }

    public static byte[] encryptHMAC(String data, String secret) {
        byte[] bytes = null;

        try {
            SecretKey secretKey = new SecretKeySpec(secret.getBytes("utf-8"), "HmacMD5");
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            bytes = mac.doFinal(data.getBytes("utf-8"));
        } catch (Exception var5) {
            logger.error("hmacEncrypt error [%s] error", data, var5);
        }

        return bytes;
    }

    public static byte[] encryptMD5(String data) {
        byte[] bytes = null;

        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(data.getBytes("utf-8"));
            bytes = md.digest();
        } catch (Exception var3) {
            logger.error("md5Encrypt error [%s] error", data, var3);
        }

        return bytes;
    }

    public static byte[] encryptSHA1(String data) {
        byte[] bytes = null;

        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(data.getBytes("utf-8"));
            bytes = md.digest();
        } catch (Exception var3) {
            logger.error("sha1Encrypt error [%s] error", data, var3);
        }

        return bytes;
    }

    public static String byte2hex(byte[] bytes) {
        StringBuilder sign = new StringBuilder();

        for (int i = 0; i < bytes.length; ++i) {
            String hex = Integer.toHexString(bytes[i] & 255);
            if (hex.length() == 1) {
                sign.append("0");
            }

            sign.append(hex.toUpperCase());
        }

        return sign.toString();
    }

    public static void main(String[] args) {
        byte[] bytes = encryptMD5("10468620-0483-451c-a574-b920cf398d52");
        System.out.println(byte2hex(bytes));
    }
}
