package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.QuartzJob;
import com.github.pagehelper.PageInfo;
import org.quartz.SchedulerException;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2019/11/14 14:53
 * ClassExplain :
 * ->
 */
public interface JobService {

    //添加定时任务
    void addJob(QuartzJob quartzJob);

    //修改任务
    void updateJob(QuartzJob quartzJob) throws SchedulerException;

    //删除任务
    void deleteJob(QuartzJob quartzJob);

    //启动任务
    void JobStart(QuartzJob quartzJob);

    //暂停任务
    void pauseJob(QuartzJob quartzJob);

    //查询所有任务
    PageInfo<QuartzJob> selectAll(Integer page, Integer limit);

    //查询单个任务
    QuartzJob selectQuartzJobById(String id);


}
