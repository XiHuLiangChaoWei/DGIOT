package cn.zz.dgcc.DGIOT.utils.MsgAnalysis;

import cn.zz.dgcc.DGIOT.VO.N2VO;
import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.entity.Device;
import cn.zz.dgcc.DGIOT.entity.N2;
import cn.zz.dgcc.DGIOT.mapper.DepotDevMapper;
import cn.zz.dgcc.DGIOT.service.DeviceService;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.BytesUtil;
import com.alibaba.fastjson.JSONObject;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by: LT001
 * Date: 2020/5/22 10:20
 * ClassExplain :
 * ->
 */
@Component
public class Dg4AnalysisN2 {
    @Autowired
    DeviceService deviceService;
    @Resource
    DepotDevMapper depotDevMapper;

    private Dg4AnalysisN2() {
    }

//    public static void main(String[] args) {
//       String str = "AA55E4030501000214061E153431FFFFFFFFFFFF000000C8FFFFF5F505000000000001000200D3027400010A00EBFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF01FCFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0A000000000000000000000100024899EFEF";
//       N2 n = new N2();
//       n.setContent(str);
//
//      dg4AnalysisN2.analysisN2Info(n,"1",new Depot()).toJSONString();
//    }

    private static final Dg4AnalysisN2 dg4AnalysisN2 = new Dg4AnalysisN2();

    public static Dg4AnalysisN2 newInstance() {
        return dg4AnalysisN2;
    }

    private final Logger log = Logger.getLogger(Dg4AnalysisN2.class.getName());

    public static void main(String[] args) {
        N2 n = new N2();
        n.setContent("AA55E4030B010000140703111A06FFFFFFFFFFFF000000C8FFFF01F305000000000001000000CE01C900010AFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0A000000000000000000000100020CC0EFEF");
        String devName = "qt001";
        Depot depot = new Depot();
        JSONObject j = newInstance().analysisN2Info(n, devName, depot);
        System.out.println(j);
    }

