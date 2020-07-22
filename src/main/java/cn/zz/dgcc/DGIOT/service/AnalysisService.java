package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;

/**
 * Created by: LT001
 * Date: 2020/5/18 10:34
 * ClassExplain :
 * ->
 */
public interface AnalysisService {
    void analysisInfo(AMQPMessage amqpMessage);
}
