//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package cn.zz.dgcc.DGIOT.utils.MsgAnalysis;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import cn.zz.dgcc.DGIOT.VO.GrainVO;
import cn.zz.dgcc.DGIOT.entity.Depot;
import cn.zz.dgcc.DGIOT.entity.Grain;
import cn.zz.dgcc.DGIOT.entity.GrainInfo;
import cn.zz.dgcc.DGIOT.service.DepotService;
import cn.zz.dgcc.DGIOT.service.Exception.ISqlException;
import cn.zz.dgcc.DGIOT.service.GrainService;
import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.BytesUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 粮情协议解析
 */
@Component
public class Dg3AnalysisGrain {

    private Dg3AnalysisGrain() {
    }

    private static final Dg3AnalysisGrain dg3AnalysisGrain = new Dg3AnalysisGrain();

    public static Dg3AnalysisGrain newInstance() {
        return dg3AnalysisGrain;
    }

    private final Logger log = Logger.getLogger(Dg3AnalysisGrain.class.getName());
    private int DEFAULT_X = 7;
    private int DEFAULT_Z = 4;
    private int DEFAULT_Y = 6;
    public static double TEMP_LIMIT_MAX = 0.0D;
    public static double ERROR_TEMP_TAG = -250.0D;
    public static double TEMP_LIMIT_MIN = 0.0D;

//    public static void main(String[] args) throws Exception {
//       String str = "AAB0002E2F6131526E05E001DF01E101E301E301E301E301E001E201DF01DE01DF01E101E201DF01DF01E001DF01E201DE01DF01E201E001DE01DE01DF01DF01E001E101E201E101E301E401E101DD01E301E101E101E401E101DE01DF01E401E101E101E501DD01E101E201E101DF01E001E201E301E301E201E201DE01DE01E101E101E301E101E601E301E201DD01DF01E101E301E101E101E001DF01DF01DD01E101E101DF01E301E301E101E301E201DF01E101E101E101E001E001E201DF01E101DD01E201E001DF01DE01E201DD01E201E001E001E401DE01E201E201E101E301E401E001DE01E101E001DE01E101E301E101E301E301E401E201E201E301E201DE01DF01E201E201E301E001E001E301DF01E101E001E201E201E201E101DE01E001DE01E301DF01E001E201DF01E001E101E201E301E401E101E301E401E101E001E101E301E401E201E401DD01E401E201E301E101E201E101E101DC01E001DF01DF01E201E201E501E501E201E401E601E501E301E201E301E001E401E201E101E301E401E001E301E201E001E001E701E101E301FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF490136FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF0003379EEFEF";
//        String s = str.substring(2068,2068+6);
//        System.err.println(s);
//    }

