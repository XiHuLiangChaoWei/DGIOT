package cn.zz.dgcc.DGIOT.VO;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.List;

/**
 * int
 * Created by: YYL
 * Date: 2020/4/26 10:05
 * ClassExplain :
 * ->
 */
public class N2VO {
    int depotId;  //绑定仓库id
    String devNote;
    String devName;
    int devBH;       //设备编号
    int devZH;   //设备站号
    String devStatus;
    int model;   //气调模式
    String QTstartTime; //气调开始时间
    String QMJCtime;    //气密性检测时间
    String halfTime;    //半衰期
    String liangcheng;  //量程
    String pressureDif; //内外压差
    int valveNum;   //阀门数量
    List<Integer> valveStatus;  //阀门状态
    int fanNum; //风机数量
    List<Integer> fanStatus;//风机状态
    int CLStatus;    //测气状态 0-未测量 1-正在测量 2-测量完毕
    double realTimeO2;  //实时O2浓度
    double realTimeN2;  //由O2浓度换算来的N2浓度
    double realTimeCO2; //实时CO2浓度
    int running;     //正在测气通道
    int startWay;    //测气开始通道
    int endWay;      //测气结束通道
    List<Double> O2;   //O2浓度
    List<Double> CO2;  //CO2浓度
    int qiufaNum;
    int qibengNum;
    int chouqiTime;

    public int getChouqiTime() {
        return chouqiTime;
    }

    public void setChouqiTime(int chouqiTime) {
        this.chouqiTime = chouqiTime;
    }

    String CRC;

    public int getQiufaNum() {
        return qiufaNum;
    }

    public void setQiufaNum(int qiufaNum) {
        this.qiufaNum = qiufaNum;
    }

    public int getQibengNum() {
        return qibengNum;
    }

    public void setQibengNum(int qibengNum) {
        this.qibengNum = qibengNum;
    }

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public String getDevNote() {
        return devNote;
    }

    public void setDevNote(String devNote) {
        this.devNote = devNote;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
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

    public String getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public String getQTstartTime() {
        return QTstartTime;
    }

    public void setQTstartTime(String QTstartTime) {
        this.QTstartTime = QTstartTime;
    }

    public String getQMJCtime() {
        return QMJCtime;
    }

    public void setQMJCtime(String QMJCtime) {
        this.QMJCtime = QMJCtime;
    }

    public String getHalfTime() {
        return halfTime;
    }

    public void setHalfTime(String halfTime) {
        this.halfTime = halfTime;
    }

    public String getLiangcheng() {
        return liangcheng;
    }

    public void setLiangcheng(String liangcheng) {
        this.liangcheng = liangcheng;
    }

    public String getPressureDif() {
        return pressureDif;
    }

    public void setPressureDif(String pressureDif) {
        this.pressureDif = pressureDif;
    }

    public int getValveNum() {
        return valveNum;
    }

    public void setValveNum(int valveNum) {
        this.valveNum = valveNum;
    }

    public List<Integer> getValveStatus() {
        return valveStatus;
    }

    public void setValveStatus(List<Integer> valveStatus) {
        this.valveStatus = valveStatus;
    }

    public int getFanNum() {
        return fanNum;
    }

    public void setFanNum(int fanNum) {
        this.fanNum = fanNum;
    }

    public List<Integer> getFanStatus() {
        return fanStatus;
    }

    public void setFanStatus(List<Integer> fanStatus) {
        this.fanStatus = fanStatus;
    }

    public int getCLStatus() {
        return CLStatus;
    }

    public void setCLStatus(int CLStatus) {
        this.CLStatus = CLStatus;
    }

    public double getRealTimeO2() {
        return realTimeO2;
    }

    public void setRealTimeO2(double realTimeO2) {
        this.realTimeO2 = realTimeO2;
    }

    public double getRealTimeN2() {
        return realTimeN2;
    }

    public void setRealTimeN2(double realTimeN2) {
        this.realTimeN2 = realTimeN2;
    }

    public double getRealTimeCO2() {
        return realTimeCO2;
    }

    public void setRealTimeCO2(double realTimeCO2) {
        this.realTimeCO2 = realTimeCO2;
    }

    public int getRunning() {
        return running;
    }

    public void setRunning(int running) {
        this.running = running;
    }

    public int getStartWay() {
        return startWay;
    }

    public void setStartWay(int startWay) {
        this.startWay = startWay;
    }

    public int getEndWay() {
        return endWay;
    }

    public void setEndWay(int endWay) {
        this.endWay = endWay;
    }

    public List<Double> getO2() {
        return O2;
    }

    public void setO2(List<Double> o2) {
        O2 = o2;
    }

    public List<Double> getCO2() {
        return CO2;
    }

    public void setCO2(List<Double> CO2) {
        this.CO2 = CO2;
    }

    public String getCRC() {
        return CRC;
    }

    public void setCRC(String CRC) {
        this.CRC = CRC;
    }
}
