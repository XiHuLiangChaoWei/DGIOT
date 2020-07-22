package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;

/**
 * Created by: LT001
 * Date: 2020/5/28 14:33
 * ClassExplain :
 * ->
 */
public interface QTConfService {
    QTConfigure getPZByDevName(String devName);

    int saveConf(QTConfigure n2conf);
}
