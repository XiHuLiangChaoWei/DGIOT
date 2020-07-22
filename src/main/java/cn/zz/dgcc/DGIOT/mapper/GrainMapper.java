package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Grain;
import cn.zz.dgcc.DGIOT.entity.GrainInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created by: LT001
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

    List<Grain> selectGrainInfoByDevNameLimitByDate(String devName, Date start, Date end);

    int quartzDelete(Date date);

    Integer selectByMsgId(String batchId);

    int updateAdd(String devName, Integer address,String batchId);

    Grain selectNewContentByDevNameAndDevAdd(String devName, int devAdd);
}
