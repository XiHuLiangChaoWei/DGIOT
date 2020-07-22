package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Product;
import cn.zz.dgcc.DGIOT.service.IoTService;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.eclipse.paho.client.mqttv3.internal.websocket.Base64;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/4/10 8:39
 * ClassExplain : 云端设备操作
 * ->
 */
@Service
public class IotServiceImpl implements IoTService {
    private final static Logger log = Logger.getLogger(IotServiceImpl.class.getSimpleName());

    public List<Product> getProductList() {
        /*
        查询产品列表
         */
        JSONObject productList = queryProductList();
        JSONArray pl = productList.getJSONArray("ProductInfo");
        List<Product> productKeys = new ArrayList<Product>();
        log.warning(pl.toJSONString());
        JSONObject row = null;
        for (int i = 0; i < pl.size(); i++) {
            row = pl.getJSONObject(i);
            Product product = new Product();
            product.setProductKey(row.getString("ProductKey"));
            product.setProductName(row.getString("ProductName"));
            product.setDeviceCount(row.getInteger("DeviceCount"));
            product.setDesc(row.getString("Description"));
            productKeys.add(product);
        }
        return productKeys;
    }

    private final static int 通风设备 = 1;
    private final static int 气调设备 = 2;
    private final static int 粮情设备 = 3;
    private final static int 环流设备 = 4;
    private final static int 制氮设备 = 5;
    private final static int 油情设备 = 6;
    private final static int 太阳能分机设备 = 7;
    private final static int 测试设备 = 0;

    /*
    根据产品列表查询设备列表
     */
    public List<Device> getDeviceList(List<Product> pL) {
        List<Device> devList = new ArrayList<Device>();
        JSONObject row;
        for (Product p : pL
        ) {
            String pk = p.getProductKey();
//            System.err.println("查询设备列表····pk="+pk);
            JSONObject deviceR = queryDevice(pk).getJSONObject("Data");
            JSONArray deviceInfo = deviceR.getJSONArray("DeviceInfo");
            for (int i = 0; i < deviceInfo.size(); i++) {
                row = deviceInfo.getJSONObject(i);
                Device a = new Device();
                a.setProductKey(row.getString("ProductKey"));
                a.setDeviceName(row.getString("DeviceName"));
                a.setDeviceSecret(row.getString("DeviceSecret"));
                a.setProductName(p.getProductName());
                a.setDeviceStatus(row.getString("DeviceStatus"));
                a.setDeviceNickName("NickName");
                a.setIotId(row.getString("IotId"));
                String devname = row.getString("DeviceName");
                String[] strs = devname.split("-");
                int devBh = Integer.parseInt(strs[strs.length - 1].substring(3));
                a.setDevBH(String.valueOf(devBh));
                a.setDevZH("1");
                switch (row.getString("ProductKey")) {
                    case "a1KhXudYrKw":
                        a.setType(测试设备);
                        break;
                    case "a1fmlHyzz82":
                        a.setType(环流设备);
                        break;
                    case "a1zuatjJlcg":
                        a.setType(通风设备);
                        break;
                    case "a1RnedE9Gfb":
                        a.setType(粮情设备);
                        break;
                    case "a1bop2X1JTB":
                        a.setType(制氮设备);
                        break;
                    case "a1J3Y773MWq":
                        a.setType(气调设备);
                        break;
                    case "g092bBa0bSV":
                        a.setType(油情设备);
                        break;
                    case "g092mlxAtWS":
                        a.setType(太阳能分机设备);
                        break;
                }
                devList.add(a);
            }

        }
        return devList;
    }

    /**
     * 测试用
     *
     * @param args
     */
//    public static void main(String[] args) {
//        IotServiceImpl is = new IotServiceImpl();
//        is.queryProductList();
//        is.queryProduct("a1KhXudYrKw");
//        is.queryDevice("a1KhXudYrKw");
//        is.queryDeviceDetail("kFdPCUwLAvSEM4uf6w5x000100",null,null);
//        is.queryProductTopic("a1KhXudYrKw");
//        is.deleteProductTopic("9217766");
//        is.createProductTopic("ADDTEST","a1KhXudYrKw","sub","试一试");
//        is.updateProductTopic("升级试一试","all","UPdateTest","9218128");
//        is.pub("/a1KhXudYrKw/DGCC_TEST1/user/UPdateTest", "大帅逼", "a1KhXudYrKw", "0");
//        is.pubBoradcast("小帅逼","a1KhXudYrKw",null);
//    }


    /**
     * 传入str转换成json对象
     *
     * @param str
     * @return
     */
    JSON getJson(String str) {
        JSONObject json = JSONObject.parseObject(str);
        return json;
    }

