package cn.zz.dgcc.DGIOT.mapper;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/5/21 17:02
 * ClassExplain :
 * ->
 */
public interface DepotDevMapper {
    Integer selectDepotIdByDevName(String devName);

    String selectDevNameByDepotIdAndType(int depotId, int type);

    List<String> selectDevNamesByDepotId(int depotId);
}
