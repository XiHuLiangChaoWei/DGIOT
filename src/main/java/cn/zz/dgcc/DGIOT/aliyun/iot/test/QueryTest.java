package cn.zz.dgcc.DGIOT.aliyun.iot.test;

import com.aliyuncs.iot.model.v20180120.*;

import java.util.List;

import static cn.zz.dgcc.DGIOT.aliyun.iot.api.sdk.openapi.DeviceManager.*;
import static cn.zz.dgcc.DGIOT.aliyun.iot.api.sdk.openapi.ProductManager.*;

/**
 * Created by: YYL
 * Date: 2020/4/7 8:37
 * ClassExplain :
 * ->
 */
public class QueryTest {
    public static void main(String[] args){
        QueryTest queryTest = new QueryTest();
//        queryTest.queryTest1();

//        queryTest.queryTest2();

//        queryTest.queryTest3();

//        queryTest.queryTest4();

        queryTest.queryTest5();
    }

    /*
        查询产品信息
            authType
            dataFormat
            description
            deviceCount
            gmtCreate
            nodeType
            productKey
            productName
     */
    public void queryTest1(){
        QueryProductListResponse.Data data = queryProductListTest(1,20,null);
        System.out.println(data);
    }
    /*
        查询产品详细信息
     */
    public void queryTest2(){
        QueryProductResponse.Data data = queryProductTest("a1gQiP9WsBk");
        System.out.println(data);
    }
    /*
        查询产品下设备列表
     */
    public void queryTest3(){
        List<QueryDeviceResponse.DeviceInfo> list = queryDevice("a1gQiP9WsBk",1,null);
        for (QueryDeviceResponse.DeviceInfo data:
             list) {
            System.out.println(data);
        }
    }
    /*
        设备运行状态查询
     */
    public void queryTest4(){
        GetDeviceStatusResponse.Data data = getDeviceStatus("a1gQiP9WsBk","DGCC_TEST1",null);
        System.out.println(data);
    }

    /*
        查询指定设备上传的历史文件（失败，未提供正确的设备id）
     */
    public void queryTest5(){
        List<QueryDeviceFileListResponse.FileSummary> list = queryDeviceFileList("kFdPCUwLAvSEM4uf6w5x","a1gQiP9WsBk","DGCC_TEST1",1,null);
    }

}
