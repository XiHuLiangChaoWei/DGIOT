package cn.zz.dgcc.DGIOT.service;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Product;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/4/10 8:39
 * ClassExplain :
 * ->
 */
@Service
public interface IoTService {

    JSONObject queryProductList();

    JSONObject queryProduct(String productKey);


    JSONObject queryDevice(String productKey);


    JSONObject queryDeviceDetail(String IotId, String productKey, String deviceName);


    JSONObject queryProductTopic(String productKey);


    JSONObject createProductTopic(String topicShortName, String productKey, String operation, String desc);

    JSONObject deleteProductTopic(String topicId);

    JSONObject updateProductTopic(String desc, String operation, String topicShortName, String topicId);


    JSONObject pub(String topicFullName, String messageContent, String productKey, String qos);

    JSONObject pubBoradcast(String messageContent, String pk, String topicFullName);


    List<Product> getProductList();

    List<Device> getDeviceList(List<Product> pL);


}