    /**
     * 获取client
     *
     * @return
     */
    static IAcsClient commonClient() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI4FtLrEDXjUoG8vy9hqga", "N25z0lGWyu6mB6DxrGxI9mtIswkDeo");
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    /**
     * 创建请求，并填入所有请求的公共属性
     *
     * @return
     */

    static CommonRequest commonRequest() {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("iot.cn-shanghai.aliyuncs.com");
        request.setVersion("2018-01-20");
        request.putQueryParameter("RegionId", "cn-shanghai");
        request.putQueryParameter("IotInstanceId", "iot-cn-v641mge9j01");
        return request;
    }

    /**
     * 查询产品列表
     *
     * @return
     */
    @Override
    public JSONObject queryProductList() {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("QueryProductList");
        cr.putQueryParameter("PageSize", "200");
        cr.putQueryParameter("CurrentPage", "1");
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject jsonO = (JSONObject) getJson(response.getData());
            JSONObject j = jsonO.getJSONObject("Data").getJSONObject("List");
//            System.err.println("jsonO:" + jsonO);
//            System.err.println("j:" + j);
            return j;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询产品信息
     *
     * @param productKey
     * @return
     */
    @Override
    public JSONObject queryProduct(String productKey) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("QueryProduct");
        cr.putQueryParameter("ProductKey", productKey);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject json = (JSONObject) getJson(response.getData());
//            System.err.println("IotLog:" + json);
            return json;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询设备列表,返回的是返回值中的data数据
     *
     * @param productKey
     * @return
     */
    @Override
    public JSONObject queryDevice(String productKey) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("QueryDevice");
        cr.putQueryParameter("ProductKey", productKey);
        cr.putQueryParameter("PageSize", "200");
        try {
            CommonResponse response = client.getCommonResponse(cr);
//            System.out.println(response.getData());
            JSONObject json = (JSONObject) getJson(response.getData());
            JSONObject json1 = json.getJSONObject("Data");
//            JSONObject j1 = json1.getJSONArray("DeviceInfo");
            return json;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 查询设备具体信息
     *
     * @param IotId
     * @param productKey
     * @param deviceName
     * @return
     */
    @Override
    public JSONObject queryDeviceDetail(String IotId, String productKey, String deviceName) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("QueryDeviceDetail");
        cr.putQueryParameter("IotId", IotId);
        cr.putQueryParameter("ProductKey", productKey);
        cr.putQueryParameter("DeviceName", deviceName);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject json = (JSONObject) getJson(response.getData());
            JSONObject json1 = json.getJSONObject("Data");
//            System.err.println("IotLog:" + json1);
            return json1;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 批量查询设备状态 TODO
     *
     * @param list
     * @return
     */
    public JSONArray batchQueryDevDetail(List<String> list) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("BatchQueryDeviceDetail");
        cr.setMethod(MethodType.POST);


        return null;
    }


    /**
     * 查询产品Topic
     *
     * @param productKey
     * @return
     */
    @Override
    public JSONObject queryProductTopic(String productKey) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("QueryProductTopic");
        cr.putQueryParameter("ProductKey", productKey);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject json = (JSONObject) getJson(response.getData());
            JSONObject json1 = json.getJSONObject("Data");
//            System.err.println("IotLog:" + json1);
            return json1;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * @param topicShortName topic名
     * @param productKey
     * @param operation      Topic权限 SUB PUB ALL
     * @param desc           描述
     * @return
     */
    @Override
    public JSONObject createProductTopic(String topicShortName, String productKey, String operation, String desc) {
        operation = operation.toUpperCase();
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("CreateProductTopic");
        cr.putQueryParameter("TopicShortName", topicShortName);
        cr.putQueryParameter("ProductKey", productKey);
        cr.putQueryParameter("Operation", operation);
        cr.putQueryParameter("Desc", desc);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSON json = getJson(response.getData());
            System.err.println(json);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除Topic
     *
     * @param topicId
     * @return
     */
    @Override
    public JSONObject deleteProductTopic(String topicId) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("DeleteProductTopic");
        cr.putQueryParameter("TopicId", topicId);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSON json = getJson(response.getData());
            System.err.println(json);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新指定Topic
     *
     * @param desc
     * @param operation
     * @param topicShortName
     * @param topicId
     * @return
     */
    @Override
    public JSONObject updateProductTopic(String desc, String operation, String topicShortName, String topicId) {
        operation = operation.toUpperCase();
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("UpdateProductTopic");
        cr.putQueryParameter("Desc", desc);
        cr.putQueryParameter("Operation", operation);
        cr.putQueryParameter("TopicShortName", topicShortName);
        cr.putQueryParameter("TopicId", topicId);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSON json = getJson(response.getData());
            System.err.println(json);
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 向指定设备的指定topic发送消息
     *
     * @param topicFullName
     * @param messageContent
     * @param productKey
     * @param qos            null/0 -ASCII发送    1 -HEX发送
     * @return
     */
    @Override
    public JSONObject pub(String topicFullName, String messageContent, String productKey, String qos) {
        if (qos == null) {
            qos = "0";
        }

        System.err.println("输入的初始数据messageC:" + messageContent + "||" + topicFullName);
        String parseMsg = messageContent.replace(" ", "");
//        parseMsg = parseMsg.toUpperCase();
//        System.err.println("去除空格的中间数据parseMsg:" + parseMsg);
        String msg1 = null;
        if (qos.equals("0")) {
            byte[] mid = messageContent.getBytes();
            //解析消息正文；转化为正文二进制的base64编码;编码后的消息传入
            msg1 = java.util.Base64.getEncoder().encodeToString(mid);
        } else if (qos.equals("1")) {//将输入消息作为16进制发送
            byte[] mid = new byte[parseMsg.length() / 2];
            qos = "0";
            for (int i = 0; i < mid.length; i++) {
                try {
                    mid[i] = (byte) (Integer.parseInt(parseMsg.substring(i * 2, i * 2 + 2), 16));
//                    System.err.println("单个十六进制数解析=" + mid[i]);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //解析消息正文；转化为正文二进制的base64编码;编码后的消息传入
                msg1 = java.util.Base64.getEncoder().encodeToString(mid);
            }
        }


        //将解析的消息正文进行base64编码；

//        String msg1 = Hex.encodeHexString(mid);
        System.err.println("转换过的字符串转HEX消息:" + msg1);


//        String msgBinary = toBinary(messageContent);
//        String msg = Base64.encode(messageContent);
//        System.err.println("binary:"+msgBinary);
//        System.err.println("base64:" + msg);

        /*解析并替换TopicFullName,因为传递来的参数是Product下的Topic参数
         */
//        String[] fullName = topicFullName.split("/");
//        for (String str:fullName
//             ) {
//            System.out.println("name:"+str);
//        }
//        if (fullName[2].equals("${deviceName}")) {
//            JSONObject deviceQ = queryDevice(productKey);
//            JSONArray deviceList = deviceQ.getJSONArray("DeviceInfo");
//            if (deviceList.size() > 0) {
//                for (int i = 0; i < deviceList.size(); i++) {
//                    JSONObject device = deviceList.getJSONObject(i);
//                    System.err.println(device.get("DeviceName"));
//                    topicFullName = topicFullName.replace("${deviceName}", device.get("DeviceName").toString());
//                    pub(topicFullName, messageContent, productKey, qos);
//                }
//            }
////            System.err.println(deviceList);
//            return null;
//        }

        CommonRequest cr = commonRequest();
        cr.putQueryParameter("IotInstanceId", "iot-cn-v641mge9j01");
        IAcsClient client = commonClient();
        cr.setAction("Pub");
        cr.putQueryParameter("TopicFullName", topicFullName);
        cr.putQueryParameter("MessageContent", msg1);
        cr.putQueryParameter("ProductKey", productKey);
        cr.putQueryParameter("Qos", "0");
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject json = (JSONObject) getJson(response.getData());
            System.err.println("server层推送结果" + json);
            String messageId = json.getString("MessageId");
            String suc = json.getString("Success");
            System.err.println(messageId + "===" + suc);
            AMQPMessage amqpMessage = new AMQPMessage(topicFullName, messageId, suc);
//            amqp2WebSocket.setMsgC(amqpMessage);
//            try {
//                amqp2WebSocket.init();
//            } catch (ServletException e) {
//                e.printStackTrace();
//            }
            return json;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 向指定product的所有topic进行广播
     *
     * @param messageContent
     * @param pk
     * @param topicFullName
     * @return
     */
    @Override
    public JSONObject pubBoradcast(String messageContent, String pk, String topicFullName) {
        String qos = "0";

        String msg = Base64.encode(messageContent);

        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setAction("PubBroadcast");
        cr.putQueryParameter("MessageContent", msg);
        cr.putQueryParameter("ProductKey", pk);
        cr.putQueryParameter("Qos", qos);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject json = (JSONObject) getJson(response.getData());
//            System.err.println("server层推送结果" + json);
            /*连接网页右侧显示区域
             */
            String messageId = json.getString("MessageId");
            String suc = json.getString("Success");
            System.err.println(messageId + "===" + suc);
            AMQPMessage amqpMessage = new AMQPMessage(topicFullName, messageId, suc);
//            amqp2WebSocket.setMsgC(amqpMessage);
//            try {
//                amqp2WebSocket.init();
//            } catch (ServletException e) {
//                e.printStackTrace();
//            }
            return json;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return null;
    }


    protected String toBinary(String str) {
        char[] chars = str.toCharArray();
        String result = "";
        for (int i = 0; i < chars.length; i++) {
            result += Integer.toBinaryString((chars[i]));
        }
        return result;
    }

}
