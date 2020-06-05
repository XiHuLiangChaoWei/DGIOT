package cn.zz.dgcc.DGIOT.utils;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;
import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.N2;
import cn.zz.dgcc.DGIOT.entity.Order;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.*;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/6/4 16:49
 * ClassExplain :
 * ->
 */
@Service
public class DownOrderUtils {
    int success = 200;
    private final static Logger log = Logger.getLogger(DownOrderUtils.class.getSimpleName());
    @Autowired
    IoTService ioTService;
    @Autowired
    OrderService orderService;
    @Autowired
    DepotService depotService;
    @Autowired
    GrainService grainService;
    @Autowired
    N2Service n2Service;
    @Autowired
    DeviceService deviceService;
    @Autowired
    UserService userService;
    @Autowired
    QTConfService QTConfService;
    @Autowired
    AppVersionService appVersionService;

    /**
     * json查询配置
     * @param depotId
     * @param userId
     */
    public void CXPZ(int depotId,int userId) {
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        Device rs = deviceService.getDevByDevName(devName);
        ConfCommondBuilder ccb = ConfCommondBuilder.getInstance();
        String commond = ccb.getPZCXInfo(rs.getDevId());
        String pk = rs.getProductKey();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(commond + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, commond, pk, null);
        log.info(json.toJSONString());
        Order o = new Order(userId, 0, json.getString("MessageId"), 2, commond,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
            r = 0;
        }
    }

    /**
     * 同步时间
     * @param depotId
     * @param userId
     */
    public void JsonTime(int depotId, int userId) {
        ConfCommondBuilder confCommondBuilder = ConfCommondBuilder.getInstance();
        String commond;
        String devId;
        String pk;
        String topicFullName;
        List<Device> allDev = deviceService.getAllDev();
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        for (Device d : allDev
        ) {
            devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
            devId = d.getDevId();
            commond = confCommondBuilder.setTimes(devId);
            pk = d.getProductKey();
            topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
            log.info(commond + "-----" + topicFullName);
            JSONObject json = ioTService.pub(topicFullName, commond, pk, null);
            log.info(json.toJSONString());
            Order o = new Order(userId, 0, json.getString("MessageId"), 2, commond,
                    ContextUtil.getTimeYMDHMM(null), d.getDeviceName(), json.getString("Success"));
            int r = orderService.save(o);
            if (r == 1) {
                log.info("保存命令成功");
                r = 0;
            }
        }
    }

