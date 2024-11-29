//package me.yuxiaoyao.sl651.netty.server.define;
//
//
//import io.netty.buffer.ByteBuf;
//import lombok.Getter;
//import me.yuxiaoyao.sl651.netty.server.util.ByteArrayUtil;
//import me.yuxiaoyao.sl651.netty.server.util.HexUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import java.util.Arrays;
//
///**
// * @author kerryzhang on 2024/11/07
// */
//
//@Getter
//public class NumberDataDefine extends AbstractDynamicLengthDataDefine<Number> {
//
//    private static final Logger logger = LoggerFactory.getLogger(NumberDataDefine.class);
//
//    public static final NumberDataDefine INSTANCE_2_0 = new NumberDataDefine(2, 0);
//    public static final NumberDataDefine INSTANCE_2_1 = new NumberDataDefine(2, 1);
//    public static final NumberDataDefine INSTANCE_3_0 = new NumberDataDefine(3, 0);
//    public static final NumberDataDefine INSTANCE_3_1 = new NumberDataDefine(3, 1);
//    public static final NumberDataDefine INSTANCE_3_2 = new NumberDataDefine(3, 2);
//    public static final NumberDataDefine INSTANCE_4_0 = new NumberDataDefine(4, 0);
//    public static final NumberDataDefine INSTANCE_4_1 = new NumberDataDefine(4, 1);
//    public static final NumberDataDefine INSTANCE_4_2 = new NumberDataDefine(4, 2);
//    public static final NumberDataDefine INSTANCE_5_0 = new NumberDataDefine(5, 0);
//    public static final NumberDataDefine INSTANCE_5_1 = new NumberDataDefine(5, 1);
//    public static final NumberDataDefine INSTANCE_5_2 = new NumberDataDefine(5, 2);
//    public static final NumberDataDefine INSTANCE_5_3 = new NumberDataDefine(5, 3);
//    public static final NumberDataDefine INSTANCE_6_0 = new NumberDataDefine(6, 0);
//    public static final NumberDataDefine INSTANCE_6_1 = new NumberDataDefine(6, 1);
//    public static final NumberDataDefine INSTANCE_6_2 = new NumberDataDefine(6, 2);
//    public static final NumberDataDefine INSTANCE_6_3 = new NumberDataDefine(6, 3);
//    public static final NumberDataDefine INSTANCE_6_4 = new NumberDataDefine(6, 4);
//    public static final NumberDataDefine INSTANCE_7_3 = new NumberDataDefine(7, 3);
//    public static final NumberDataDefine INSTANCE_7_4 = new NumberDataDefine(7, 4);
//    public static final NumberDataDefine INSTANCE_7_5 = new NumberDataDefine(7, 5);
//    public static final NumberDataDefine INSTANCE_8_2 = new NumberDataDefine(8, 2);
//    public static final NumberDataDefine INSTANCE_9_3 = new NumberDataDefine(9, 3);
//    public static final NumberDataDefine INSTANCE_10_2 = new NumberDataDefine(10, 2);
//    public static final NumberDataDefine INSTANCE_11_3 = new NumberDataDefine(11, 3);
//
//    private final int defineDataLen;
//    private final int defineDecLen;
//
//    public NumberDataDefine(int defineDataLen, int defineDecLen) {
//        this.defineDataLen = defineDataLen;
//        this.defineDecLen = defineDecLen;
//    }
//
//    @Override
//    protected Number doRead(int dataLen, int decLen, ByteBuf buf) {
//        byte[] data = ByteArrayUtil.readByteBuf(buf, dataLen);
//        try {
//            if (data[0] == (byte) 0xAA && data[1] == (byte) 0xAA) {
//                //读取数据无效
//                logger.warn("读取Num数据无效, dataLen: {}, decLen: {}", dataLen, decLen);
//            } else {
//                int flag = 1;
//                if (data[0] == (byte) 0xFF) {
//                    flag = -1;
//                    data = Arrays.copyOfRange(data, 1, data.length);
//                }
//                String hexVal = HexUtil.byteArray2HexStr(data);
//                var value = Double.parseDouble(hexVal) * flag;
//
//                // 1. 感觉这里写错了...
//                //if (Double.compare(value, 1.0) != 0) {
//                //    value = value / Math.pow(10, decLen);
//                //}
//
//                // 2. 使用这种方式替换
//                if (decLen == 0) {
//                    // 没有小数点.整数
//                    if (value > Integer.MAX_VALUE) {
//                        return (long) value;
//                    }
//                    return (int) value;
//                }
//                value = value / Math.pow(10, decLen);
//                return value;
//            }
//        } catch (RuntimeException e) {
//            logger.error("解析Num数据异常, dataLen: {}, decLen: {}, cause: {}", dataLen, decLen, e.getMessage());
//        }
//        return null;
//    }
//
//    @Override
//    public ByteBuf write(Number data) {
//        return null;
//    }
//}
