package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: LT001
 * Date: 2020/6/2 11:42
 * ClassExplain :
 * ->
 */
public class Fireware {
    int id;
    String version;
    String path;

    public Fireware(String version, String path) {
        this.version = version;
        this.path = path;
    }

    public Fireware() {
    }

    public Fireware(int id, String version, String path) {
        this.id = id;
        this.version = version;
        this.path = path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Fireware{" +
                "id=" + id +
                ", version='" + version + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}
