package me.yuxiaoyao.sl651.netty.server.message;


import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;

import java.util.List;

/**
 * @author kerryzhang on 2024/11/11
 */

public interface IElementMessage {

    List<ElementItem> getElements();

}
