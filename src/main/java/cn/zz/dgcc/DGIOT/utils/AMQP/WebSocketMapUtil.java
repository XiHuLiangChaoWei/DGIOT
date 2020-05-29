package cn.zz.dgcc.DGIOT.utils.AMQP;

import cn.zz.dgcc.DGIOT.utils.AMQP.WebSocketServiceUtil;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by: YYL
 * Date: 2020/4/20 16:06
 * ClassExplain :维护一个MAP来保存连接和对象
 * ->
 */
public class WebSocketMapUtil {
    public static ConcurrentMap<String, WebSocketServiceUtil> webSocketMap = new ConcurrentHashMap<>();

    public static void put(String key, WebSocketServiceUtil myWebSocketServer) {
        webSocketMap.put(key, myWebSocketServer);
    }

    public static WebSocketServiceUtil get(String key) {
        return webSocketMap.get(key);
    }

    public static void remove(String key) {
        webSocketMap.remove(key);
    }

    public static Collection<WebSocketServiceUtil> getValues() {
        return webSocketMap.values();
    }
}
