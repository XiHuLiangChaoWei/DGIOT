package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Company;

/**
 * Created by: LT001
 * Date: 2020/6/4 18:01
 * ClassExplain :
 * ->
 */
public interface CompanyService {
    Company getC(int companyId);

    int getCIDByName(String xiangMu);

    int addPj(String project, String desc);
}
