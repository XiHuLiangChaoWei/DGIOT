package cn.zz.dgcc.DGIOT.mapper;

import cn.zz.dgcc.DGIOT.entity.Company;

/**
 * Created by: LT001
 * Date: 2020/6/4 18:03
 * ClassExplain :
 * ->
 */
public interface CompanyMapper {
    Company getComName(int companyId);

    int selectIdByName(String xiangMu);

    int insertPj(Company company);

    int selectCountByName(String project);
}
