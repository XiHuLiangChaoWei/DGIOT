package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import org.apache.poi.ss.formula.functions.T;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/6/9 10:22
 * ClassExplain :
 * ->
 */
public class IJOB implements Job {
    @Autowired
    DeviceService deviceService;
    @Autowired
    DownOrderUtils downOrderUtils;

    private final static Logger log = Logger.getLogger(IJOB.class.getSimpleName());

    public IJOB() {
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
                if (device.getType() == 2) {
                    new Thread(() -> {
                        log.info("定时查询气调··············");
                        downOrderUtils.deployN2Order(0, device);
                    }).start();
                } else if (device.getType() == 3) {
                    new Thread(() -> {
                        log.info("定时查询粮情··············");
                        downOrderUtils.deployGrainOrder(0, device);
                    }).start();
                }


//            switch (device.getType()) {
//                case 0:
//                    break;
//                case 2:
//                    log.info("定时查询气调任务开始··············");
//                    downOrderUtils.deployN2Order(0, device);
//                    log.info("定时查询气调任务结束··············");
//                    break;
//                case 3:
//                    log.info("定时查询粮情任务开始··············");
//                    downOrderUtils.deployGrainOrder(0, device);
//                    log.info("定时查询粮情任务结束··············");
//                    break;
//                case 5:
//                    break;
//            }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