    /**
     * 获取制氮机信息
     * @param n2
     * @param userId
     * @param choose
     * @return
     */
    public JsonResult<JSONObject> getN2DevInfo(Device n2, int userId, int choose) {
        String pk = n2.getProductKey();
        String devName = n2.getDeviceName();
        //拼装下发命令所需参数
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        String msg = new String();
        String msgFix = new String();
        switch (choose) {
            case 1:
                msg = N2DevCommondBuilder.getN2Purity();
                msgFix = "%";
                break;
            case 2:
                msg = N2DevCommondBuilder.getN2Flow();
                msgFix = "m³/h";
                break;
            case 3:
                msg = N2DevCommondBuilder.getN2Pressure();
                msgFix = "bar";
                break;
            case 4:
                msg = N2DevCommondBuilder.getN2Temp();
                msgFix = "摄氏度";
                break;
        }

        JSONObject json = ioTService.pub(topicFullName, msg, pk, "1");
        Order o = new Order(userId, 0, json.getString("MessageId"), 5, msg,
                ContextUtil.getTimeYMDHMM(null), devName, json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        //获取最新存入数据库的制氮机返回 休眠0.5s
        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String repeat = getN2DevR("2", n2);
        if (repeat.startsWith("030304")) {
            JSONObject jsonObject = new JSONObject();
            repeat = repeat.substring(6, 14);
            Float rs = BytesUtil.Hex2Float(repeat);
            jsonObject.put(String.valueOf(choose), rs + msgFix);
            return new JsonResult<>(success, jsonObject);
        }
        return new JsonResult<>(success, "");
    }

    /**
     * 获取制氮机状态
     * @param n2
     * @param userId
     * @return
     */
    public JsonResult<Integer> getN2DevStatus(Device n2, int userId) {
        String pk = n2.getProductKey();
        String devName = n2.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        //查询制氮机运行装填
        String getStatus = N2DevCommondBuilder.getN2DevStatus();
        JSONObject json = ioTService.pub(topicFullName, getStatus, pk, "1");
        Order o = new Order(userId, 0, json.getString("MessageId"), 5, getStatus,
                ContextUtil.getTimeYMDHMM(null), devName, json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        try {
            Thread.sleep(1200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String repeat = getN2DevR("2", n2);
        if ("0303020004C047".equals(repeat)) {
            log.info("设备正在运行");
            return new JsonResult<>(success, 1);
        } else if ("0303020008C042".equals(repeat)) {
            log.info("设备已停机");
            return new JsonResult<>(success, 0);
        } else {
            return new JsonResult<>(success, 0);
        }
    }

    /**
     * 制氮机开关
     * @param controller
     * @param n2
     * @param userId
     */
    public void N2DevControler(String controller, Device n2, int userId) {
        String pk = n2.getProductKey();
        String devName = n2.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        //新建进程执行制氮机开操作
        if ("on".equals(controller)) {
            Thread t = new Thread(() -> {
                String[] on = N2DevCommondBuilder.getN2DevOn();
                JSONObject json = ioTService.pub(topicFullName, on[0].replace(" ", ""), pk, "1");
                Order o = new Order(userId, 0, json.getString("MessageId"), 5, on[0],
                        ContextUtil.getTimeYMDHMM(null), devName, json.getString("Success"));
                System.out.println("OOOO----" + o);
                int r = orderService.save(o);
                if (r == 1) {
                    log.info("保存命令成功");
                }
                try {
                    Thread.sleep(1200);
                } catch (InterruptedException e) {

                }
                json = ioTService.pub(topicFullName, on[1].replace(" ", ""), pk, "1");
                o = new Order(userId, 0, json.getString("MessageId"), 5, on[1],
                        ContextUtil.getTimeYMDHMM(null), devName, json.getString("Success"));
                System.out.println("1111----" + o);
                r = orderService.save(o);
                if (r == 1) {
                    log.info("保存命令成功");
                }

            });
            t.start();
        } else if ("off".equals(controller)) {
            Thread t = new Thread(() -> {
                String[] off = N2DevCommondBuilder.getN2DevOff();
                JSONObject json = ioTService.pub(topicFullName, off[0], pk, "1");
                Order o = new Order(userId, 0, json.getString("MessageId"), 5, off[0],
                        ContextUtil.getTimeYMDHMM(null), devName, json.getString("Success"));
                System.out.println("OOOO----" + o);
                int r = orderService.save(o);
                if (r == 1) {
                    log.info("保存命令成功");
                }
                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {

                }
                json = ioTService.pub(topicFullName, off[1], pk, "1");
                o = new Order(userId, 0, json.getString("MessageId"), 5, off[1],
                        ContextUtil.getTimeYMDHMM(null), devName, json.getString("Success"));
                System.out.println("1111----" + o);
                r = orderService.save(o);
                if (r == 1) {
                    log.info("保存命令成功");
                }
            });
            t.start();
        }
    }


    /**
     * 下发查询N2命令
     *
     * @param userId
     * @param rs
     */
    public JsonResult<String> deployN2Order(int userId, Device rs) {
        log.info("IndexController=" + rs);
        GasInfoCommondBuilder gasInfoCommondBuilder = GasInfoCommondBuilder.getInstance();
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();
        gasInfoCommondBuilder.setDevBH(devBH);
        gasInfoCommondBuilder.setDevZH(devZH);
        BuildMessage gasMsg = gasInfoCommondBuilder.build();
        String pk = rs.getProductKey();
        String devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(gasMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, gasMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(userId, 0, json.getString("MessageId"), 2, gasMsg.toString(),
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        if (json.getString("Success").equals("true")) {
            return new JsonResult<>(success, "sendMsg = " + json.getString("Success"));
        }
        return new JsonResult<>(success, "");
    }

    /**
     * 下发查询grain
     * @param userId
     * @param rs
     * @return
     */
    public JsonResult<String> deployGrainOrder(int userId, Device rs) {
        log.info("IndexController=" + rs);
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();
        if (devBH == null) {
            devBH = "01";
        }
        if (devZH == null) {
            devZH = "01";
        }
        GrainInfoCommondBuilder grainInfoCommondBuilder = GrainInfoCommondBuilder.getGrainInfoCommondBuilder();
        grainInfoCommondBuilder.setDevBH(devBH);
        grainInfoCommondBuilder.setDevZH(devZH);
        BuildMessage grainMsg = grainInfoCommondBuilder.build();
        String pk = rs.getProductKey();
        String devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(grainMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, grainMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(userId, 0, json.getString("MessageId"), 3, grainMsg.toString(),
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        if (json.getString("Success").equals("true")) {
            return new JsonResult<>(success, "sendMsg = " + json.getString("Success"));
        }
        return new JsonResult<>(success, "");
    }

    /**
     * type 1-开关制氮机  2-查询命令
     *
     * @param
     * @param n2
     * @return
     */
    private String getN2DevR(String type, Device n2) {
        String devName = n2.getDeviceName();
        N2 rs = n2Service.getNewInfoByDevName2(devName, type);
        return rs.getContent().toUpperCase();
    }
}