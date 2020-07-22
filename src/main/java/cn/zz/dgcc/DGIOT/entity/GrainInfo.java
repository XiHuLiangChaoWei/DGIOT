package cn.zz.dgcc.DGIOT.entity;

/**
 * Created by: LT001
 * Date: 2020/4/23 15:02
 * ClassExplain :
 * ->
 */
public class GrainInfo {
    String batchId;
    int depotId;
    int x;
    int y;
    int z;
    double outDoor;
    double innerTemp;
    double innerHumidity;

    public GrainInfo(String batchId, int depotId, double innerTemp ,int x,int y,int z ) {
        this.batchId = batchId;
        this.depotId = depotId;
        this.x = x;
        this.y = y;
        this.z = z;
        this.innerTemp = innerTemp;
    }

    public String getBatchId() {
        return batchId;
    }

    public void setBatchId(String batchId) {
        this.batchId = batchId;
    }

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    public double getOutDoor() {
        return outDoor;
    }

    public void setOutDoor(double outDoor) {
        this.outDoor = outDoor;
    }

    public double getInnerTemp() {
        return innerTemp;
    }

    public void setInnerTemp(double innerTemp) {
        this.innerTemp = innerTemp;
    }

    public double getInnerHumidity() {
        return innerHumidity;
    }

    public void setInnerHumidity(double innerHumidity) {
        this.innerHumidity = innerHumidity;
    }
}
