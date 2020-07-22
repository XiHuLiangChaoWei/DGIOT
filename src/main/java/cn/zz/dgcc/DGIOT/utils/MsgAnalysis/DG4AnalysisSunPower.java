package cn.zz.dgcc.DGIOT.utils.MsgAnalysis;

import cn.zz.dgcc.DGIOT.VO.GrainVO;
import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.entity.Grain;
import cn.zz.dgcc.DGIOT.entity.GrainInfo;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.BytesUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static cn.zz.dgcc.DGIOT.utils.MsgAnalysis.Dg3AnalysisGrain.ERROR_TEMP_TAG;

/**
 * Created by: LT001
 * Date: 2020/6/23 17:17
 * ClassExplain :
 * ->
 */
public class DG4AnalysisSunPower {

    private DG4AnalysisSunPower() {
    }

    private static final DG4AnalysisSunPower DG4AnalysisSunPower = new DG4AnalysisSunPower();

    public static DG4AnalysisSunPower newInstance() {
        return DG4AnalysisSunPower;
    }

    private final Logger log = Logger.getLogger(DG4AnalysisSunPower.class.getName());

    public JSONObject analysis(Grain grain, Depot depot) {
        //获取消息正文
        String msg = grain.getContent();
        //获取层行列数
        String ceng = msg.substring(2 * 3, 2 * 3 + 2);
        String hang = msg.substring(2 * 4, 2 * 4 + 2);
        String lie = msg.substring(2 * 5, 2 * 5 + 2);
        int cengNum = Integer.parseInt(ceng, 16);
        int hangNum = Integer.parseInt(hang, 16);
        int lieNum = Integer.parseInt(lie, 16);
        int t = cengNum * hangNum * lieNum;
        //获取温湿度点
        String TempAndHumity = msg.substring(2 * 6, 2 * 6 + 2);
        int THNum = Integer.parseInt(TempAndHumity, 16);
        //是否携带外温外湿
        String outTH = msg.substring(2 * 7, 2 * 7 + 2);
        //获取分机地址
        String devAddress = msg.substring(2 * 9, 2 * 9 + 2);
        int devAdd = Integer.parseInt(devAddress, 16);
        //获取测温具体数据
        String temps = msg.substring(2 * 10, 2 * 10 + t * 4);
        List<Double> templ = parseTemps(temps);
        //获取温湿度点具体数据
        String ths = msg.substring(2 * 10 + t * 4 + 4, 2 * 10 + t * 4 + 4 + THNum * 6);
        JSONArray thl = parseTHPoint(ths);
        //获取外温外湿具体数据
        String outTHs = msg.substring(2 * 10 + t * 4 + 4 + THNum * 6 + 4, 2 * 10 + t * 4 + 4 + THNum * 6 + 4 + 6);
        JSONArray outTHl = parseTHPoint(outTHs);
        //获取电压信息
        String voltage = msg.substring(2 * 10 + t * 4 + 4 + THNum * 6 + 4 + 6 + 4, 2 * 10 + t * 4 + 4 + THNum * 6 + 4 + 6 + 4 + 4);
        double V = parseV(voltage);
        //
        double maxTemp = 0;
        double minTemp = 0;
        double avgTemp = 0;
        int index = 0;
        for (Double d : templ
        ) {
            if (maxTemp == 0) {
                maxTemp = d;
            }
            if (minTemp == 0) {
                minTemp = d;
            }
            if (maxTemp < d) {
                maxTemp = d;
            }
            if (minTemp > d) {
                minTemp = d;
            }
            avgTemp += d;
            index += 1;
        }
        avgTemp = avgTemp / index;
        String batchId = grain.getBatchId();
        Date date = grain.getreceivedTime();
        List<GrainInfo> grainInfos = parseTemp2GrainInfo(templ,hangNum,lieNum,cengNum,date,depot);
        JSONArray js = grainList2JsonArray(grainInfos);

        JSONObject rs = new JSONObject();
        rs.put("batchId", batchId);
        rs.put("depotId", grain.getDepotId());
        rs.put("x", hangNum);
        rs.put("y", lieNum);
        rs.put("z", cengNum);
        rs.put("maxTemp", maxTemp);
        rs.put("minTemp", minTemp);
        rs.put("avgTemp", avgTemp);
        rs.put("outTH",outTH);
        rs.put("address",devAdd);
        rs.put("outTHs",outTHl);
        rs.put("innerTHs",thl);
        rs.put("V",V);
        rs.put("date",js);
        return rs;
    }

