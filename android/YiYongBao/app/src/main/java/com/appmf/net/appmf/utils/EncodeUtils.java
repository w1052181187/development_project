package com.appmf.net.appmf.utils;

import android.text.TextUtils;
import android.util.Base64;

import com.just.agentweb.LogUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;

public final class EncodeUtils {

    private EncodeUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }

    /**
     * Return Base64-encode string.
     *
     * @param input The input.
     * @return Base64-encode string
     */
    public static String base64Encode2String(final String input) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        return new String(Base64.encode(input.getBytes("UTF-8"), Base64.DEFAULT),
                "UTF-8");
    }

    /**
     * Return the bytes of decode Base64-encode string.
     *
     * @param input The input.
     * @return the string of decode Base64-encode string
     */
    public static String base64Decode(final String input) throws UnsupportedEncodingException {
        if (TextUtils.isEmpty(input)) {
            return "";
        }
        return new String(Base64.decode(input.getBytes( "UTF-8"), Base64.DEFAULT), "UTF-8");
    }

    /**
     * 加密函数
     * @param input
     * @param key
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String encodeByKey(String input, String key) throws UnsupportedEncodingException {

//        $key = $key?:C('PWDHASH');
        String encrypt_key = md5(noRand());
        // 变量初始化
        int ctr = 0;
        StringBuffer tmp = new StringBuffer();
        int inputLength = input.length();
        int keyLength = encrypt_key.length();
        char[] tmpChar = new char[2];
        // for 循环，$i 为从 0 开始，到小于 $txt 字串长度的整数
        for(int i = 0; i < inputLength; i++) {
            // 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
            ctr = ctr == keyLength ? 0 : ctr;
            // $tmp 字串在末尾增加两位，其第一位内容为 $encrypt_key 的第 $ctr 位，
            // 第二位内容为 $txt 的第 $i 位与 $encrypt_key 的 $ctr 位取异或。然后 $ctr = $ctr + 1
            tmpChar[0] = encrypt_key.charAt(ctr++);
            tmpChar[1] = (char) (input.charAt(i) ^ tmpChar[0]);
            tmp.append(new String(tmpChar));
        }
        // 返回结果，结果为 passport_key() 函数返回值的 base64 编码结果
        return base64Encode2String(passport_key(tmp.toString(), key));
    }

    /**
     * [noRand 不重复随机数]
     */
    static String noRand(){
        int min = 0, max=32000;
        // 使用随机数发生器产生 0~32000 的值
        long num = (long) (Math.random() * (max - min)) + min;
        return String.valueOf(num);
    }

    /**
     * Passport 解密函数
     *
     * @param    encodeInput          加密后的字串
     * @param    key         私有密匙(用于解密和加密)
     *
     * @return          字串经过私有密匙解密后的结果
     */
    public static String decodeBykey(String encodeInput, String key) throws UnsupportedEncodingException {
//        $key = $key?:C('PWDHASH');
        // $txt 的结果为加密后的字串经过 base64 解码，然后与私有密匙一起，
        // 经过 passport_key() 函数处理后的返回值
        String txt = passport_key(base64Decode(encodeInput), key);
        // 变量初始化
        StringBuffer tmp = new StringBuffer();
        long length = txt.length();
        char tmpChar;
        char s;
        char s1;
        // for 循环，$i 为从 0 开始，到小于 $txt 字串长度的整数
        for (int i = 0; i < length; i++) {
            // $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
            // 与 $txt 的第 $i + 1 位取异或。然后 $i = $i + 1
            s = txt.charAt(i);
            s1 = txt.charAt(++i);
            tmpChar = (char) (s ^ s1);
            tmp.append(tmpChar);
        }
        // 返回 $tmp 的值作为结果
        return tmp.toString();
    }
    /**
     * Passport 密匙处理函数
     *
     * @param  txt          待加密或待解密的字串
     * @param  key          私有密匙(用于解密和加密)
     *
     * @return  string          处理后的密匙
     */
    static String passport_key(String txt, String key) {
        // 将 $encrypt_key 赋为 $encrypt_key 经 md5() 后的值
        String encrypt_key = md5(key);
        // 变量初始化
        int ctr = 0;
        StringBuffer tmp = new StringBuffer();
        int inputLength = txt.length();
        int keyLength = encrypt_key.length();
        char tmpChar;
        int tmpInt;
        // for 循环，$i 为从 0 开始，到小于 $txt 字串长度的整数
        for(int i = 0; i < inputLength; i++) {
            // 如果 $ctr = $encrypt_key 的长度，则 $ctr 清零
            ctr = ctr == keyLength ? 0 : ctr;
            // $tmp 字串在末尾增加一位，其内容为 $txt 的第 $i 位，
            // 与 $encrypt_key 的第 $ctr + 1 位取异或。然后 $ctr = $ctr + 1
            tmpChar = (char)((txt.charAt(i)) ^ (encrypt_key.charAt(ctr++)));
            tmp.append(tmpChar);
        }
        // 返回 $tmp 的值作为结果
        return tmp.toString();
    }

    static int char2Int(char value) {
        return Math.abs((byte)(value));
    }

    public static String md5(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            return new BigInteger(1, md.digest()).toString(16);
        } catch (Exception e) {
            if (LogUtils.isDebug()) {
                e.printStackTrace();
            }
        }
        return "";
    }
}


