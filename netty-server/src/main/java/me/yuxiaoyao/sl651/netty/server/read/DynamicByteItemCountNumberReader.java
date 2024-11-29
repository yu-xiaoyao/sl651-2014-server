package me.yuxiaoyao.sl651.netty.server.read;


import io.netty.buffer.ByteBuf;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kerryzhang on 2024/11/20
 */

@Getter
public class DynamicByteItemCountNumberReader extends AbstractDynamic53LengthReader<List<Number>> {

    private final int itemSize;
    private final int itemCount;
    private final int totalSize;
    private final Double divisor;

    public DynamicByteItemCountNumberReader(int itemCount, int itemSize) {
        this(itemCount, itemSize, null);
    }

    public DynamicByteItemCountNumberReader(int itemCount, int itemSize, Double divisor) {
        this.itemSize = itemSize;
        this.itemCount = itemCount;
        this.totalSize = itemCount * itemSize;
        this.divisor = divisor;
    }

    @Override
    protected List<Number> doRead(int dataLen, int decLen, ByteBuf buf) {
        if (dataLen != totalSize) {
            throw new RuntimeException("解析数据长度和预期数据长度不一致. 预期数据长度 = " + totalSize + ", 解析长度 = " + dataLen);
        }

        List<Number> list = new ArrayList<>(itemCount);
        for (int i = 0; i < itemCount; i++) {
            //System.out.println(ByteBufUtil.hexDump(buf));
            list.add(readNextValue(buf, decLen));
        }
        return list;
    }

    private Number readNextValue(ByteBuf buf, int decLen) {
        if (itemSize <= 4) {
            int value;
            if (itemSize == 1) {
                value = buf.readByte() & 0xFF;
            } else if (itemSize == 2) {
                value = buf.readShort() & 0xFFFF;
            } else if (itemSize == 3) {
                value = buf.readMedium();
            } else {
                value = buf.readInt();
            }

            if (decLen == 0) {
                // 没有小数点.整数
                if (divisor == null) {
                    return value;
                }
                return value * divisor;
            } else {
                var ret = value / Math.pow(10, decLen);
                if (divisor == null) {
                    return value;
                }
                return ret * divisor;
            }
        }

        if (itemSize == 8) {
            var value = buf.readLong();
            if (divisor == null) {
                return value;
            }
            return value * divisor;
        }
        throw new RuntimeException("更多位的整数未实现");
    }
}
