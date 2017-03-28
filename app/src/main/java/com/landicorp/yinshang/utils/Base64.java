package com.landicorp.yinshang.utils;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;

/**
 * Created by u on 2017/1/9.
 */

public class Base64 {
    /**
     * BASE64解密
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws Exception {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) throws Exception {
        return (new BASE64Encoder()).encodeBuffer(key);
    }
}
