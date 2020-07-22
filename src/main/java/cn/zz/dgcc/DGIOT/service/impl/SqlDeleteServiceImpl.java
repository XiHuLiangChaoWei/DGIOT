package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.mapper.GrainMapper;
import cn.zz.dgcc.DGIOT.mapper.N2Mapper;
import cn.zz.dgcc.DGIOT.mapper.OilMapper;
import cn.zz.dgcc.DGIOT.service.ISqlDeleteService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by: LT001
 * Date: 2020/7/10 14:34
 * ClassExplain :
 * ->
 */
@Service
public class SqlDeleteServiceImpl implements ISqlDeleteService {
    @Resource
    GrainMapper grainMapper;
    @Resource
    N2Mapper n2Mapper;
    @Resource
    OilMapper oilMapper;


    @Override
    public int removeGrainHis(Date date) {
        return grainMapper.quartzDelete(date);
    }

    @Override
    public int removeN2His(Date date) {
        return n2Mapper.quartzDelete(date);
    }

    @Override
    public int removeOilHis(Date date) {
        return oilMapper.quartzDelete(date);
    }
}
