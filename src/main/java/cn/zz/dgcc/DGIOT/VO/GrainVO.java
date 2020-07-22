package cn.zz.dgcc.DGIOT.VO;

/**
 * Created by: LT001
 * Date: 2020/4/27 8:34
 * ClassExplain :
 * ->
 */
public class GrainVO {
    int x;
    int y;
    int z;
    double temp;


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

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }


    @Override
    public String toString() {
        return "GrainVO{" +
                ", x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", temp=" + temp +
                '}';
    }
}
