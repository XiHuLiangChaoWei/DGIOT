package cn.zz.dgcc.DGIOT.service;

import java.util.Date;

/**
 * Created by: LT001
 * Date: 2020/7/10 14:33
 * ClassExplain :   定时删除相关
 * ->
 */
public interface ISqlDeleteService {
    int removeGrainHis(Date date);
    int removeN2His(Date date);
    int removeOilHis(Date date);
}
