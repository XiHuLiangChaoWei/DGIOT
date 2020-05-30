package cn.zz.dgcc.DGIOT.entity;

import java.lang.reflect.Field;
import java.util.Objects;

/**
 * Created by: YYL
 * Date: 2020/5/28 9:01
 * ClassExplain :
 * ->
 */
public class QTConfigure {
    private String devName;
    private String devId;
    private String commondType;
    private int devBH;
    private int devZH;
    private int type;
    private int busType;
    private int dieFFK;
    private int hlfjFk;
    private int dieFTime;
    private int n2NDUpper;
    private int n2NDLower;
    private int n2FYUpper;
    private int n2FYLower;
    private int n2CQTime;
    private int timeInterval;
    private int cycleMeasure;
    private int airTightness;
    private int startCH;
    private int endCH;
    private int cqTime;

    public QTConfigure(String devName, String devId, String commondType, int devBH, int devZH, int type, int busType, int dieFFK, int hlfjFk, int dieFTime, int n2NDUpper, int n2NDLower, int n2FYUpper, int n2FYLower, int n2CQTime, int timeInterval, int cycleMeasure, int airTightness, int startCH, int endCH, int cqTime) {
        this.devName = devName;
        this.devId = devId;
        this.commondType = commondType;
        this.devBH = devBH;
        this.devZH = devZH;
        this.type = type;
        this.busType = busType;
        this.dieFFK = dieFFK;//蝶阀反馈 0-无反馈 1-有反馈
        this.hlfjFk = hlfjFk;//轴流风机反馈  0-无反馈 1-有反馈
        this.dieFTime = dieFTime;// 蝶阀时间
        this.n2NDUpper = n2NDUpper;// 充气浓度上限 %
        this.n2NDLower = n2NDLower;// 充气浓度下限%
        this.n2FYUpper = n2FYUpper;// 负压上限
        this.n2FYLower = n2FYLower;// 负压下限
        this.n2CQTime = n2CQTime;//充气时间 min
        this.timeInterval = timeInterval;//氮气浓度监测时间 间隔
        this.cycleMeasure = cycleMeasure;//循环测试标记
        this.airTightness = airTightness;//气密性半衰气压力值
        this.startCH = startCH;// 开始通道
        this.endCH = endCH;// 结束通道
        this.cqTime = cqTime;// 抽气时间
    }

