package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.AppVersion;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by: YYL
 * Date: 2020/6/1 12:39
 * ClassExplain :
 * ->
 */
@Mapper
public interface AppVersionMapper {
    AppVersion selectNewVersion();
}
