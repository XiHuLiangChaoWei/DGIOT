package cn.zz.dgcc.DGIOT.IQuartzJOB;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by: YYL
 * Date: 2020/6/8 11:26
 * ClassExplain :
 * ->
 */
@Configuration
@Order(1)
public class IQuartzConf implements ApplicationRunner {

    @Autowired
    IQuartzStart iQuartzStart;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.err.println("开始启动默认定时任务");
        iQuartzStart.bindDevWithJob();
        System.err.println("默认定时任务启动完成");
    }
}


