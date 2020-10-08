package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.N2;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created by: LT001
 * Date: 2020/4/26 8:53
 * ClassExplain :
 * ->
 */
@Mapper
public interface N2Mapper {
     int quartzDelete(Date date) ;

    List<N2> getAll();

    int insert(N2 n2);

    N2 selectInfoByMsgId(String msgId);

    N2 selectNewInfoByDevName(String devName);

    N2 selectNewInfo();

    N2 selectNewInfoByDevName2(String devName, String type);

    Integer selectByMsgId(String msgId);

    List<N2> getAlldevname(String devname);
}
