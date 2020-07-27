package cn.zz.dgcc.DGIOT.controller;

import cn.zz.dgcc.DGIOT.entity.AppVersion;
import cn.zz.dgcc.DGIOT.entity.BuildMessage;
import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Fireware;
import cn.zz.dgcc.DGIOT.service.*;
import cn.zz.dgcc.DGIOT.service.Exception.IFileException;
import cn.zz.dgcc.DGIOT.utils.*;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.SunFengjiCommondBuilder;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.SunPowerCommondBuilder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by: LT001
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
    @Autowired
    DownOrderUtils downOrderUtils;
    @Autowired
    AppVersionService appVersionService;

    @RequestMapping("uploadapp")
    public String app(){
        return "html/appUpload";
    }

    @RequestMapping("/test1")
    public String test1(Model model) {
        return "html/admin";
    }

    @RequestMapping("/fileOrFireware")
    public String update_fileOrFireware() {
        return "html/up";
    }

    @RequestMapping("/ShanXiJinZhong")
    public String test12() {
        return "html/ShanXiJinZhong";
    }

    @RequestMapping("/AnShunXiXiu")
    public String test16() {
        return "html/AnShunXiXiu";
    }

    @RequestMapping("/firewareUpdate2")
    public String firewareUpdate2() {
        return "html/firewareUpdate2";
    }

    @RequestMapping("/sunfengji")
    public String tets() {
        return "html/sunFengji";
    }

    @ResponseBody
    @RequestMapping("updata")
    public void a(@RequestParam(name = "devName") String dev,
                  @RequestParam(name = "version") String version) {
        System.err.println("dev=" + dev);
        System.err.println("version=" + version);

    }

    /**
     * 太阳能分机控制分机
     *
     * @param session
     * @param address
     * @param num
     * @param action
     * @return
     */
    @RequestMapping("sun/fengji")
    @ResponseBody
    public JsonResult<String> sun(HttpSession session, int address, int num, int action) {
        int companyId = getCompanyIdFromSession(session);
        int userId = getUserIdFromSession(session);
        String devName = depotService.getDevNameByDepotIdAndType(1, 太阳能分机设备, companyId);
        Device rs = deviceService.getDevByDevName(devName);
        SunFengjiCommondBuilder sunFengjiCommondBuilder = SunFengjiCommondBuilder.getInstance();
        String address1 = ContextUtil.toShortHex(address);
        String num1 = ContextUtil.toShortHex(num);
        String action1 = ContextUtil.toShortHex(action);
        sunFengjiCommondBuilder.setAddress(address1);
        sunFengjiCommondBuilder.setNum(num1);
        sunFengjiCommondBuilder.setAction(action1);
        BuildMessage sunfengjiMsg = sunFengjiCommondBuilder.build();
        JsonResult<String> jr = downOrderUtils.deployAndSaveOrder(userId, rs, sunfengjiMsg, 太阳能分机设备, 1);
        return jr;
    }

    /**
     * 查询指令
     */
    @RequestMapping("sun")
    @ResponseBody
    public void b() {
        SunPowerCommondBuilder sunPowerCommondBuilder = SunPowerCommondBuilder.getInstance();
        sunPowerCommondBuilder.setCeng("04");
        sunPowerCommondBuilder.setHang("08");
        sunPowerCommondBuilder.setLie("09");
        sunPowerCommondBuilder.setThNum("01");
        sunPowerCommondBuilder.setIfOut("00");
        sunPowerCommondBuilder.setDevAddress("02");
        BuildMessage msg = sunPowerCommondBuilder.build();
        String fullTopic = "/g092mlxAtWS/ZZ-DG-CS-SUN001/user/sev/downdate";
        String pk = "g092mlxAtWS";
        ioTService.pub(fullTopic, msg.toString(), pk, "1");
        System.err.println(msg.toString());
    }

    /**
     * 返回版本列表
     *
     * @return
     */
    @RequestMapping("fireware")
    @ResponseBody
    public JsonResult2<List<Fireware>> f() {
        List<Fireware> list = firewareService.getAll();
        return new JsonResult2<>(0, list);
    }

    /**
     * 获取 固件版本 列表
     * <p>
     * 修改获取固件版本逻辑，从dev表中获取当前版本，根据当前版本获取对应固件版本
     *
     * @param devName
     * @return
     */
    @RequestMapping("getlist")
    @ResponseBody
    public JsonResult<String> getFirewareByProject(String devName) {
        //获取当前版本
        String nowFirewareVer = deviceService.getFirewareVerByDevName(devName);
        String middle = nowFirewareVer.substring(0, nowFirewareVer.length() - 3);
        //从当前版本获取对应信息
        int index = middle.lastIndexOf("-");
        String deviceVer = middle.substring(0, index);
        //获取列表
        List<Fireware> list = firewareService.getFirewareListByDevVer(deviceVer);
        Gson g = new Gson();
        String rs = g.toJson(list);
        System.err.println(rs);
        return new JsonResult<>(success, rs);
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


    @RequestMapping("upApp")
    @ResponseBody
    public JsonResult<Void> updateAppFile(
            @RequestParam("fileName") MultipartFile file,
            String version, String vernote,
            HttpSession session) {

        System.err.println("version:"+version+"===vercode:"+vernote);

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
        System.err.println(orginalFilename);
        //设置文件存储路径
        String parentPath = "D:/dgcc";
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
        //生成服务器上文件存储全路径  该路径只用来在后台查看路径
        String avatarpath = parentPath + "/" + orginalFilename;
        //文件生成操作  两个参数分别为 路径,文件名
        File avatar = new File(parent, orginalFilename);
        try {
            file.transferTo(avatar);
        } catch (IllegalStateException e) {
            throw new IFileException("文件状态码出错");
        } catch (IOException e) {
            throw new IFileException("您的文件已经移动或者被删除,请重新上传");
        }
        //
        System.err.println(avatarpath);
        AppVersion appVersion = new AppVersion(version, vernote, avatarpath);
        AppVersion oldApp = appVersionService.getNowAppVersion();
        appVersion.setVerCode(String.valueOf((Integer.parseInt(oldApp.getVerCode())+1)));
        int rsAppVer = appVersionService.gengxin(appVersion);
        if(rsAppVer!=1){
            return new JsonResult<Void>(servWrong, avatarpath);
        }
        return new JsonResult<Void>(success, avatarpath);
    }


    /**
     * @param devName 设备名
     * @param version 目标版本
     * @param type    1=dev other = dtu
     */
    @ResponseBody
    @RequestMapping("Sev_fireware")
    public void down(String devName, String version, @RequestParam(required = false, defaultValue = "1") int type) {
        System.err.println("开始固件升级······");
        System.err.println("设备名：" + devName);
        System.err.println("目标版本：" + version);
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


    //判断字符串是否包含汉字
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 获取设备列表
     *
     * @param str
     * @return
     */
    @RequestMapping("/devList")
    @ResponseBody
    public JsonResult2<List<Device>> devList(@RequestParam(required = false, defaultValue = "null") String str,
                                             @RequestParam(required = false, defaultValue = "1") int page,
                                             @RequestParam(required = false, defaultValue = "30") int limit) {
        System.err.println("page=" + page);
        System.err.println("limit=" + limit);
        if (isContainChinese(str)) {
            str = ContextUtil.chinese2PinYin2(str);
        }
        str = str.toUpperCase();
        List<Device> list;
        if ("NULL".equals(str)) {
            list = deviceService.getAllActiveDev();
        } else {
            list = deviceService.getDevListByDevNameInfo(str);
        }
//        for (Device d:list
//             ) {
//            System.err.println(d.toString());
//        }
        return new JsonResult2<>(0, list, list.size());
    }

    /**
     * 产品列表信息
     *
     * @return
     */
    @ResponseBody
    @RequestMapping("/showProductList")
    public JsonResult<JSON> test2() {
        JSONObject json0 = ioTService.queryProductList();
        JSONArray productInfo = json0.getJSONArray("ProductInfo");
        log.info("ControllerLog:查询产品列表信息" + productInfo);
        return new JsonResult<>(success, productInfo);
    }

    /**
     * 根据产品pk返回设备列表
     *
     * @param productKey
     * @return
     */
    @ResponseBody
    @RequestMapping("{productKey}/showDeviceListByProductKey")
    public JsonResult<JSON> test3(@PathVariable("productKey") String productKey) {
        JSONObject json0 = ioTService.queryDevice(productKey).getJSONObject("Data");
        JSONArray deviceInfo = json0.getJSONArray("DeviceInfo");
        log.info("ControllerLog:查询设备信息" + deviceInfo);
        return new JsonResult<>(success, deviceInfo);
    }

    /**
     * 根据产品pk获取产品详细信息
     *
     * @param productKey
     * @return
     */
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

    /**
     * 根据pk获取产品自定义topic
     *
     * @param productKey
     * @return
     */
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

    /**
     * 发送消息
     *
     * @param productKey
     * @param fullName
     * @param content
     * @param qos
     * @return
     */
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

    /**
     * 根据设备的iotId获取设备详细信息
     *
     * @param deviceIot
     * @return
     */
    @ResponseBody
    @RequestMapping("{deviceIot}/getDeviceDetail")
    public JsonResult<JSON> test6(@PathVariable("deviceIot") String deviceIot) {
        JSONObject json0 = ioTService.queryDeviceDetail(deviceIot, null, null);
//        log.info("ControllerLog:查询产品Topic信息" + json0);
        log.info("ControllerLog:查询设备信息" + json0);
        return new JsonResult<>(success, json0);
    }
}
