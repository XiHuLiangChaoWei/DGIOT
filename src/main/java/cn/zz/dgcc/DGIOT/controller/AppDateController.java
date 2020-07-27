package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.VO.GrainHistoryVO;
import cn.zz.dgcc.DGIOT.entity.QTConfigure;
import cn.zz.dgcc.DGIOT.entity.*;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.DownOrderUtils;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.DG4AnalysisSunPower;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg4AnalysisN2;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg4AnalysisOil;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.*;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/5/21 15:54
 * ClassExplain : 移动端接口
 * ->
 */
@Controller
@RequestMapping("/app")
public class AppDateController extends BaseController {
    private final static Logger log = Logger.getLogger(AppDateController.class.getName());
    @Autowired
    DownOrderUtils downOrderUtils;
    @Autowired
    CompanyService companyService;
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
    @Autowired
    OilService oilService;

    @ResponseBody
    @RequestMapping("getDev")
    public JsonResult<List<Device>> getDevList(int companyId, int devType) {
        List<Device> rs = deviceService.getDevByCompanyIdAndType(companyId, devType);
        return new JsonResult<>(success, rs);
    }


    @ResponseBody
    @RequestMapping("onlineCount")
    public JsonResult<JSONObject> onlineNum() {
        int rs = deviceService.getOnlineCount();
        int qtrs = deviceService.getOnlineCount(气调设备);
        int lqrs = deviceService.getOnlineCount(粮情设备);
        JSONObject jo = new JSONObject();
        jo.put("qtOnline", qtrs);
        jo.put("lqOnline", lqrs);
        return new JsonResult<>(success, jo);
    }

    /**
     * 获取app版本
     *
     * @return
     */
    @RequestMapping("getAppVersion")
    @ResponseBody
    public JsonResult<AppVersion> getVersion() {
        AppVersion appVersion = appVersionService.getNowAppVersion();
        return new JsonResult<>(success, appVersion);
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
        int companyId = userService.getCompanyIdByUserId(userId);
        Device n2 = deviceService.getN2DevByUser(companyId);
        downOrderUtils.N2DevControler(controller, n2, userId);
        return new JsonResult<>(success, "下发成功");
    }

    @RequestMapping("/grain/history")
    @ResponseBody
    public JsonResult<JSONObject> grainHistory(HttpSession session, GrainHistoryVO grainHistoryVO) {
//        System.err.println(grainHistoryVO);
        //获取前端信息
        //仓库信息
        int depotId = grainHistoryVO.getDepotId();
        //开始时间
        Date start = grainHistoryVO.getStart();
        //结束使劲
        Date end = grainHistoryVO.getEnd();
        //从sessino获取项目id
        int companyId = getCompanyIdFromSession(session);
        //通过项目id和仓库id获取对应的仓库对象
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        //获取到该仓库下的粮情分机名
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 粮情设备, companyId);
        //通过garinService获取到历史粮情
        List<Grain> list = grainService.getGrainList(devName, start, end);
        //获取大公3粮情解析协议工具实例
        Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
        //新建JSONArray存放历史粮情数据
        JSONArray js = new JSONArray();
        //TODO  注意：使用dg3解析出的JSONObject引用同一个地址，这里需要确保JSONArray中的地址不同，以存放数据
        for (Grain g : list
        ) {
            System.out.println("LQ:" + g.toString());
            JSONObject a = new JSONObject();
            try {
                String jo = dg3AnalysisGrain.analysis(g, depot).toJSONString();
                a = JSONObject.parseObject(jo);
            } catch (Exception e) {
                e.printStackTrace();
            }
            js.add(a);

        }
        //获取项目实例
        Company c = companyService.getC(companyId);
        //新建jsonObject存放需要返回前端的数据
        JSONObject js1 = new JSONObject();
        //放入仓库信息
        js1.put("depot", depot);
        //放入相关设备信息
        //放入项目名
        js1.put("companyName", c.getName());

        js1.put("history", js);

