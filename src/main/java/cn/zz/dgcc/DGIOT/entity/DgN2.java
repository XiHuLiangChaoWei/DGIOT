package cn.zz.dgcc.DGIOT.entity;

public class DgN2 {
    private int id ;
    private String cangfang;
    private String moshi;
    private String nongdu;
    private String qiya;
    private String bsqyali;
    private String bsqtime;
    private String iresult;
    private String wendu;
    private String shidu;
    private String status;
    private String devname;
    private String companyid;
    private String depotid;
    private String zhanhao;
    private String cdl;

    @Override
    public String toString() {
        return "DgN2{" +
                "id=" + id +
                ", cangfang='" + cangfang + '\'' +
                ", moshi='" + moshi + '\'' +
                ", nongdu='" + nongdu + '\'' +
                ", qiya='" + qiya + '\'' +
                ", bsqyali='" + bsqyali + '\'' +
                ", bsqtime='" + bsqtime + '\'' +
                ", iresult='" + iresult + '\'' +
                ", wendu='" + wendu + '\'' +
                ", shidu='" + shidu + '\'' +
                ", status='" + status + '\'' +
                ", devname='" + devname + '\'' +
                ", companyid='" + companyid + '\'' +
                ", depotid='" + depotid + '\'' +
                ", zhanhao='" + zhanhao + '\'' +
                ", cdl='" + cdl + '\'' +
                ", operator='" + operator + '\'' +
                ", begintime='" + begintime + '\'' +
                ", endtime='" + endtime + '\'' +
                '}';
    }

    private String operator;
    private String begintime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCangfang() {
        return cangfang;
    }

    public void setCangfang(String cangfang) {
        this.cangfang = cangfang;
    }

    public String getMoshi() {
        return moshi;
    }

    public void setMoshi(String moshi) {
        this.moshi = moshi;
    }

    public String getNongdu() {
        return nongdu;
    }

    public void setNongdu(String nongdu) {
        this.nongdu = nongdu;
    }

    public String getQiya() {
        return qiya;
    }

    public void setQiya(String qiya) {
        this.qiya = qiya;
    }

    public String getBsqyali() {
        return bsqyali;
    }

    public void setBsqyali(String bsqyali) {
        this.bsqyali = bsqyali;
    }

    public String getBsqtime() {
        return bsqtime;
    }

    public void setBsqtime(String bsqtime) {
        this.bsqtime = bsqtime;
    }

    public String getIresult() {
        return iresult;
    }

    public void setIresult(String iresult) {
        this.iresult = iresult;
    }

    public String getWendu() {
        return wendu;
    }

    public void setWendu(String wendu) {
        this.wendu = wendu;
    }

    public String getShidu() {
        return shidu;
    }

    public void setShidu(String shidu) {
        this.shidu = shidu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDevname() {
        return devname;
    }

    public void setDevname(String devname) {
        this.devname = devname;
    }

    public String getCompanyid() {
        return companyid;
    }

    public void setCompanyid(String companyid) {
        this.companyid = companyid;
    }

    public String getDepotid() {
        return depotid;
    }

    public void setDepotid(String depotid) {
        this.depotid = depotid;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public String getBegintime() {
        return begintime;
    }

    public void setBegintime(String begintime) {
        this.begintime = begintime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    private String endtime;

    public String getZhanhao() {
        return zhanhao;
    }

    public void setZhanhao(String zhanhao) {
        this.zhanhao = zhanhao;
    }

    public String getCdl() {
        return cdl;
    }

    public void setCdl(String cdl) {
        this.cdl = cdl;
    }
}
