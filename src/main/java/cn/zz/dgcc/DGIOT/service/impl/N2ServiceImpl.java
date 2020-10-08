package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.N2;
import cn.zz.dgcc.DGIOT.mapper.N2Mapper;
import cn.zz.dgcc.DGIOT.service.N2Service;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.swing.text.html.parser.Entity;
import java.util.*;

/**
 * Created by: LT001
 * Date: 2020/4/26 8:58
 * ClassExplain :
 * ->
 */
@Service
public class N2ServiceImpl implements N2Service {
    @Resource
    N2Mapper n2Mapper;

//    @Override
//    public Map findAll() {
//        List<N2> list = n2Mapper.getAll();
//        List lst = new ArrayList();
//        /*
//            首先遍历数据取出表中所有的depotId；
//         */
//        Set<String> depotList= new HashSet();
//        for (N2 a : list
//        ) {
////            System.out.println(a.getDepotId());
//            lst.add(a.getDepotId());
////            System.out.println(lst.toString());
//        }
//        depotList.addAll(lst);
////        System.out.println(depotList);
//
//        /*
//            第二步，遍历数据并按照deptoId存放到HASHMAP
//         */
//
//        Map<String, Set> result = new HashMap<>();
//        for (String depotI:depotList
//             ) {
//            Set<N2> n2Set = new LinkedHashSet<>();
//            for (N2 a:list
//                 ) {
//                if(a.getDepotId().equals(depotI)){
//                    n2Set.add(a);
////                    log.info(a);
//                }
//            }
//            result.put(depotI,n2Set);
//        }
//        Iterator<Map.Entry<String, Set>> it = result.entrySet().iterator();
//        while(it.hasNext()){
//            Map.Entry<String, Set> entity = it.next();
//            String key = entity.getKey();
//            Set value = entity.getValue();
////            log.info(key);
////            log.info(value.toString());
//        }
//
//        return result;
//    }

    @Override
    public int saveN2(N2 n2) {
        int rs = n2Mapper.insert(n2);
        return rs;
    }

    @Override
    public N2 getInfo(String msgId) {
        N2 rs = n2Mapper.selectInfoByMsgId(msgId);
        return rs;
    }

    @Override
    public N2 getNewInfoByDevName(String devName) {
        return n2Mapper.selectNewInfoByDevName(devName);
    }

    @Override
    public N2 getNewInfo() {
        return n2Mapper.selectNewInfo();
    }

    @Override
    public N2 getNewInfoByDevName2(String devName, String type) {
        return n2Mapper.selectNewInfoByDevName2(devName, type);
    }

    @Override
    public List<N2> getAlldevname(String devname) {
        return n2Mapper.getAlldevname(devname);
    }


}