        return new JsonResult<>(success, js1);
    }


    /**
     * @param depotInfo 设置仓库信息（部分
     * @param session
     * @return
     */
    @RequestMapping(value = "/depotInfo")
    @ResponseBody
    public JsonResult testJson(String depotInfo, HttpSession session) {
        int companyId = getCompanyIdFromSession(session);
        System.out.println(depotInfo);
        int rs = depotService.updateDepotInfoByCompanyAndDepot(companyId, depotInfo);
        //这里可以使用JsonMapper来处理jsonStr，比如转成需要的bean对象
        if (rs == 1) {
            return new JsonResult(success);
        }
        return new JsonResult(notFound);
    }

    /**
     * 获取制氮机运行状态,需要解析返回值并回复
     *
     * @return
     */
    @RequestMapping("/getN2DevStatus")
    @ResponseBody
    public JsonResult<Integer> N2DevStatus(HttpSession h) {
        //从session获取userId
        int userId = getUserIdFromSession(h);
        //获取当前用户管理下的制氮机
        User rs = userService.getByUid(userId);
        int companyId = rs.getCompanyId();
        Device n2 = deviceService.getN2DevByUser(companyId);
        JsonResult<Integer> jr = downOrderUtils.getN2DevStatus(n2, userId);
        return jr;
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
        JsonResult<JSONObject> jr = downOrderUtils.getN2DevInfo(n2, userId, choose);
        return jr;
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
        List<Depot> depots = depotService.getDepotListOnCompanyId(companyId);
        //获取项目实例
        Company c = companyService.getC(companyId);
        String companyName = c.getName();
        //遍历集合
        for (Depot d : depots
        ) {
            JSONObject js = new JSONObject();
            int depotId = d.getDepotId();
            //通过仓库id获取绑定的devNames
            List<String> devNames = depotService.getDevNamesByDepotId(depotId, companyId);
            //获取仓库与设备的映射关系
            List<DepotDev> depotDevs = depotService.getDepotDevByDepotId(depotId, companyId);
            //获取设备状态
            JSONObject jsonObject = new JSONObject();
            for (DepotDev depotdev : depotDevs
            ) {
                if (depotdev.getDevName().contains("LQ")) {
                    jsonObject.put("LQ", depotdev.getStatus());
                } else if (depotdev.getDevName().contains("QT")) {
                    jsonObject.put("QT", depotdev.getStatus());
                }
            }
            //放入仓库信息
            js.put("depot", d);
            //放入相关设备信息
            js.put("devInfo", jsonObject);
            //放入项目名
            js.put("companyName", companyName);
            jsonArray.add(js);
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("depotList", jsonArray);
        jsonObject.put("productName", companyName);
        int qtrs = deviceService.getOnlineCount(气调设备);
        int lqrs = deviceService.getOnlineCount(粮情设备);
        jsonObject.put("qtOnline", qtrs);
        jsonObject.put("lqOnline", lqrs);
        return new JsonResult<>(success, jsonArray);
    }


    /**
     * 根据depot 下发查询粮情命令
     *
     * @param depotId
     * @param session
     * @return
     */
    @RequestMapping("/getNewGrain.do")
    @ResponseBody
    public JsonResult<String> sendNewGrainInfo(int depotId, HttpSession session) {
        log.info("下发查询Grain命令");
        int companyId = getCompanyIdFromSession(session);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 粮情设备, companyId);
        Device rs = deviceService.getDevByDevName(devName);
        JsonResult<String> jr =
                downOrderUtils.deployGrainOrder(getUserIdFromSession(session), rs);
        return jr;
    }

    /**
     * 下发太阳能命令，查询太阳能粮情数据
     *
     * @param devAdd
     * @param session
     * @return
     */
    @RequestMapping("/getNewSunGrain.do")
    @ResponseBody
    public JsonResult<JSONObject> sendSunInfo(int devAdd, HttpSession session, @RequestParam(required = false, defaultValue = "1") int depotId) {
        log.info("下发查询太阳能分机");
        int companyId = getCompanyIdFromSession(session);
        int userId = getUserIdFromSession(session);
        String devName = depotService.getDevNameByDepotIdAndType(1, 太阳能分机设备, companyId);
        Device rs = deviceService.getDevByDevName(devName);
        JsonResult<String> jr = downOrderUtils.deploySunOrder(userId, rs, devAdd);
        //通过id获取仓库信息
        JSONObject js = new JSONObject();
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        //获取指定设备最新消息
        Grain grainInfo = grainService.getNewGrainInfoByDevNameAndDevAdd(devName, devAdd);
        if (grainInfo == null) {
            return new JsonResult<>(servWrong, "没有获取到历史粮情");
        }
        //解析粮情信息
        DG4AnalysisSunPower dg4AnalysisSunPower = DG4AnalysisSunPower.newInstance();
        try {
            js = dg4AnalysisSunPower.analysis(grainInfo, depot);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return new JsonResult<>(success, js);
    }

    /**
     * TODO
     *
     * @param session
     * @param depotId
     * @return
     */
    @ResponseBody
    @RequestMapping("/oil/list")
    public JsonResult<JSONArray> getListByDepotId(HttpSession session, Integer depotId) {
        int companyId = getCompanyIdFromSession(session);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 油情设备, companyId);
        List<Oil> list = oilService.getOilInfoListByDevName(devName);
        Dg4AnalysisOil dg4AnalysisOil = Dg4AnalysisOil.newInstance();
        JSONArray ja = new JSONArray();
        String batch = "";
        for (Oil o : list
        ) {
            if (o.getBatch().equals(batch)) {
                continue;
            }
            batch = o.getBatch();
            JSONObject jo = dg4AnalysisOil.analysisOilInfo(o.getContent(), o.getReceivedTime());
            ja.add(jo);
        }
        return new JsonResult<>(success, ja);
    }

    /**
     * @param session
     * @param devName
     * @return
     */
    @ResponseBody
    @RequestMapping("/oil/list2")
    public JsonResult<JSONArray> getListByDevName(HttpSession session, String devName) {
        List<Oil> list = oilService.getOilInfoListByDevName(devName);
        Dg4AnalysisOil dg4AnalysisOil = Dg4AnalysisOil.newInstance();
        JSONArray ja = new JSONArray();
        String batch = "";
        for (Oil o : list
        ) {
            if (o.getBatch().equals(batch)) {
                continue;
            }
            batch = o.getBatch();
            JSONObject jo = dg4AnalysisOil.analysisOilInfo(o.getContent(), o.getReceivedTime());
            ja.add(jo);
        }
        return new JsonResult<>(success, ja);
    }

    /**
     * @param session
     * @param depotId
     * @return
     */
    @ResponseBody
    @RequestMapping("/oil")
    public JsonResult<JSONObject> showOil(HttpSession session, Integer depotId) {
        int companyId = getCompanyIdFromSession(session);
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 油情设备, companyId);
        Device device = deviceService.getDevByDevName(devName);
        if (devName == null | devName.length() == 0) {
            return new JsonResult<>(servWrong, "该仓库中不存在油情设备");
        }
        Oil oil = oilService.getOilInfoByDevName(devName);
        if (oil == null) {
            downOrderUtils.deployOilOrder(getUserIdFromSession(session), device);
            return new JsonResult<>(servWrong, "没有获取到历史油情");
        }
        String content = oil.getContent();
        Dg4AnalysisOil dg4AnalysisOil = Dg4AnalysisOil.newInstance();
        JSONObject jo = dg4AnalysisOil.analysisOilInfo(content, oil.getReceivedTime());
        downOrderUtils.deployOilOrder(getUserIdFromSession(session), device);
        return new JsonResult<>(success, jo);
    }

    /**
     * 查询校正配置
     *
     * @param session
     * @param depotId
     * @return
     */
    @ResponseBody
    @RequestMapping("/oil/conf")
    public JsonResult<JSONObject> showOilConf(HttpSession session, Integer depotId) {
        int companyId = getCompanyIdFromSession(session);
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 油情设备, companyId);
        Device device = deviceService.getDevByDevName(devName);
        if (devName == null | devName.length() == 0) {
            return new JsonResult<>(servWrong, "该仓库中不存在油情设备");
        }
        OilConf oilConf = oilService.getOilConfByDevName(devName);
        if (oilConf == null) {
            downOrderUtils.deployOilConfChaxun(getUserIdFromSession(session), device);
            return new JsonResult<>(servWrong, "没有获取到历史油情校正信息");
        }
        String content = oilConf.getContent();
        Dg4AnalysisOil dg4AnalysisOil = Dg4AnalysisOil.newInstance();
        JSONObject jo = dg4AnalysisOil.analysisOil2(content);
        //下发查询配置
        downOrderUtils.deployOilConfChaxun(getUserIdFromSession(session), device);
        return new JsonResult<>(success, jo);
    }

    /**
     * 油情配置下发
     *
     * @param session
     * @param depotId
     * @return
     */
    @ResponseBody
    @RequestMapping("/oil/conf/set")
    public JsonResult<String> setOilConf(HttpSession session, Integer depotId) {
        int companyId = getCompanyIdFromSession(session);
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 油情设备, companyId);
        Device device = deviceService.getDevByDevName(devName);
        if (devName == null | devName.length() == 0) {
            return new JsonResult<>(servWrong, "该仓库中不存在油情设备");
        }
