package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.mapper.DgN2Mapper;
import cn.zz.dgcc.DGIOT.service.DgN2Service;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Component
@Service
public class DgN2ServiceImp implements DgN2Service {

    @Resource
    DgN2Mapper dgN2Mapper;

    @Override
    public List getsel() {

        return dgN2Mapper.getsel();
    }

    @Override
    public List getcompanyid(String id,String cangfang) {
        return dgN2Mapper.getcompanyid(id,cangfang);
    }
}
