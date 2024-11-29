package me.yuxiaoyao.sl651.client;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author kerryzhang on 2024/11/13
 */

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeviceConfig {
    @Builder.Default
    private byte[] startCode = new byte[]{0x7E, 0x7E};
    private byte stationId;
    /**
     * hex
     */
    @Builder.Default
    private String stationType = "48";
    /**
     * hex
     */
    private String telemetryId;
    /**
     * hex
     */
    private String password;
}
