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
    /**
     * 查询产品列表
     * @return
     */
    JSONObject queryProductList();

    /**
     * 查询产品信息
     * @param productKey
     * @return
     */
    JSONObject queryProduct(String productKey);

    /**
     * 查询设备列表
     * @param productKey
     * @return
     */
    JSONObject queryDevice(String productKey);

    /**
     * 查询设备信息
     * @param IotId
     * @param productKey
     * @param deviceName
     * @return
     */
    JSONObject queryDeviceDetail(String IotId,String productKey,String deviceName);

    /**
     * 查询产品的Topic列表
     * @param productKey
     * @return
     */
    JSONObject queryProductTopic(String productKey);

    /**
     * 创建产品Topic
     * @param topicShortName
     * @param productKey
     * @param operation
     * @param desc
     * @return
     */
    JSONObject createProductTopic(String topicShortName,String productKey,String operation,String desc);
    JSONObject deleteProductTopic(String topicId);
    JSONObject updateProductTopic(String desc,String operation,String topicShortName,String topicId);

    /**
     * 向指定Topic发送消息
     * @param topicFullName
     * @param messageContent
     * @param productKey
     * @param qos
     * @return
     */
    JSONObject pub(String topicFullName,String messageContent,String productKey,String qos);

    JSONObject pubBoradcast(String messageContent,String pk,String topicFullName);


    List<Product> getProductList();

    List<Device> getDeviceList(List<Product> pL);

    
}
