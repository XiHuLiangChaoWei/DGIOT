package cn.zz.dgcc.DGIOT.entity;

import java.util.Objects;

/**
 * Created by: YYL
 * Date: 2020/5/21 8:41
 * ClassExplain :
 * ->
 */
public class N2Info {
    String devBH;
    String devZH;
    String model;
    String QTstart;
    String qimixing;
    String banshuaiqi;
    String liangcheng;
    String pressuerDifferent;
    int valveNum;
    String valveStatus;
    int fanNum;
    String fanStatus;
    String status;
    String realTimeO2;
    String realTimeCO2;
    String passwayNow;
    String passwayStart;
    String passwayEnd;
    String O2;
    String CO2;
    String CRC;

    @Override
    public String toString() {
        return "N2Info{" +
                "devBH='" + devBH + '\'' +
                ", devZH='" + devZH + '\'' +
                ", model='" + model + '\'' +
                ", QTstart='" + QTstart + '\'' +
                ", qimixing='" + qimixing + '\'' +
                ", banshuaiqi='" + banshuaiqi + '\'' +
                ", liangcheng='" + liangcheng + '\'' +
                ", pressuerDifferent='" + pressuerDifferent + '\'' +
                ", valveNum=" + valveNum +
                ", valveStatus='" + valveStatus + '\'' +
                ", fanNum=" + fanNum +
                ", fanStatus='" + fanStatus + '\'' +
                ", status='" + status + '\'' +
                ", realTimeO2='" + realTimeO2 + '\'' +
                ", realTimeCO2='" + realTimeCO2 + '\'' +
                ", passwayNow='" + passwayNow + '\'' +
                ", passwayStart='" + passwayStart + '\'' +
                ", passwayEnd='" + passwayEnd + '\'' +
                ", O2='" + O2 + '\'' +
                ", CO2='" + CO2 + '\'' +
                ", CRC='" + CRC + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        N2Info n2Info = (N2Info) o;
        return valveNum == n2Info.valveNum &&
                fanNum == n2Info.fanNum &&
                Objects.equals(devBH, n2Info.devBH) &&
                Objects.equals(devZH, n2Info.devZH) &&
                Objects.equals(model, n2Info.model) &&
                Objects.equals(QTstart, n2Info.QTstart) &&
                Objects.equals(qimixing, n2Info.qimixing) &&
                Objects.equals(banshuaiqi, n2Info.banshuaiqi) &&
                Objects.equals(liangcheng, n2Info.liangcheng) &&
                Objects.equals(pressuerDifferent, n2Info.pressuerDifferent) &&
                Objects.equals(valveStatus, n2Info.valveStatus) &&
                Objects.equals(fanStatus, n2Info.fanStatus) &&
                Objects.equals(status, n2Info.status) &&
                Objects.equals(realTimeO2, n2Info.realTimeO2) &&
                Objects.equals(realTimeCO2, n2Info.realTimeCO2) &&
                Objects.equals(passwayNow, n2Info.passwayNow) &&
                Objects.equals(passwayStart, n2Info.passwayStart) &&
                Objects.equals(passwayEnd, n2Info.passwayEnd) &&
                Objects.equals(O2, n2Info.O2) &&
                Objects.equals(CO2, n2Info.CO2) &&
                Objects.equals(CRC, n2Info.CRC);
    }

    @Override
    public int hashCode() {
        return Objects.hash(devBH, devZH, model, QTstart, qimixing, banshuaiqi, liangcheng, pressuerDifferent, valveNum, valveStatus, fanNum, fanStatus, status, realTimeO2, realTimeCO2, passwayNow, passwayStart, passwayEnd, O2, CO2, CRC);
    }

    public String getDevBH() {
        return devBH;
    }

    public void setDevBH(String devBH) {
        this.devBH = devBH;
    }

    public String getDevZH() {
        return devZH;
    }

    public void setDevZH(String devZH) {
        this.devZH = devZH;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getQTstart() {
        return QTstart;
    }

    public void setQTstart(String QTstart) {
        this.QTstart = QTstart;
    }

    public String getQimixing() {
        return qimixing;
    }

    public void setQimixing(String qimixing) {
        this.qimixing = qimixing;
    }

    public String getBanshuaiqi() {
        return banshuaiqi;
    }

    public void setBanshuaiqi(String banshuaiqi) {
        this.banshuaiqi = banshuaiqi;
    }

    public String getLiangcheng() {
        return liangcheng;
    }

    public void setLiangcheng(String liangcheng) {
        this.liangcheng = liangcheng;
    }

    public String getPressuerDifferent() {
        return pressuerDifferent;
    }

    public void setPressuerDifferent(String pressuerDifferent) {
        this.pressuerDifferent = pressuerDifferent;
    }

    public int getValveNum() {
        return valveNum;
    }

    public void setValveNum(int valveNum) {
        this.valveNum = valveNum;
    }

    public String getValveStatus() {
        return valveStatus;
    }

    public void setValveStatus(String valveStatus) {
        this.valveStatus = valveStatus;
    }

    public int getFanNum() {
        return fanNum;
    }

    public void setFanNum(int fanNum) {
        this.fanNum = fanNum;
    }

    public String getFanStatus() {
        return fanStatus;
    }

    public void setFanStatus(String fanStatus) {
        this.fanStatus = fanStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRealTimeO2() {
        return realTimeO2;
    }

    public void setRealTimeO2(String realTimeO2) {
        this.realTimeO2 = realTimeO2;
    }

    public String getRealTimeCO2() {
        return realTimeCO2;
    }

    public void setRealTimeCO2(String realTimeCO2) {
        this.realTimeCO2 = realTimeCO2;
    }

    public String getPasswayNow() {
        return passwayNow;
    }

    public void setPasswayNow(String passwayNow) {
        this.passwayNow = passwayNow;
    }

    public String getPasswayStart() {
        return passwayStart;
    }

    public void setPasswayStart(String passwayStart) {
        this.passwayStart = passwayStart;
    }

    public String getPasswayEnd() {
        return passwayEnd;
    }

    public void setPasswayEnd(String passwayEnd) {
        this.passwayEnd = passwayEnd;
    }

    public String getO2() {
        return O2;
    }

    public void setO2(String o2) {
        O2 = o2;
    }

    public String getCO2() {
        return CO2;
    }

    public void setCO2(String CO2) {
        this.CO2 = CO2;
    }

    public String getCRC() {
        return CRC;
    }

    public void setCRC(String CRC) {
        this.CRC = CRC;
    }
}
