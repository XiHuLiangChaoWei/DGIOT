package cn.zz.dgcc.DGIOT.IQuartzJOB;

import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

/**
 * Created by: LT001
 * Date: 2020/6/8 11:26
 * ClassExplain : 将定时任务添加到程序启动
 * ->
 */
@Configuration
@Order(1)
public class IQuartzConf implements ApplicationRunner {

    @Autowired
    IQuartzStart iQuartzStart;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.err.println("start defalt quartz jobs ， please waiting ......");
//        iQuartzStart.bindDevWithJob();
        iQuartzStart.quartzJobForDevice();
        System.err.println("start defalt quartz jobs complete !");

    }
}