    //解析电压
    double parseV(String voltage) {
        int v1 = BytesUtil.hexToInt(voltage);
        double v2 = (double) v1 / 1000;
        System.err.println(v2);
        return v2;
    }

    //解析温湿度点具体数据
    JSONArray parseTHPoint(String ths) {
        JSONArray ja = new JSONArray();
        String indexH = null;
        String indexT = null;
        for (int i = 0; i < ths.length(); i += 6) {
            indexH = ths.substring(i, i + 2);
            indexT = ths.substring(i + 2, i + 6);
            JSONObject jo = new JSONObject();
            jo.put("indexH", (double) BytesUtil.hexToInt(indexH));
            jo.put("indexT", ((double) BytesUtil.hexToInt(indexT) / 10));
            ja.add(jo);
        }
        System.err.println(ja);
        return ja;
    }


    //解析测温具体数据
    List<Double> parseTemps(String temps) {
        List<Double> list = new ArrayList<>();
        String index;
        for (int i = 0; i < temps.length(); i += 4) {
            //高低转换
            index = BytesUtil.tran_LH(temps.substring(i, i + 4));
            int rs = BytesUtil.hexToInt(index);
            if(rs==-1){
                continue;
            }
            list.add(rs * 0.0625);
        }
//        for (Double d : list
//        ) {
//            System.err.println("温度" + d);
//        }
        return list;
    }


    private JSONArray grainList2JsonArray(List<GrainInfo> grains) {

        JSONArray js = new JSONArray();
        double maxTemp = 0;
        double minTemp = 0;
        double avgTemp = 0;
        double index = 0;
        for (GrainInfo g : grains
        ) {
            if (maxTemp == 0) {
                maxTemp = g.getInnerTemp();
            }
            if (minTemp == 0) {
                minTemp = g.getInnerTemp();
            }
            if (maxTemp < g.getInnerTemp()) {
                maxTemp = g.getInnerTemp();
            }
            if (minTemp > g.getInnerTemp() && g.getInnerTemp() != ERROR_TEMP_TAG) {
                minTemp = g.getInnerTemp();
            }
            if (g.getInnerTemp() != ERROR_TEMP_TAG) {
                avgTemp += g.getInnerTemp();
                index += 1;
            }
            GrainVO vo = new GrainVO();
            vo.setX(g.getX());
            vo.setY(g.getY());
            vo.setZ(g.getZ());
            vo.setTemp(g.getInnerTemp());
            js.add(vo);
        }
       return js;
    }

                                                //x     y     z
    List<GrainInfo> parseTemp2GrainInfo(List<Double> temps,int j,int k,int l,Date receiveDate,Depot depot){
        List<GrainInfo> grains = new ArrayList();
        GrainInfo info = null;
        int x = 0, y = 0, z = 0, fz = 0;
        for (int i = 0; i < temps.size(); ++i) {
            z = i % l + 1;//z = 0,1,2,3,4
            fz = z;
            x = i / (l * k);
            y = x * l * k;
            y = (i - y) / l;
            x = j - 1 - x;
            if (temps.get(i) != ERROR_TEMP_TAG) {
                //存放 接收时间 depotId temp x,y,z
                info = new GrainInfo(ContextUtil.getTimeYMDHMM(receiveDate), depot.getId(), temps.get(i), x, y, z);
            } else {
                if (i > 0 && temps.get(i - 1) != ERROR_TEMP_TAG) {
                    info = new GrainInfo(ContextUtil.getTimeYMDHMM(receiveDate), depot.getId(), temps.get(i - 1), x, y, z);
                } else {
                    info = new GrainInfo(ContextUtil.getTimeYMDHMM(receiveDate), depot.getId(), temps.get(i), x, y, z);
                }
//                log.warning("仓库" + depot.getId() + "粮情检测点出现故障，系统坐标X=" + x + ",Y=" + y + ",Z=" + z);
            }
            grains.add(info);
        }
        return grains;
    }
}
