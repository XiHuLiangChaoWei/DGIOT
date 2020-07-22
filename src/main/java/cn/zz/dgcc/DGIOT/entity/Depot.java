package cn.zz.dgcc.DGIOT.entity;

import cn.zz.dgcc.DGIOT.utils.ContextUtil;

import java.util.Date;
import java.util.Objects;

/**
 * Created by: LT001
 * Date: 2020/5/14 15:38
 * ClassExplain :
 * ->
 */
public class Depot {
    int id;
    int depotId;
    int companyId;
    String devNote;//可以用来查询匹配的云端设备列表
    String start;
    String end;
    int xx;
    int yy;
    int zz;
    double innerTemp;
    double innerH;
    double outTemp;
    String mqttInfo;
    int designMax;
    int designMin;
    String keeper;
    String phone;
    String address;
    String maxTemp;
    String minTemp;
    String avgTemp;
    int QTStatus;
    int model;
    Date grainUpTime;
    String depotType;


    Date inTime;
    String grainType;
    String grainNum;
    String inHumidity;
    String nowHumidity;
    String tester;

    String grainUpTimeFormat;
    String inTimeFormat;

    public Depot(){

    }

    public Depot(int companyId,int depotId){
        this.companyId = companyId;
        this.depotId = depotId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Depot depot = (Depot) o;
        return id == depot.id &&
                depotId == depot.depotId &&
                companyId == depot.companyId &&
                xx == depot.xx &&
                yy == depot.yy &&
                zz == depot.zz &&
                Double.compare(depot.innerTemp, innerTemp) == 0 &&
                Double.compare(depot.innerH, innerH) == 0 &&
                Double.compare(depot.outTemp, outTemp) == 0 &&
                designMax == depot.designMax &&
                designMin == depot.designMin &&
                QTStatus == depot.QTStatus &&
                model == depot.model &&
                Objects.equals(devNote, depot.devNote) &&
                Objects.equals(start, depot.start) &&
                Objects.equals(end, depot.end) &&
                Objects.equals(mqttInfo, depot.mqttInfo) &&
                Objects.equals(keeper, depot.keeper) &&
                Objects.equals(phone, depot.phone) &&
                Objects.equals(address, depot.address) &&
                Objects.equals(maxTemp, depot.maxTemp) &&
                Objects.equals(minTemp, depot.minTemp) &&
                Objects.equals(avgTemp, depot.avgTemp) &&
                Objects.equals(grainUpTime, depot.grainUpTime) &&
                Objects.equals(depotType, depot.depotType) &&
                Objects.equals(inTime, depot.inTime) &&
                Objects.equals(grainType, depot.grainType) &&
                Objects.equals(grainNum, depot.grainNum) &&
                Objects.equals(inHumidity, depot.inHumidity) &&
                Objects.equals(nowHumidity, depot.nowHumidity) &&
                Objects.equals(tester, depot.tester);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depotId, companyId, devNote, start, end, xx, yy, zz, innerTemp, innerH, outTemp, mqttInfo, designMax, designMin, keeper, phone, address, maxTemp, minTemp, avgTemp, QTStatus, model, grainUpTime, depotType, inTime, grainType, grainNum, inHumidity, nowHumidity, tester);
    }

    @Override
    public String toString() {
        return "Depot{" +
                "id=" + id +
                ", depotId=" + depotId +
                ", companyId=" + companyId +
                ", devNote='" + devNote + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                ", xx=" + xx +
                ", yy=" + yy +
                ", zz=" + zz +
                ", innerTemp=" + innerTemp +
                ", innerH=" + innerH +
                ", outTemp=" + outTemp +
                ", mqttInfo='" + mqttInfo + '\'' +
                ", designMax=" + designMax +
                ", designMin=" + designMin +
                ", keeper='" + keeper + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", maxTemp='" + maxTemp + '\'' +
                ", minTemp='" + minTemp + '\'' +
                ", avgTemp='" + avgTemp + '\'' +
                ", QTStatus=" + QTStatus +
                ", model=" + model +
                ", grainUpTime=" + grainUpTime +
                ", depotType='" + depotType + '\'' +
                ", inTime=" + inTime +
                ", grainType='" + grainType + '\'' +
                ", grainNum='" + grainNum + '\'' +
                ", inHumidity='" + inHumidity + '\'' +
                ", nowHumudity='" + nowHumidity + '\'' +
                ", tester='" + tester + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public String getDevNote() {
        return devNote;
    }

    public void setDevNote(String devNote) {
        this.devNote = devNote;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getXx() {
        return xx;
    }

    public void setXx(int xx) {
        this.xx = xx;
    }

    public int getYy() {
        return yy;
    }

    public void setYy(int yy) {
        this.yy = yy;
    }

    public int getZz() {
        return zz;
    }

    public void setZz(int zz) {
        this.zz = zz;
    }

    public double getInnerTemp() {
        return innerTemp;
    }

    public void setInnerTemp(double innerTemp) {
        this.innerTemp = innerTemp;
    }

    public double getInnerH() {
        return innerH;
    }

    public void setInnerH(double innerH) {
        this.innerH = innerH;
    }

    public double getOutTemp() {
        return outTemp;
    }

    public void setOutTemp(double outTemp) {
        this.outTemp = outTemp;
    }

    public String getMqttInfo() {
        return mqttInfo;
    }

    public void setMqttInfo(String mqttInfo) {
        this.mqttInfo = mqttInfo;
    }

    public int getDesignMax() {
        return designMax;
    }

    public void setDesignMax(int designMax) {
        this.designMax = designMax;
    }

    public int getDesignMin() {
        return designMin;
    }

    public void setDesignMin(int designMin) {
        this.designMin = designMin;
    }

    public String getKeeper() {
        return keeper;
    }

    public void setKeeper(String keeper) {
        this.keeper = keeper;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(String maxTemp) {
        this.maxTemp = maxTemp;
    }

    public String getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(String minTemp) {
        this.minTemp = minTemp;
    }

    public String getAvgTemp() {
        return avgTemp;
    }

    public void setAvgTemp(String avgTemp) {
        this.avgTemp = avgTemp;
    }

    public int getQTStatus() {
        return QTStatus;
    }

    public void setQTStatus(int QTStatus) {
        this.QTStatus = QTStatus;
    }

    public int getModel() {
        return model;
    }

    public void setModel(int model) {
        this.model = model;
    }

    public Date getGrainUpTime() {
        return grainUpTime;
    }

    public void setGrainUpTime(Date grainUpTime) {
        this.grainUpTime = grainUpTime;
        this.grainUpTimeFormat = ContextUtil.dateFormatMdhms(grainUpTime);
    }

    public String getDepotType() {
        return depotType;
    }

    public void setDepotType(String depotType) {
        this.depotType = depotType;
    }

    public Date getInTime() {
        return inTime;
    }

    public void setInTime(Date inTime) {
        this.inTime = inTime;
        this.inTimeFormat = ContextUtil.dateFormatMdhms(inTime);
    }

    public String getGrainUpTimeFormat() {
        return grainUpTimeFormat;
    }

    public void setGrainUpTimeFormat(String grainUpTimeFormat) {
        this.grainUpTimeFormat = grainUpTimeFormat;
    }

    public String getInTimeFormat() {
        return inTimeFormat;
    }

    public void setInTimeFormat(String inTimeFormat) {
        this.inTimeFormat = inTimeFormat;
    }

    public String getGrainType() {
        return grainType;
    }

    public void setGrainType(String grainType) {
        this.grainType = grainType;
    }

    public String getGrainNum() {
        return grainNum;
    }

    public void setGrainNum(String grainNum) {
        this.grainNum = grainNum;
    }

    public String getInHumidity() {
        return inHumidity;
    }

    public void setInHumidity(String inHumidity) {
        this.inHumidity = inHumidity;
    }

    public String getNowHumidity() {
        return nowHumidity;
    }

    public void setNowHumidity(String nowHumidity) {
        this.nowHumidity = nowHumidity;
    }

    public String getTester() {
        return tester;
    }

    public void setTester(String tester) {
        this.tester = tester;
    }
}
