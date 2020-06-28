package cn.zz.dgcc.DGIOT.service.impl;

import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.Fireware;
import cn.zz.dgcc.DGIOT.mapper.FirewareMapper;
import cn.zz.dgcc.DGIOT.service.FirewareService;
import cn.zz.dgcc.DGIOT.service.IoTService;
import cn.zz.dgcc.DGIOT.utils.AMQP.AMQPMessage;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.FileUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by: YYL
 * Date: 2020/6/2 11:45
 * ClassExplain :
 * ->
 */
@Service
public class FirewareServiceImpl implements FirewareService {
    @Resource
    FirewareMapper firewareMapper;
    @Autowired
    IoTService ioTService;

    @Override
    public Fireware getFirewareByVersion(String version) {
        return firewareMapper.selectFirewareVersion(version);
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }

    /**
     * @param amqpMessage
     */
    @Override
    public void analysisInfo(AMQPMessage amqpMessage) {
        String topic = amqpMessage.getTopic();
        String[] topicSplit = topic.split("/");
        String pk = topicSplit[1];
        String devName = topicSplit[2];
        String msgId = amqpMessage.getMessageId();
        String content = amqpMessage.getContent();
        if (content.startsWith("{")) {
            JSONObject jo1 = JSON.parseObject(content);
            String reqVer = jo1.getString("Req_Ver");
            int reqNum = jo1.getInteger("Req_Num");
            String nowVer = jo1.getString("Now_Ver");

            Fireware fireware = firewareMapper.selectFirewareVersion(reqVer);
            String url = fireware.getPath();
            //根据url获取到文件
            File file = FileUtils.getFile(url);
            int total = 0;
            JSONArray ja = null;
            byte[] targetByte = null;
            try {
                //将文件解析，拆分成1kb的子包。
                ja = FileUtils.getFileSplit(file);
                for (int i = 0; i < ja.size(); i++) {
                    if (i == reqNum) {
                        JSONObject jo = ja.getJSONObject(i);
                        byte[] bytes = jo.getBytes("fileData");
                        targetByte = bytes;
                        total = jo.getInteger("sum");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String wholeCheckSum = Integer.toHexString(total).toUpperCase();
            wholeCheckSum = ContextUtil.FormatHEXString(wholeCheckSum, 4);

            String hexStr = bytesToHexString(targetByte).toUpperCase();
            //拼装下发topic
            String fullTopic = "/" + pk + "/" + devName + "/user/dev/version/upgrade/response";
            JSONObject jo = new JSONObject();
            jo.put("Res_Ver", reqVer);
            jo.put("Res_Num", reqNum);
            jo.put("Now_Ver",nowVer);
            String targetStr = jo.toJSONString();
            String contentStart = ContextUtil.stringToHexString(targetStr);
            String contentEnd = hexStr;

            JSONObject rs = ioTService.pub(fullTopic, contentStart + wholeCheckSum + contentEnd, pk, "1");
        }
    }

    @Override
    public int saveFirewareVersion(Fireware fireware) {
        return firewareMapper.addNewVersion(fireware);
    }

    @Override
    public List<Fireware> getAll() {
        return firewareMapper.selectAll();
    }

    @Override
    public List<Fireware> getFirewareListByDevName(String deviceName) {
        return firewareMapper.selectFirewareByDevName(deviceName);
    }
}
