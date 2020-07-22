package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Grain;
import cn.zz.dgcc.DGIOT.entity.GrainInfo;
import cn.zz.dgcc.DGIOT.entity.N2;
import cn.zz.dgcc.DGIOT.mapper.GrainMapper;
import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.service.GrainService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:27
 * ClassExplain :
 * ->
 */
@Service
public class GrainServiceImpl implements GrainService {
    @Resource
    GrainMapper grainMapper;

//    @Override
//    public Map findAll() {
//        //从数据库获取列表
//        List<GrainInfo> list = grainMapper.getAll();
//        List lst = new ArrayList();
//        Set<String> depotList = new HashSet();
//        //获取仓库列表
//        for (GrainInfo a : list
//        ) {
////            System.out.println(a.getDepotId());
//            lst.add(a.getDepotId());
////            System.out.println(lst.toString());
//        }
//        depotList.addAll(lst);
//        //新建MAP用于存储 仓库-温度点的对应关系
//        Map<String, Set> result = new HashMap<>();
//        //遍历列表，建立仓库表和温湿度集合的key-value关系
//        for (String depotI : depotList
//        ) {
//            //
//            Set<GrainInfo> GrainSet = new LinkedHashSet<>();
//            for (GrainInfo a : list
//            ) {
//                if (a.getDepotId().equals(depotI)) {
//                    GrainSet.add(a);
//                    //                    log.info(a);
//
//                }
//            }
//            result.put(depotI, GrainSet);
//        }
//
//        return result;
//    }

    @Override
    public int saveGrain(Grain grain) {
        int rs = grainMapper.insert(grain);
        return rs;
    }

    @Override
    public Grain getNewGrainInfoByDevName(String devName) {
        Grain grain = grainMapper.selectNewContentByDevName(devName);
        return grain;
    }

    @Override
    public List<Grain> getGrainInfosByDevName(String devName1) {
        List<Grain> ls = grainMapper.selectGrainInfoByDevName(devName1);
        return ls;
    }

    @Override
    public Grain getChooseGrainInfo(String batchId) {
        return grainMapper.selectChooseGrainInfo(batchId);
    }

    @Override
    public List<Grain> getGrainList(String devName, Date start, Date end) {
        return grainMapper.selectGrainInfoByDevNameLimitByDate(devName,start,end);

    }

    @Override
    public int updateDev(String devName, Integer address,String batchId) {
        return grainMapper.updateAdd(devName,address,batchId);
    }

    @Override
    public Grain getNewGrainInfoByDevNameAndDevAdd(String devName, int devAdd) {
        return grainMapper.selectNewContentByDevNameAndDevAdd(devName,devAdd);
    }
}
