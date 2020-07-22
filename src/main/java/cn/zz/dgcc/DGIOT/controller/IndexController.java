package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.BuildMessage;
import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Order;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.service.IoTService;
import cn.zz.dgcc.DGIOT.service.OrderService;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.Html.ExcelFactory;
import cn.zz.dgcc.DGIOT.utils.Html.PDFutils;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.ModelCommondBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.DecimalFormat;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/4/30 10:31
 * ClassExplain :
 * ->
 */
@Controller
public class IndexController extends BaseController {
    private final static Logger log = Logger.getLogger(IndexController.class.getName());
    private final int 上冲下排 = 1;
    private final int 下冲上排 = 2;
    private final int 环流熏蒸 = 3;
    private final int 负压充氮 = 4;
    private final int 气密性检查 = 5;
    private final int N2排空 = 6;
    private final int N2回收 = 7;
    private final int 快速投药 = 8;
    private final int 停止 = 0;

    @Autowired
    IoTService ioTService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    OrderService orderService;

    @RequestMapping("/index")
    public ModelAndView Index() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index.html");
        return mav;
    }

    @RequestMapping("/index2")
    public ModelAndView Index2() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("index");
        return mav;
    }

    @RequestMapping("/getExcel.do")
    public void getExcel(String lian, String pi, HttpServletRequest request, HttpServletResponse response) {
        log.info("lian=" + lian);
        log.info("pi=" + pi);
        ExcelFactory.ExcelSingleOutputStream("测试", request, response);
    }

    @RequestMapping("/getPdf.do")
    public void getPdf(String lian, String pi, HttpServletRequest request, HttpServletResponse response) {
        log.info("lian=" + lian);
        log.info("pi=" + pi);
        PDFutils.PDFOutputStream("测试PDF导出问题", request, response);
    }

    @RequestMapping("indexN2")
    public ModelAndView N2Controller() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("indexN2");
        return mav;
    }




    /**
     * 模式命令下发
     * @param model 模式
     * @param dev   设备
     */
    @RequestMapping("/sendModel.do")
    @ResponseBody
    public JsonResult<String> sendModel(int model, String dev) {
        log.info("选择设备=" + dev);
        log.info("模式" + model);
        //根据devName获取设备信息
        Device rs = deviceService.getDevByDevName(dev);
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
        String devName = rs.getDeviceName();
        String topicFullName = "/" + pk + "/" + devName + "/user/sev/downdate";
        log.info(buildMessage.toString() + "-----" + topicFullName);
        // TODO: 2020/5/20
        //pk固定,因为model下发的目标全为气调设备
        JSONObject json = ioTService.pub(topicFullName, buildMessage.toString(), pk, "1");
        log.info(json.toJSONString());
        Order o = new Order(1,0,json.getString("MessageId"),2,buildMessage.toString(),
                ContextUtil.getTimeYMDHMM(null),rs.getDeviceName(),json.getString("Success"));
        System.out.println("OOOO----"+o);

        int r = orderService.save(o);
        if(r==1){
            log.info("保存命令成功");
        }

        if (json.getString("Success").equals("true")) {
            return new JsonResult<>(success, "sendMsg = " + json.getString("Success"));
        }
        return new JsonResult<>(success, "sendMsg failed");
    }

}
