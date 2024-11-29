package me.yuxiaoyao.sl651.netty.server.message.item;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerryzhang on 2024/11/09
 */


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TelemetryCollectEleSetting {
    // byte 1
    // 降水量
    private boolean precipitation;
    // 蒸发量
    private boolean emission;
    // 风向
    private boolean windDirection;
    // 风速
    private boolean windSpeed;
    // 气温
    private boolean temperature;
    // 湿度
    private boolean humidity;
    // 地温
    private boolean groundTemperature;
    // 气压
    private boolean pressure;

    // byte 2
    // 水位8
    private boolean waterLevel8;
    // 水位7
    private boolean waterLevel7;
    // 水位6
    private boolean waterLevel6;
    // 水位5
    private boolean waterLevel5;
    // 水位4
    private boolean waterLevel4;
    // 水位3
    private boolean waterLevel3;
    // 水位2
    private boolean waterLevel2;
    // 水位1
    private boolean waterLevel1;

    // byte 3
    // 地下水埋深
    private boolean groundWaterDepth;
    // 图片
    private boolean picture;
    // 波浪
    private boolean wave;
    // 闸门开度
    private boolean gateOpening;
    // 水量
    private boolean waterAmount;
    // 流速
    private boolean flowSpeed;
    // 流量
    private boolean flowAmount;
    // 水压
    private boolean waterPressure;

    // byte 4
    // 水表8
    private boolean waterMeter8;
    // 水表7
    private boolean waterMeter7;
    // 水表6
    private boolean waterMeter6;
    // 水表5
    private boolean waterMeter5;
    // 水表4
    private boolean waterMeter4;
    // 水表3
    private boolean waterMeter3;
    // 水表2
    private boolean waterMeter2;
    // 水表1
    private boolean waterMeter1;

    // byte 5
    // 100CM墒情
    private boolean soilMoisture100Cm;
    // 80CM墒情
    private boolean soilMoisture80Cm;
    // 60CM墒情
    private boolean soilMoisture60Cm;
    // 50CM墒情
    private boolean soilMoisture50Cm;
    // 40CM墒情
    private boolean soilMoisture40Cm;
    // 30CM墒情
    private boolean soilMoisture30Cm;
    // 20CM墒情
    private boolean soilMoisture20Cm;
    // 10CM墒情
    private boolean soilMoisture10Cm;

    // byte 6
    // ph 值
    private boolean phValue;
    // 溶解氧
    private boolean solubleOxygen;
    // 电导率
    private boolean electricConductivity;
    // 浊度
    private boolean turbidity;
    // 氧化还原电位
    private boolean redoxPotential;
    // 高锰酸盐指数
    private boolean permanganateExponent;
    // 氨氮
    private boolean ammoniaNitrogen;
    // 水温
    private boolean waterTemperature;

    // byte 7
    // 总有机碳
    private boolean totalOrganicCarbon;
    // 总氮
    private boolean totalNitrogen;
    // 总磷
    private boolean totalPhosphorus;
    // 锌
    private boolean zinc;
    // 硒
    private boolean selenium;
    // 砷
    private boolean arsenic;
    // 总汞
    private boolean totalMercury;
    // 镉
    private boolean cadmium;

    // byte 8
    // 叶绿素a
    private boolean chlA;
    // 铜
    private boolean copper;
    // 铅
    private boolean lead;


}
