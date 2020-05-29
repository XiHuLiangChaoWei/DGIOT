package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.N2Configure;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by: YYL
 * Date: 2020/5/28 14:38
 * ClassExplain :
 * ->
 */
@Mapper
public interface N2ConfMapper {
    N2Configure selectConfByDevName(String devName);

    int insertNewConf(N2Configure n2conf);
}
