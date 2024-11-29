package me.yuxiaoyao.sl651.netty.server.service;


import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import lombok.*;
import me.yuxiaoyao.sl651.netty.server.codec.en.*;
import me.yuxiaoyao.sl651.netty.server.common.DeviceSendInfo;
import me.yuxiaoyao.sl651.netty.server.common.SendResult;
import me.yuxiaoyao.sl651.netty.server.enums.ControlChar;
import me.yuxiaoyao.sl651.netty.server.enums.SendStateEnum;
import me.yuxiaoyao.sl651.netty.server.header.MessageHeader;
import me.yuxiaoyao.sl651.netty.server.header.Messages;
import me.yuxiaoyao.sl651.netty.server.message.*;
import me.yuxiaoyao.sl651.netty.server.message.common.ConfigMessageItem;
import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;
import me.yuxiaoyao.sl651.netty.server.message.rec.ReceiveMessage;
import me.yuxiaoyao.sl651.netty.server.message.send.BaseSendMessage;
import me.yuxiaoyao.sl651.netty.server.message.send.QueryElementRealTimeSendMessage;
import me.yuxiaoyao.sl651.netty.server.message.send.QueryTimeRangeSendMessage;
import me.yuxiaoyao.sl651.netty.server.message.send.UpdatePasswordSendMessage;
import me.yuxiaoyao.sl651.netty.server.tcp.TcpSessionManager;
import me.yuxiaoyao.sl651.netty.server.telemetry.TelemetryBaseConfigTable;
import me.yuxiaoyao.sl651.netty.server.telemetry.TelemetryRuntimeConfigTable;
import me.yuxiaoyao.sl651.netty.server.util.DateTimeUtil;
import me.yuxiaoyao.sl651.netty.server.util.SerialNumbers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author kerryzhang on 2024/11/18
 */

public class DeviceSendService {

    private static final Logger logger = LoggerFactory.getLogger(DeviceSendService.class);

    public static DeviceSendService get() {
        return DeviceSendService.Holder.INSTANCE;
    }


    private static class Holder {
        private static final DeviceSendService INSTANCE = new DeviceSendService();
    }

    private final Map<DeviceRwMessageKey, SynchronousQueue<ReceiveMessage<? extends BaseMessage>>> sendQueue = new ConcurrentHashMap<>();

    @Setter
    private long defaultWaitTimeout = 15 * 1000L;

    public void onReceiveMessage(ReceiveMessage<BaseMessage> rm) {
        logger.info("读写回复. header = {}, body = {}", rm.getHeader(), rm.getBody());
        var deviceKey = makeReceiveDeviceKey(rm);
        var queue = sendQueue.get(deviceKey);
        if (queue != null) {
            queue.offer(rm);
        }
    }

