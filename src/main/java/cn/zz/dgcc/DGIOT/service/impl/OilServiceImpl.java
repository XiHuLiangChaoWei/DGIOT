package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Oil;
import cn.zz.dgcc.DGIOT.entity.OilConf;
import cn.zz.dgcc.DGIOT.mapper.OilMapper;
import cn.zz.dgcc.DGIOT.service.OilService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by: LT001
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

    @Override
    public int saveConf(OilConf oilConf) {
        return oilMapper.insertConf(oilConf);
    }

    @Override
    public OilConf getOilConfByDevName(String devName) {
        return oilMapper.selectOilConfByName(devName);
    }

    @Override
    public List<Oil> getOilInfoListByDevName(String devName) {
        return oilMapper.selectTenOilInfoByName(devName);
    }
}
