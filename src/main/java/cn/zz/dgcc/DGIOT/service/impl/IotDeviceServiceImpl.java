package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.service.IotDeviceService;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

/**
 * Created by: YYL
 * Date: 2020/5/6 14:36
 * ClassExplain :
 * ->
 */
@Service
public class IotDeviceServiceImpl implements IotDeviceService {
    private final static Logger log = Logger.getLogger(IotDeviceServiceImpl.class.getSimpleName());
    static IAcsClient commonClient() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI4FtLrEDXjUoG8vy9hqga", "N25z0lGWyu6mB6DxrGxI9mtIswkDeo");
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }

    static JSON getJson(String str) {
        JSONObject json = JSONObject.parseObject(str);
        return json;
    }

    /**
     * 创建请求，并填入固定的属性
     *
     * @return
     */
    static CommonRequest commonRequest() {

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("iot.cn-shanghai.aliyuncs.com");
        request.setVersion("2018-01-20");
        request.putQueryParameter("RegionId", "cn-shanghai");
        return request;
    }

    static String batchCheckDeviceName(int i, String pk, String deviceName, int deviceNameFix) {
        if (i == 0) {
            i = 2;
        }
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setMethod(MethodType.POST);
        cr.setAction("BatchCheckDeviceNames");
        cr.putQueryParameter("ProductKey", pk);
        String batchId = null;
        for (int a = 1; a <= i; a++) {
            cr.putQueryParameter("DeviceName." + a, deviceName + ContextUtil.FormatNum6(deviceNameFix + a));

        }
        log.info("申请了" + i + "个新设备");
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject jsonResult = (JSONObject) getJson(response.getData());
            batchId = jsonResult.getJSONObject("Data").getString("ApplyId");
            log.info("申请批次+" + batchId);


        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return batchId;
    }


    private static void batchRegisterDeviceWithApplyId(String batchId, String pk) {
        CommonRequest cr = commonRequest();
        IAcsClient client = commonClient();
        cr.setMethod(MethodType.POST);
        cr.setAction("BatchRegisterDeviceWithApplyId");
        cr.putQueryParameter("ApplyId", batchId);
        cr.putQueryParameter("ProductKey", pk);
        try {
            CommonResponse response = client.getCommonResponse(cr);
            System.out.println(response.getData());
            JSONObject result = (JSONObject) getJson(response.getData());
            JSONObject resultDate = result.getJSONObject("Data");
            String resultBatchId = resultDate.getString("ApplyId");
            if (resultBatchId.equals(batchId)) {
                log.info("批量注册成功");
            }
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param pk
     * @param deviceName
     * @param deviceNameFix 从库中读取到的初始位置
     */
    @Override
    public void batchAddDevices(String pk, String deviceName, int deviceNameFix) {

        String batchId = batchCheckDeviceName(13, pk, deviceName, deviceNameFix);
        if (batchId != "" | !batchId.equals(null)) {
            //出现创建设备错误，是因为传入的deviceName问题
            batchRegisterDeviceWithApplyId(batchId, pk);
        }
    }


}
