package com.common.core.util;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.io.*;
import java.nio.ByteBuffer;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Calendar;

public final class CryptoUtils {
    public static byte[] symmetricEncrypto(byte[] keyData, byte[] byteSource)
            throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int mode = 1;
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES", "BC");
            DESKeySpec keySpec = new DESKeySpec(keyData);
            Key key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding", "BC");
            cipher.init(mode, key);

            CipherOutputStream cOut = new CipherOutputStream(baos, cipher);
            cOut.write(byteSource);
            cOut.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            baos.close();
        }
    }

    public static byte[] symmetricDecrypto(byte[] keyData, byte[] byteSource)
            throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int mode = 2;
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES", "BC");
            DESKeySpec keySpec = new DESKeySpec(keyData);
            Key key = keyFactory.generateSecret(keySpec);
            Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding", "BC");
            cipher.init(mode, key);

            CipherOutputStream cOut = new CipherOutputStream(baos, cipher);
            cOut.write(byteSource);
            cOut.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            baos.close();
        }
    }

    public static byte[] doHash(String strAlgorithm, byte[] byteSource)
            throws NoSuchAlgorithmException {
        MessageDigest currentAlgorithm = MessageDigest.getInstance(strAlgorithm);

        currentAlgorithm.update(byteSource);
        return currentAlgorithm.digest();
    }

    public static String doHash(String strAlgorithm, String strSource)
            throws NoSuchAlgorithmException {
        MessageDigest currentAlgorithm = MessageDigest.getInstance(strAlgorithm);

        currentAlgorithm.update(strSource.getBytes());
        return byte2hex(currentAlgorithm.digest());
    }

    public static boolean verifyHash(String strAlgorithm, byte[] byteSource, byte[] byteHash)
            throws NoSuchAlgorithmException {
        MessageDigest alga = MessageDigest.getInstance(strAlgorithm);
        alga.update(byteSource);
        byte[] digesta = alga.digest();

        return (MessageDigest.isEqual(digesta, byteHash));
    }

    public static boolean verifyHash(String strAlgorithm, String strSource, String strHexHash)
            throws NoSuchAlgorithmException {
        MessageDigest alga = MessageDigest.getInstance(strAlgorithm);
        alga.update(strSource.getBytes());
        byte[] digesta = alga.digest();

        return (MessageDigest.isEqual(digesta, hex2byte(strHexHash)));
    }

    public static byte[] signByKeyStore(String strKeyStorePath, String strKeyStorePass, String strPKIAlias, String strPKIPass, byte[] byteSource, String algorithm)
            throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        try {
            FileInputStream in = new FileInputStream(strKeyStorePath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, strKeyStorePass.toCharArray());

            PrivateKey myprikey = (PrivateKey) ks.getKey(strPKIAlias, strPKIPass.toCharArray());

            Signature signet = Signature.getInstance(algorithm, "BC");
            signet.initSign(myprikey);
            signet.update(byteSource);
            return signet.sign();
        } catch (Exception e) {
            throw e;
        }
    }

    public static boolean verifyByKeyStore(String strKeyStorePath, String strKeyStorePass, String strPKIAlias, byte[] byteSource, byte[] byteSigned, String algorithm)
            throws Exception {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(new BouncyCastleProvider());
        }

        try {
            FileInputStream in = new FileInputStream(strKeyStorePath);
            KeyStore ks = KeyStore.getInstance("JKS");
            ks.load(in, strKeyStorePass.toCharArray());

            Certificate c1 = ks.getCertificate(strPKIAlias);

            Signature signet = Signature.getInstance(algorithm, "BC");
            signet.initVerify(c1);
            signet.update(byteSource);
            return signet.verify(byteSigned);
        } catch (Exception e) {
            throw e;
        }
    }

    public static byte[] asc2bcd(byte[] byteSrc, int nLen) {
        return null;
    }

    public static byte[] bcd2asc(byte[] byteSrc, int nLen) {
        return null;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; ++i) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    public static byte[] hex2byte(String hex)
            throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        int i = 0;
        int j = 0;
        for (int l = hex.length(); i < l; ++j) {
            String swap = arr[(i++)] + arr[i] + "";
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();

            ++i;
        }

        return b;
    }

    public static KeyPair generateKeyPair(String strAlgorithm, Provider provider, int nKeySize)
            throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(strAlgorithm, provider);

        keyPairGen.initialize(nKeySize, new SecureRandom());
        return keyPairGen.genKeyPair();
    }

    public static PublicKey getFilePublicKey(String strAlgorithm, String strFilePath)
            throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance(strAlgorithm);
        File file = new File(strFilePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] bPublicKey = new byte[(int) file.length()];
        BufferedInputStream bis = new BufferedInputStream(fis);
        if (bis.available() > 0) {
            bis.read(bPublicKey);
        }
        bis.close();
        X509EncodedKeySpec keySpecPublic = new X509EncodedKeySpec(bPublicKey);
        return kf.generatePublic(keySpecPublic);
    }

    public static PrivateKey getFilePrivateKey(String strAlgorithm, String strFilePath)
            throws NoSuchAlgorithmException, IOException, InvalidKeySpecException {
        KeyFactory kf = KeyFactory.getInstance(strAlgorithm);
        File file = new File(strFilePath);
        FileInputStream fis = new FileInputStream(file);
        byte[] bPrivateKey = new byte[(int) file.length()];
        BufferedInputStream bis = new BufferedInputStream(fis);
        if (bis.available() > 0) {
            bis.read(bPrivateKey);
        }
        bis.close();
        PKCS8EncodedKeySpec keySpecPrivate = new PKCS8EncodedKeySpec(bPrivateKey);
        return kf.generatePrivate(keySpecPrivate);
    }

    public static byte[] sign(String strAlgorithm, Provider provider, PrivateKey privateKey, byte[] btSignedData)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signet = Signature.getInstance(strAlgorithm, provider);

        signet.initSign(privateKey);
        signet.update(btSignedData);
        return signet.sign();
    }

    public static String sign(String strAlgorithm, Provider provider, PrivateKey privateKey, String strSignedData)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signet = Signature.getInstance(strAlgorithm, provider);

        signet.initSign(privateKey);
        signet.update(strSignedData.getBytes());
        return byte2hex(signet.sign());
    }

    public static boolean verifySign(String strAlgorithm, Provider provider, PublicKey publicKey, byte[] btSignedData, byte[] btDigest)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signet = Signature.getInstance(strAlgorithm, provider);

        signet.initVerify(publicKey);
        signet.update(btSignedData);
        return signet.verify(btDigest);
    }

    public static boolean verifySign(String strAlgorithm, Provider provider, PublicKey publicKey, String strSignedData, String strDigest)
            throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature signet = Signature.getInstance(strAlgorithm, provider);

        signet.initVerify(publicKey);
        signet.update(strSignedData.getBytes());
        return signet.verify(hex2byte(strDigest));
    }

    public static String sanwingEncode(long lngID, String strPassword)
            throws NoSuchAlgorithmException {
        StringBuilder sb = new StringBuilder();

        sb.append(lngID * 2L + 119L).append(strPassword).append("金莲花开");
        String strContent = sb.toString();
        String strMD5 = doHash("MD5", strContent);

        sb.setLength(0);
        sb.append(strMD5);
        sb.reverse();
        sb.append("万里江山万里晴，一缕尘心一缕烟。");
        strContent = sb.toString();
        strMD5 = doHash("MD5", strContent);

        String strContent1 = strMD5.substring(0, 5);
        String strContent2 = strMD5.substring(5);
        strContent = strContent1;
        strMD5 = doHash("MD5", strContent);
        sb.setLength(0);
        sb.append(strMD5);

        strContent = strContent2;
        strMD5 = doHash("MD5", strContent);
        sb.append(strMD5);

        strContent = sb.toString();
        strMD5 = doHash("MD5", strContent);

        return strMD5;
    }

    public static byte[] str2bcd(String str) {
        if (str.length() % 2 != 0) str = "0" + str;

        StringBuffer sb = new StringBuffer(str);
        ByteBuffer bb = ByteBuffer.allocate(str.length() / 2);

        int i = 0;
        while (i < str.length()) {
            bb.put((byte) (Integer.parseInt(sb.substring(i, i + 1)) << 4 | Integer.parseInt(sb.substring(i + 1, i + 2))));
            i += 2;
        }
        return bb.array();
    }

    public static byte[] pinBlock(String strPassword, String strCardNo) {
        byte[] bytesPin = {-1, -1, -1, -1, -1, -1, -1, -1};
        bytesPin[0] = (byte) strPassword.length();
        byte[] bcdPwd = str2bcd(strPassword);
        System.arraycopy(bcdPwd, 0, bytesPin, 1, bcdPwd.length);

        int nLength = strCardNo.length();
        String strCardNo12 = strCardNo.substring(nLength - 13, nLength - 1);
        byte[] bcdPAN = str2bcd(strCardNo12);

        byte[] bytesPinBlock = new byte[8];
        bytesPinBlock[0] = bytesPin[0];
        bytesPinBlock[1] = bytesPin[1];
        for (int i = 2; i < 8; ++i) {
            bytesPinBlock[i] = (byte) (bytesPin[i] ^ bcdPAN[(i - 2)]);
        }
        return bytesPinBlock;
    }

    public static byte[] ansi99MAC(String strData, byte[] bytesKey)
            throws Exception {
        byte[] bytesData = strData.getBytes();
        int nLength = bytesData.length % 8;

        ByteArrayOutputStream byteArrayData = new ByteArrayOutputStream();
        byteArrayData.write(bytesData);

        if (nLength != 0) {
            byteArrayData.write(new byte[8 - nLength]);
            nLength = bytesData.length + 8 - nLength;
        } else {
            nLength = bytesData.length;
        }
        bytesData = byteArrayData.toByteArray();

        byte[] bytesE = new byte[8];
        for (int i = 0; i < nLength; i += 8) {
            for (int j = 0; j < 8; ++j) {
                int tmp83_81 = j;
                byte[] tmp83_79 = bytesE;
                tmp83_79[tmp83_81] = (byte) (tmp83_79[tmp83_81] ^ bytesData[(i + j)]);
            }

            bytesE = symmetricEncrypto(bytesKey, bytesE);
        }

        return bytesE;
    }

    public static void main(String[] args) {
        String strKey = "32AEABDF1CFE3D0E";
        byte[] bytesKey = hex2byte(strKey);
        String strData = "11205103200000000134091029005311000291020102000803000002";
        byte[] bytesData = strData.getBytes();

        byte[] byteRet = (byte[]) null;
        try {
            System.out.println("key hex:" + byte2hex(bytesKey));
            System.out.println("data hex:" + byte2hex(bytesData));
            System.out.println("data:" + new String(bytesData));
            Calendar cal1 = Calendar.getInstance();

            byteRet = ansi99MAC(new String(bytesData), bytesKey);

            System.out.println(byte2hex(byteRet));
        } catch (Exception ex) {
            ex.printStackTrace(System.err);
        }
    }
}