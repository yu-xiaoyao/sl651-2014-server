package me.yuxiaoyao.sl651.netty.server.util;

import io.netty.buffer.ByteBuf;

/**
 * @author kerryzhang on 2024/11/06
 */

public class ByteArrayUtil {


    public static byte[] readByteBuf(ByteBuf byteBuf, int len) {
        byte[] bytes = new byte[len];
        byteBuf.readBytes(bytes);
        return bytes;
    }

    public static String bcdToString(byte[] bytes) {
        StringBuilder temp = new StringBuilder(bytes.length * 2);
        for (byte aByte : bytes) {
            temp.append((byte) ((aByte & 0xf0) >>> 4));
            temp.append((byte) (aByte & 0x0f));
        }
        return temp.substring(0, 1).equalsIgnoreCase("0") ? temp.substring(1) : temp.toString();
    }

    public static int bcdToInt(byte[] bytes) {
        return Integer.parseInt(bcdToString(bytes));
    }

}
