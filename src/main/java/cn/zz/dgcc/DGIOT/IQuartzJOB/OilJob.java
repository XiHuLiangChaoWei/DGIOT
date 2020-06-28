package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Logger;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Autowired;

public class OilJob implements Job {
    @Autowired
    DeviceService deviceService;

    @Autowired
    DownOrderUtils downOrderUtils;

    private static final Logger log = Logger.getLogger(OilJob.class.getSimpleName());

    public List<Device> getDeviceList() {
        List<Device> deviceList = deviceService.getAllDev();
        return deviceList;
    }

    static ExecutorService executorService = Executors.newCachedThreadPool();

    public void execute(JobExecutionContext jobExecutionContext) {

        List<Device> devices = getDeviceList();
        try {
            for (Device device : devices) {
                if (device.getType() != 6)
                    continue;
                if (device.getType() == 6)
                    executorService.execute(() -> {
                        log.info("定时查询油情··············" + device.getDeviceName());
                        downOrderUtils.deployOilOrder(0, device);
                    });


//                    (new Thread(() -> {
//                        log.info("定时查询油情··············"+device.getDeviceName());
//                        this.downOrderUtils.deployOilOrder(0, device);
//                    })).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