//        OilConf oilConf = oilService.getOilConfByDevName(devName);
//        if (oilConf == null) {
//            downOrderUtils.deployOilConfChaxun(getUserIdFromSession(session), device);
//            return new JsonResult<>(servWrong, "没有获取到历史油情校正信息");
//        }
//        String content = oilConf.getContent();
//        Dg4AnalysisOil dg4AnalysisOil = Dg4AnalysisOil.newInstance();
//        JSONObject jo = dg4AnalysisOil.analysisOil2(content);
        //下发查询配置
        //温度校准值
        StringBuffer sb = new StringBuffer();

        for (int i = 0; i < 121; i++) {
            sb.append("08");
        }
        //高度校准值
        String heigh = "0000";

        String set = "";
        return downOrderUtils.deployOilConfSet(getUserIdFromSession(session), device, heigh + sb.toString());
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
        int companyId = getCompanyIdFromSession(session);
        //通过id获取仓库信息
        JSONObject js = new JSONObject();
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        //通过仓库获取devName
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 粮情设备, companyId);
        //获取指定设备最新消息
        Grain grainInfo = grainService.getNewGrainInfoByDevName(devName);
        if (grainInfo == null) {
            log.info("下发查询粮情命令···");
            Device rs = deviceService.getDevByDevName(devName);
            log.info("IndexController=" + rs);
            downOrderUtils.deployGrainOrder(getUserIdFromSession(session), rs);
            log.info("下发查询粮情成功···");
            return new JsonResult<>(servWrong, "没有获取到历史粮情");
        }
        //解析粮情信息
        Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
        try {
            js = dg3AnalysisGrain.analysis(grainInfo, depot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //解析气调信息后，获取新的粮情信息
        log.info("下发查询粮情命令···");
        Device rs = deviceService.getDevByDevName(devName);
        log.info("IndexController=" + rs);
        if (rs.getDtuId() == null) {
            return new JsonResult<>(success, js, "grainInfo");
        }
        downOrderUtils.deployGrainOrder(getUserIdFromSession(session), rs);
        log.info("下发查询粮情成功···");
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
        int companyId = getCompanyIdFromSession(session);
        //通过depotId获取仓库信息
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(depotId, companyId);
        //通过depotId获取对应的气调设备信息devName
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
        //通过devName获得最新气调信息
        log.info("获取详细气调信息");
        N2 n2 = n2Service.getNewInfoByDevName(devName);
        String content = n2.getContent();
        Dg4AnalysisN2 dg4AnalysisN2 = Dg4AnalysisN2.newInstance();
        //将气调信息解析
        JSONObject js = dg4AnalysisN2.analysisN2Info(n2, devName, depot);
        log.info("气调信息解析完成");
        //下发查询N2命令
        log.info("下发查询N2命令···");
        Device rs = deviceService.getDevByDevName(devName);
        JsonResult<String> jr = downOrderUtils.deployN2Order(getUserIdFromSession(session), rs);
        log.info("下发查询N2成功···");
        return new JsonResult<>(success, js, "N2Info");
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
        int companyId = getCompanyIdFromSession(session);
        log.info("下发查询N2命令");
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
        Device rs = deviceService.getDevByDevName(devName);
        JsonResult<String> jr = downOrderUtils.deployN2Order(getUserIdFromSession(session), rs);
        return jr;
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
        int companyId = getCompanyIdFromSession(session);
        log.info("下发测控命令");
        action += action;
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
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
     * 下发气调模式
     *
     * @param model   模式选择 1-8 int
     * @param depotId 仓库id
     * @return
     */
    @RequestMapping("/sendModel.do")
    @ResponseBody
    public JsonResult<String> sendModel(int model, int depotId, HttpSession session) {
        int companyId = getCompanyIdFromSession(session);
        log.info("模式" + model);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
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
            log.info("下发查询N2命令···");
            JsonResult<String> jr = downOrderUtils.deployN2Order(getUserIdFromSession(session), rs);
            log.info("下发查询N2成功···");
        }
//


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
    public JsonResult<String> setTime(int depotId, int type, HttpSession h) {
        downOrderUtils.JsonTime(getUserIdFromSession(h));
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
        int companyId = getCompanyIdFromSession(h);
        JSONObject jo = JSONObject.parseObject(jsonDate);
        QTConfigure qtConfigure = parseJson2QT(jo);
        //设置属性
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
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
        int companyId = getCompanyIdFromSession(h);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
        Device rs = deviceService.getDevByDevName(devName);
        QTConfigure QTConfigure = QTConfService.getPZByDevName(devName);
        Gson g = new Gson();
        String json = g.toJson(QTConfigure);
        JSONObject jo = JSONObject.parseObject(json);
        downOrderUtils.CXPZ(depotId, getUserIdFromSession(h), companyId);
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
        int companyId = getCompanyIdFromSession(h);
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 气调设备, companyId);
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
        Order o = new Order(getUserIdFromSession(h), 0, json.getString("MessageId"), 气调设备, modeCommond,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        return new JsonResult<>(success, "命令已下发");
    }

    /**
     * 设置太阳能分机
     *
     * @param session
     * @param oldAdd
     * @param sunConf
     * @return
     */
    @RequestMapping("/sunJson.do")
    @ResponseBody
    public JsonResult<String> sun(HttpSession session, int oldAdd, SunConf sunConf) {
        System.err.println(sunConf.toString());
        String devName = depotService.getDevNameByDepotIdAndType(sunConf.getDepotId(), 太阳能分机设备, sunConf.getCompanyId());
        SunPowerJsonCommondBuilder sunPowerJsonCommondBuilder = SunPowerJsonCommondBuilder.getInstance();
        sunPowerJsonCommondBuilder.setSunConf(sunConf);
        sunPowerJsonCommondBuilder.setOldAdd(oldAdd);
        String sumConfCommand = sunPowerJsonCommondBuilder.setConf();
        Device rs = deviceService.getDevByDevName(devName);
        String pk = rs.getProductKey();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        JSONObject jsonObject = ioTService.pub(topicFullName, sumConfCommand, pk, null);
        Order o = new Order(getUserIdFromSession(session), 0, jsonObject.getString("MessageId"), 太阳能分机设备, sumConfCommand,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), jsonObject.getString("Success"));
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        return new JsonResult<>(success, "命令已下发");
    }

    /**
     * 查询太阳能分机配置
     *
     * @param session
     * @param depotId
     * @return
     */
    @RequestMapping("/lookSun.do")
    @ResponseBody
    public JsonResult<String> lookSum(HttpSession session, int depotId) {
        SunPowerJsonCommondBuilder sunPowerJsonCommondBuilder = SunPowerJsonCommondBuilder.getInstance();
        String sumConfCommand = sunPowerJsonCommondBuilder.getConf();
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 太阳能分机设备, getCompanyIdFromSession(session));

        Device rs = deviceService.getDevByDevName(devName);
        String pk = rs.getProductKey();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        JSONObject jsonObject = ioTService.pub(topicFullName, sumConfCommand, pk, null);
        Order o = new Order(getUserIdFromSession(session), 0, jsonObject.getString("MessageId"), 太阳能分机设备, sumConfCommand,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), jsonObject.getString("Success"));
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
        return new JsonResult<>(success);
    }


    /**
     * @param depotId 仓库id
     * @param action  1-"RESET DTU"  2-"RESET DEV"
     * @param session
     * @param type    设备类型
     */
    @RequestMapping("jsonAction.do")
    @ResponseBody
    public void action(int depotId, int action, HttpSession session, @RequestParam(required = false, defaultValue = "2") int type) {

        int companyId = getCompanyIdFromSession(session);
        String act = new String();
        switch (action) {
            case 1:
                act = "RESET DTU";
                break;
            case 2:
                act = "RESET DEV";
                break;
        }
        String devName = depotService.getDevNameByDepotIdAndType(depotId, type, companyId);
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
        Order o = new Order(getUserIdFromSession(session), 0, json.getString("MessageId"), 气调设备, com,
                ContextUtil.getTimeYMDHMM(null), rs.getDeviceName(), json.getString("Success"));
        System.out.println("OOOO----" + o);
        int r = orderService.save(o);
        if (r == 1) {
            log.info("保存命令成功");
        }
    }
}
