package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/6/4 17:38
 * ClassExplain :
 * ->
 */
public class GrainJob implements Job {
    @Autowired
    DeviceService deviceService;
    @Autowired
    DownOrderUtils downOrderUtils;

    private final static Logger log = Logger.getLogger(GrainJob.class.getSimpleName());

    public GrainJob() {
    }

    public List<Device> getDeviceList() {
        List<Device> deviceList = deviceService.getAllDev();
        return deviceList;
    }

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.out.println("开始执行计划任务");
        //从jobDataMap中获取设备列表
        List<Device> devices = getDeviceList();
        try {
            for (Device device : devices
            ) {
                if (device.getType() == 0) {
                    continue;
                }
                if (device.getType() == 3) {
                    new Thread(() -> {
                        log.info("定时查询粮情··············");
                        downOrderUtils.deployN2Order(0, device);
                    }).start();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
//        log.info("定时查询粮情任务开始··············");
//        String devName = jobExecutionContext.getJobDetail().getJobDataMap().getString("device");
//        String userId = jobExecutionContext.getJobDetail().getJobDataMap().getString("userId");
//        Device device = deviceService.getDevByDevName(devName);
//        downOrderUtils.deployGrainOrder(Integer.parseInt(userId), device);
//        log.info("定时查询粮情任务结束··············");
//    }
}
