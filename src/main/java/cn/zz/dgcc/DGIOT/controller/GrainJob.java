package cn.zz.dgcc.DGIOT.controller;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2019/11/8 11:12
 * ClassExplain :
 * ->
 */
public class GrainJob implements Job {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        log.info("GrainJob 测试打印功能................");
    }
}