    /**
     * 解析函数
     *
     * @param grain 粮情消息
     * @param depot 通过上传设备匹配的仓库信息
     * @return 一个json格式的单仓粮情解析数据 包含 仓库信息，和各测温点温度数据
     * @throws Exception
     */
    public JSONObject analysis(Grain grain, Depot depot) throws Exception {
        //取出仓库信息
        if (null != depot) {
            TEMP_LIMIT_MAX = Integer.valueOf(depot.getDesignMax()) == null ? 0.0D : depot.getDesignMax();
            TEMP_LIMIT_MIN = Integer.valueOf(depot.getDesignMin()) == null ? 0.0D : depot.getDesignMin();
            this.DEFAULT_X = depot.getXx();
            this.DEFAULT_Y = depot.getYy();
            this.DEFAULT_Z = depot.getZz();
        } else {
            return null;
        }
        String msg = grain.getContent();
        String body = msg.substring(10 * 2, 9 * 2 + 512 * 4);

//        if (this.DEFAULT_X * this.DEFAULT_Y * this.DEFAULT_Z <= 512 && msg.startsWith("AAB1")) {
//            //采集点没有达到第二包的数据量，直接舍弃第二包数据
//            log.info("采集点没有达到第二包的数据量，直接舍弃第二包数据");
//            return null;
//        }
//        if (this.DEFAULT_X * this.DEFAULT_Y * this.DEFAULT_Z > 512) {
//            //大于512个采集点需要分包
//            if (msg.startsWith("AAB0")) {
//                //第一包数据，放入缓存,等待第二包数据
//
//                Cache.setObjectValue("GRAIN_B0_" + ser.getId() + "_" + ser.getIp(), ser.getCompanyId(), body);
//                log.info("第一包数据放入缓存---");
//                return;
//            }
//            if (msg.startsWith("AAB1")) {
//                //第二包数据
//                String b0Str = (String) CacheUtils.getObjectValue("GRAIN_B0_" + ser.getId() + "_" + ser.getIp(), ser.getCompanyId());
//                log.info("缓存中获取第一包数据：" + b0Str);
//                if (StringUtils.isEmpty(b0Str)) {
//                    log.info("粮情第一包数据从缓存中没获取到！");
//                    return;
//                }
//                //删除缓存
//                CacheUtils.removeCacheObject("GRAIN_B0_" + ser.getId() + "_" + ser.getIp(), ser.getCompanyId());
//                //第一包数据加上第二包数据直接解析
//                body = b0Str.substring(9 * 2, 9 * 2 + 512 * 4) + body;
//                log.info("两包数据：" + body);
//            }
//
//        }

        List<Double> temps = new ArrayList();
        if (body.length() > this.DEFAULT_Y * this.DEFAULT_X * this.DEFAULT_Z * 4) {
            String temp = null;
            for (int i = 0; i < this.DEFAULT_Y * this.DEFAULT_Z * this.DEFAULT_X; ++i) {
                temp = body.substring(i * 4, i * 4 + 4);
                if (temp == null) {
                    temp = "0000";
                }
                if ("FFFF".equals(temp)) {
                    //代表异常或者没数据,解析为异常数据
                    temps.add(ERROR_TEMP_TAG);
                } else {
                    temps.add(Double.valueOf(new DecimalFormat("0.0").format(BytesUtil.hexToInt(tran_LH(temp)) * 0.0625)));
                }

            }
        }
        System.err.println(temps);
        String start_ = depot.getStart();
        String end_ = depot.getEnd();
        int l = Integer.parseInt(start_) - Integer.parseInt(end_) + 1;
        double innH = 0;
        double innT = 0;
        int num = 0;
        String innerTH = msg.substring(2068, 2068 + l * 6);
        for (int i = 0; i < innerTH.length(); i += 6) {
            String strH = innerTH.substring(i, i + 2);
            String strT = innerTH.substring(i + 2, i + 6);
            innH += Integer.parseInt(strH, 16);
            innT += Integer.parseInt(strT, 16);
            num = i + 1;
        }
        double finalInnH = innH / num;
        double finalInnT = innT / num / 10;


//        Date date = new Date();
        String batchId = grain.getBatchId();
        Date date = grain.getreceivedTime();
        rs.put("recTime", grain.getreceivedTime());
        return this.addPoint(temps, date, depot, batchId, finalInnH, finalInnT);
    }

    public static String tran_LH(String info) {
        return info.substring(2) + info.substring(0, 2);
    }


