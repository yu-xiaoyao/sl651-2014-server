# SL651-2014 水文协议 Netty 实现

## Server 模块

### 1. netty-server
> 原始 Netty.

- [NettySL651Application.java](netty-server/src/main/java/me/yuxiaoyao/sl651/netty/server/NettySL651Application.java)


### 2. spring-boot-server
> Spring Boot + Web + Netty

- [SL651ServerApplication.java](spring-boot-server/src/main/java/me/yuxiaoyao/sl651/server/SL651ServerApplication.java)

读取和写入接口:
- [DeviceController.java](spring-boot-server/src/main/java/me/yuxiaoyao/sl651/server/controller/DeviceController.java)


## 自定义事件保存

```java

@Component
@Primary
public class MyDeviceEventPublisher implements DeviceEventPublisher {
    /**
     * 事件使用 netty EventLoop 线程, 非异步
     *
     * @param header
     * @param decodeMessage
     */
    @Override
    public void onReceive(MessageHeader header, Object decodeMessage) {
        // TODO
    }


    // ... more event
}
```

### 示例

```
me.yuxiaoyao.sl651.server.mqtt.MqttDeviceEventPublisher
```

MQTT配置:

```yaml
mqtt:
  enabled: true   # 是否启用
  username: publicun
  password: public
  urls: tcp://127.0.0.1:1883
```