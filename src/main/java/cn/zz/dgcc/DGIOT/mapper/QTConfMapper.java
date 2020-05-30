package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by: YYL
 * Date: 2020/5/28 14:38
 * ClassExplain :
 * ->
 */
@Mapper
public interface QTConfMapper {
    QTConfigure selectConfByDevName(String devName);

    int insertNewConf(QTConfigure n2conf);
}
