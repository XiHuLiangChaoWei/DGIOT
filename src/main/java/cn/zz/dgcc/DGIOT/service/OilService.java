package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Oil;
import cn.zz.dgcc.DGIOT.entity.OilConf;

import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/6/10 9:41
 * ClassExplain :
 * ->
 */
public interface OilService {
    int saveOil(Oil oil);

    Oil getOilInfoByDevName(String devName);

    int saveConf(OilConf oilConf);

    OilConf getOilConfByDevName(String devName);

    List<Oil> getOilInfoListByDevName(String devName);
}
