package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.VO.DownDevInfo;
import cn.zz.dgcc.DGIOT.aliyun.JavaLinkKitDemo.demo.HelloWorld;
import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Product;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import cn.zz.dgcc.DGIOT.utils.DeviceUtil;
import com.alibaba.fastjson.JSONObject;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/5/7 16:01
 * ClassExplain :   真实设备与云端设备进行绑定操作
 *
 */
@Controller
public class DeviceBindController extends BaseController {
    int deviceNameFix = 0;
    private final Logger log = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    IoTService ioTService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    IotDeviceService iotDeviceService;
    @Autowired
    AnalysisService analysisService;
    @Autowired
    FirewareService firewareService;


    /**
     * 解析消息
     */
    public void parseInfo(AMQPMessage amqpMessage) {
        //版本升级请求，转入firewareService进行处理
        if (amqpMessage.getTopic().contains("/user/dev/version/upgrade/request")) {
            firewareService.analysisInfo(amqpMessage);
        }
        //其他请求，直接进入analysisService
        analysisService.analysisInfo(amqpMessage);

    }

    @ResponseBody
    @RequestMapping("devlist")
    List<Device> getTotalList() {
        List<Product> products = ioTService.getProductList();
        List<Device> devices = ioTService.getDeviceList(products);
        return devices;
    }

    @ResponseBody
    @RequestMapping("adddevlist")
    public void save(){
        List<Product> products = ioTService.getProductList();
        List<Device> devices = ioTService.getDeviceList(products);
        for (Device d : devices
        ) {
            log.info("查询到设备列表" + d);
        }
        deviceService.saveDeviceList(devices);
    }

    public void savaDevList() {
        List<Device> devices = getTotalList();
        for (Device d : devices
        ) {
            log.info("查询到设备列表" + d);
        }
        deviceService.saveDeviceList(devices);
    }

    //设备登陆
    public void loginDevice(AMQPMessage amqpMessage) {
        //判断是否登陆成功
        String devId = null;
        boolean logSuccess = deviceService.loginDevice(amqpMessage);
        if (!logSuccess) {
            Device logDevice = DeviceUtil.parseDev(amqpMessage);
            String pk = logDevice.getProductKey();
            String deviceName = logDevice.getDeviceName();
            devId = logDevice.getDevId();
            String dtu恢复出厂设置 = "{\"devId\":\"" + devId + "\",\"command type\":\"RESET DTU\"}";
            String fullTopic = "/" + pk + "/" + deviceName + "/user/dev/register/response";
            JSONObject jsonR = ioTService.pub(fullTopic, dtu恢复出厂设置, pk, null);
            log.info("恢复出厂设置的结果" + jsonR.toJSONString());
        }
    }

    private final int 需要云端注册新设备 = 0;
    private final int 需要更新设备信息 = 1;
    private final int 设备匹配成功 = 2;

    //进行云端设备绑定操作
    public void registerDevice(AMQPMessage a) {
        String str = a.getContent();
        //收到消息后，刷新数据库中iot_device_list表信息
        if (str.equals("")) {
            savaDevList();
            return;
        }
        //
        int regSuccess = deviceService.registerDevice(a);
        if (regSuccess == 需要云端注册新设备) {
            //匹配现有云端设备失败，没有足够设备分配
            log.info("设备表可分配不足，向平台申请新设备");
            //批量添加设备
            //获取设备类型
            Device r = DeviceUtil.parseDev(a);
            int type = r.getType();
            //获取与类型匹配的pk
            String pk = null;
            List<Device> devices = getTotalList();
            for (Device d : devices
            ) {
                if (type == d.getType()) {
                    pk = d.getProductKey();
                    break;
                }
            }
            //获取设备列表中已存在同产品下的设备数量
            deviceNameFix = deviceService.getCountByType(pk);

            String deviceNameSuf = null;
            switch (type) {
                case 1:
                    deviceNameSuf = "TF";
                    break;
                case 2:
                    deviceNameSuf = "QT";
                    break;
                case 3:
                    deviceNameSuf = "LQ";
                    break;
                case 4:
                    deviceNameSuf = "HL";
                    break;
                case 5:
                    deviceNameSuf = "ZD";
                    break;
                case 6:
                    deviceNameSuf = "YQ";
                    break;
                case 7:
                    deviceNameSuf = "SUN";
                    break;
                case 0:
                    deviceNameSuf = "CS";
                    break;
            }


            log.info("批量添加云端设备获取pk=" + pk);
            iotDeviceService.batchAddDevices(pk, deviceNameSuf, deviceNameFix,13);
//            Thread.sleep(50);
            log.info("调用刷新云端设备列表");
            savaDevList();
//            log.info("循环设备列表，进行云端设备激活");
            //重新进行设备注册操作
            registerDevice(a);
        } else if (regSuccess == 需要更新设备信息) {

            log.info("重新下发设备三元组");
            //从申请获取 devcie信息
            Device regSuc = DeviceUtil.parseDev(a);

            //在数据库查询记录 reged = deviceService.getDeviceInfoByDevAndDtu(devId, dtuId);
            Device
//                    reged = deviceService.getDevInfoByDetail(devNote,type,devBH,devZH,busType);
                    //通过6元素查询
                    reged = deviceService.getDevByRegDevInfo(regSuc);
            //获取新分配的三元组
            String pk = reged.getProductKey();
            String deviceName = reged.getDeviceName();
            String ds = reged.getDeviceSecret();
            String dtuId = reged.getDtuId();
            String devId = reged.getDevId();
            //拼装下发消息
            DownDevInfo downDevInfo = new DownDevInfo(pk, deviceName, ds, dtuId, devId);
            log.info("拼装下发消息" + downDevInfo.toString());
            //消息下发时，调用模拟设备端进行TOPIC注册
            log.info("进行云端设备激活");
            String[] can = new String[]{downDevInfo.toString()};
            HelloWorld h = new HelloWorld();
            h.main(can);
            //增加延迟 以保证信息下发成功率
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //调用下发API 下发三元组信息processMessage
            JSONObject jsonRs = ioTService.pub("/a1KhXudYrKw/000000/user/dev/register/response", downDevInfo.toString(),
                    "a1KhXudYrKw", null);
            String succ = jsonRs.getString("Success");
            if ("true".equals(succ)) {
                log.info("第一次三元组信息下发成功");
            }
//            jsonRs = ioTService.pub("/a1KhXudYrKw/000000/user/dev/register/response", downDevInfo.toString(),
//                    "a1KhXudYrKw", null);
//            succ = jsonRs.getString("Success");
//            if ("true".equals(succ)) {
//                log.info("第二次三元组信息下发成功");
//            }
        }

    }

}