    private JSONObject addPoint(List<Double> temps, Date receiveDate, Depot depot, String batchId, double finalInnH, double finalInnT) throws Exception {
        List<GrainInfo> grains = new ArrayList();
        GrainInfo info = null;
        int x = 0, y = 0, z = 0, fz = 0;
        for (int i = 0; i < temps.size(); ++i) {
            z = i % this.DEFAULT_Z + 1;//z = 0,1,2,3,4
//             fz = this.DEFAULT_Z - z;  //fz = 4,3,2,1,0
            fz = z;
            x = i / (this.DEFAULT_Z * this.DEFAULT_Y);
            y = x * this.DEFAULT_Z * this.DEFAULT_Y;
            y = (i - y) / this.DEFAULT_Z;
            x = this.DEFAULT_X - 1 - x;
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
        JSONObject jsonResult = grainList2JsonArray(grains, depot, batchId, finalInnH, finalInnT);
        this.log.info("粮情解析成功:" + receiveDate.toString());
//        String j = jsonResult.toJSONString();
//        log.info(j);
        return jsonResult;
    }

    JSONObject rs = new JSONObject();

    private JSONObject grainList2JsonArray(List<GrainInfo> grains, Depot depot, String batchId, double finalInnH, double finalInnT) {

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
        avgTemp = avgTemp / index;
        rs.put("batchId", batchId);
        rs.put("depotId", depot.getDepotId());
        rs.put("devNote", depot.getDevNote());
        rs.put("keeper", depot.getKeeper());
        rs.put("phone", depot.getPhone());
        rs.put("x", depot.getXx());
        rs.put("y", depot.getYy());
        rs.put("z", depot.getZz());
        rs.put("designMax", depot.getDesignMax());
        rs.put("designMin", depot.getDesignMin());
        rs.put("maxTemp", maxTemp);
        rs.put("minTemp", minTemp);
        rs.put("avgTemp", avgTemp);
        rs.put("date", js);
        rs.put("innerH", finalInnH);
        rs.put("InnerTemp", finalInnT);
        return rs;
    }

    public static void main(String[] args) {
        String s = "AA B0 FF FF FF FF FF FF 00 01 29 01 2C 01 59 01 63 01 28 01 26 01 40 01 22 01 28 01 1F 01 3E 01 07 01 2B 01 27 01 3E 01 06 01 1D 01 23 01 43 01 12 01 1C 01 20 01 4C 01 25 01 28 01 2A 01 45 01 1D 01 25 01 27 01 29 01 F5 00 25 01 20 01 2F 01 D4 00 25 01 1D 01 2E 01 D6 00 1C 01 1C 01 29 01 D9 00 1F 01 1F 01 33 01 ED 00 22 01 26 01 48 01 19 01 26 01 27 01 32 01 CB 00 2D 01 20 01 2E 01 C1 00 1C 01 21 01 2D 01 BD 00 19 01 1A 01 2E 01 C5 00 23 01 21 01 3B 01 E9 00 32 01 2F 01 51 01 28 01 2F 01 28 01 30 01 CE 00 26 01 2A 01 31 01 BC 00 1A 01 24 01 2E 01 BD 00 1D 01 25 01 2C 01 D5 00 26 01 23 01 40 01 FC 00 31 01 2D 01 52 01 2E 01 28 01 24 01 2E 01 CF 00 25 01 26 01 29 01 BF 00 24 01 19 01 24 01 FB 00 24 01 2A 01 27 01 BB 00 23 01 27 01 3C 01 F8 00 27 01 2F 01 49 01 28 01 2D 01 2A 01 2E 01 CD 00 24 01 27 01 28 01 BE 00 28 01 25 01 28 01 B1 00 24 01 22 01 2B 01 B5 00 24 01 1B 01 37 01 F5 00 30 01 29 01 4A 01 42 01 2A 01 29 01 31 01 C9 00 2F 01 1F 01 29 01 C0 00 2A 01 26 01 23 01 AE 00 1D 01 24 01 24 01 AA 00 22 01 24 01 3B 01 05 01 2C 01 34 01 50 01 1C 01 27 01 27 01 26 01 C8 00 20 01 28 01 26 01 BE 00 2A 01 21 01 2B 01 B2 00 23 01 23 01 27 01 B4 00 22 01 25 01 3C 01 EF 00 22 01 23 01 46 012E 01 2A 01 26 01 34 01 D6 00 26 01 1F 01 31 01 C1 00 22 01 23 01 30 01 B5 00 25 01 22 01 2E 01 B2 00 22 01 25 01 41 01 FF 00 2F 01 29 01 50 01 42 01 28 01 24 01 2D 01 D8 00 26 01 28 01 2D 01 C6 00 28 01 29 01 2B 01 C0 00 22 01 24 01 2E 01 BB 00 25 01 2B 01 3F 01 10 01 11 01 09 01 4A 01 4F 01 02 01 0D 01 23 01 FB 00 0B 01 11 01 1D 01 F6 00 00 01 05 01 1B 01 EF 00 FD 00 F5 00 14 01 FD 00 F6 00 FD 00 25 01 04 01 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 00 02 56 CB EF EF";
        String msg = s.replace(" ","");
        String body = msg.substring(10 * 2, 9 * 2 + 512 * 4);

        List<Double> temps = new ArrayList();

            String temp = null;

            for (int i = 0; i < 200; ++i) {
                temp = body.substring(i * 4, i * 4 + 4);

                if (temp == null) {
                    temp = "0000";
                }
                if ("FFFF".equals(temp)) {
                    //代表异常或者没数据,解析为异常数据
                    temps.add(ERROR_TEMP_TAG);
                } else {
                    temps.add(Double.valueOf(new DecimalFormat("0.0").format(BytesUtil.hexToInt(tran_LH(temp)) * 0.0625)));
                }

            }
        System.out.println(temps);

        }

    }

