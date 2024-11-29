package me.yuxiaoyao.sl651.netty.server.read;

import io.netty.buffer.ByteBuf;
import me.yuxiaoyao.sl651.netty.server.message.item.DisableStationChannelConfig;
import me.yuxiaoyao.sl651.netty.server.message.item.IStationChannelConfig;
import me.yuxiaoyao.sl651.netty.server.message.item.Ip4StationChannelConfig;
import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
import me.yuxiaoyao.sl651.netty.server.util.HexUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author KerryMiBook on 24/11/9
 */


public class DynamicStationChannelConfigReader extends AbstractDynamic53LengthReader<IStationChannelConfig> {

    @Override
    protected IStationChannelConfig doRead(int dataLen, int decLen, ByteBuf buf) {
        var type = buf.readByte() & 0xFF;
        switch (type) {
            case 0x00 -> {
                buf.skipBytes(dataLen - 1);
                return new DisableStationChannelConfig();
            }
            case 0x01 -> {
                // 短信
                buf.skipBytes(dataLen - 1);
                //TODO
                return null;
            }
            case 0x02 -> {
                // IPv4
                byte[] ipArr = ByteArrayUtil.readByteBuf(buf, 6);
                byte[] portArr = ByteArrayUtil.readByteBuf(buf, 3);

                String ip = HexUtil.byteArray2HexStr(ipArr);

                var port = Integer.parseInt(HexUtil.byteArray2HexStr(portArr), 16);

                List<String> ips = new ArrayList<>(4);
                for (int i = 0; i < ip.length(); i += 3) {
                    var tmp = ip.substring(i, i + 3);
                    ips.add(Integer.toHexString(Integer.parseInt(tmp, 16)));
                }

                var ipv4 = String.join(".", ips) + ":" + port;

                return new Ip4StationChannelConfig(ipv4);
            }
            case 0x03 -> {
                // 北斗
                buf.skipBytes(dataLen - 1);
                //TODO
                return null;
            }
            case 0x04 -> {
                // 海事卫星
                buf.skipBytes(dataLen - 1);
                //TODO
                return null;
            }
            case 0x05 -> {
                // PSTN
                buf.skipBytes(dataLen - 1);
                //TODO
                return null;
            }
            case 0x06 -> {
                // 超短波
                buf.skipBytes(dataLen - 1);
                //TODO
                return null;
            }
        }

        throw new RuntimeException("未知的通道配置类型. type = " + Integer.toHexString(type));
    }
}
