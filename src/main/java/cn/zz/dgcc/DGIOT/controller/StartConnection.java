package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.service.AMQPService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import javax.annotation.Resource;

/**
 * Created by: LT001
 * Date: 2020/4/20 17:27
 * ClassExplain :
 * ->
 */
@Controller
@Component
public class StartConnection extends BaseController implements ApplicationRunner {
    @Autowired
    AMQPService amqpService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        amqpService.initAMQP();
    }
}
