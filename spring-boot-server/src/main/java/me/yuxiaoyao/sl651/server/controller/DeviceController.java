package me.yuxiaoyao.sl651.server.controller;


import lombok.RequiredArgsConstructor;
import me.yuxiaoyao.sl651.netty.server.message.TelemetryStateAndAlarmMessage;
import me.yuxiaoyao.sl651.netty.server.message.item.TimeStepValue;
import me.yuxiaoyao.sl651.netty.server.service.DeviceSendService;
import me.yuxiaoyao.sl651.server.common.ResultResponse;
import me.yuxiaoyao.sl651.server.service.DeviceService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * @author kerryzhang on 2024/11/18
 */

@RequestMapping("/device")
@RestController
@RequiredArgsConstructor
public class DeviceController {

    private static final Logger logger = LoggerFactory.getLogger(DeviceController.class);

    private final DeviceService deviceService;

    private final DeviceSendService deviceSendService = DeviceSendService.get();


    // 6.6.4.10 中心站查询遥测站实时数据
    @GetMapping("/queryRealTime/{deviceId}")
    public ResultResponse<?> queryRealTime(@PathVariable("deviceId") String deviceId) {
        // 0x37
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryRealTime(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.11 中心站查询遥测站时段数据
    @GetMapping("/queryTimeRange/{deviceId}")
    public ResultResponse<?> queryTimeRange(@PathVariable("deviceId") String deviceId,
                                            @RequestParam("startTime") @DateTimeFormat(pattern = "yyMMddHH") LocalDateTime startTime,
                                            @RequestParam("startTime") @DateTimeFormat(pattern = "yyMMddHH") LocalDateTime endTime) {
        // 0x38
        var info = deviceService.getById(deviceId);
        //var startTime = DateTimeUtil.getTimeRange("17071810");
        //var endTime = DateTimeUtil.getTimeRange("17071811");
        var result = deviceSendService.queryTimeRange(info,
                startTime, endTime,
                TimeStepValue.ofMinute(5), 0x06);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.12 中心站查询遥测站人工置数
    @GetMapping("/queryManualSet/{deviceId}")
    public ResultResponse<?> queryManualSet(@PathVariable("deviceId") String deviceId) {
        // 0x39
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryManualSet(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.13 中心站查询遥测站指定要素实时数据
    @GetMapping("/queryElementRealTime/{deviceId}")
    public ResultResponse<?> queryElementRealTime(@PathVariable("deviceId") String deviceId) {
        // 0x3A
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryElementRealTime(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    //TODO
    // 6.6.4.14 中心站修改遥测站基本配置表
    // 6.6.4.15 中心站读取遥测站基本配置表/遥测站自报基本配置表
    // 6.6.4.16 中心站修改遥测站运行参数配置表
    // 6.6.4.17 中心站读取遥测站运行参数配置表/遥测站自报运行参数配置表

    // 6.6.4.18 中心站查询水泵电机实时工作数据
    @GetMapping("/queryWaterPumpRealTime/{deviceId}")
    public ResultResponse<?> queryWaterPumpRealTime(@PathVariable("deviceId") String deviceId) {
        // 0x44
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryWaterPumpRealTime(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.19 中心站查询遥测站软件版本
    @GetMapping("/querySoftVersion/{deviceId}")
    public ResultResponse<String> querySoftVersion(@PathVariable("deviceId") String deviceId) {
        // 0x45
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.querySoftVersion(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult().getVersion());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.20 中心站查询遥测站状态和报警信息
    @GetMapping("/queryStateAndAlarm/{deviceId}")
    public ResultResponse<TelemetryStateAndAlarmMessage> queryStateAndAlarm(@PathVariable("deviceId") String deviceId) {
        // 0x46
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryStateAndAlarm(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.21 初始化固态存储数据
    @GetMapping("/initDataStorage/{deviceId}")
    public ResultResponse<?> initDataStorage(@PathVariable("deviceId") String deviceId) {
        // 0x47
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.initDataStorage(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.22 恢复遥测站出厂设置
    @GetMapping("/restoreFactorySettings/{deviceId}")
    public ResultResponse<?> restoreFactorySettings(@PathVariable("deviceId") String deviceId) {
        // 0x48
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.restoreFactorySettings(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.23 修改密码
    @GetMapping("/updatePassword/{deviceId}")
    public ResultResponse<?> updatePassword(@PathVariable("deviceId") String deviceId) {
        // 0x49
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.updatePassword(info, "1122");
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.24 设置遥测站时钟
    @GetMapping("/updateClock/{deviceId}")
    public ResultResponse<?> updateClock(@PathVariable("deviceId") String deviceId) {
        // 0x4A
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.updateClock(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.25 设置遥测站 IC 卡状态
    @GetMapping("/updateICCardState/{deviceId}")
    public ResultResponse<?> updateICCardState(@PathVariable("deviceId") String deviceId) {
        // 0x4B
        var info = deviceService.getById(deviceId);
        //TODO
        return ResultResponse.success();
    }

    //TODO
    // 6.6.4.26 控制水泵开关命令/水泵状态自报
    // 6.6.4.27 控制阀门开关命令/阀门状态信息自报
    // 6.6.4.28 控制闸门开关命令/闸门状态信息自报
    // 6.6.4.29 水量定值控制命令

    // 6.6.4.30 中心站查询遥测站事件记录
    @GetMapping("/queryEventRecord/{deviceId}")
    public ResultResponse<?> queryEventRecord(@PathVariable("deviceId") String deviceId) {
        // 0x50
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryEventRecord(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult());
        }
        return ResultResponse.failed(result.getState().name());
    }

    // 6.6.4.31 中心站查询遥测站时钟
    @GetMapping("/queryClock/{deviceId}")
    public ResultResponse<String> queryClock(@PathVariable("deviceId") String deviceId) {
        // 0x51
        var info = deviceService.getById(deviceId);
        var result = deviceSendService.queryClock(info);
        if (result.isSuccess()) {
            return ResultResponse.success(result.getResult().getSendTime());
        }
        return ResultResponse.failed(result.getState().name());
    }


}
