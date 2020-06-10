package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Company;
import cn.zz.dgcc.DGIOT.mapper.CompanyMapper;
import cn.zz.dgcc.DGIOT.service.CompanyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by: YYL
 * Date: 2020/6/4 18:01
 * ClassExplain :
 * ->
 */
@Service
public class CompanyServiceImpl implements CompanyService {
    @Resource
    CompanyMapper companyMapper;
    @Override
    public Company getC(int companyId) {
        return companyMapper.getComName(companyId);
    }

    @Override
    public int getCIDByName(String xiangMu) {
        return companyMapper.selectIdByName(xiangMu);
    }
}
