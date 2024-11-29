package me.yuxiaoyao.sl651.netty.server.util;


import me.yuxiaoyao.sl651.netty.server.exception.DecodeException;

import java.util.Locale;

/**
 * @author kerryzhang on 2022/12/23
 */

public class HexUtil {

    public static String byteArray2HexStr(byte[] bArr) {
        if (bArr == null || bArr.length == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (byte b : bArr) {
            String hexString = Integer.toHexString(b & 255);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
        }
        return sb.toString().toUpperCase(Locale.ROOT);
    }


    public static byte[] hexStringToByteArray(String str) {
        if (str == null || str.isEmpty()) {
            return null;
        }
        int length = str.length();
        if (length % 2 != 0) {
            throw new DecodeException("hex string to byte array error. " + str);
        }
        int dataLen = length / 2;
        byte[] data = new byte[dataLen];
        for (int i = 0; i < dataLen; i++) {
            String substring = str.substring(i * 2, (i + 1) * 2);
            data[i] = (byte) Integer.parseInt(substring, 16);
        }
        return data;
    }


    public static byte[] intToByteArray(int n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static byte[] unsignedIntToByteArray(long n) {
        byte[] b = new byte[4];
        b[3] = (byte) (n & 0xff);
        b[2] = (byte) (n >> 8 & 0xff);
        b[1] = (byte) (n >> 16 & 0xff);
        b[0] = (byte) (n >> 24 & 0xff);
        return b;
    }

    public static byte[] concat(byte[] first, byte[]... concat) {
        int size = first.length;
        for (byte[] bytes : concat) {
            size += bytes.length;
        }
        byte[] data = new byte[size];

        int index = 0;
        System.arraycopy(first, 0, data, index, first.length);
        index = first.length;

        for (byte[] bytes : concat) {
            System.arraycopy(bytes, 0, data, index, bytes.length);
            index += bytes.length;
        }
        return data;
    }


}
