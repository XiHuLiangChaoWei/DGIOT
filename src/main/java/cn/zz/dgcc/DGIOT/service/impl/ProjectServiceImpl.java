package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.VO.ProjectVO;
import cn.zz.dgcc.DGIOT.entity.*;
import cn.zz.dgcc.DGIOT.mapper.CompanyMapper;
import cn.zz.dgcc.DGIOT.mapper.DepotMapper;
import cn.zz.dgcc.DGIOT.mapper.DeviceMapper;
import cn.zz.dgcc.DGIOT.mapper.UserMapper;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Created by: YYL
 * Date: 2020/7/22 8:59
 * ClassExplain :
 * ->
 */
@Service
public class ProjectServiceImpl {
    @Autowired
    DepotService depotService;
    @Autowired
    CompanyService companyService;
    @Autowired
    UserService userService;
    @Autowired
    IotDeviceService iotDeviceService;
    @Autowired
    IoTService ioTService;
    @Autowired
    DeviceService deviceService;


    @Resource
    DepotMapper depotMapper;
    @Resource
    CompanyMapper companyMapper;
    @Resource
    UserMapper userMapper;
    ;
    @Resource
    DeviceMapper deviceMapper;


    ExecutorService executorService = Executors.newCachedThreadPool();
    ThreadPoolExecutor pool = (ThreadPoolExecutor) executorService;

    @Transactional
    public JsonResult<String> addP(ProjectVO projectVO) {
        int rs = companyMapper.selectCountByName(projectVO.getProject());
        if (rs != 0) {
            throw new ISqlException("项目已存在");
        }
        Company company = new Company(projectVO.getProject(), projectVO.getDesc());
        int rsPro = companyMapper.insertPj(company);
        if (rsPro != 1) {
            throw new ISqlException("新建项目出错");
        }
        //获取自动生成的项目id
        int cid = companyMapper.selectIdByName(projectVO.getProject());
        //向用户表中添加新用户
        User usr = new User(projectVO.getUsername(), projectVO.getPassword(), cid);
        usr.setType("3");
        int rsUser = userService.reg(usr);
        if (rsUser != 1) {
            throw new ISqlException("用户注册操作出错");
        }
        //根据项目id向depot表添加仓库信息
        for (int i = 1; i <= projectVO.getDepotNum(); i++) {
            Depot depot = new Depot(cid, i);
            depotMapper.insert(depot);
        }
        //根据项目地址描述向阿里云申请新设备
        //获取由“-”分割的string数组
        String[] tars = projectVO.getDescription().split("-");
        //拼接成拼音模式 XX-XX-XX-
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < tars.length; i++) {
            sb.append(ContextUtil.chinese2PinYin2(tars[i]));
            if (i < tars.length) {
                sb.append("-");
            }
        }
        String deviceNameSuf = sb.toString();
        int lqNumBefore = getTotal("a1RnedE9Gfb");
        int qtNumBefore = getTotal("a1J3Y773MWq");
        //添加粮情
        iotDeviceService.batchAddDevices("a1RnedE9Gfb", deviceNameSuf + "LQ", 0, projectVO.getLq());
        //添加气调
        iotDeviceService.batchAddDevices("a1J3Y773MWq", deviceNameSuf + "QT", 0, projectVO.getQt());
        //更新设备列表，
        int lqNumAfter;
        int qtNumAfter;
        //死循环 直到云端列表数和预期列表数目一致
        while (true) {
            lqNumAfter = getTotal("a1RnedE9Gfb");
            qtNumAfter = getTotal("a1J3Y773MWq");
            if (lqNumAfter == lqNumBefore + projectVO.getLq() && qtNumAfter == qtNumBefore + projectVO.getQt()) {
                break;
            }
        }
        int rsUpdate = savaDevList();
        //通过云端设备名为远端设备表更新项目信息
//        if (rsUpdate == (projectVO.getLq() + projectVO.getQt())) {
//            throw new ISqlException("更新设备列表出错");
//        } 导致报错的原因==分析 savaDevList()方法是将查询到的数据全部插入，所以 rsUpdate ！= 申请设备的数量
        int rsDev = deviceMapper.updateCidByDevInfo(cid, deviceNameSuf);
        if (rsDev == 0) {
            throw new ISqlException("更新设备所属项目出错");
        }
        return new JsonResult<>(200, "新项目添加成功,请管理员自行添加云端设备与仓库的映射关系");
    }

    List<Device> getTotalList() {
        List<Product> products = ioTService.getProductList();
        List<Device> devices = ioTService.getDeviceList(products);
        return devices;
    }

    public int savaDevList() {
        List<Device> devices = getTotalList();
        return deviceService.saveDeviceList(devices);
    }

    int getTotal(String pk) {
        return ioTService.queryDevice(pk).getInteger("Total");
    }
}
