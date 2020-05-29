package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.N2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by: YYL
 * Date: 2020/4/26 8:58
 * ClassExplain :
 * ->
 */
public interface N2Service {
//    Map findAll();
    int saveN2(N2 n2);

    N2 getInfo(String msgId);

    N2 getNewInfoByDevName(String devName);

    N2 getNewInfo();

    N2 getNewInfoByDevName2(String devName, String type);
}
