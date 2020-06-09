package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/6/8 8:38
 * ClassExplain :      defalt QuartzJob
 * ->
 */
@Configuration
public class IQuartzStart {

    @Autowired
    Scheduler scheduler;
    @Autowired
    DeviceService deviceService;
    @Autowired
    DownOrderUtils downOrderUtils;

    /**
     * 1.获取设备列表
     */
    public List<Device> getDeviceList() {
        List<Device> deviceList = deviceService.getAllDev();
        return deviceList;
    }

    /**
     * 2.设备分类
     */
    public void splitByType() {
        List<Device> deviceList = getDeviceList();
        List<Device> qtDev = null;
        List<Device> grainDev = null;
        List<Device> zdDev = null;
        for (Device device : deviceList
        ) {
            if (device.getType() == 2) {
                qtDev.add(device);
            } else if (device.getType() == 3) {
                grainDev.add(device);
            } else if (device.getType() == 5) {
                zdDev.add(device);
            }
        }
    }


    public void quartzJobForDevice() throws SchedulerException {

//        SchedulerFactory sf = new StdSchedulerFactory();
//        Scheduler scheduler = sf.getScheduler();

        scheduler.clear();
        scheduler.start();
        JobDetail jobDetail = JobBuilder.newJob(IJOB.class).withIdentity("local", "Defalt")
                .storeDurably().build();
        CronTrigger trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 * * * ? "))
                .withIdentity("local", "Defalt").build();
        scheduler.scheduleJob(jobDetail, trigger);


        JobDetail timer = JobBuilder.newJob(new Job() {

            @Override
            public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
                downOrderUtils.JsonTime(0);
            }
        }.getClass()).withIdentity("timer", "Defalt")
                .storeDurably().build();
        CronTrigger trigger2 = TriggerBuilder.newTrigger().forJob(timer).withSchedule(CronScheduleBuilder.cronSchedule("0 0 0 * * ? "))
                .withIdentity("timer", "Defalt").build();
        scheduler.scheduleJob(timer, trigger2);
    }


    /**
     * 3.为每个设备绑定定时任务,将任务注册到scheduler上执行
     */
//    public void bindDevWithJob() throws SchedulerException {
//        scheduler.clear();
//        List<Device> deviceList = getDeviceList();
//        //创建job实例
//        //创建触发器
//        //注册
//        JobDataMap jobDataMap = new JobDataMap();
//        for (Device device : deviceList
//        ) {
//            CronTrigger trigger;
//            jobDataMap.clear();
//            if (device.getType() == 0) {
//                continue;
//            }
//            if (device.getType() == 2) {
//                jobDataMap.put("userId", device.getUserId());
//                jobDataMap.put("device", device.getDeviceName());
//
//                JobDetail jobDetail = JobBuilder.newJob(QTJob.class).usingJobData(jobDataMap)
//                        .withIdentity(device.getDeviceName(), device.getDevNote()).storeDurably().build();
//                trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 * * * ? "))
//                        .withIdentity(device.getDeviceName(), device.getDevNote()).build();
//                scheduler.scheduleJob(jobDetail, trigger);
//            } else if (device.getType() == 3) {
//                JobDetail jobDetail = JobBuilder.newJob(GrainJob.class).usingJobData(jobDataMap)
//                        .withIdentity(device.getDeviceName(), device.getDevNote()).storeDurably().build();
//                trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 * * * ? "))
//                        .withIdentity(device.getDeviceName(), device.getDevNote()).build();
//                scheduler.scheduleJob(jobDetail, trigger);
//            } else if (device.getType() == 5) {
//                JobDetail jobDetail = JobBuilder.newJob(ZDJob.class).usingJobData(jobDataMap)
//                        .withIdentity(device.getDeviceName(), device.getDevNote()).storeDurably().build();
//                trigger = TriggerBuilder.newTrigger().forJob(jobDetail).withSchedule(CronScheduleBuilder.cronSchedule("0 0/3 * * * ? "))
//                        .withIdentity(device.getDeviceName(), device.getDevNote()).build();
//                scheduler.scheduleJob(jobDetail, trigger);
//            }
//        }
//        scheduler.start();
//    }


//    @Bean
//    public JobDetail QTjobDetail() {
//        JobDataMap qtMap = new JobDataMap();
//        qtMap.put("userId", 1);
//        qtMap.put("device", "HC-JCJ-DJ-QT001");
//        return JobBuilder.newJob(QTJob.class).withIdentity("qt1", "qt1").usingJobData(qtMap).storeDurably().build();
//    }
//
//    @Bean
//    public Trigger QTTrigger() {
//        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInMinutes(2).repeatForever();
//        return TriggerBuilder.newTrigger().forJob(QTjobDetail()).withSchedule(scheduleBuilder).build();
//    }
//
//    @Bean
//    public JobDetail GrainJobDetail() {
//
//        return JobBuilder.newJob(GrainJob.class).storeDurably().build();
//    }
//
//    @Bean
//    public Trigger GrainTrigger() {
//        SimpleScheduleBuilder scheduleBuilder2 = SimpleScheduleBuilder.simpleSchedule()
//                .withIntervalInMinutes(2).repeatForever();
//        return TriggerBuilder.newTrigger().forJob(GrainJobDetail()).withSchedule(scheduleBuilder2).build();
//    }


}
