package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Grain;
import cn.zz.dgcc.DGIOT.entity.GrainInfo;

import java.util.List;
import java.util.Map;

/**
 * Created by: YYL
 * Date: 2020/4/23 15:27
 * ClassExplain :
 * ->
 */
public interface GrainService {
//    Map findAll();

    int saveGrain(Grain grain);

    Grain getNewGrainInfoByDevName(String devName);

    List<Grain> getGrainInfosByDevName(String devName1);

    Grain getChooseGrainInfo(String batchId);
}
