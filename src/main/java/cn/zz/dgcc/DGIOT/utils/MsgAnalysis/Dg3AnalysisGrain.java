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

    private static final Dg3AnalysisGrain Dg3AnalysisGrain = new Dg3AnalysisGrain();

    public static Dg3AnalysisGrain newInstance() {
        return Dg3AnalysisGrain;
    }

    private final Logger log = Logger.getLogger(Dg3AnalysisGrain.class.getName());
    private int DEFAULT_X = 7;
    private int DEFAULT_Z = 4;
    private int DEFAULT_Y = 6;
    public static double TEMP_LIMIT_MAX = 0.0D;
    public static double ERROR_TEMP_TAG = -250.0D;
    public static double TEMP_LIMIT_MIN = 0.0D;


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

        if (this.DEFAULT_X * this.DEFAULT_Y * this.DEFAULT_Z <= 512 && msg.startsWith("AAB1")) {
            //采集点没有达到第二包的数据量，直接舍弃第二包数据
            log.info("采集点没有达到第二包的数据量，直接舍弃第二包数据");
            return null;
        }
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
        String innerTH = msg.substring(2020, 2020 + l * 6);
        for (int i = 0; i < innerTH.length(); i += 6) {
            String strH = innerTH.substring(i, i + 2);
            String strT = innerTH.substring(i + 2, i + 6);
            innH += Integer.parseInt(strH, 16);
            innT += Integer.parseInt(strT, 16);
            num = i + 1;
        }
        double finalInnH = innH / num;
        double finalInnT = innT / num;


//        Date date = new Date();
        String batchId = grain.getBatchId();
        Date date = grain.getreceivedTime();
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

    private JSONObject grainList2JsonArray(List<GrainInfo> grains, Depot depot, String batchId, double finalInnH, double finalInnT) {
        JSONObject rs = new JSONObject();
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
}