    public QTConfigure() {

    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getCommondType() {
        return commondType;
    }

    public void setCommondType(String commondType) {
        this.commondType = commondType;
    }

    public int getDevBH() {
        return devBH;
    }

    public void setDevBH(int devBH) {
        this.devBH = devBH;
    }

    public int getDevZH() {
        return devZH;
    }

    public void setDevZH(int devZH) {
        this.devZH = devZH;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getBusType() {
        return busType;
    }

    public void setBusType(int busType) {
        this.busType = busType;
    }

    public int getDieFFK() {
        return dieFFK;
    }

    public void setDieFFK(int dieFFK) {
        this.dieFFK = dieFFK;
    }

    public int getHlfjFk() {
        return hlfjFk;
    }

    public void setHlfjFk(int hlfjFk) {
        this.hlfjFk = hlfjFk;
    }

    public int getDieFTime() {
        return dieFTime;
    }

    public void setDieFTime(int dieFTime) {
        this.dieFTime = dieFTime;
    }

    public int getN2NDUpper() {
        return n2NDUpper;
    }

    public void setN2NDUpper(int n2NDUpper) {
        this.n2NDUpper = n2NDUpper;
    }

    public int getN2NDLower() {
        return n2NDLower;
    }

    public void setN2NDLower(int n2NDLower) {
        this.n2NDLower = n2NDLower;
    }

    public int getN2FYUpper() {
        return n2FYUpper;
    }

    public void setN2FYUpper(int n2FYUpper) {
        this.n2FYUpper = n2FYUpper;
    }

    public int getN2FYLower() {
        return n2FYLower;
    }

    public void setN2FYLower(int n2FYLower) {
        this.n2FYLower = n2FYLower;
    }

    public int getN2CQTime() {
        return n2CQTime;
    }

    public void setN2CQTime(int n2CQTime) {
        this.n2CQTime = n2CQTime;
    }

    public int getTimeInterval() {
        return timeInterval;
    }

    public void setTimeInterval(int timeInterval) {
        this.timeInterval = timeInterval;
    }

    public int getCycleMeasure() {
        return cycleMeasure;
    }

    public void setCycleMeasure(int cycleMeasure) {
        this.cycleMeasure = cycleMeasure;
    }

    public int getAirTightness() {
        return airTightness;
    }

    public void setAirTightness(int airTightness) {
        this.airTightness = airTightness;
    }

    public int getStartCH() {
        return startCH;
    }

    public void setStartCH(int startCH) {
        this.startCH = startCH;
    }

    public int getEndCH() {
        return endCH;
    }

    public void setEndCH(int endCH) {
        this.endCH = endCH;
    }

    public int getCqTime() {
        return cqTime;
    }

    public void setCqTime(int cqTime) {
        this.cqTime = cqTime;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("{");
        if (devName != null) {
            sb.append("\"devName\":\"" + devName + "\"");
        }
        if (devId != null) {
            sb.append(",\"devId\":\"" + devId + "\"");
        }
        if (commondType != null) {
            sb.append(",\"commond type\":\"" + commondType + "\"");
        }
        if (devBH != 0) {
            sb.append(",\"dev BH\":" + devBH);
        }
        if (devZH != 0) {
            sb.append(",\"dev ZH\":" + devZH);
        }
        if (type != 0) {
            sb.append(",\"type\":" + type);
        }
        if (busType != 0) {
            sb.append(",\"busType\":" + busType);
        }
        if (dieFFK != 0) {
            sb.append(",\"DieF FK\":" + dieFFK);
        }
        if (hlfjFk != 0) {
            sb.append(",\"HLFJ FK\":" + hlfjFk);
        }
        if (dieFTime != 0) {
            sb.append(",\"DieF Time\":" + dieFTime);
        }
        if (n2NDUpper != 0) {
            sb.append(",\"N2 ND Upper\":" + n2NDUpper);
        }
        if (n2NDLower != 0) {
            sb.append(",\"N2 ND lower\":" + n2NDLower);
        }
        if (n2FYUpper != 0) {
            sb.append(",\"N2 FY Upper\":" + n2FYUpper);
        }
        if (n2FYLower != 0) {
            sb.append(",\"N2 FY lower\":" + n2FYLower);
        }
        if (n2CQTime != 0) {
            sb.append(",\"N2 Cq Time\":" + n2CQTime);
        }
        if (timeInterval != 0) {
            sb.append(",\"N2 Time interval\":" + timeInterval);
        }
        if (cycleMeasure != 0) {
            sb.append(",\"N2 Cycle measure\":" + cycleMeasure);
        }
        if (airTightness != 0) {
            sb.append(",\"Air tightness\":" + airTightness);
        }
        if (startCH != 0) {
            sb.append(",\"Sta Ch\":" + startCH);
        }
        if (endCH != 0) {
            sb.append(",\"End Ch\":" + endCH);
        }
        if (cqTime != 0) {
            sb.append(",\"CQ Time\":" + cqTime);
        }
        sb.append("}");


        return sb.toString();
//                "{\"devName\":\" " + devName +
//                "\",\"devId\":\"" + devId +
//                "\",\"commond type\":\"" + commondType +
//                "\",\"dev BH\":" + devBH +
//                ",\"dev ZH\":" + devZH +
//                ",\"type\":" + type +
//                ",\"busType\":" + busType +
//                ",\"DieF FK\":" + dieFFK +
//                ",\"ZLFJ FK\":" + zlfjFk +
//                ",\"DieF Time\":" + dieFTime +
//                ",\"N2 ND Upper\":" + n2NDUpper +
//                ",\"N2 ND lower\":" + n2NDLower +
//                ",\"N2 FY Upper\":" + n2FYUpper +
//                ",\"N2 FY lower\":" + n2FYLower +
//                ",\"N2 Cq Time\" " + n2CQTime +
//                ",\"N2 Time interval\" " + timeInterval +
//                ",\"N2 Cycle measure\":" + cycleMeasure +
//                ",\"Air tightness\" " + airTightness +
//                ",\"Sta Ch\":" + startCH +
//                ",\"End Ch\":" + endCH +
//                ",\"CQ Time\" " + cqTime + "}";
    }
}
