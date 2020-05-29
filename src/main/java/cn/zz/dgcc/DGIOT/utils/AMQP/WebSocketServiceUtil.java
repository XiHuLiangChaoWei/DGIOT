package cn.zz.dgcc.DGIOT.utils.AMQP;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/4/13 10:35
 * ClassExplain :
 * ->
 */
@Component
@ServerEndpoint(value = "/iot")
public class WebSocketServiceUtil {


    private static final Logger logger = Logger.getLogger(String.valueOf(WebSocketServiceUtil.class));
    private Session session;

    /**
     * 连接建立后触发的方法
     */
    @OnOpen
    public void onOpen(Session session) {
        //获取连接session
        this.session = session;
//        logger.info("====== onOpen" + session.getId()+"  ======");
        //存储连接map
        WebSocketMapUtil.put(session.getId(), this);
    }


    /**
     * 连接关闭后触发的方法
     */
    @OnClose
    public void onClose() {
        //从map中删除
        WebSocketMapUtil.remove(session.getId());
//        logger.info("====== onClose:" + session.getId() + " ======");
    }


    /**
     * 接收到客户端消息时触发的方法
     */
    @OnMessage
    public void onMessage(String params, Session session) throws Exception {
        //获取服务端到客户端的通道
        WebSocketServiceUtil myWebSocket = WebSocketMapUtil.get(session.getId());
        logger.info("收到来自" + session.getId() + "的消息:" + params);
        String result = "收到来自" + session.getId() + "的消息:" + params;
        //返回消息给Web Socket客户端（浏览器）
//        myWebSocket.sendMessage(1,"成功！", result);
        sendMessage2All(params);
    }


    /**
     * 发生错误时触发的方法
     */
    @OnError
    public void onError(Session session, Throwable error) {
//        logger.info(session.getId() + "连接发生错误" + error.getMessage());
//        error.printStackTrace();
    }

    public void sendMessage(int status, String message, Object datas) throws IOException {
        JSONObject result = new JSONObject();
        result.put("status", status);
        result.put("message", message);
        result.put("datas", datas);
        this.session.getBasicRemote().sendText(result.toString());
    }

    public void  sendMessage2All(String message) throws IOException {
        JSONObject json1 = JSON.parseObject(message);
//        log.info("WebSocketServiceUtil:"+json1);
        for (WebSocketServiceUtil e : WebSocketMapUtil.getValues()
        ) {
            e.session.getBasicRemote().sendText(json1.toJSONString());
        }
    }
}
