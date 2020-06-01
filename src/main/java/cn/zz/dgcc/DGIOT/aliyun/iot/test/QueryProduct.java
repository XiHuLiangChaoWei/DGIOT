package cn.zz.dgcc.DGIOT.aliyun.iot.test;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * Created by: YYL
 * Date: 2020/4/9 8:52
 * ClassExplain :
 * ->
 */
public class QueryProduct {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-shanghai", "LTAI4FtLrEDXjUoG8vy9hqga", "N25z0lGWyu6mB6DxrGxI9mtIswkDeo");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("iot.cn-shanghai.aliyuncs.com");
        request.setVersion("2018-01-20");
        request.setAction("QueryProductList");
        request.putQueryParameter("RegionId", "cn-shanghai");
//        request.putQueryParameter("ProductKey", "a1KhXudYrKw");
        request.putQueryParameter("PageSize","200");
        request.putQueryParameter("CurrentPage","1");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}