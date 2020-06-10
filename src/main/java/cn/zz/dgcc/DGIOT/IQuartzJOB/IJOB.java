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
        System.out.println("计划任务测试-----");
        //从jobDataMap中获取设备列表
    }
}
