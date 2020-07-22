package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.AppVersion;
import cn.zz.dgcc.DGIOT.mapper.AppVersionMapper;
import cn.zz.dgcc.DGIOT.service.AppVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by: LT001
 * Date: 2020/6/1 12:36
 * ClassExplain :
 * ->
 */
@Service
public class AppVersionServiceImpl implements AppVersionService {
    @Resource
    AppVersionMapper appVersionMapper;
    @Override
    public AppVersion getNowAppVersion() {
        return appVersionMapper.selectNewVersion();
    }
}
