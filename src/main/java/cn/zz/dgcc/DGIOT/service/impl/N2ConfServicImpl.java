package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;
import cn.zz.dgcc.DGIOT.mapper.N2ConfMapper;
import cn.zz.dgcc.DGIOT.service.N2ConfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by: YYL
 * Date: 2020/5/28 14:33
 * ClassExplain :
 * ->
 */
@Service
public class N2ConfServicImpl implements N2ConfService {
    @Resource
    N2ConfMapper n2ConfMapper;


    @Override
    public QTConfigure getPZByDevName(String devName) {
        QTConfigure rs = n2ConfMapper.selectConfByDevName(devName);
        return rs;
    }

    @Override
    public int saveConf(QTConfigure n2conf) {
        int rs = n2ConfMapper.insertNewConf(n2conf);
        return 0;
    }
}
