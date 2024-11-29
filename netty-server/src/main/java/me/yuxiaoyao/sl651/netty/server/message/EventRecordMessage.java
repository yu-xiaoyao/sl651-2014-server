package me.yuxiaoyao.sl651.netty.server.message;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/21
 */

@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class EventRecordMessage extends TelemetryIdMessage {
    private int erc1;
    private int erc2;
    private int erc3;
    private int erc4;
    private int erc5;
    private int erc6;
    private int erc7;
    private int erc8;
    private int erc9;
    private int erc10;
    private int erc11;
    private int erc12;
    private int erc13;
    private int erc14;
    private int erc15;
    private int erc16;
    private int erc17;
    private int erc18;
}
