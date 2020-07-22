package cn.zz.dgcc.DGIOT.entity;

import java.util.Objects;

/**
 * Created by: LT001
 * Date: 2020/5/21 17:14
 * ClassExplain :
 * ->
 */
public class DepotDev {
    int id;
    int depotId;
    int type;
    String devName;
    String status;
    int companyId;

    @Override
    public String toString() {
        return "DepotDev{" +
                "id=" + id +
                ", depotId=" + depotId +
                ", type=" + type +
                ", devName='" + devName + '\'' +
                ", status='" + status + '\'' +
                ", companyId=" + companyId +
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DepotDev depotDev = (DepotDev) o;
        return id == depotDev.id &&
                depotId == depotDev.depotId &&
                type == depotDev.type &&
                companyId == depotDev.companyId &&
                Objects.equals(devName, depotDev.devName) &&
                Objects.equals(status, depotDev.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, depotId, type, devName, status, companyId);
    }
}
