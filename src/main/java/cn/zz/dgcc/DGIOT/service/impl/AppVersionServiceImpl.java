package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.AppVersion;
import cn.zz.dgcc.DGIOT.mapper.AppVersionMapper;
import cn.zz.dgcc.DGIOT.service.AppVersionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by: LT001
 * Date: 2020/6/1 12:36
 * ClassExplain : app版本相关
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

    @Transactional
    @Override
    public int gengxin(AppVersion appVersion) {
        return appVersionMapper.updateAppVer(appVersion);
    }
}
