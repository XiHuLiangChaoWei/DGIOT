package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.*;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/4/23 14:09
 * ClassExplain :
 * ->
 */
@Controller
@RequestMapping("/sql")
public class SQLConnectController extends BaseController {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    private final int success = 200;
    @Autowired
    DeviceService deviceService;
    @Autowired
    UserService userService;
    @Autowired
    N2Service n2Service;
    @Autowired
    GrainService grainService;
    @Autowired
    DepotService depotService;

    @ResponseBody
    @RequestMapping("/{depotId}/grain")
    public JsonResult<JSONObject> showDevList(@PathVariable int depotId, HttpSession session) {
        int companyId = getCompanyIdFromSession(session);
//        int userId = getUserIdFromSession(session);
        //通过id获取仓库信息
        JSONObject js = new JSONObject();
        Depot depot = depotService.getDepotByDepotIdAndCompanyId(companyId, companyId);
        //通过仓库获取devName
        String devName = depotService.getDevNameByDepotIdAndType(depotId, 3, companyId);
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
        return new JsonResult<>(success,js);
    }


    @ResponseBody
    @RequestMapping("/1")
    public ModelAndView test1() {
        ModelAndView mav = new ModelAndView("html/sql");
        List<User> list = userService.findAll();
        mav.addObject("list", list);
        return mav;
    }

//    @ResponseBody
//    @RequestMapping("/getN2Info")
//    public JsonResult<Map> showN2() {
//        Map list = n2Service.findAll();
//        Iterator it = list.entrySet().iterator();
//        String key = null;
//        Set value = null;
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            key = (String) entry.getKey();
//            value = (Set) entry.getValue();
////            System.out.println("key:" + key + "---" + "value:" + value);
//        }
//        return new JsonResult<>(success, list);
//    }


//    @ResponseBody
//    @RequestMapping("/getGrainInfo")
//    public JsonResult<Map> showGrain() {
//        Map list = grainService.findAll();
//
//        Iterator it = list.entrySet().iterator();
//        String key = null;
//        Set value = null;
//        while (it.hasNext()) {
//            Map.Entry entry = (Map.Entry) it.next();
//            key = (String) entry.getKey();
//            value = (Set) entry.getValue();
//            System.out.println("key:" + key + "---" + "value:" + value);
//        }
//        return new JsonResult<>(success, list);
//    }


    @ResponseBody
    @RequestMapping("grain/choose")
    public JsonResult<JSONObject> showChooseGrain(String batchId, String devBH, String devZH, HttpSession session) {
//        int userId = getUserIdFromSession(session);
        log.info("----------------------------------------");
        log.info("batchId=" + batchId + ";devBH=" + devBH + ";devZH=" + devZH);
        JSONObject js = new JSONObject();
        //通过设备信息获取devName
//        String devName = depotService.getDevNameByDepotIdAndType(depotId, 3);
        Device device = deviceService.getDevByBHAndZH(devBH, devZH, 3);
        String devName = device.getDeviceName();
        Depot depot = depotService.getDepotByDevName(devName);
        //获取指定设备最新消息
        Grain grainInfo = grainService.getChooseGrainInfo(batchId);
        String content = grainInfo.getContent();
        //解析粮情信息
        Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
        try {
            js = dg3AnalysisGrain.analysis(grainInfo, depot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Controller = " + js);
        return new JsonResult<>(success, js, "grainInfo");
    }


    /**
     * @param batchId
     * @param devBH
     * @param devZH
     * @param session
     * @return
     */
    @ResponseBody
    @RequestMapping("grain")
    public JsonResult<JSONObject> showDevList(String batchId, String devBH, String devZH, HttpSession session) {
//        int userId = getUserIdFromSession(session);
        log.info("----------------------------------------");
        log.info("batchId=" + batchId + ";devBH=" + devBH + ";devZH=" + devZH);
        JSONObject js = new JSONObject();
        //通过设备信息获取devName
//        String devName = depotService.getDevNameByDepotIdAndType(depotId, 3);
        Device device = deviceService.getDevByBHAndZH(devBH, devZH, 3);
        String devName = device.getDeviceName();
        Depot depot = depotService.getDepotByDevName(devName);
        //获取指定设备最新消息
        Grain grainInfo = grainService.getNewGrainInfoByDevName(devName);
        String content = grainInfo.getContent();
        //解析粮情信息
        Dg3AnalysisGrain dg3AnalysisGrain = Dg3AnalysisGrain.newInstance();
        try {
            js = dg3AnalysisGrain.analysis(grainInfo, depot);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info("Controller = " + js);
        return new JsonResult<>(success, js, "grainInfo");
    }

    @ResponseBody
    @RequestMapping("getBatchList")
    public JsonResult<JSONArray> getBatchList(String devName, String devBH, String devZH) {
        Device device = deviceService.getDevByBHAndZH(devBH, devZH, 3);
        String devName1 = device.getDeviceName();
        List<Grain> grains = grainService.getGrainInfosByDevName(devName1);
//        log.info(grains);
        JSONArray ja = new JSONArray();
        ja.add(grains);
        log.info(ja.toJSONString());
        return new JsonResult<>(success, ja);
    }

    @ResponseBody
    @RequestMapping("/dev/getLQList")
    public JsonResult<List> showLQDevList() {
        List ls = deviceService.getDeviceListByType(3);
//        log.info(ls);
        return new JsonResult<>(success, ls);
    }

    @ResponseBody
    @RequestMapping("/dev/getQTList")
    public JsonResult<List> showQTDevList() {
        List ls = deviceService.getDeviceListByType(2);
        return new JsonResult<>(success, ls);
    }
}
