package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.SunConf;
import cn.zz.dgcc.DGIOT.mapper.SunDevMapper;
import cn.zz.dgcc.DGIOT.service.SunConfService;
import org.springframework.stereotype.Service;


import javax.annotation.Resource;

/**
 * Created by: LT001
 * Date: 2020/7/13 9:22
 * ClassExplain :
 * ->
 */
@Service
public class SunConfServiceImpl implements SunConfService {
    @Resource
    SunDevMapper sunDevMapper;

    @Override
    public int saveConf(SunConf sunConf) {
        int rs = sunDevMapper.CidDidAdd(sunConf);
        if (rs == 1) {
            rs = sunDevMapper.update(sunConf);
        } else if(rs == 0){
            rs = sunDevMapper.insert(sunConf);
        }
        return rs;
    }
}
