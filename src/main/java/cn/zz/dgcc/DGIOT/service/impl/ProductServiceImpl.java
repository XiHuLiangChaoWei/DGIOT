package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Iproduct;
import cn.zz.dgcc.DGIOT.mapper.IproductMapper;
import cn.zz.dgcc.DGIOT.service.ProductService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/7/29 16:10
 * ClassExplain :
 * ->
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Resource
    IproductMapper iproductMapper;
    @Override
    public List<Iproduct> getAll() {
        return iproductMapper.selectAll();
    }
}
