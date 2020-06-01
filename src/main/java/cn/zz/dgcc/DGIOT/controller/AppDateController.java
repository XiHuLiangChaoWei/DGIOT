package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.QTConfigure;
import cn.zz.dgcc.DGIOT.entity.*;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg4AnalysisN2;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/5/21 15:54
 * ClassExplain : 移动端接口
 * ->
 */
@Controller
@RequestMapping("/app")
public class AppDateController extends BaseController {
    private final static Logger log = Logger.getLogger(AppDateController.class.getName());

    @Autowired
    DepotService depotService;
    @Autowired
    GrainService grainService;
    @Autowired
    N2Service n2Service;
    @Autowired
    DeviceService deviceService;
    @Autowired
    IoTService ioTService;
    @Autowired
    OrderService orderService;
    @Autowired
    UserService userService;
    @Autowired
    QTConfService QTConfService;
    @Autowired
    AppVersionService appVersionService;


    @RequestMapping("DGCC.apk")
    @ResponseBody
    public void download(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws UnsupportedEncodingException {
        String downloadFilePath = "D:/dgcc";//被下载的文件在服务器中的路径,
        String fileName = "DGCC.apk";//被下载文件的名称
        File file = new File(downloadFilePath + "/" + fileName);
        if (file.exists()) {
            httpServletResponse.setContentType("application/vnd.ms-excel;charset=UTF-8");
            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setHeader("Content-Disposition", "attachment;fileName=" + java.net.URLEncoder.encode(fileName, "UTF-8"));
            byte[] buffer = new byte[1024];
            FileInputStream fis = null;
            BufferedInputStream bis = null;
            OutputStream os = null;
            try {
                os = httpServletResponse.getOutputStream();
                fis = new FileInputStream(file);
                bis = new BufferedInputStream(fis);
                int i = bis.read(buffer);
                while (i != -1) {
                    os.write(buffer);
                    i = bis.read(buffer);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                bis.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    @RequestMapping("getAppVersion")
    @ResponseBody
    public JsonResult<AppVersion> getVersion() {
        AppVersion appVersion = appVersionService.getNowAppVersion();
        return new JsonResult<>(success, appVersion);
    }


    /**
     * type 1-开关制氮机  2-查询命令
     *
     * @param
     * @return
     */
    private String getN2DevR(String type) {
        N2 rs = n2Service.getNewInfoByDevName2("ZD000001", type);
        return rs.getContent().toUpperCase();
    }

    @RequestMapping("/t")
    public String t(Model model) {
        return "html/sql";
    }

    /**
     * 制氮机开关命令   GET  controller == on ? on : controller == off ? off : wrong
     *
     * @param controller on/off
     * @return
     */
    @RequestMapping("/N2DevControler")
    @ResponseBody
    public JsonResult<String> N2Dev(String controller, HttpSession h) {
        //从session获取userId
        int userId = getUserIdFromSession(h);
        //获取当前用户管理下的制氮机
        Device n2 = deviceService.getN2DevByUser(userId);

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
//            if ("030600230001B822".equals(getN2DevR("1"))) {
//                t.interrupt();
//            }
//            if ("03060023000079E2".equals(getN2DevR("1"))) {
//                return new JsonResult<>(success, "下发成功");
//            }

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
//            if ("03060024000109E3".equals(getN2DevR("1"))) {
//                t.interrupt();
//            }
//            if ("030600240000C823".equals(getN2DevR("1"))) {
//                return new JsonResult<>(success, "下发成功");
//            }
        }
        return new JsonResult<>(success, "下发成功");
    }

    /**
     * 获取制氮机运行状态,需要解析返回值并回复
     *
     * @return
     */
    @RequestMapping("/getN2DevStatus")
    @ResponseBody
    public JsonResult<String> N2DevStatus(HttpSession h) {
        //从session获取userId
        int userId = getUserIdFromSession(h);
        //获取当前用户管理下的制氮机
        Device n2 = deviceService.getN2DevByUser(userId);
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
        String repeat = getN2DevR("2");
        if ("0303020008C042".equals(repeat)) {
            return new JsonResult<>(success, "设备正在运行！");
        } else if ("0303020009C042".equals(repeat)) {
            return new JsonResult<>(success, "设备已关机！");
        }

        return new JsonResult<>(success, "···");
    }

    /**
     * 获取制氮机具体属性,需要解析返回值并回复
     *
     * @param choose 1氮气纯度    2氮气流量    3氮气压力    4氮气温度
     * @return
     */
    @RequestMapping("/getN2DevInfo")
    @ResponseBody
    public JsonResult<JSONObject> N2DevInfo(int choose, HttpSession h) {
        //获取制氮机的5元组
        //从session获取userId
        int userId = getUserIdFromSession(h);
        //获取当前用户管理下的制氮机
        Device n2 = deviceService.getN2DevByUser(userId);

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
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String repeat = getN2DevR("2");
        if (repeat.startsWith("030304")) {
            JSONObject jsonObject = new JSONObject();
            Float rs = BytesUtil.Hex2Float(repeat);
            jsonObject.put(String.valueOf(choose), rs + msgFix);

            return new JsonResult<>(success, jsonObject);
        }
        return new JsonResult<>(success, "");
    }


    /**
     * 调用获取全部depot列表，并获取与depot绑定的云端设备名
     *
     * @return
     */
    @RequestMapping("/getDepotList")
    @ResponseBody
    public JsonResult<JSONArray> showDevList(HttpSession session) {
        //从session获取userId
        int userId = getUserIdFromSession(session);
        //获取userId对应的companyId
        User user = userService.getByUid(userId);
        int companyId = user.getCompanyId();
        //获取仓库列表
        JSONArray jsonArray = new JSONArray();
        List<Depot> depots = depotService.getDepotList();

        depots = depotService.getDepotListOnCompanyId(companyId);

        for (Depot d : depots
        ) {
            JSONObject js = new JSONObject();
            int depotId = d.getDepotId();
            //通过仓库id获取绑定的devNames
            List<String> devNames = depotService.getDevNamesByDepotId(depotId);
            js.put("depot", d);
            js.put("devInfo", devNames);
            jsonArray.add(js);
        }
        return new JsonResult<>(success, jsonArray);
    }


    /**
     * 通过depotId 获取仓库信息，并查询最新的粮情信息
     *
     * @param depotId
     * @return
     */
    @ResponseBody
    @RequestMapping("/{depotId}/grain")
    public JsonResult<JSONObject> showDevList(@PathVariable int depotId, HttpSession session) {
//        int userId = getUserIdFromSession(session);
        //通过id获取仓库信息
        JSONObject js = new JSONObject();
        Depot depot = depotService.getDepotByDepotId(depotId);
        //通过仓库获取devName
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 3);
        //获取指定设备最新消息
        Grain grainInfo = grainService.getNewGrainInfoByDevName(devName);
        if (grainInfo == null) {
            return new JsonResult<>(servWrong, "没有获取到历史粮情");
        }
        String content = grainInfo.getContent();
        //解析粮情信息
        Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
        try {
            js = dg3AnalysisGrain.analysis(grainInfo, depot);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //解析气调信息后，获取新的粮情信息
        Device rs = deviceService.getDevByDevName(devName);
        log.info("IndexController=" + rs);
        if (rs.getDtuId() == null) {
            return new JsonResult<>(success, js, "grainInfo");
        }
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();

        GrainInfoCommondBuilder grainInfoCommondBuilder = GrainInfoCommondBuilder.getGrainInfoCommondBuilder();
        grainInfoCommondBuilder.setDevBH(devBH);
        grainInfoCommondBuilder.setDevZH(devZH);
        BuildMessage grainMsg = grainInfoCommondBuilder.build();


        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(grainMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, grainMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 3, grainMsg.toString(),
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        log.info("APPController = " + js);
        return new JsonResult<>(success, js, "grainInfo");
    }

    /**
     * 获取选择仓库的氮气气调解析信息
     *
     * @param depotId
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("/{depotId}/N2")
    public JsonResult<JSONObject> getGasInfo(@PathVariable int depotId, HttpSession session) {
        JSONObject js = new JSONObject();
        //通过depotId获取仓库信息
        Depot depot = depotService.getDepotByDepotId(depotId);
        //通过depotId获取对应的气调设备信息devName
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        //通过devName获得最新气调信息
        N2 n2 = n2Service.getNewInfoByDevName(devName);
        String content = n2.getContent();
        Dg4AnalysisN2 dg4AnalysisN2 = Dg4AnalysisN2.newInstance();
        //将气调信息解析
        js = dg4AnalysisN2.analysisN2Info(n2, devName, depot);

        log.info("下发查询N2命令");
        Device rs = deviceService.getDevByDevName(devName);
        if (rs.getDtuId() == null) {
            return new JsonResult<>(success, js, "N2Info");
        }
        log.info("IndexController=" + rs);
        GasInfoCommondBuilder gasInfoCommondBuilder = GasInfoCommondBuilder.getInstance();
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();
        gasInfoCommondBuilder.setDevBH(devBH);
        gasInfoCommondBuilder.setDevZH(devZH);
        BuildMessage gasMsg = gasInfoCommondBuilder.build();
        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(gasMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, gasMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 2, gasMsg.toString(),
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }

        return new JsonResult<>(success, js, "N2Info");
    }

    /**
     * 根据devName 下发查询粮情命令
     *
     * @param depotId
     * @param session
     * @return
     */
    @RequestMapping("/getNewGrain.do")
    @ResponseBody
    public JsonResult<String> sendNewGrainInfo(int depotId, HttpSession session) {
        log.info("下发查询Grain命令");
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 3);
        Device rs = deviceService.getDevByDevName(devName);
        //根据编号站号选择设备
        //根据设备编号，站号，和设备类型选择
//        rs = deviceService.getDevByBHAndZH(devBH, devZH, 3);

        log.info("IndexController=" + rs);
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();

        GrainInfoCommondBuilder grainInfoCommondBuilder = GrainInfoCommondBuilder.getGrainInfoCommondBuilder();
        grainInfoCommondBuilder.setDevBH(devBH);
        grainInfoCommondBuilder.setDevZH(devZH);
        BuildMessage grainMsg = grainInfoCommondBuilder.build();


        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(grainMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, grainMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 3, grainMsg.toString(),
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

    private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

    public static String byteToHex(int n) {
        if (n < 0) {
            n = n + 256;
        }
        int d1 = n / 16;
        int d2 = n % 16;
        return hexArray[d1] + hexArray[d2];
    }

    /**
     * 下发测控命令
     *
     * @param depotId    仓库id
     * @param devAddress 设备类地址
     * @param devNum     设备编号
     * @param action     动作      00/11：关/开
     * @param session
     * @return
     */
    @RequestMapping("/sendTestOrder")
    @ResponseBody
    public JsonResult<String> sendTestOrder(int depotId, String devAddress, int devNum, String action, HttpSession session) {
        log.info("下发测控命令");
        action += action;

        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        Device rs = deviceService.getDevByDevName(devName);
        //根据编号站号选择设备
        //根据设备编号，站号，和设备类型选择
//        rs = deviceService.getDevByBHAndZH(devBH, devZH, 2);
        log.info("IndexController=" + rs);
        ControlOrderCommondBuilder controlOrderCommondBuilder = ControlOrderCommondBuilder.getInstance();
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();
        int devAdd = Integer.parseInt(devAddress, 16);
        String devDZ = byteToHex(devAdd);

        String devBHDZ = byteToHex(devNum);
        controlOrderCommondBuilder.setDevBH(devBH);
        controlOrderCommondBuilder.setDevZH(devZH);
        controlOrderCommondBuilder.setDevDZ(devDZ);
        controlOrderCommondBuilder.setDevBHDZ(devBHDZ);
        controlOrderCommondBuilder.set开关动作(action);
        BuildMessage controlerMsg = controlOrderCommondBuilder.build();

        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(controlerMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, controlerMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 2, controlerMsg.toString(),
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }


        return new JsonResult<>(success, "");
    }


    /**
     * 通过depotId exp:  下发查询气调状态命令
     *
     * @param depotId
     * @param session
     * @return
     */
    @RequestMapping("/getNewN2Info.do")
    @ResponseBody
    public JsonResult<String> sendNewN2Info(int depotId, HttpSession session) {
        log.info("下发查询N2命令");
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        Device rs = deviceService.getDevByDevName(devName);

        //根据编号站号选择设备
        //根据设备编号，站号，和设备类型选择
//        rs = deviceService.getDevByBHAndZH(devBH, devZH, 2);

        log.info("IndexController=" + rs);
        GasInfoCommondBuilder gasInfoCommondBuilder = GasInfoCommondBuilder.getInstance();
        String devBH = rs.getDevBH();
        String devZH = rs.getDevZH();
        gasInfoCommondBuilder.setDevBH(devBH);
        gasInfoCommondBuilder.setDevZH(devZH);
        BuildMessage gasMsg = gasInfoCommondBuilder.build();
        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(gasMsg.toString() + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, gasMsg.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 2, gasMsg.toString(),
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
     * 下发气调模式
     *
     * @param model   模式选择 1-8 int
     * @param depotId 仓库id
     * @return
     */
    @RequestMapping("/sendModel.do")
    @ResponseBody
    public JsonResult<String> sendModel(int model, int depotId, HttpSession session) {
        log.info("模式" + model);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        log.info("选择设备=" + devName);
        //根据devName获取设备信息
        Device rs = deviceService.getDevByDevName(devName);
        //根据编号站号选择设备
        //根据设备编号，站号，和设备类型选择
//        rs = deviceService.getDevByBHAndZH(devBH, devZH, 2);

        log.info("IndexController=" + rs);
        //格式化model
        DecimalFormat df = new DecimalFormat("00");
        String model1 = df.format(model);
        //创建命令
        ModelCommondBuilder mcb = ModelCommondBuilder.getModelCommonBuilder();
        String devBH = df.format(Integer.parseInt(rs.getDevBH()));
        String devZH = df.format(Integer.parseInt(rs.getDevZH()));
        log.info("model1=" + model1 + "   devBH=" + devBH + "   devZH=" + devZH);
        mcb.setModel(model1);
        mcb.setDevNote(rs.getDevNote());
        mcb.setDevBH(devBH);
        mcb.setDevZH(devZH);
        BuildMessage buildMessage = mcb.build();
        //拼装目标下发地址
        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(buildMessage.toString() + "-----" + topicFullName);

        JSONObject json = ioTService.pub(topicFullName, buildMessage.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 2, buildMessage.toString(),
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        if (json.getString("Success").equals("true")) {
            return new JsonResult<>(success, "sendMsg = " + json.getString("Success"));
        }
        return new JsonResult<>(success, "sendMsg failed");
    }

    /**
     * 同步时间              修改-》遍历所有设备下发指令
     *
     * @param depotId
     * @param h
     */
    @RequestMapping("/JsonTime.do")
    @ResponseBody
    public JsonResult<String> setTime(int depotId, HttpSession h) {
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
            Order o = new Order(getUserIdFromSession(h), 0, json.getString("MessageId"), 2, commond,
                    ContextUtil.getTimeYMDHMM(null), d.getDeviceName(), json.getString("Success"));
            int r = orderService.save(o);
            if (r == 1) {
                log.info("保存命令成功");
                r = 0;
            }
        }
        return new JsonResult<>(success, "同步时间成功");
    }

    public QTConfigure parseJson2QT(JSONObject jsonObject) {
        String devName = jsonObject.getString("devName");
        String devId = jsonObject.getString("devId");
        String commondType = jsonObject.getString("commondType");
        int devBH = null == jsonObject.getInteger("devBH") ? 0 : jsonObject.getInteger("devBH");
        int devZH = null == jsonObject.getInteger("devZH") ? 0 : jsonObject.getInteger("devZH");
        int type = null == jsonObject.getInteger("type") ? 0 : jsonObject.getInteger("type");
        int busType = null == jsonObject.getInteger("busType") ? 0 : jsonObject.getInteger("busType");
        int dieFFK = null == jsonObject.getInteger("dieFFK") ? 0 : jsonObject.getInteger("dieFFK");
        int hlfjFk = null == jsonObject.getInteger("hlfjFk") ? 0 : jsonObject.getInteger("hlfjFk");
        int dieFTime = null == jsonObject.getInteger("dieFTime") ? 0 : jsonObject.getInteger("dieFTime");
        int n2NDUpper = null == jsonObject.getInteger("n2NDUpper") ? 0 : jsonObject.getInteger("n2NDUpper");
        int n2NDLower = null == jsonObject.getInteger("n2NDLower") ? 0 : jsonObject.getInteger("n2NDLower");
        int n2FYUpper = null == jsonObject.getInteger("n2FYUpper") ? 0 : jsonObject.getInteger("n2FYUpper");
        int n2FYLower = null == jsonObject.getInteger("n2FYLower") ? 0 : jsonObject.getInteger("n2FYLower");
        int n2CQTime = null == jsonObject.getInteger("n2CQTime") ? 0 : jsonObject.getInteger("n2CQTime");
        int timeInterval = null == jsonObject.getInteger("timeInterval") ? 0 : jsonObject.getInteger("timeInterval");
        int cycleMeasure = null == jsonObject.getInteger("cycleMeasure") ? 0 : jsonObject.getInteger("cycleMeasure");
        int airTightness = null == jsonObject.getInteger("airTightness") ? 0 : jsonObject.getInteger("airTightness");
        int startCH = null == jsonObject.getInteger("startCH") ? 0 : jsonObject.getInteger("startCH");
        int endCH = null == jsonObject.getInteger("endCH") ? 0 : jsonObject.getInteger("endCH");
        int cqTime = null == jsonObject.getInteger("cqTime") ? 0 : jsonObject.getInteger("cqTime");
        return new QTConfigure(devName,
                devId,
                commondType,
                devBH,
                devZH,
                type,
                busType,
                dieFFK,
                hlfjFk,
                dieFTime,
                n2NDUpper,
                n2NDLower,
                n2FYUpper,
                n2FYLower,
                n2CQTime,
                timeInterval,
                cycleMeasure,
                airTightness,
                startCH,
                endCH,
                cqTime);
    }

    /**
     * json格式 系统配置 下发
     *
     * @param depotId
     * @param jsonDate json格式属性设置
     * @param h
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/JsonSetConfMsg.do")
    @ResponseBody
    public JsonResult<JSONObject> jsonMsg(int depotId, String jsonDate, HttpSession h) throws InterruptedException {
        JSONObject jo = JSONObject.parseObject(jsonDate);
        QTConfigure qtConfigure = parseJson2QT(jo);
        //设置属性
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        Device rs = deviceService.getDevByDevName(devName);
        String devId = rs.getDevId();
        ConfCommondBuilder ccb = ConfCommondBuilder.getInstance();
        ccb.setQTConfigure(qtConfigure);
        String commond = ccb.n2ConfToCommond(devId);
        //获取 下发目标信息
        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(commond + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, commond, pk, null);
        log.info(json.toJSONString());
        //保存 已经下发的命令
        Order o = new Order(getUserIdFromSession(h), 0, json.getString("MessageId"), 2, commond,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
            r = 0;
        }
        Thread.sleep(1000);
        //设置命令下发完成,下发查询参数命令
        devId = rs.getDevId();
        String cx = ccb.getPZCXInfo(devId);//查询配置命令
        json = ioTService.pub(topicFullName, cx, pk, null);
        o = new Order(getUserIdFromSession(h), 0, json.getString("MessageId"), 2, cx,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }

        return new JsonResult<>(success, "配置下发完成");
    }

    /**
     * json格式获取气调设备配置
     *
     * @param depotId 仓库id
     * @param h
     * @return 返回指定仓库 气调设备的配置对象
     */
    @RequestMapping("/JsonGetConfMsg.do")
    @ResponseBody
    public JsonResult<JSONObject> jsonM(int depotId, HttpSession h) {
        //
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        Device rs = deviceService.getDevByDevName(devName);
        QTConfigure QTConfigure = QTConfService.getPZByDevName(devName);
        Gson g = new Gson();
        String json = g.toJson(QTConfigure);
        JSONObject jo = JSONObject.parseObject(json);


        return new JsonResult<>(success, jo);
    }

    /**
     * 通过json格式下发模式命令
     *
     * @param depotId 仓库id
     * @param model   模式类型
     *                case 1 : str = "ddcq";break; 单独测气
     *                case 2 : str = "scxp";break; 上充下排
     *                case 3 : str = "xcsp";break; 下充上排
     *                case 4 : str = "hlxz";break; 环流熏蒸
     *                case 5 : str = "fycd";break; 负压充氮
     *                case 6 : str = "qmjc";break; 气密性检测
     * @param action  开关动作
     * @param h
     * @return
     */
    @RequestMapping("/modelJson.do")
    @ResponseBody
    public JsonResult<String> model(int depotId, int model, int action, HttpSession h) {
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        log.info("选择设备=" + devName);
        //根据devName获取设备信息
        Device rs = deviceService.getDevByDevName(devName);
        String devId = rs.getDevId();
        ConfCommondBuilder ccb = ConfCommondBuilder.getInstance();
        String modeCommond = ccb.modelDowndate(devId, model, action);


        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(modeCommond + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, modeCommond, pk, null);
        Order o = new Order(getUserIdFromSession(h), 0, json.getString("MessageId"), 2, modeCommond,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }

        return new JsonResult<>(success, "命令已下发");
    }


    /**
     * @param depotId 仓库id
     * @param action  1-"RESET DTU"  2-"RESET DEV"
     * @param session
     */
    @RequestMapping("jsonAction.do")
    @ResponseBody
    public void action(int depotId, int action, HttpSession session) {
        String act = new String();
        switch (action) {
            case 1:
                act = "RESET DTU";
                break;
            case 2:
                act = "RESET DEV";
                break;
        }
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 2);
        log.info("选择设备=" + devName);
        //根据devName获取设备信息
        Device rs = deviceService.getDevByDevName(devName);
        String devId = rs.getDevId();
        ConfCommondBuilder ccb = ConfCommondBuilder.getInstance();
        String com = ccb.downDate(devId, act);

        String pk = rs.getProductKey();
        devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(com + "-----" + topicFullName);
        JSONObject json = ioTService.pub(topicFullName, com, pk, null);
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 2, com,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
    }
}
