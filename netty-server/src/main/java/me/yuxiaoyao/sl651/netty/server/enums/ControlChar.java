package me.yuxiaoyao.sl651.netty.server.enums;

/**
 * @author kerryzhang on 2024/11/10
 * <p>
 * 表10 控制字符定义
 */
public class ControlChar {
    /**
     * 传输正文起始
     */
    public static final int STX = 0x02;
    /**
     * 多包传输正文起始
     * 多包发送，一次确认的传输模式中使用
     */
    public static final int SYN = 0x16;
    /**
     * 报文结束，后续无报文
     * 作为报文结束符，表示传输完成，等待退出通信
     */
    public static final int ETX = 0x03;
    /**
     * 报文结束，后续有报文
     * 在报文分包传输时作为报文结束符，表示传输未完成，不可退出通信
     */
    public static final int ETB = 0x17;
    /**
     * 询问
     * 作为下行查询及控制命令帧的报文结束符
     */
    public static final int ENQ = 0x05;
    /**
     * 传输结束，退出
     * 作为传输结束确认帧报文结束符，表示可以退出通信
     */
    public static final int EOT = 0x04;
    /**
     * 肯定确认，继续发送
     * 作为有后续报文帧的“确认帧”报文结束符
     */
    public static final int ACK = 0x06;
    /**
     * 否定应答，反馈重发
     * 用于要求对方重发某数据包的报文结束符。
     */
    public static final int NAK = 0x15;
    /**
     * 传输结束，终端保持在线
     * 在下行确认帧代替EOT作为报文结束符，要求终端在线。保持在线10分钟内若没有接收到中心站命令，终端退回原先设定的工作状态。
     */
    public static final int ESC = 0x1B;

}
