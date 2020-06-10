package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Oil;
import cn.zz.dgcc.DGIOT.mapper.OilMapper;
import cn.zz.dgcc.DGIOT.service.OilService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by: YYL
 * Date: 2020/6/10 9:41
 * ClassExplain :
 * ->
 */
@Service
public class OilServiceImpl implements OilService {
    @Resource
    OilMapper oilMapper;

    @Override
    public int saveOil(Oil oil) {
        return oilMapper.insert(oil);
    }

    @Override
    public Oil getOilInfoByDevName(String devName) {
        return oilMapper.selectOilInfoByName(devName);
    }
}
