package me.yuxiaoyao.sl651.client;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import lombok.SneakyThrows;
import org.apache.commons.codec.binary.Hex;

import java.util.Locale;

/**
 * @author kerryzhang on 2024/11/13
 */

public class Utils {

    public static String getSendTime() {
        // 591011161118
        return "591011161118";
    }

    public static String getObserverTime() {
        //
        return "5910111611";
    }

    @SneakyThrows
    public static ByteBuf getDeviceByteBuf(DeviceConfig device) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(device.getStartCode());
        buffer.writeByte(device.getStationId() & 0xFf);
        buffer.writeBytes(Hex.decodeHex(device.getTelemetryId()));
        buffer.writeBytes(Hex.decodeHex(device.getPassword()));
        return buffer;
    }

    @SneakyThrows
    public static byte[] getDevicePrefix(DeviceConfig device) {
        ByteBuf buffer = Unpooled.buffer();
        buffer.writeBytes(device.getStartCode());
        buffer.writeByte(device.getStationId() & 0xFf);
        buffer.writeBytes(Hex.decodeHex(device.getTelemetryId()));
        buffer.writeBytes(Hex.decodeHex(device.getPassword()));

        byte[] data = new byte[buffer.readableBytes()];
        buffer.readBytes(data);
        buffer.release();
        return data;
    }

    public static byte[] concat(byte[] first, byte[]... datas) {
        int length = first.length;
        for (byte[] data : datas) {
            length += data.length;
        }
        byte[] result = new byte[length];
        System.arraycopy(first, 0, result, 0, first.length);
        int offset = first.length;
        for (byte[] data : datas) {
            System.arraycopy(data, 0, result, offset, data.length);
            offset += data.length;
        }
        return result;
    }

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
            throw new RuntimeException("hex string to byte array error. " + str);
        }
        int dataLen = length / 2;
        byte[] data = new byte[dataLen];
        for (int i = 0; i < dataLen; i++) {
            String substring = str.substring(i * 2, (i + 1) * 2);
            data[i] = (byte) Integer.parseInt(substring, 16);
        }
        return data;
    }


    public static void main(String[] args) {
        // 7E7E01001234567812342F000802000359101115511103 6BCA
        //var data = hexStringToByteArray("7E7E01001234567812342F000802000359101115511103");
        //System.out.println(CrcUtil.calcCrc16(data));
        //
        //byte[] bytes = CrcUtil.calcCrc16Bytes(data);
        //System.out.println(byteArray2HexStr(bytes));
        //
        calcCrc16_1();
    }

    private static void calcCrc16_1() {
        String hex = """
                7E7E 00 1122334405 03E8 50 0 04F
                  02
                  0040 170718102149
                  F1F1 1122334405
                  1111
                  2222
                  3333
                  4444
                  5555
                  6666
                  7777
                  8888
                  9999
                  1111
                  2222
                  3333
                  4444
                  5555
                  6666
                  7777
                  8888
                  9999
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  0000
                  05
                """;
        hex = hex.replace(" ", "").replace("\r\n", "").replace("\n", "");
        byte[] bytes = hexStringToByteArray(hex);
        byte[] crc = CrcUtil.calcCrc16Bytes(bytes);
        System.out.print(byteArray2HexStr(bytes));
        System.out.print(byteArray2HexStr(crc));
        System.out.println();
    }
}
