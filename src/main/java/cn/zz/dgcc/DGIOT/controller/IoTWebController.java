package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.service.IoTService;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/4/9 15:01
 * ClassExplain :前端页面控制器层
 * ->
 */
@Controller
@RequestMapping("/dgiot")
public class IoTWebController extends BaseController {
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());
    @Autowired
    IoTService ioTService;

    @RequestMapping("/test1")
    public String test1(Model model) {
        return "html/admin";
    }


    @ResponseBody
    @RequestMapping("/showProductList")
    public JsonResult<JSON> test2() {
        JSONObject json0 = ioTService.queryProductList();
        JSONArray productInfo = json0.getJSONArray("ProductInfo");
        log.info("ControllerLog:查询产品列表信息" + productInfo);
        return new JsonResult<>(success, productInfo);
    }

    @ResponseBody
    @RequestMapping("{productKey}/showDeviceListByProductKey")
    public JsonResult<JSON> test3(@PathVariable("productKey") String productKey) {
        JSONObject json0 = ioTService.queryDevice(productKey);
        JSONArray deviceInfo = json0.getJSONArray("DeviceInfo");
        log.info("ControllerLog:查询设备信息" + deviceInfo);
        return new JsonResult<>(success, deviceInfo);
    }

    @ResponseBody
    @RequestMapping("{productKey}/getProductDetail")
    public JsonResult<JSON> test4(@PathVariable("productKey") String productKey) {
        if (productKey == null) {
            System.out.println("没有传入key");
        }
        JSONObject json0 = ioTService.queryProduct(productKey);
        JSONObject productInfo = json0.getJSONObject("Data");
        log.info("ControllerLog:查询产品信息" + productInfo);

        return new JsonResult<>(success, productInfo);
    }

    @ResponseBody
    @RequestMapping("{productKey}/getProductTopic")
    public JsonResult<JSON> test5(@PathVariable("productKey") String productKey) {
        if (productKey == null) {
            System.out.println("没有传入key");
        }
        JSONObject json0 = ioTService.queryProductTopic(productKey);
//        log.info("ControllerLog:查询产品Topic信息" + json0);
        JSONArray productTopicInfo = json0.getJSONArray("ProductTopicInfo");
        log.info("ControllerLog:查询产品Topic信息" + productTopicInfo);
        return new JsonResult<>(success, productTopicInfo);
    }

    @ResponseBody
    @RequestMapping("{productKey}/sendMsg/{fullName}/content/{content}/Qos/{qos}")
    public JsonResult<JSON> test6(@PathVariable("productKey") String productKey,
                                  @PathVariable("fullName") String fullName,
                                  @PathVariable("content") String content,
                                  @PathVariable("qos") String qos) {
        fullName = fullName.replace("-","/");

        String[] fullNames = fullName.split("/");
        if (fullNames[2].equals("${deviceName}")) {
            JSONObject json1 = ioTService.pubBoradcast(content,productKey,null);

            return new JsonResult<>(success,json1);
        }

        log.info("ControllerLog:"+productKey+"=="+fullName+"=="+content+"=="+qos);
        JSONObject json0 = ioTService.pub(fullName, content, productKey, qos);

//        log.info("ControllerLog:查询产品Topic信息" + json0);
//        JSONArray productTopicInfo = json0.getJSONArray("ProductTopicInfo");
//        log.info("ControllerLog:查询产品Topic信息" + productTopicInfo);
        return new JsonResult<>(success,json0);
    }


    @ResponseBody
    @RequestMapping("{deviceIot}/getDeviceDetail")
    public JsonResult<JSON> test6(@PathVariable("deviceIot") String deviceIot) {

        JSONObject json0 = ioTService.queryDeviceDetail(deviceIot,null,null);
//        log.info("ControllerLog:查询产品Topic信息" + json0);
        log.info("ControllerLog:查询设备信息" + json0);
        return new JsonResult<>(success, json0);
    }
}
