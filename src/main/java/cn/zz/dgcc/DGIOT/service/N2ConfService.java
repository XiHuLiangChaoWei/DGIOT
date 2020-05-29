package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.N2Configure;

/**
 * Created by: YYL
 * Date: 2020/5/28 14:33
 * ClassExplain :
 * ->
 */
public interface N2ConfService {
    N2Configure getPZByDevName(String devName);

    int saveConf(N2Configure n2conf);
}
