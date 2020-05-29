package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.controller.GrainJob;
import cn.zz.dgcc.DGIOT.entity.QuartzJob;
import cn.zz.dgcc.DGIOT.mapper.QuartzJobMapper;
import cn.zz.dgcc.DGIOT.service.JobService;
import cn.zz.dgcc.DGIOT.utils.GenerateUUID;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by: YYL
 * Date: 2019/11/14 14:53
 * ClassExplain :
 * ->
 */
@Service
public class JobServiceImpl implements JobService {

    @Autowired
    private Scheduler scheduler;

    @Resource
    private QuartzJobMapper quartzJobMapper;


    private final static Logger LOGGER = LoggerFactory.getLogger(JobServiceImpl.class);


    /**
     * 添加任务
     *
     */
    @Override
    public void addJob(QuartzJob quartzJob){

        try{

            settingQuartz(quartzJob);

            JobDetail jobDetail = JobBuilder.newJob(quartzJob.getaClass())
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCron()))
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail,cronTrigger);


            //判断任务是否开启
            JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
            scheduler.pauseJob(jobKey);
            quartzJob.setStart("READY");


            quartzJobMapper.saveQuartzJob(quartzJob);
        }catch(Exception e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }






    }


    /**
     * 修改任务
     * @param quartzJob
     */
    @Override
    public void updateJob( QuartzJob quartzJob) {

        TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        try{
            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null)
                return;
            //停止调度器
            scheduler.pauseTrigger(triggerKey);
            //移除调度器
            scheduler.unscheduleJob(triggerKey);
            //移除任务
            scheduler.deleteJob(jobKey);

            //quartzJob.setJobName(quartzJob.getJobName()+quartzJob.getJobName().substring(quartzJob.getJobName().length()-2));
            if ("grain".equals(quartzJob.getType())){
                quartzJob.setaClass(GrainJob.class);
            }

            JobDetail jobDetail = JobBuilder.newJob(quartzJob.getaClass())
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .build();

            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(quartzJob.getJobName(), quartzJob.getJobGroup())
                    .withSchedule(CronScheduleBuilder.cronSchedule(quartzJob.getCron()))
                    .startNow()
                    .build();
            scheduler.scheduleJob(jobDetail,cronTrigger);

            JobKey jobKey1 = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());
            if (!"RUN".equals(quartzJob.getStart()))
                scheduler.pauseJob(jobKey);

            quartzJobMapper.updateQuartzJob(quartzJob);
            LOGGER.info("修改了一个任务=====");

        }catch(SchedulerException e){
            e.printStackTrace();
        }




    }

    /**
     * 移除一个任务
     * @param quartzJob
     */
    @Override
    public void deleteJob(QuartzJob quartzJob) {

        TriggerKey triggerKey = TriggerKey.triggerKey(quartzJob.getJobName(), quartzJob.getJobGroup());
        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        try{

            Trigger trigger = scheduler.getTrigger(triggerKey);
            if (trigger == null)
                return;
            //停止触发器
            scheduler.pauseTrigger(triggerKey);
            //移除触发器
            scheduler.unscheduleJob(triggerKey);
            //移除任务
            scheduler.deleteJob(jobKey);

            quartzJobMapper.deleteQuartzJob(quartzJob.getId());

            LOGGER.info("移除了任务 : " + quartzJob.getSchemeName());
        }catch(SchedulerException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }


    }


    /**
     * 恢复一个任务
     * @param quartzJob
     */
    @Override
    public void JobStart(QuartzJob quartzJob) {

        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        try{
            scheduler.resumeJob(jobKey);
            quartzJob.setStart("RUN");
            quartzJobMapper.updateQuartzJob(quartzJob);
            LOGGER.info("恢复了一个任务 : " + quartzJob.getSchemeName());
        }catch(SchedulerException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }


    /**
     * 暂停一个任务
     */
    @Override
    public void pauseJob(QuartzJob quartzJob) {

        JobKey jobKey = JobKey.jobKey(quartzJob.getJobName(), quartzJob.getJobGroup());

        try{
            scheduler.pauseJob(jobKey);

            quartzJob.setStart("READY");
            quartzJobMapper.updateQuartzJob(quartzJob);
            LOGGER.info("暂停了一个任务 : " + quartzJob.getSchemeName());
        }catch(SchedulerException e){
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 分页查询所有的定时任务
     * @return
     */
    @Override
    public PageInfo<QuartzJob> selectAll(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<QuartzJob> quartzJobs = quartzJobMapper.selectAll();
        PageInfo<QuartzJob> result = new PageInfo(quartzJobs);
        return result;
    }

    @Override
    public QuartzJob selectQuartzJobById(String id) {
        return quartzJobMapper.selectQuartzJobById(id);
    }


    private synchronized Integer getSchedulerLength() throws SchedulerException {
        List<String> triggerGroupNames = scheduler.getTriggerGroupNames();
        return triggerGroupNames.size() + 1;
    }


    private QuartzJob settingQuartz(QuartzJob quartzJob) throws SchedulerException {
        //根据已建立的定时任务量生成std
        Integer std = getSchedulerLength();
        
        quartzJob.setId(GenerateUUID.GetUUID());
        quartzJob.setJobName(quartzJob.getType()+"_job_"+std);
        quartzJob.setJobGroup("group_"+std);
        quartzJob.setJobKey(quartzJob.getType()+std);
        if ("grain".equals(quartzJob.getType())){
            quartzJob.setaClass(GrainJob.class);
        }
        quartzJob.setJobClass(GrainJob.class.toString());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        quartzJob.setUpdateTime(sdf.format(new Date()));

        return quartzJob;
    }






}
