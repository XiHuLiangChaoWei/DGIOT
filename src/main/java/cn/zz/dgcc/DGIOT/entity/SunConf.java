package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: LT001
 * Date: 2020/7/13 8:18
 * ClassExplain :
 * ->
 */
public class SunConf {
    int id;
     int companyId;
     int depotId;
     int address;
     int ceng;
     int hang;
     int lie;
     int innerTH;
     int outTH;

    @Override
    public String toString() {
        return "SunConf{" +
                "  companyId=" + companyId +
                ", depotId=" + depotId +
                ", address=" + address +
                ", ceng=" + ceng +
                ", hang=" + hang +
                ", lie=" + lie +
                ", innerTH=" + innerTH +
                ", outTH=" + outTH +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public int getAddress() {
        return address;
    }

    public void setAddress(int address) {
        this.address = address;
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

    public int getInnerTH() {
        return innerTH;
    }

    public void setInnerTH(int innerTH) {
        this.innerTH = innerTH;
    }

    public int getOutTH() {
        return outTH;
    }

    public void setOutTH(int outTH) {
        this.outTH = outTH;
    }
}
