package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Fireware;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/6/2 11:45
 * ClassExplain :
 * ->
 */
public interface FirewareService {
    Fireware getFirewareByVersion(String version);

    void analysisInfo(AMQPMessage amqpMessage);

    int saveFirewareVersion(Fireware fireware);

    List<Fireware> getAll();

    List<Fireware> getFirewareListByDevName(String deviceName);
}
