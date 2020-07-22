package cn.zz.dgcc.DGIOT.VO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * Created by: LT001
 * ClassExplain : VO封装类，封装查询粮情时传入的3个属性，仓库编号和时间信息
 *
 * 需要注意，注解时，dataFormat 注意 H-24小时制，h-12小时制
 */
public class GrainHistoryVO {

    int depotId;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date start;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    Date end;

    public int getDepotId() {
        return depotId;
    }

    public void setDepotId(int depotId) {
        this.depotId = depotId;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public String toString() {
        return "GrainHistoryVO{" +
                "depotId=" + depotId +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
