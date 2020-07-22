package cn.zz.dgcc.DGIOT.VO;

/**
 * Created by: YYL
 * Date: 2020/7/21 15:23
 * ClassExplain :
 * ->
 */
public class ProjectVO {
    String project;
    String username;
    String password;
    String description;
    String desc;
    int depotNum;
    int qt;
    int lq;
    int autocreate;

    @Override
    public String toString() {
        return "ProjectVO{" +
                "project='" + project + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", description='" + description + '\'' +
                ", desc='" + desc + '\'' +
                ", depotNum='" + depotNum + '\'' +
                ", qt=" + qt +
                ", lq=" + lq +
                ", autocreate=" + autocreate +
                '}';
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getDepotNum() {
        return depotNum;
    }

    public void setDepotNum(int depotNum) {
        this.depotNum = depotNum;
    }

    public int getQt() {
        return qt;
    }

    public void setQt(int qt) {
        this.qt = qt;
    }

    public int getLq() {
        return lq;
    }

    public void setLq(int lq) {
        this.lq = lq;
    }

    public int getAutocreate() {
        return autocreate;
    }

    public void setAutocreate(int autocreate) {
        this.autocreate = autocreate;
    }
}
