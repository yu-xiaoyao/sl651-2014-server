package me.yuxiaoyao.sl651.client;


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kerryzhang on 2024/11/13
 */

public class M3PacketSplitter {

    public static List<byte[]> splitImageToM3Packets(DeviceConfig device, byte[] bytes, int serialNo) {
        return splitImageToM3Packets(device, bytes, 4000, serialNo);
    }

    public static List<byte[]> splitImageToM3Packets(DeviceConfig device, byte[] bytes, int splitSize, int serialNo) {
        splitSize = Math.min(splitSize, 4000);
        byte[][] split = split(bytes, splitSize);

        var total = split.length;

        byte[] prefix = Utils.getDevicePrefix(device);
        List<byte[]> list = new ArrayList<>(total);
        for (int i = 0; i < total; i++) {
            byte[] packet = split[i];
            int endCode = 0x17;
            if (i == total - 1) {
                // end packet
                endCode = 0x03;
            }

            var seqNo = i + 1;


            ByteBuf sendDataBuf = Unpooled.buffer();
            int totalSeqNo = (total << 12) | (seqNo & 0xFFF);
            sendDataBuf.writeMedium(totalSeqNo);
            if (i == 0) {
                // 流水号
                sendDataBuf.writeShort(serialNo);
                var first = Utils.getSendTime()
                        + "F1F1" + device.getTelemetryId()
                        + device.getStationType()
                        + "F0F0" + Utils.getObserverTime()
                        + "F3F3";
                sendDataBuf.writeBytes(Utils.hexStringToByteArray(first));
            }
            sendDataBuf.writeBytes(packet);
            int rawDataLen = sendDataBuf.readableBytes();


            ByteBuf buffer = Unpooled.buffer();
            buffer.writeBytes(prefix);
            buffer.writeByte(0x36); // 功能码
            buffer.writeShort(rawDataLen); // 数据长度
            buffer.writeByte(0x16); // 数据起始符
            buffer.writeBytes(sendDataBuf);
            buffer.writeByte(endCode);

            sendDataBuf.release();

            byte[] data = new byte[buffer.readableBytes()];
            buffer.readBytes(data);
            buffer.release();

            // calc crc
            byte[] crc = CrcUtil.calcCrc16Bytes(data);

            byte[] sd = new byte[data.length + 2];
            System.arraycopy(data, 0, sd, 0, data.length);
            System.arraycopy(crc, 0, sd, data.length, 2);

            list.add(sd);
        }

        return list;
    }


    private static byte[][] split(byte[] bytes, int size) {
        int len = bytes.length;
        int count = len / size;
        int mod = len % size;
        if (mod != 0) {
            count += 1;
        }
        byte[][] result = new byte[count][];
        for (int i = 0; i < count; i++) {
            int start = i * size;
            int end = (i + 1) * size;
            if (end > len) {
                end = len;
            }
            result[i] = new byte[end - start];
            System.arraycopy(bytes, start, result[i], 0, result[i].length);
        }
        return result;
    }
}
