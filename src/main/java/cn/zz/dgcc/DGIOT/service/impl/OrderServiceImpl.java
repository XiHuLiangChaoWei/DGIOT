package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Order;
import cn.zz.dgcc.DGIOT.mapper.OrderMapper;
import cn.zz.dgcc.DGIOT.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:32
 * ClassExplain :
 * ->
 */
@Service
public class OrderServiceImpl implements OrderService {
    @Resource
    OrderMapper orderMapper;

    @Override
    public int save(Order o) {
        return orderMapper.insert(o);
    }

    @Override
    public Order getNewOn1(String s) {
        return orderMapper.getNewOpen1(s);
    }

    @Override
    public void updateOrder(String messageId) {
        orderMapper.update(messageId);
    }

    @Override
    public Order getdevid(String devId) {
        return orderMapper.getdevid(devId);
    }
}
