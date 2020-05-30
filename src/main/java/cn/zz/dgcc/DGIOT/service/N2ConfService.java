package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;

/**
 * Created by: YYL
 * Date: 2020/5/28 14:33
 * ClassExplain :
 * ->
 */
public interface N2ConfService {
    QTConfigure getPZByDevName(String devName);

    int saveConf(QTConfigure n2conf);
}
