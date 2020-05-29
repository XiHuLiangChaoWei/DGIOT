package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Grain;
import cn.zz.dgcc.DGIOT.entity.GrainInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/4/23 15:27
 * ClassExplain :
 * ->
 */
@Mapper
public interface GrainMapper {
    List<GrainInfo> getAll();

    int insert(Grain grain);

    Grain selectNewContentByDevName(String devName);

    List<Grain> selectGrainInfoByDevName(String devName1);

    Grain selectChooseGrainInfo(String batchId);
}
