package cn.zz.dgcc.DGIOT.IQuartzJOB;

import cn.zz.dgcc.DGIOT.service.ISqlDeleteService;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by: LT001
 * Date: 2020/7/10 14:29
 * ClassExplain :
 * ->
 */
public class IDeleteJob implements Job {
    @Autowired
    ISqlDeleteService iSqlDeleteService;

    /**
     * 删除30天以前的粮情油情气调数据
     * @param jobExecutionContext
     * @throws JobExecutionException
     */
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE,-30);
        Date date = cal.getTime();
        iSqlDeleteService.removeOilHis(date);
        iSqlDeleteService.removeGrainHis(date);
        iSqlDeleteService.removeN2His(date);
    }
}
