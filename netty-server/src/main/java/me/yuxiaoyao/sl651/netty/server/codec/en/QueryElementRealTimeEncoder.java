package me.yuxiaoyao.sl651.netty.server.codec.en;


import me.yuxiaoyao.sl651.netty.server.codec.BaseFuncEncoder;
import me.yuxiaoyao.sl651.netty.server.message.send.QueryElementRealTimeSendMessage;

/**
 * @author kerryzhang on 2024/11/18
 */

public class QueryElementRealTimeEncoder implements BaseFuncEncoder<QueryElementRealTimeSendMessage> {
    public static QueryElementRealTimeEncoder INSTANCE = new QueryElementRealTimeEncoder();
}
