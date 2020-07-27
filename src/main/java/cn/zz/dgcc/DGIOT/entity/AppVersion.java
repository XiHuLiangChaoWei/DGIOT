package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: LT001
 * Date: 2020/6/1 12:12
 * ClassExplain :
 * ->
 */
public class AppVersion {
    int id;
    String verName;
    String verCode;
    String verNote;
    String address;

    public AppVersion() {

    }

    public AppVersion(String version, String verNote, String avatarpath) {
        this.verName = version;
        this.verNote = verNote;
        this.address = avatarpath;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getVerNote() {
        return verNote;
    }

    public void setVerNote(String verNote) {
        this.verNote = verNote;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
