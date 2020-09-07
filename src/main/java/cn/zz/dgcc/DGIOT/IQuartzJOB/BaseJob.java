package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.service.DepotService;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.service.ISqlDeleteService;
import cn.zz.dgcc.DGIOT.service.IoTService;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/7/13 9:14
 * ClassExplain :   
 * ->
 */
public class BaseJob {
    @Autowired
    DeviceService deviceService;
    @Autowired
    DownOrderUtils downOrderUtils;
    @Autowired
    ISqlDeleteService iSqlDeleteService;
    @Autowired
    IoTService ioTService;
    @Autowired
    DepotService depotService;

    final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    private final static ExecutorService executorService = new ThreadPoolExecutor(
            Runtime.getRuntime().availableProcessors(),
            Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(50000));
    ThreadPoolExecutor pool = (ThreadPoolExecutor) executorService;
}
