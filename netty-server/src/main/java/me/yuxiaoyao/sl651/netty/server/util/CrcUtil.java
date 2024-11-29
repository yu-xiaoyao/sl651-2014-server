package me.yuxiaoyao.sl651.netty.server.util;


import io.netty.buffer.ByteBuf;

/**
 * @author kerryzhang on 2024/11/06
 */

public class CrcUtil {

    public static int calcCrc16(ByteBuf buf) {
        buf.markReaderIndex();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);
        buf.resetReaderIndex();
        return calcCrc16(data);
    }

    /**
     * by GPT
     *
     * @param data
     * @return
     */
    public static int calcCrc16(byte[] data) {
        int crc = 0xFFFF; // Initial CRC value
        for (byte b : data) {
            crc ^= b & 0xFF; // XOR byte with CRC
            for (int i = 0; i < 8; i++) {
                if ((crc & 1) != 0) {
                    crc = (crc >> 1) ^ 0xA001; // Apply polynomial if LSB is 1
                } else {
                    crc >>= 1; // Just shift if LSB is 0
                }
            }
        }
        return crc & 0xFFFF; // Return the 16-bit result
    }

    public static byte[] calcCrc16Bytes(byte[] data) {
        int crc = calcCrc16(data);
        byte[] crcBytes = new byte[2];
        crcBytes[0] = (byte) ((crc >> 8) & 0xFF); // High byte
        crcBytes[1] = (byte) (crc & 0xFF);        // Low byte
        return crcBytes;
    }

}
