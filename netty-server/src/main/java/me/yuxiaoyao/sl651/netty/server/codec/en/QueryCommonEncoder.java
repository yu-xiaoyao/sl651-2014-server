package me.yuxiaoyao.sl651.netty.server.codec.en;


import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncEncoder;
import me.yuxiaoyao.sl651.netty.server.message.send.BaseSendMessage;

/**
 * @author kerryzhang on 2024/11/18
 */


public class QueryCommonEncoder implements BaseFuncEncoder<BaseSendMessage> {
    public static QueryCommonEncoder INSTANCE = new QueryCommonEncoder();
}
