package cn.zz.dgcc.DGIOT.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * Created by: YYL
 * Date: 2020/4/23 14:56
 * ClassExplain :
 * ->
 */
public class Device implements Serializable {
    String productName;
    String productKey;
    String deviceName;
    String deviceStatus;
    String deviceSecret;
    String deviceNickName;
    String deviceIpAddress;
    int isUsed;
    String DevId;
    String DtuId;
    String devNote;
    String devBH;
    String devZH;
    int type;
    String busType;
    String UserId;
    String ccId;
    String devVersion;
    String dtuVersion;

    @Override
    public String toString() {
        return "Device{" +
                "productName='" + productName + '\'' +
                ", productKey='" + productKey + '\'' +
                ", deviceName='" + deviceName + '\'' +
                ", deviceStatus='" + deviceStatus + '\'' +
                ", deviceSecret='" + deviceSecret + '\'' +
                ", deviceNickName='" + deviceNickName + '\'' +
                ", deviceIpAddress='" + deviceIpAddress + '\'' +
                ", isUsed=" + isUsed +
                ", DevId='" + DevId + '\'' +
                ", DtuId='" + DtuId + '\'' +
                ", devNote='" + devNote + '\'' +
                ", devBH='" + devBH + '\'' +
                ", devZH='" + devZH + '\'' +
                ", type=" + type +
                ", busType='" + busType + '\'' +
                ", UserId='" + UserId + '\'' +
                ", ccId='" + ccId + '\'' +
                ", devVersion='" + devVersion + '\'' +
                ", dtuVersion='" + dtuVersion + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Device device = (Device) o;
        return isUsed == device.isUsed &&
                type == device.type &&
                Objects.equals(productName, device.productName) &&
                Objects.equals(productKey, device.productKey) &&
                Objects.equals(deviceName, device.deviceName) &&
                Objects.equals(deviceStatus, device.deviceStatus) &&
                Objects.equals(deviceSecret, device.deviceSecret) &&
                Objects.equals(deviceNickName, device.deviceNickName) &&
                Objects.equals(deviceIpAddress, device.deviceIpAddress) &&
                Objects.equals(DevId, device.DevId) &&
                Objects.equals(DtuId, device.DtuId) &&
                Objects.equals(devNote, device.devNote) &&
                Objects.equals(devBH, device.devBH) &&
                Objects.equals(devZH, device.devZH) &&
                Objects.equals(busType, device.busType) &&
                Objects.equals(UserId, device.UserId) &&
                Objects.equals(ccId, device.ccId) &&
                Objects.equals(devVersion, device.devVersion) &&
                Objects.equals(dtuVersion, device.dtuVersion);
    }

    @Override
    public int hashCode() {
        return Objects.hash(productName, productKey, deviceName, deviceStatus, deviceSecret, deviceNickName, deviceIpAddress, isUsed, DevId, DtuId, devNote, devBH, devZH, type, busType, UserId, ccId, devVersion, dtuVersion);
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductKey() {
        return productKey;
    }

    public void setProductKey(String productKey) {
        this.productKey = productKey;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public String getDeviceSecret() {
        return deviceSecret;
    }

    public void setDeviceSecret(String deviceSecret) {
        this.deviceSecret = deviceSecret;
    }

    public String getDeviceNickName() {
        return deviceNickName;
    }

    public void setDeviceNickName(String deviceNickName) {
        this.deviceNickName = deviceNickName;
    }

    public String getDeviceIpAddress() {
        return deviceIpAddress;
    }

    public void setDeviceIpAddress(String deviceIpAddress) {
        this.deviceIpAddress = deviceIpAddress;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getDevId() {
        return DevId;
    }

    public void setDevId(String devId) {
        DevId = devId;
    }

    public String getDtuId() {
        return DtuId;
    }

    public void setDtuId(String dtuId) {
        DtuId = dtuId;
    }

    public String getDevNote() {
        return devNote;
    }

    public void setDevNote(String devNote) {
        this.devNote = devNote;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getBusType() {
        return busType;
    }

    public void setBusType(String busType) {
        this.busType = busType;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getCcId() {
        return ccId;
    }

    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    public String getDevVersion() {
        return devVersion;
    }

    public void setDevVersion(String devVersion) {
        this.devVersion = devVersion;
    }

    public String getDtuVersion() {
        return dtuVersion;
    }

    public void setDtuVersion(String dtuVersion) {
        this.dtuVersion = dtuVersion;
    }
}
