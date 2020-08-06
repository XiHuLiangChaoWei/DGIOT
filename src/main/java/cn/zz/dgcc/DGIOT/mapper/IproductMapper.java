package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Iproduct;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/7/29 16:14
 * ClassExplain :
 * ->
 */
@Mapper
public interface IproductMapper {
    List<Iproduct> selectAll();
}
