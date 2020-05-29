package cn.zz.dgcc.DGIOT.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by: YYL
 * Date: 2020/4/20 13:34
 * ClassExplain :
 * ->
 */

public interface AMQPService {
    /*
    初始化AMQP服务器订阅
     */
    void initAMQP();

    void initWebSocketServer();
}
