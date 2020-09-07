package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.DgN2;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface DgN2Mapper {
    List getsel();

    List getcompanyid(String companyid,String cangfang);
}
