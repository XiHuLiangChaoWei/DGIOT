package cn.zz.dgcc.DGIOT.utils.AMQP;

import cn.zz.dgcc.DGIOT.controller.DeviceBindController;
import cn.zz.dgcc.DGIOT.utils.SpringUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.qpid.jms.JmsConnection;
import org.apache.qpid.jms.JmsConnectionListener;
import org.apache.qpid.jms.message.JmsInboundMessageDispatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import java.net.*;
import java.util.Hashtable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by: YYL
 * Date: 2020/4/20 13:40
 * ClassExplain :AMQP服务端 接收消息并转发
 * ->
 */
public class AMQPServiceUtils {
    //静态初始化一个自身变量
    static AMQPServiceUtils amqpServiceUtils = new AMQPServiceUtils();

    ApplicationContext context = SpringUtil.getApplicationContext();

    static AMQPMessage amqpMessage;
//    DeviceService deviceService = context.getBean(DeviceService.class);
    DeviceBindController deviceBindController = context.getBean(DeviceBindController.class);

//    public static void main(String[] args) throws Exception {
////        initAMQPClient();
////    }


    private final static Logger logger = LoggerFactory.getLogger(AMQPServiceUtils.class);

    //业务处理异步线程池，线程池参数可以根据您的业务特点调整；或者您也可以用其他异步方式处理接收到的消息
    private final static ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50000));

    public static void initAMQPClient() throws Exception {
        //参数说明，请参见上一篇文档：AMQP客户端接入说明。
        String accessKey = "LTAI4Fo7DGZq2QP1ddPgBkmu";
        String accessSecret = "jPtEyZUeQkOHTodidA8xirLjug4nAO";
        String consumerGroupId = "DEFAULT_GROUP";
        String iotInstanceId = "iot-cn-v641mge9j01";
        long timeStamp = System.currentTimeMillis();
        //签名方法：支持hmacmd5，hmacsha1和hmacsha256
        String signMethod = "hmacsha1";
        //控制台服务端订阅中消费组状态页客户端ID一栏将显示clientId参数。
        //建议使用机器UUID、MAC地址、IP等唯一标识等作为clientId。便于您区分识别不同的客户端。
        String clientId = getMac();

        //UserName组装方法，请参见上一篇文档：AMQP客户端接入说明。
//        String userName = clientId + "|authMode=aksign"
//                + ",signMethod=" + signMethod
//                + ",timestamp=" + timeStamp
//                + ",authId=" + accessKey
//                + ",consumerGroupId=" + consumerGroupId
//                + "|";
        String userName = clientId + "|authMode=aksign"
                + ",signMethod=" + signMethod
                + ",timestamp=" + timeStamp
                + ",authId=" + accessKey
                + ",iotInstanceId=" + iotInstanceId
                + ",consumerGroupId=" + consumerGroupId
                + "|";

        //password组装方法，请参见上一篇文档：AMQP客户端接入说明。
        String signContent = "authId=" + accessKey + "&timestamp=" + timeStamp;
        String password = doSign(signContent, accessSecret, signMethod);
        //按照qpid-jms的规范，组装连接URL。
        String connectionUrl = "failover:(amqps://1009477666139006.iot-amqp.cn-shanghai.aliyuncs.com:5671?amqp.idleTimeout=80000)"
                + "?failover.reconnectDelay=30";

        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("connectionfactory.SBCF", connectionUrl);
        hashtable.put("queue.QUEUE", "default");
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "org.apache.qpid.jms.jndi.JmsInitialContextFactory");
        //初始化上下文
        Context context = new InitialContext(hashtable);
        ConnectionFactory cf = (ConnectionFactory) context.lookup("SBCF");
        Destination queue = (Destination) context.lookup("QUEUE");
        // Create Connection
        Connection connection = cf.createConnection(userName, password);
        ((JmsConnection) connection).addConnectionListener(myJmsConnectionListener);
        // Create Session
        // Session.CLIENT_ACKNOWLEDGE: 收到消息后，需要手动调用message.acknowledge()
        // Session.AUTO_ACKNOWLEDGE: SDK自动ACK（推荐）
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        connection.start();
        // Create Receiver Link
        MessageConsumer consumer = session.createConsumer(queue);
        consumer.setMessageListener(messageListener);

    }

    /*
    获取本机MAC地址
     */
    private static String getMac() {
        try {
            InetAddress ia = Inet4Address.getLocalHost();
            byte[] mac = NetworkInterface.getByInetAddress(ia).getHardwareAddress();
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < mac.length; i++) {
                if (i != 0) {
                    sb.append("-");
                }
                int temp = mac[i] & 0xff;
                String str = Integer.toHexString(temp);
                if (str.length() == 1) {
                    sb.append("0" + str);
                } else {
                    sb.append(str);
                }
            }
            return sb.toString().toUpperCase();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static final MessageListener messageListener = new MessageListener() {
        @Override
        public void onMessage(Message message) {
            try {
                //1.收到消息之后一定要ACK
                // 推荐做法：创建Session选择Session.AUTO_ACKNOWLEDGE，这里会自动ACK。
                // 其他做法：创建Session选择Session.CLIENT_ACKNOWLEDGE，这里一定要调message.acknowledge()来ACK。
                // message.acknowledge();
                //2.建议异步处理收到的消息，确保onMessage函数里没有耗时逻辑。
                // 如果业务处理耗时过程过长阻塞住线程，可能会影响SDK收到消息后的正常回调。
                executorService.submit(() -> processMessage(message));
            } catch (Exception e) {
                logger.error("submit task occurs exception ", e);
            }
        }
    };


    /**
     * 在这里处理您收到消息后的具体业务逻辑。
     */
    private static void processMessage(Message message) {
        try {
//            if(message.getBody(byte[].class)==null){
//                return;
//            }
            //将message主体转化成字符串类型数据
            byte[] body = message.getBody(byte[].class);

            String content = new String(body,"GB2312").replace(" ", "");
            //获取topic和messageId
            String topic = message.getStringProperty("topic");
            String messageId = message.getStringProperty("messageId");

            logger.info("receive message"
                    + ", topic = " + topic
                    + ", messageId = " + messageId
                    + ", content = " + content);
//            log.info("receive message"
//                    + ", topic = " + topic
//                    + ", messageId = " + messageId
//                    + ", content = " + content);
            //拼装消息对象
            amqpMessage = new AMQPMessage(topic, messageId, content,body);
            //判断dtu接入topic
            if ("/a1KhXudYrKw/000000/user/dev/register".equals(topic)) {
                amqpServiceUtils.registerDevice(amqpMessage);
            }else if(topic.contains("/user/dev/Login")){
                amqpServiceUtils.deviceLogin(amqpMessage);
            }else if(topic.contains("/dev/update")){
                amqpServiceUtils.parseMsg(amqpMessage);
            }
            amqpServiceUtils.parseMsg(amqpMessage);
            alertMsg(amqpMessage);

        } catch (Exception e) {
            logger.error("processMessage occurs error ", e);
        }
    }

    private void firewareUpdate(AMQPMessage amqpMessage) { deviceBindController.firewareUpdate(amqpMessage); }

    private void deviceLogin(AMQPMessage amqpMessage) { deviceBindController.loginDevice(amqpMessage); }

    private void registerDevice(AMQPMessage amqpMessage) {
        deviceBindController.registerDevice(amqpMessage);
    }

    private void parseMsg(AMQPMessage amqpMessage){deviceBindController.parseInfo(amqpMessage);}

    static AMQP2WebSocket amqp2WebSocket = AMQP2WebSocket.getAMQP2WebSocket();

    /**
     * 消息转发
     * @param amqpMessage
     * @throws Exception
     */
    private static void alertMsg(AMQPMessage amqpMessage) throws Exception {
        amqp2WebSocket.setMsgC(amqpMessage);
        amqp2WebSocket.init();
        amqp2WebSocket.destroy();
    }


    private static final JmsConnectionListener myJmsConnectionListener = new JmsConnectionListener() {
        /**
         * 连接成功建立
         */
        @Override
        public void onConnectionEstablished(URI remoteURI) {
            logger.info("onConnectionEstablished, remoteUri:{}", remoteURI);
        }

        /**
         * 尝试过最大重试次数之后，最终连接失败。
         */
        @Override
        public void onConnectionFailure(Throwable error) {
            logger.error("onConnectionFailure, {}", error.getMessage());
        }

        /**
         * 连接中断。
         */
        @Override
        public void onConnectionInterrupted(URI remoteURI) {
            logger.info("onConnectionInterrupted, remoteUri:{}", remoteURI);
        }

        /**
         * 连接中断后又自动重连上。
         */
        @Override
        public void onConnectionRestored(URI remoteURI) {
            logger.info("onConnectionRestored, remoteUri:{}", remoteURI);
        }

        @Override
        public void onInboundMessage(JmsInboundMessageDispatch envelope) {
        }

        @Override
        public void onSessionClosed(Session session, Throwable cause) {
        }

        @Override
        public void onConsumerClosed(MessageConsumer consumer, Throwable cause) {
        }

        @Override
        public void onProducerClosed(MessageProducer producer, Throwable cause) {
        }
    };

    /**
     * password签名计算方法，请参见上一篇文档：AMQP客户端接入说明。
     */
    private static String doSign(String toSignString, String secret, String signMethod) throws Exception {
        SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), signMethod);
        Mac mac = Mac.getInstance(signMethod);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(toSignString.getBytes());
        return Base64.encodeBase64String(rawHmac);
    }
}
