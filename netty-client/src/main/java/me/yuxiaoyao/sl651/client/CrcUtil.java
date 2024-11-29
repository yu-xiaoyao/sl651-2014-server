package me.yuxiaoyao.sl651.client;


/**
 * @author kerryzhang on 2024/11/06
 */

public class CrcUtil {

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


    public static void main(String[] args) {
        String hex = """
                7E7E
                05
                0011223344
                1234
                44
                0 035
                02
                0020
                170718110226
                F1F1 0011223344 48
                F0F0 1707181005
                7019022003
                7119022003
                7219022003
                7319022003
                7419022003
                7519022003
                03""".replace("\n", "").replace("\r\n", "").replace(" ", "");
        byte[] bytes = Utils.hexStringToByteArray(hex);
        byte[] crc = calcCrc16Bytes(bytes);
        System.out.print(Utils.byteArray2HexStr(bytes));
        System.out.print(Utils.byteArray2HexStr(crc));
        System.out.println();
    }
}
