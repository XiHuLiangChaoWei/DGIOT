package cn.zz.dgcc.DGIOT.utils.AMQP;


import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.net.URI;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/4/21 9:09
 * ClassExplain :TODO
 * ->
 */

public class AMQP2WebSocket extends HttpServlet {
    private AMQP2WebSocket() {
    }

    final static AMQP2WebSocket amqp2WebSocket = new AMQP2WebSocket();

    public static AMQP2WebSocket getAMQP2WebSocket() {
        return amqp2WebSocket;
    }

    public static WebSocketClient client;
    public static final String url = "ws://localhost:80/iot";
    Logger logger = Logger.getLogger(String.valueOf(AMQP2WebSocket.class));
    private String msg;
    private AMQPMessage msgC;

    public void setMsgC(AMQPMessage amqpMessage) {
        this.msgC = amqpMessage;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

//    public void sendMsg(String message) {
////        log.info("A2W=" + message);
//        this.setMsg(message);
//        client.send(msg);
//    }

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            client = new WebSocketClient(new URI(url), new Draft_6455()) {
                @Override
                public void onOpen(ServerHandshake serverHandshake) {
//                    logger.info("连接已建立");
//                    log.info("客户端已连接");
//                    try {
//                        client.close();
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }

                    if (msg != null) client.send(msg);
                    if (msgC != null) client.send(msgC.toString());
                    client.close();
                }

                @Override
                public void onMessage(String s) {
                    logger.info("收到：" + s);

                }

                @Override
                public void onClose(int i, String s, boolean b) {
                    client.close();
                }

                @Override
                public void onError(Exception e) {

                }
            };
            client.connect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
