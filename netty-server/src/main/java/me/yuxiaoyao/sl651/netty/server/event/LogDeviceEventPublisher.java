package me.yuxiaoyao.sl651.netty.server.event;


import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.message.IElementMessage;
import me.yuxiaoyao.sl651.netty.server.message.ImageCollectMessage;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryConfigMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.ElementItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.List;

/**
 * @author kerryzhang on 2024/11/06
 */

public class LogDeviceEventPublisher implements DeviceEventPublisher {

    private static final Logger logger = LoggerFactory.getLogger(LogDeviceEventPublisher.class);

    private final String saveDir = "./.image";

    public LogDeviceEventPublisher() {
        File dir = new File(this.saveDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void onConnect(String id) {
        logger.info("onConnect. id = {}", id);
    }

    @Override
    public void onMessageReport(MessageHeader header, Object decodeMessage) {
        logger.info("onReceive. header = {}", header);

        if (decodeMessage instanceof IElementMessage ceMessage) {
            List<ElementItem> elements = ceMessage.getElements();
            for (int i = 0; i < elements.size(); i++) {
                logger.info("{}/{}, CodingElementItem = {}", i + 1, elements.size(), elements.get(i));
            }
        } else if (decodeMessage instanceof TelemetryConfigMessage configMessage) {
            var configs = configMessage.getConfigs();
            for (int i = 0; i < configs.size(); i++) {
                logger.info("{}/{}, Config = {}", i + 1, configs.size(), configs.get(i));
            }
        } else if (decodeMessage instanceof ImageCollectMessage imgMessage) {
            // 保存文件
            byte[] image = imgMessage.getImage();
            var f = new File(saveDir, imgMessage.getTelemetryId() + "_" + imgMessage.getSerialNo() + ".jpg");
            logger.info("保存文件: {}", f);
            try {
                Files.write(f.toPath(), image, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            logger.info("onReceive. decodeMessage = {}", decodeMessage);
        }
    }

    @Override
    public void onDisconnect(String id) {
        logger.info("onDisconnect. id = {}", id);
    }
}
