package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.List;
import java.util.concurrent.*;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/6/5 8:34
 * ClassExplain :   查询QT信息
 * ->
 */
public class QTJob extends BaseJob implements Job {

    public QTJob() {
    }

    public List<Device> getDeviceList() {
        List<Device> deviceList = deviceService.getAllDev();
        return deviceList;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        System.out.println("开始执行计划任务");
        log.info("开始执行气调任务--------------------------------------------");
        //从jobDataMap中获取设备列表
        List<Device> devices = getDeviceList();
        try {
            for (Device device : devices
            ) {
                if (device.getType() == 2) {
                    pool.submit(() -> {
                        log.info("定时查询气调··············" + device.getDeviceName());
                        downOrderUtils.deployN2Order(0, device);

                    });
//                    executorService.execute(() -> {
//                        log.info("定时查询气调··············" + device.getDeviceName());
//                        downOrderUtils.deployN2Order(0, device);
//                    });
//                    new Thread(() -> {
//                        log.info("定时查询气调··············"+device.getDeviceName());
//                        downOrderUtils.deployN2Order(0, device);
//                    }).start();

                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
        log.info("结束执行气调任务--------------------------------------------");
//        pool.shutdown();
    }
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.info("定时查询气调任务开始··············");
//        String devName = jobExecutionContext.getJobDetail().getJobDataMap().getString("device");
//        String userId = jobExecutionContext.getJobDetail().getJobDataMap().getString("userId");
//        Device device = deviceService.getDevByDevName(devName);
//        downOrderUtils.deployN2Order(Integer.parseInt(userId), device);
//        log.info("定时查询气调任务结束··············");
//    }
}
