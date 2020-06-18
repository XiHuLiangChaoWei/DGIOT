package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Fireware;
import cn.zz.dgcc.DGIOT.service.DepotService;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.service.Exception.IFileException;
import cn.zz.dgcc.DGIOT.service.FirewareService;
import cn.zz.dgcc.DGIOT.service.IoTService;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.FileUtils;
import cn.zz.dgcc.DGIOT.utils.JsonResult;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;
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
    @Autowired
    FirewareService firewareService;
    @Autowired
    DeviceService deviceService;
    @Autowired
    DepotService depotService;

    @RequestMapping("/test1")
    public String test1(Model model) {
        return "html/admin";
    }

    @RequestMapping("/test2")
    public String test11() {
        return "html/up";
    }

    /**
     * 固件文件上传
     *
     * @param file
     * @param session
     * @return
     */
    @RequestMapping("fileUpload")
    @ResponseBody
    public JsonResult<Void> updateAvatar(
            @RequestParam("fileName") MultipartFile file,
            HttpSession session) {
        Integer userId = getUserIdFromSession(session);
        String username = getUserNameFromSession(session);
        //检查上传文件是否为空
        if (file.isEmpty()) {
            throw new IFileException("请使用非空文件");
        }
        //检查上传文件大小
//        if(file.getSize()>AVATAR_MAX_SIZE) {
//            throw new IFileException("请使用不超过" + AVATAR_MAX_SIZE + "kb的文件");
//        }
        //检查上传文件类型  通过contentType
//        if(AVATAR_TYPES.contains(file.getContentType())) {
//            throw new IFileException("文件类型错误,请使用" + AVATAR_TYPES + "类型的文件");
//        }
        //获取文件原名
        String orginalFilename = file.getOriginalFilename();
        //设置文件存储路径
        String parentPath = "D:/update/Fireware";
        System.out.println("parentPath: " + parentPath);
        File parent = new File(parentPath);
        if (!parent.exists()) {
            parent.mkdirs();
        }
        //检查文件后缀
        int beginIndex = orginalFilename.lastIndexOf(".");
        String suffix = "";
        if (beginIndex > 0) {
            suffix = orginalFilename.substring(beginIndex);
        }
        orginalFilename = orginalFilename.substring(0, beginIndex);
        //为文件生成唯一文件名
        String child = UUID.randomUUID().toString() + suffix;
        //生成服务器上文件存储全路径  该路径只用来在后台查看路径
        String avatarpath = parentPath + "/" + child;
        //文件生成操作  两个参数分别为 路径,文件名
        File avatar = new File(parent, child);
        try {
            file.transferTo(avatar);
        } catch (IllegalStateException e) {
            throw new IFileException("文件状态码出错");
        } catch (IOException e) {
            throw new IFileException("您的文件已经移动或者被删除,请重新上传");
        }
        //
        System.err.println(avatarpath);
        Fireware fireware = new Fireware(orginalFilename.trim(), avatarpath);
        int rs = firewareService.saveFirewareVersion(fireware);
        return new JsonResult<Void>(success, avatarpath);
    }


    /**
     * @param devName
     * @param version
     * @param type    1=dev other = dtu
     */
    @ResponseBody
    @RequestMapping("Sev_fireware")
    public void down(String devName, String version, int type) {
        //根据版本获取 固件升级对象
        Fireware fireware = firewareService.getFirewareByVersion(version);
        //获取固件升级包所在位置
        String url = fireware.getPath();
        //根据url获取到文件
        File file = FileUtils.getFile(url);
        int total = 0;
        JSONArray ja = null;
        try {
            //将文件解析，拆分成1kb的子包。
            ja = FileUtils.getFileSplit(file);
            for (int i = 0; i < ja.size(); i++) {
                JSONObject jo = ja.getJSONObject(i);
                int index = jo.getInteger("index");
                int sum = jo.getInteger("sum");
                byte[] bytes = jo.getBytes("fileData");
                total += sum;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //总包数
        int totalPackge = ja.size();
        //校验和
        String wholeCheckSum = Integer.toHexString(total).toUpperCase();
        wholeCheckSum = ContextUtil.FormatHEXString(wholeCheckSum, 8);

        Device device = deviceService.getDevByDevName(devName);
        //当前版本
        String ver = type == 1 ? device.getDevVersion() : device.getDtuVersion();

        String pk = device.getProductKey();
        String fullTopic = "/" + pk + "/" + devName + "/user/dev/version/upgrade";
        //json格式拼装
        JSONObject jo = new JSONObject();
        jo.put("Now_Ver", ver);
        jo.put("Upgrade_Ver", version);
        jo.put("Total_Packge", totalPackge);
        jo.put("Whole_CheckSum", wholeCheckSum);
        JSONObject rs = ioTService.pub(fullTopic, jo.toJSONString(), pk, null);

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
        fullName = fullName.replace("-", "/");

        String[] fullNames = fullName.split("/");
        if (fullNames[2].equals("${deviceName}")) {
            JSONObject json1 = ioTService.pubBoradcast(content, productKey, null);

            return new JsonResult<>(success, json1);
        }

        log.info("ControllerLog:" + productKey + "==" + fullName + "==" + content + "==" + qos);
        JSONObject json0 = ioTService.pub(fullName, content, productKey, qos);

//        log.info("ControllerLog:查询产品Topic信息" + json0);
//        JSONArray productTopicInfo = json0.getJSONArray("ProductTopicInfo");
//        log.info("ControllerLog:查询产品Topic信息" + productTopicInfo);
        return new JsonResult<>(success, json0);
    }


    @ResponseBody
    @RequestMapping("{deviceIot}/getDeviceDetail")
    public JsonResult<JSON> test6(@PathVariable("deviceIot") String deviceIot) {

        JSONObject json0 = ioTService.queryDeviceDetail(deviceIot, null, null);
//        log.info("ControllerLog:查询产品Topic信息" + json0);
        log.info("ControllerLog:查询设备信息" + json0);
        return new JsonResult<>(success, json0);
    }
}
