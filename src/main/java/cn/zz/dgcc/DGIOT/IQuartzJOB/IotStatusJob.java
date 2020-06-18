package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Product;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.service.IoTService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/6/13 14:49
 * ClassExplain : 定时刷新设备状态
 * ->
 */
public class IotStatusJob implements Job {
    private final static Logger log = Logger.getLogger(IotStatusJob.class.getSimpleName());
    @Autowired
    IoTService ioTService;
    @Autowired
    DeviceService deviceService;

    List<Device> getTotalList() {
        List<Product> products = ioTService.getProductList();
        List<Device> devices = ioTService.getDeviceList(products);
        return devices;
    }

    public void savaDevList() {
        List<Device> devices = getTotalList();
        for (Device d : devices
        ) {
            log.info("查询到设备列表" + d);
        }
        deviceService.updateDevStatus(devices);
    }

    /**
     * 执行刷新数据库中设备信息的操作
     *
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("开始更新设备状态······");
        savaDevList();
        log.info("更新设备状态完成······");
    }
}