    /**
     * 6.6.4.10 中心站查询遥测站实时数据
     * 0x37
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, RealTimeMessage> queryRealTime(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x37);
        //TODO 测试流水号
        var serialNo = 0x09;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.11 中心站查询遥测站时段数据
     * 0x38
     *
     * @param deviceInfo
     * @param startTime     格式: yyMMddHH
     * @param endTime       格式: yyMMddHH
     * @param timeStep      时间步长码
     * @param eleIdentifier 要素标识
     * @return
     */
    public SendResult<QueryTimeRangeSendMessage, TelemetryTimeStepMessage> queryTimeRange(DeviceSendInfo deviceInfo,
                                                                                          LocalDateTime startTime,
                                                                                          LocalDateTime endTime,
                                                                                          TimeStepValue timeStep,
                                                                                          int eleIdentifier) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryTimeRangeEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x39);
        //TODO 测试流水号
        var serialNo = 0x14;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        var sendMessage = QueryTimeRangeSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .startTime(startTime)
                .endTime(endTime)
                .timeStep(timeStep)
                .eleIdentifier(eleIdentifier)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.12 中心站查询遥测站人工置数
     * 0x39
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, DeviceManualSetMessage> queryManualSet(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x39);
        //TODO 测试流水号
        var serialNo = 0x14;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }


    /**
     * 6.6.4.13 中心站查询遥测站指定要素实时数据
     * 0x3A
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, DeviceManualSetMessage> queryElementRealTime(DeviceSendInfo deviceInfo, int... elements) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryElementRealTimeEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x3A);
        //TODO 测试流水号
        var serialNo = 0x14;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        var sendMessage = QueryElementRealTimeSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .elements(elements)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }


    /**
     * 6.6.4.14 中心站修改遥测站基本配置表
     * 0x40
     *
     * @param deviceSendInfo
     * @param items          flag: 基本配置: {@link TelemetryBaseConfigTable#DATA_DEFINE_MAP}
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> updateBaseConfig(DeviceSendInfo deviceSendInfo, ConfigMessageItem... items) {
        //TODO
        return null;
    }


    /**
     * 6.6.4.15 中心站读取遥测站基本配置表/遥测站自报基本配置表
     * 0x41
     * 仅读取.
     *
     * @param deviceSendInfo
     * @param configParams   基本配置: {@link TelemetryBaseConfigTable#DATA_DEFINE_MAP}
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> queryBaseConfig(DeviceSendInfo deviceSendInfo, int... configParams) {
        //TODO
        return null;
    }


    /**
     * 6.6.4.16 中心站修改遥测站运行参数配置表
     * 0x42
     *
     * @param deviceSendInfo
     * @param items          flag: 运行配置. {@link TelemetryRuntimeConfigTable#DATA_DEFINE_MAP}
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> updateRuntimeConfig(DeviceSendInfo deviceSendInfo, ConfigMessageItem... items) {
        //TODO
        return null;
    }

    /**
     * 6.6.4.17 中心站读取遥测站运行参数配置表/遥测站自报运行参数配置表
     * 0x43
     * 仅读取.
     *
     * @param deviceSendInfo
     * @param configParams   运行配置. {@link TelemetryRuntimeConfigTable#DATA_DEFINE_MAP}
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> queryRuntimeConfig(DeviceSendInfo deviceSendInfo, int... configParams) {
        //TODO
        return null;
    }


    /**
     * 6.6.4.18 中心站查询水泵电机实时工作数据
     * 0x44
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, WaterPumpRealTimeMessage> queryWaterPumpRealTime(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x44);
        //TODO 测试流水号
        var serialNo = 0x0020;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.19 中心站查询遥测站软件版本
     * 0x45
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, TelemetrySoftVersionMessage> querySoftVersion(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x45);
        //TODO 测试流水号
        //var serialNo = 0x0022;
        var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.20 中心站查询遥测站状态和报警信息
     * 0x46
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, TelemetryStateAndAlarmMessage> queryStateAndAlarm(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x46);
        //TODO 测试流水号
        var serialNo = 0x23;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.21 初始化固态存储数据
     * 0x47
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, TelemetryIdMessage> initDataStorage(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = InitDataStorageEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x47);
        //TODO 测试流水号
        var serialNo = 0x3A;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        var sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }


    /**
     * 6.6.4.22 恢复遥测站出厂设置
     * 0x48
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, TelemetryIdMessage> restoreFactorySettings(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = RestoreFactorySettingsEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x48);
        //TODO 测试流水号
        var serialNo = 0x3B;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        var sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.23 修改密码
     * 0x49
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, UpdatePasswordMessage> updatePassword(DeviceSendInfo deviceInfo, String newPassword) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = UpdatePasswordEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x49);
        //TODO 测试流水号
        var serialNo = 0x24;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        var sendMessage = UpdatePasswordSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .oldPassword(deviceInfo.getPassword())
                .newPassword(newPassword)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }

    /**
     * 6.6.4.24 设置遥测站时钟
     * 0x4A
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> updateClock(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x4A);
        //TODO 测试流水号
        var serialNo = 0x25;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        var sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        SendResult<BaseSendMessage, BaseMessage> result = doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
        //TODO 若遥测站原时间与校时时间差大于5分钟，应进行2次校时
        return result;
    }

    /**
     * 6.6.4.30 中心站查询遥测站事件记录
     * 0x50
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> queryEventRecord(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x50);
        //TODO 测试流水号
        var serialNo = 0x40;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }


    /**
     * 6.6.4.31 中心站查询遥测站时钟
     * 0x51
     *
     * @param deviceInfo
     * @return
     */
    public SendResult<BaseSendMessage, BaseMessage> queryClock(DeviceSendInfo deviceInfo) {
        String deviceId = deviceInfo.getDeviceId();
        var encoder = QueryCommonEncoder.INSTANCE;
        MessageHeader header = buildSendMessageHeader(deviceInfo.getStationId(), deviceId, deviceInfo.getPassword(), 0x51);
        //TODO 测试流水号
        var serialNo = 0x027E;
        //var serialNo = SerialNumbers.nextSerialNumber(deviceId);
        BaseSendMessage sendMessage = BaseSendMessage.builder()
                .header(header)
                .sendTime(DateTimeUtil.getSendTime())
                .serialNo(serialNo)
                .build();
        byte[] encode = encoder.encode(sendMessage);
        return doSend(deviceId, sendMessage, encode, defaultWaitTimeout);
    }


    private <S extends BaseSendMessage, R extends BaseMessage> SendResult<S, R> doSend(String deviceId,
                                                                                       S sendMessage,
                                                                                       byte[] sendBytes,
                                                                                       long timeout) {
        var channelId = TcpSessionManager.get(deviceId);
        if (channelId == null) {
            return SendResult.offline(sendMessage);
        }
        Channel channel = TcpSessionManager.TCP_CHANNEL_GROUP.find(channelId);
        if (channel == null) {
            TcpSessionManager.remove(deviceId);
            return SendResult.offline(sendMessage);
        }

        if (channel.isActive() && channel.isOpen() && channel.isWritable()) {
            DeviceRwMessageKey sendDeviceKey = makeSendDeviceKey(sendMessage);
            var synchronousQueue = new SynchronousQueue<ReceiveMessage<? extends BaseMessage>>();
            sendQueue.put(sendDeviceKey, synchronousQueue);
            logger.atDebug().log("开始发送消息. deviceId = {}, sendMessage = {}", deviceId, sendMessage);
            try {
                channel.writeAndFlush(Unpooled.copiedBuffer(sendBytes));
                var poll = synchronousQueue.poll(timeout, TimeUnit.MILLISECONDS);
                if (poll != null) {
                    SendResult<S, R> sr = new SendResult<>();
                    sr.setState(SendStateEnum.SUCCESS);
                    sr.setResult((R) poll.getBody());
                    sr.setSend(sendMessage);
                    return sr;
                }
                logger.warn("发送消息等待超时. deviceId = {}, sendMessage = {}", deviceId, sendMessage);
                return SendResult.ofFailed(SendStateEnum.TIMEOUT, sendMessage);
            } catch (InterruptedException e) {
                logger.error("发送消息异常. deviceId = {}, sendMessage = {}, cause: {}", deviceId, sendMessage, e.getMessage());
            } finally {
                sendQueue.remove(sendDeviceKey);
            }
            return SendResult.ofFailed(SendStateEnum.SEND_FAILED, sendMessage);
        }
        logger.warn("设备 Channel 状态异常. deviceId = {} ", deviceId);
        return SendResult.ofFailed(SendStateEnum.SEND_FAILED, sendMessage);
    }


    private MessageHeader buildSendMessageHeader(int stationId, String deviceId, String password, int funcCode) {
        return MessageHeader.builder()
                .startFrame(Messages.START_CODE)
                .stationId(stationId)
                .telemetryId(deviceId)
                .password(password)
                .funcCode(funcCode)
                .isM3Mode(false)
                .packetStartCode(ControlChar.STX)
                .packetEndCode(ControlChar.ENQ)
                .build();
    }


    private DeviceRwMessageKey makeSendDeviceKey(BaseSendMessage sendMessage) {
        return DeviceRwMessageKey.builder()
                .stationId(sendMessage.getHeader().getStationId())
                .telemetryId(sendMessage.getHeader().getTelemetryId())
                .serialNo(sendMessage.getSerialNo())
                .build();
    }

    private DeviceRwMessageKey makeReceiveDeviceKey(ReceiveMessage<BaseMessage> receiveMessage) {
        return DeviceRwMessageKey.builder()
                .stationId(receiveMessage.getHeader().getStationId())
                .telemetryId(receiveMessage.getHeader().getTelemetryId())
                .serialNo(receiveMessage.getBody().getSerialNo())
                .build();
    }

    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @Data
    private static class DeviceRwMessageKey {
        private int stationId;
        private String telemetryId;
        private int serialNo;
    }

}
