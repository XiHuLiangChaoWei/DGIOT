package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Oil;
import cn.zz.dgcc.DGIOT.entity.OilConf;

/**
 * Created by: YYL
 * Date: 2020/6/10 9:43
 * ClassExplain :
 * ->
 */
public interface OilMapper {
    int insert(Oil oil);

    Oil selectOilInfoByName(String devName);

    int insertConf(OilConf oilConf);

    OilConf selectOilConfByName(String devName);
}
