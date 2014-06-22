package com.common.security.util;

import org.apache.commons.lang3.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5加密实现
 * User: Allen
 * Date: 12-9-23
 */
public class Md5PasswordEncoder {

    private static final String algorithm = "MD5";    //加密算法

    /**
     * Encodes the rawPass using a MessageDigest.
     * If a salt is specified it will be merged with the password before encoding.
     *
     * @param rawPass The plain text password
     * @param salt    The salt to sprinkle
     * @return Hex string of password digest
     */
    public static String encodePassword(String rawPass, String salt) {
        String saltedPass = mergePasswordAndSalt(rawPass, salt);

        MessageDigest messageDigest = null;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("No such algorithm [" + algorithm + "]");
        }
        byte[] digest = messageDigest.digest(saltedPass.getBytes());
        return new String(Hex.encode(digest));
    }

    /**
     * Used by subclasses to generate a merged password and salt String
     * The generated password will be in the form of password{salt}
     * Null can be passed to either method, and will be handled correctly.
     * If the salt is null or empty, the resulting generated password will simply be the passed password.
     *
     * @param password the password to be used (can be null)
     * @param salt     the salt to be used (can be null)
     * @return a merged password and salt String
     */
    private static String mergePasswordAndSalt(String password, String salt) {
        if (password == null) {
            password = "";
        }

        if (StringUtils.isEmpty(salt)) {
            return password;
        } else {
            return password + "{" + salt + "}";
        }
    }
}
