package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * Created by: YYL
 * Date: 2020/5/23 15:04
 * ClassExplain :
 * ->
 */
@Mapper
public interface OrderMapper {
    int insert(Order o);

    Order getNewOpen1(String s);

    void update(String messageId);
}
