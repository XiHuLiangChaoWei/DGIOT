package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Oil;

/**
 * Created by: YYL
 * Date: 2020/6/10 9:41
 * ClassExplain :
 * ->
 */
public interface OilService {
    int saveOil(Oil oil);

    Oil getOilInfoByDevName(String devName);
}
