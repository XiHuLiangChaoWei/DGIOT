package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/6/4 17:38
 * ClassExplain :
 * ->
 */
public class GrainJob extends BaseJob implements Job{

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
                if (device.getType() == 3) {
                    pool.submit(() -> {
                        log.info("定时查询粮情··············");
                        downOrderUtils.deployGrainOrder(0, device);
                    });
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
