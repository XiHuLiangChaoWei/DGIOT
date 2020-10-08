package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Order;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:32
 * ClassExplain :
 * ->
 */
public interface OrderService {
    int save(Order o);

    Order getNewOn1(String s);

    void updateOrder(String messageId);

    Order getdevid(String devId);
}
