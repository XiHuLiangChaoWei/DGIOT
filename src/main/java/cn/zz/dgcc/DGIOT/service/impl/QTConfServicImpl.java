package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;
import cn.zz.dgcc.DGIOT.mapper.QTConfMapper;
import cn.zz.dgcc.DGIOT.service.QTConfService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by: LT001
 * Date: 2020/5/28 14:33
 * ClassExplain :
 * ->
 */
@Service
public class QTConfServicImpl implements QTConfService {
    @Resource
    QTConfMapper QTConfMapper;


    @Override
    public QTConfigure getPZByDevName(String devName) {
        QTConfigure rs = QTConfMapper.selectConfByDevName(devName);
        return rs;
    }

    @Override
    public int saveConf(QTConfigure n2conf) {
        int rs = QTConfMapper.insertNewConf(n2conf);
        return rs;
    }
}
