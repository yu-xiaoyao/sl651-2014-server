//package me.yuxiaoyao.sl651.netty.server.define;
//
//
//import io.netty.buffer.ByteBuf;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * @author kerryzhang on 2024/11/07
// */
//
//public class ByteItemCountDataDefine extends AbstractDynamicLengthDataDefine<List<Object>> {
//
//    private static final Logger logger = LoggerFactory.getLogger(ByteItemCountDataDefine.class);
//
//    @Override
//    public ByteBuf write(List<Object> data) {
//        return null;
//    }
//
//    public enum ItemType {
//        BCD, INT, DEC
//    }
//
//    private final ItemType itemType;
//    private final int itemSize;
//    private final int itemCount;
//    private final int totalSize;
//
//    private NumberDataDefine decDataDefine;
//
//    public ByteItemCountDataDefine(int itemCount, int itemSize) {
//        this.itemType = ItemType.INT;
//        this.itemSize = itemSize;
//        this.itemCount = itemCount;
//        this.totalSize = itemCount * itemSize;
//
//    }
//
//    public ByteItemCountDataDefine(int itemCount, int itemSize, ItemType itemType) {
//        this.itemSize = itemSize;
//        this.itemCount = itemCount;
//        this.totalSize = itemCount * itemSize;
//        this.itemType = itemType;
//    }
//
//    public ByteItemCountDataDefine(int itemCount, int itemSize, NumberDataDefine decDataDefine) {
//        this.itemSize = itemSize;
//        this.itemCount = itemCount;
//        this.totalSize = itemCount * itemSize;
//        this.itemType = ItemType.DEC;
//        this.decDataDefine = decDataDefine;
//    }
//
//
//    @Override
//    protected List<Object> doRead(int dataLen, int decLen, ByteBuf buf) {
//        if (dataLen != totalSize) {
//            throw new RuntimeException("解析数据长度和预期数据长度不一致. 预期数据长度 = " + totalSize + ", 解析长度 = " + dataLen);
//        }
//
//        List<Object> list = new ArrayList<>(itemCount);
//        for (int i = 0; i < itemCount; i++) {
//            //System.out.println(ByteBufUtil.hexDump(buf));
//            list.add(readNextValue(buf, decLen));
//        }
//        return list;
//    }
//
//    private Object readNextValue(ByteBuf buf, int decLen) {
//        if (itemType == ItemType.BCD) {
//
//        } else if (itemType == ItemType.INT) {
//            // num
//            if (itemSize == 1) {
//                return buf.readByte() & 0xFF;
//            } else if (itemSize == 2) {
//                return buf.readShort() & 0xFFFF;
//            } else if (itemSize == 3) {
//                return buf.readMedium();
//            } else if (itemSize == 4) {
//                return buf.readInt();
//            } else if (itemSize == 8) {
//                return buf.readLong();
//            } else {
//                throw new RuntimeException("更多位的整数未实现");
//            }
//        } else if (itemType == ItemType.DEC) {
//            return decDataDefine.read(buf);
//        }
//        throw new RuntimeException("未实现");
//    }
//}
