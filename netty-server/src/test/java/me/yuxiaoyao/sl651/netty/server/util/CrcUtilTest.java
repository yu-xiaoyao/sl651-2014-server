package me.yuxiaoyao.sl651.netty.server.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author kerryzhang on 2024/11/13
 */

class CrcUtilTest {

    @Test
    void calcCrc16_1() {
        // 7E7E01001234567812342F0008020003591011155111036BCA
        String hex = "7E7E01001234567812342F000802000359101115511103";
        byte[] bytes = HexUtil.hexStringToByteArray(hex);
        byte[] result = CrcUtil.calcCrc16Bytes(bytes);
        Assertions.assertArrayEquals(new byte[]{0x6B, (byte) 0xCA}, result);
    }

    @Test
    void calcCrc16_2() {
        // 7E7E00987654321012342F0008027E0822101110074703C3AE
        String hex = "7E7E00987654321012342F0008027E0822101110074703";
        byte[] bytes = HexUtil.hexStringToByteArray(hex);
        byte[] result = CrcUtil.calcCrc16Bytes(bytes);
        Assertions.assertArrayEquals(new byte[]{(byte) 0xC3, (byte) 0xAE}, result);
    }


    @Test
    void calcCrc16_3() {
        // 7E7E05001122334403E832002B020034170718110016F1F1001122334448F0F01707181100201900004026190000403923000104903812109903A421
        String hex = "7E7E05001122334403E832002B020034170718110016F1F1001122334448F0F01707181100201900004026190000403923000104903812109903";
        byte[] bytes = HexUtil.hexStringToByteArray(hex);
        byte[] result = CrcUtil.calcCrc16Bytes(bytes);

        Assertions.assertArrayEquals(new byte[]{(byte) 0xA4, (byte) 0x21}, result);
    }


}