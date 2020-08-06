package cn.zz.dgcc.DGIOT.VO;

/**
 * Created by: YYL
 * Date: 2020/7/30 8:59
 * ClassExplain :
 * ->
 */
public class SunChaXunVO {
    String devName;
    int fjAddress;
    int ceng;
    int hang;
    int lie;
    int thNum;
    int ifOut;

    @Override
    public String toString() {
        return "SunChaXunVO{" +
                "devName='" + devName + '\'' +
                ", fjAddress=" + fjAddress +
                ", ceng=" + ceng +
                ", hang=" + hang +
                ", lie=" + lie +
                ", thNum=" + thNum +
                ", ifOut=" + ifOut +
                '}';
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public int getFjAddress() {
        return fjAddress;
    }

    public void setFjAddress(int fjAddress) {
        this.fjAddress = fjAddress;
    }

    public int getCeng() {
        return ceng;
    }

    public void setCeng(int ceng) {
        this.ceng = ceng;
    }

    public int getHang() {
        return hang;
    }

    public void setHang(int hang) {
        this.hang = hang;
    }

    public int getLie() {
        return lie;
    }

    public void setLie(int lie) {
        this.lie = lie;
    }

    public int getThNum() {
        return thNum;
    }

    public void setThNum(int thNum) {
        this.thNum = thNum;
    }

    public int getIfOut() {
        return ifOut;
    }

    public void setIfOut(int ifOut) {
        this.ifOut = ifOut;
    }
}