    public JSONObject analysisN2Info(N2 n2, String devName, Depot depot) {
        String msg = n2.getContent();
        log.info("开始解析N2信息······");
        String devBH = msg.substring(8, 10);
        String devZH = msg.substring(10, 12);
        String devStatus = msg.substring(12, 14);
        String model = msg.substring(14, 16);
        String QTstart = msg.substring(16, 28);
        String qimixing = msg.substring(28, 40);
        String banshuaiqi = msg.substring(40, 44);
        String liangcheng = msg.substring(44, 48);
        String pressureDifferent = msg.substring(52, 56);
//        System.err.println(pressureDifferent);
        String valveCount = msg.substring(56, 58);
        //解析电动阀门数量
        int valveNum = Integer.valueOf(valveCount, 16);
        int valveEnd = (valveNum) * 2 + 58;
        String valveStatus = msg.substring(58, valveEnd);

        int fanStart = valveEnd;
        String fanCount = msg.substring(fanStart, fanStart + 2);
        //解析风机数量
        int fanNum = Integer.valueOf(fanCount, 16);
        int fanEnd = (fanNum) * 2 + (fanStart + 2);
        String fanStatus = msg.substring(fanStart + 2, fanEnd);
        //测气状态
        int statusS = fanEnd;
        int statusE = statusS + 2;
        String status = msg.substring(statusS, statusE);
        //实时氧气
        int realTimeO2S = statusE;
        int realTimeO2E = realTimeO2S + 4;
        String realTimeO2 = msg.substring(realTimeO2S, realTimeO2E);
        //实时二氧化碳
        int realTimeCO2S = realTimeO2E;
        int realTimeCO2E = realTimeCO2S + 4;
        String realTimeCO2 = msg.substring(realTimeCO2S, realTimeCO2E);
        //正在测气通道
        int passwayNowS = realTimeCO2E;
        int passwayNowE = passwayNowS + 2;
        String passwayNow = msg.substring(passwayNowS, passwayNowE);
        //测气开始通道
        int passwayStartS = passwayNowE;
        int passwayStartE = passwayStartS + 2;
        String passwayStart = msg.substring(passwayStartS, passwayStartE);
        //测气结束通道
        int passwayEndS = passwayStartE;
        int passwayEndE = passwayEndS + 2;
        String passwayEnd = msg.substring(passwayEndS, passwayEndE);
        //各个通道的O2浓度
        int passwayO2S = passwayEndE;
        int passwayStart1 = Integer.valueOf(passwayStart, 16);
        int passwayEnd1 = Integer.valueOf(passwayEnd, 16);
        int O2Num = passwayEnd1 - passwayStart1;
        int passwayO2E = passwayO2S + 4 * (O2Num + 1);
        String O2 = msg.substring(passwayO2S, passwayO2E);
        //各个通道CO2浓度
        int passwayCO2S = passwayO2E;
        int passwayCO2E = passwayCO2S + 4 * (O2Num + 1);
        String CO2 = msg.substring(passwayCO2S, passwayCO2E);
        int qiufaStart = passwayCO2E;
        int qiufaEnd = qiufaStart + 2;
        String qiufaNum = msg.substring(qiufaStart, qiufaEnd);

        int qiufaStatusStart = qiufaEnd;
        int qiufaStatusEnd = qiufaStatusStart + Integer.valueOf(qiufaNum, 16) * 2;
        String qiufaStatus = msg.substring(qiufaStatusStart, qiufaStatusEnd);
        int qibengStart = qiufaStatusEnd;
        int qibengEnd = qibengStart + 2;
        String qibengNum = msg.substring(qibengStart, qibengEnd);
        int qibengStatusStart = qibengEnd;
        int qibangStatusEnd = qibengStatusStart + Integer.valueOf(qibengNum, 16) * 2;
        String qibengStatus = msg.substring(qibengStatusStart, qibangStatusEnd);

        int chouqiStart = qibangStatusEnd;
        int chouqiEnd = chouqiStart + 2;
        String chouqiTime = msg.substring(chouqiStart, chouqiEnd);
        //CRC
        int CRCS = chouqiEnd;
        int CRCE = CRCS + 4;
        String CRC = msg.substring(CRCS, CRCE);

//        String devNote = depot.getDevNote();
//        Device dev = deviceService.getDevByBHAndZH(devBH,devZH,2);
//        String devNote = dev.getDevNote();

        devStatus = devStatus.equals("01") ? "远程" : devStatus.equals("00") ? "本地" : null;

        //拼装解析信息
        N2VO n2VO = new N2VO();
        {
            n2VO.setDepotId(depot.getDepotId());//设置
//            n2VO.setDevNote(devNote);
            n2VO.setDevBH(Integer.parseInt(devBH, 16));
            n2VO.setDevZH(Integer.parseInt(devZH));
            n2VO.setModel(getModel(model));   //设置模式
            n2VO.setDevStatus(devStatus);
            n2VO.setQTstartTime(parseTimes(QTstart)); //设置气调启动时间
            n2VO.setQMJCtime(parseTimes(qimixing));//设置上一次气密性检测时间

            n2VO.setHalfTime(Hex2Int2(banshuaiqi));//设置半衰期时间

            n2VO.setPressureDif(Hex2Int(pressureDifferent));//内外压差

            n2VO.setLiangcheng(Hex2Int(liangcheng));//量程
            n2VO.setValveNum(valveNum);//阀门数量
            n2VO.setValveStatus(parseStatus(valveStatus, valveNum));//阀门状态
            n2VO.setFanNum(fanNum); //风机数量
            n2VO.setFanStatus(parseStatus(fanStatus, fanNum));//风机状态

            n2VO.setCLStatus(setStatus(status));//测气状态

            n2VO.setRealTimeO2(parseCC(realTimeO2) / 10);//氧气浓度
            n2VO.setRealTimeN2((990 - parseCC(realTimeO2)) / 10);//N2浓度
            n2VO.setRealTimeCO2(parseCC(realTimeCO2));//CO2浓度


            n2VO.setRunning(getNumFromMsg(passwayNow));//正在测气通道号
            n2VO.setStartWay(getNumFromMsg(passwayStart));
            n2VO.setEndWay(getNumFromMsg(passwayEnd));

            n2VO.setO2(parseCC(O2, O2Num + 1));
            n2VO.setCO2(parseCC(CO2, O2Num + 1));
            n2VO.setQiufaNum(Integer.parseInt(qiufaNum, 16));

            n2VO.setQiufaStatus(parseStatus(qiufaStatus, Integer.parseInt(qiufaNum, 16)));
            n2VO.setQibengNum(Integer.parseInt(qibengNum, 16));

            n2VO.setQibengStatus(parseStatus(qibengStatus, Integer.parseInt(qibengNum, 16)));
            n2VO.setChouqiTime(Integer.parseInt(chouqiTime, 16));
            n2VO.setDevName(devName);
//            n2VO.setDevNote(devNote);
        }
        JSONObject jo = new JSONObject();
        String bactchId = n2.getBatchId();
        jo.put("batchId", bactchId);
        jo.put("气调信息", n2VO);
        log.info("拼装N2信息------------------------------");
        log.info(jo.toJSONString());
        log.info("拼装完成--------------------------------");
        return jo;
    }

