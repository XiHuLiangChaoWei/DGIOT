package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: LT001
 * Date: 2020/5/21 8:46
 * ClassExplain :
 * ->
 */
public class Company {
    int id;
    String name;
    String info;

    public Company(String project, String desc) {
        this.name = project;
        this.info = desc;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
