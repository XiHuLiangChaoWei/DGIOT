package cn.zz.dgcc.DGIOT.service;


import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DgN2Service {

    List getsel();

    List getcompanyid(String companyId,String cangfang);
}
