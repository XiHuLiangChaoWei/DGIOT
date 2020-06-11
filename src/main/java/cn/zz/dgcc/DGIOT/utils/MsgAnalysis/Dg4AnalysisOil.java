package cn.zz.dgcc.DGIOT.utils.MsgAnalysis;

import cn.zz.dgcc.DGIOT.utils.Constant;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.BytesUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.springframework.stereotype.Component;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain.ERROR_TEMP_TAG;
import static cn.zz.dgcc.DGIOT.utils.MsgBuilder.BytesUtil.tran_LH;

/**
 * Created by: YYL
 * Date: 2020/6/9 17:26
 * ClassExplain :
 * ->
 */
@Component
public class Dg4AnalysisOil {
    private Dg4AnalysisOil() {
    }

    private static final Dg4AnalysisOil Dg4AnalysisOil = new Dg4AnalysisOil();

    public static Dg4AnalysisOil newInstance() {
        return Dg4AnalysisOil;
    }

    private final Logger log = Logger.getLogger(Dg4AnalysisOil.class.getName());


    public JSONObject analysisOil2(String msg) {
        String getTime = msg.substring(4, 16);
        String year = getTime.substring(0, 2);
        String month = getTime.substring(2, 4);
        String day = getTime.substring(4, 6);
        String hour = getTime.substring(6, 8);
        String minute = getTime.substring(8, 10);
        String second = getTime.substring(10, 12);
        year = Integer.valueOf(year, 16).toString();
        month = Integer.valueOf(month, 16).toString();
        day = Integer.valueOf(day, 16).toString();
        hour = Integer.valueOf(hour, 16).toString();
        minute = Integer.valueOf(minute, 16).toString();
        second = Integer.valueOf(second, 16).toString();
        String time = year + month + day + hour + minute + second;

        //提取分机地址
        String devAddress = msg.substring(18, 20);
        //提取高度校准
        String height = msg.substring(24, 28);
        //提取温度校准
        String temps = msg.substring(28, 268);
        List<Float> tempList = new ArrayList<>();
        String temp = null;
        for (int i = 0; i < temps.length() / 4; i++) {
            temp = temps.substring(i * 4, i * 4 + 4);
            if ("FFFF".equals(temp)) {
            } else {
                tempList.add(Float.valueOf(new DecimalFormat("0.00").format(BytesUtil.oilTempTran(temp))));
            }
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("height", height);
        jsonObject.put("temps", temps);
        jsonObject.put("tempsDate", tempList);
        jsonObject.put("devAddress", devAddress);
        jsonObject.put("time", time);
        return jsonObject;

    }


    public JSONObject analysisOilInfo(String msg) {
        String getTime = msg.substring(4, 16);
        String year = getTime.substring(0, 2);
        String month = getTime.substring(2, 4);
        String day = getTime.substring(4, 6);
        String hour = getTime.substring(6, 8);
        String minute = getTime.substring(8, 10);
        String second = getTime.substring(10, 12);
        year = Integer.valueOf(year, 16).toString();
        month = Integer.valueOf(month, 16).toString();
        day = Integer.valueOf(day, 16).toString();
        hour = Integer.valueOf(hour, 16).toString();
        minute = Integer.valueOf(minute, 16).toString();
        second = Integer.valueOf(second, 16).toString();
        String time = year + month + day + hour + minute + second;
        //提取分机地址
        String devAddress = msg.substring(18, 20);
        //提取油温数据
        String tempDate = msg.substring(20, 532);
        List<Float> temps = new ArrayList<>();
        String temp = null;
        for (int i = 0; i < tempDate.length() / 4; i++) {
            temp = tempDate.substring(i * 4, i * 4 + 4);
//            if (temp == null) {
//                temp = "0000";
//            }
            if ("FFFF".equals(temp)) {
                //代表异常或者没数据,解析为异常数据
//                temps.add(ERROR_TEMP_TAG);
            } else {
                temps.add(Float.valueOf(new DecimalFormat("0.00").format(BytesUtil.oilTempTran(tran_LH(temp)) * 0.0625)));
            }
        }
        //提取高度数据
        String hightDate = msg.substring(538, 544);
        String M = hightDate.substring(0, 4);
        String mm = hightDate.substring(4, 6);
        Float height = Float.valueOf(new DecimalFormat("0.00").format(BytesUtil.oilTempTran(tran_LH(M)) / 100));
        double mmheight = BytesUtil.hexToInt(mm);
        //提取雷达温度
        String leidaTempFix = msg.substring(576, 580);
//        System.err.println(leidaTempFix);
        String leidaTemp = msg.substring(580, 588);
//        System.err.println(leidaTemp);
        Float leidaTemp1;
        if (!"FFFFFFFF".equals(leidaTemp)) {
            leidaTemp1 = BytesUtil.Hex2Float(leidaTemp);
        } else {
            leidaTemp1 = Float.valueOf(0);
        }

        int leidaFix1 = BytesUtil.hexStrToBytes(leidaTempFix)[0];

        double finalHeight = Double.valueOf(new DecimalFormat("0.0000").format(height + mmheight / 1000));

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("devAddress", devAddress);
        jsonObject.put("time", time);
        jsonObject.put("temps", temps);
        jsonObject.put("height", height);
        jsonObject.put("heightMM", mmheight);
        jsonObject.put("finalHeight", finalHeight);
        jsonObject.put("leidaTemp", leidaTemp1);
        jsonObject.put("leidaFix", leidaFix1);
        return jsonObject;
    }

}
