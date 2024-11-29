package me.yuxiaoyao.sl651.netty.server.message.send;


import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * @author kerryzhang on 2024/11/19
 */


@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePasswordSendMessage extends BaseSendMessage {
    private String oldPassword;
    private String newPassword;
}