    private String Hex2Int2(String msg) {
        int index = Integer.parseInt(msg, 16);
        return String.valueOf(index);
    }


    private String Hex2Int(String msg) {
        int index = Integer.parseInt(msg, 16) - 500;
        return String.valueOf(index);
    }


    private int getModel(String msg) {
        if ("FF".equals(msg)) {
            msg = "00";
        }
        return Integer.parseInt(msg);
    }

    /**
     * 解析O2和CO2
     *
     * @param msg
     * @param num
     * @return
     */
    private List<Double> parseCC(String msg, int num) {
        List<Double> rs = new ArrayList<>();
        if (msg.length() != num * 4) {
            throw new RuntimeException("气体浓度解析出错，和通道数不匹配");
        }

        String index = null;
        for (int i = 0; i < msg.length(); i += 4) {
            index = msg.substring(i, i + 4);
            double a = parseCC(index);
            rs.add(a);
        }

        return rs;
    }

    /**
     * 直接获取10进制数
     *
     * @param msg
     * @return
     */
    private int getNumFromMsg(String msg) {
        return Integer.parseInt(msg, 16);
    }

    /**
     * 解析浓度
     *
     * @param msg
     * @return
     */
    private double parseCC(String msg) {
        if ("FFFF".equals(msg)) {
            return 0;
        }
        int a = Integer.parseInt(msg, 16);
//        log.info("解析中···a="+a);
        return Integer.parseInt(msg, 16);
    }


    /**
     * 获取状态值，应该可以和getNumFromMsg通用
     *
     * @param msg
     * @return
     */
    private int setStatus(String msg) {
        return Integer.parseInt(msg, 16);
    }

    /**
     * 解析6byte时间信息
     *
     * @param time
     * @return
     */
    private String parseTimes(String time) {
        if (time.length() > 12) {
            throw new RuntimeException("解析时间数据时，长度出错");
        }
        if ("FFFFFFFFFFFF".equals(time)) {
            return time;
        }
        StringBuffer rs = new StringBuffer();
        String index = null;
        String[] t = new String[]{"年", "月", "日", "时", "分", "秒"};
        int x = 0;
        for (int i = 0; i < time.length(); i += 2) {
            index = time.substring(i, i + 2);
            x = Integer.parseInt(index, 16);
            index = ContextUtil.FormatNum(x, 2);
            rs.append(index + t[i / 2]);
        }
        return rs.toString();
    }

    /**
     * 解析阀门/风机 状态
     *
     * @param msg 状态字符串
     * @param num 设备数量
     * @return
     */
    private List<Integer> parseStatus(String msg, int num) {
        List<Integer> rs = new ArrayList<>();
        String index = null;
        int a = 0;
        if (msg.length() != num * 2) {
            throw new RuntimeException("解析氮气信息出错，阀门/风机数量与状态信息不匹配");
        }
        for (int i = 0; i < msg.length(); i += 2) {
            index = msg.substring(i, i + 2);
            if (index.equals("01")) {
                a = 1;
                rs.add(a);
            } else if (index.equals("00")) {
                a = 0;
                rs.add(a);
            }
        }
        return rs;
    }
}
