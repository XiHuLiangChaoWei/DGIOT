package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.mapper.QuartzJobMapper;
import org.quartz.Scheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/6/4 17:44
 * ClassExplain :
 * ->
 */
@Service
public class IQuartzJobServiceImpl {
    @Autowired
    private Scheduler scheduler;
    @Resource
    private QuartzJobMapper quartzJobMapper;

    private final static Logger log = Logger.getLogger(IQuartzJobServiceImpl.class.getSimpleName());

    

}
