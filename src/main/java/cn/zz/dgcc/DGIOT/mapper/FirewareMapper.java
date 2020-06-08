package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Fireware;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by: YYL
 * Date: 2020/6/2 11:47
 * ClassExplain :
 * ->
 */
@Mapper
public interface FirewareMapper {
    Fireware selectFirewareVersion(String version);

    int addNewVersion(Fireware fireware);

}
