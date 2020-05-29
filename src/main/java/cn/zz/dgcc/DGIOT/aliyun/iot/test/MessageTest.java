package cn.zz.dgcc.DGIOT.aliyun.iot.test;

import cn.zz.dgcc.DGIOT.aliyun.iot.api.sdk.openapi.MessageBrokerManager;
import org.apache.commons.codec.binary.Hex;

import java.util.Base64;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/4/8 9:56
 * ClassExplain :
 * ->
 */
public class MessageTest {
    private final static Logger log = Logger.getLogger(MessageTest.class.getSimpleName());
    public static void main(String[] args) throws IllegalAccessException, InstantiationException {

        MessageTest mt = new MessageTest();
        String message = "121213";
        char[] m1 = message.toCharArray();
        byte[] b1 = new byte[]{12,33,44,127,-12,-112,43,-111};

        String m2 = Base64.getEncoder().encodeToString(b1);
        System.out.println("m2"+m2);

        String a = new String(b1);
        String a1 = Hex.encodeHexString(b1);
        log.info("a1="+a1);


        String finalMsg = Base64.getEncoder().encodeToString(message.getBytes());
        mt.Push2Topic(finalMsg);
    }

    public void Push2Topic(String str){
        MessageBrokerManager.pub("a1gQiP9WsBk","/a1gQiP9WsBk/000000/user/sev/downdate",str,0);
    }

}
