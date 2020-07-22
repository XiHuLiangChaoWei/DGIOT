package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.SunConf;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by: LT001
 * Date: 2020/7/13 9:21
 * ClassExplain :
 * ->
 */
@Mapper
public interface SunDevMapper {
    int CidDidAdd(SunConf sunConf);

    int update(SunConf sunConf);

    int insert(SunConf sunConf);
}
