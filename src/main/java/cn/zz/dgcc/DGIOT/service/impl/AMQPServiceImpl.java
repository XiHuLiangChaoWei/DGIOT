package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.service.AMQPService;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPServiceUtils;
import org.springframework.stereotype.Service;


/**
 * Created by: LT001
 * Date: 2020/4/20 13:34
 * ClassExplain :
 * ->
 */
@Service
public class AMQPServiceImpl implements AMQPService {

    @Override
    public void initAMQP() {
        AMQPServiceImpl a = new AMQPServiceImpl();
        try {
            AMQPServiceUtils.initAMQPClient();
        } catch (Exception e) {
        }
    }


}
