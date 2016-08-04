package com.example.hongcheng.common.util;

import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by hongcheng on 16/4/2.
 */
public class SecurityUtils {

    private static String TAG = SecurityUtils.class.getName();

    private SecurityUtils(){

    }

    public static String toMD5(String password){
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] result = digest.digest(password.getBytes());
            StringBuffer sb = new StringBuffer();
            for(byte b : result){
                int number = (int)(b & 0xff) ;
                String str = Integer.toHexString(number);
                if(str.length()==1){
                    sb.append("0");
                }
                sb.append(str);
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            //can't reach
            return "";
        }
    }

    /**
     * Base64操作
     * @param source
     * @return
     */
    public static String encodeBase64(String source)
    {

        String result = "";

        if ("".equals(source) || source == null)
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }
        try
        {
            result = StringUtils.byte2String(Base64.encode(source.trim().getBytes("UTF-8"), Base64.DEFAULT));
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "", e);
        }
        return result;
    }

    // 解密Base64
    public static String decodeBase64(String source)
    {
        String result = "";
        if ("".equals(source) || source == null)
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }

        try
        {
            result = StringUtils.byte2String(Base64.decode(source.getBytes("UTF-8"), Base64.NO_WRAP));
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "", e);
        }

        return result;
    }

    // 加密Base64
    public static String encodeBase64(byte[] source)
    {
        return StringUtils.byte2String(Base64.encode(source, Base64.NO_WRAP));
    }

    // 解密Base64
    public static String encodeBase64(String source, Charset encoding)
    {
        String result = "";
        if ("".equals(source) || source == null)
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }

        result = new String(Base64.encode(source.trim().getBytes(encoding), Base64.NO_WRAP), encoding);

        return result;
    }

    // 解密Base64
    public static String decodeBase64(String source, Charset encoding)
    {
        String result = "";
        if (StringUtils.isEmpty(source))
        {
            LoggerUtils.debug(TAG, "base64 source is empty");
            return result;
        }

        return new String(Base64.decode(source.trim().getBytes(encoding), Base64.NO_WRAP), encoding);
    }

    /**
     *
     * <br>
     * AES256加密
     * @param data
     * @param key
     * @return
     */
    public static String encryptApp(String data, String key)
    {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);

            byte[] clearText = data.getBytes("UTF8");

            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] encrypted = cipher.doFinal(clearText);

            return StringUtils.byte2String(Base64.encode(encrypted, Base64.NO_WRAP));
        }
        catch (InvalidKeyException e)
        {
            LoggerUtils.error(TAG, "", e);
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (NoSuchPaddingException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (InvalidAlgorithmParameterException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (IllegalBlockSizeException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (BadPaddingException e)
        {
            LoggerUtils.debug(TAG, "encryptApp error");
        }
        catch (IllegalArgumentException e)
        {
            LoggerUtils.error(TAG, "Key may be lost.");
            return null;
        }

        return null;

    }

    /**
     *
     * <br>
     * AES256解密
     * @param data
     * @param key
     * @return
     */
    public static String decryptApp(String data, String key)
    {
        try
        {
            SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes("UTF-8"), "AES");

            final byte[] iv = new byte[16];
            Arrays.fill(iv, (byte) 0x00);
            IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);

            byte[] encrypted = Base64.decode(data.getBytes("UTF-8"), Base64.NO_WRAP);
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

            cipher.init(Cipher.DECRYPT_MODE, skeySpec, ivParameterSpec);

            byte[] original = cipher.doFinal(encrypted);
            LoggerUtils.debug(TAG, String.valueOf(original));
            return StringUtils.byte2String(original);
        }
        catch (InvalidKeyException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (NoSuchPaddingException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (InvalidAlgorithmParameterException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (IllegalBlockSizeException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (BadPaddingException e)
        {
            LoggerUtils.debug(TAG, "decryptApp error");
        }
        catch (UnsupportedEncodingException e)
        {
            LoggerUtils.debug(TAG, "", e);
        }
        catch (IllegalArgumentException e)
        {
            LoggerUtils.error(TAG, "Key may be lost.");
            return null;
        }

        return "";
    }

    /**
     * 对字符串加密,加密算法使用SHA-256,默认使用SHA-256<br>
     * @see [相关类，可选、也可多条，对于重要的类或接口建议注释]
     * @param strSrc 要加密的字符串
     * @param encName 加密类型
     * @return
     */
    public static String sha256Encrypt(String strSrc, String encName)
    {
        MessageDigest md = null;

        String vEncName = encName;

        try
        {
            byte[] bt = strSrc.getBytes("UTF-8");
            md = MessageDigest.getInstance(vEncName);
            md.update(bt);
            return StringUtils.bytesToHexString(md.digest());
        }
        catch(UnsupportedEncodingException ue)
        {
            LoggerUtils.error(TAG,"encrypt UnsupportedEncodingException.");
        }
        catch (NoSuchAlgorithmException e)
        {
            LoggerUtils.error(TAG,"There is a NoSuchAlgorithmException in method EncryptHandler.");
        }
        return "";
    }
}
