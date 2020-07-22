package cn.zz.dgcc.DGIOT.entity;

import cn.zz.dgcc.DGIOT.utils.Quartz;
import org.quartz.*;


/**
 * Created by: LT001
 * Date: 2019/11/12 15:06
 * ClassExplain :
 * ->
 */


public class QuartzJob {

    private String id;

    //方案名称
    private String schemeName;
    //方案类型
    private String schemeType;
    //星期
    private Integer week;
    //指定时间
    private String timeDate;
    //小时
    private String hour;
    //分钟
    private String minute;
    //指定间隔时间
    private String setTime;
    //备注
    private String remark;
    //定时任务类型, 如粮情定时任务...
    private String type;
    //任务名字
    private String jobName;
    //任务所属组
    private String jobGroup;
    //任务key
    private String jobKey;
    //Cron表达式
    private String Cron;
    //方案详情
    private String particulars;
    //执行任务的具体类
    private Class<? extends Job> aClass;
    //任务状态
    private String start;
    //任务更新时间
    private String updateTime;
    //任务更新人
    private String updatePeople;

    private String jobClass;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdatePeople() {
        return updatePeople;
    }

    public void setUpdatePeople(String updatePeople) {
        this.updatePeople = updatePeople;
    }

    public String getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(String timeDate) {
        this.timeDate = timeDate;
    }

    public String getSchemeType() {
        return schemeType;
    }

    public void setSchemeType(String schemeType) {
        this.schemeType = schemeType;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }


    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getMinute() {
        return minute;
    }

    public void setMinute(String minute) {
        this.minute = minute;
    }

    public String getSetTime() {
        return setTime;
    }

    public void setSetTime(String setTime) {
        this.setTime = setTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSchemeName() {
        return schemeName;
    }

    public void setSchemeName(String schemeName) {
        this.schemeName = schemeName;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup;
    }

    public String getJobKey() {
        return jobKey;
    }

    public void setJobKey(String jobKey) {
        this.jobKey = jobKey;
    }

    public String getCron() {
        setCronToType();
        return Cron;
    }

    public void setCron(String cron) {
        cron = cron;
    }

    public String getParticulars() {
        return particulars;
    }

    public void setParticulars(String particulars) {
        this.particulars = particulars;
    }

    public Class<? extends Job> getaClass() {
        return aClass;
    }

    public void setaClass(Class<? extends Job> aClass) {
        this.aClass = aClass;
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass;
    }

    private void setCronToType() {

        switch (schemeType) {
            case "0":
                if (this.week == 7) this.week = 0;
                String weeks = Quartz.getWeek(this.week);
                Cron = "0 " + minute + " " + hour + " ? * " + weeks;
                particulars = "每星期" + this.week + "的" + hour + "时" + minute + "分执行";
                break;
            case "1":
                Cron = "0 " + minute + " " + hour + " ? * *";
                particulars = "每天" + hour + "时" + minute + "分执行";
                break;
            case "2":
                Cron = "0 " + minute + " " + hour + " " + timeDate.substring(8, 10) + " " + timeDate.substring(5, 7) + " ? " + timeDate.substring(0, 4);
                particulars = timeDate.substring(0, 4) + "年" + timeDate.substring(5, 7) + "月" + timeDate.substring(8, 10) + "日" + hour + "时" + minute + "分执行";
                break;
            case "3":
                if (!"".equals(setTime)) {
                    if ("m".equals(setTime.substring(setTime.length() - 1))) {
                        Cron = "0 0/" + setTime.substring(0, setTime.length() - 1) + " * * * ?";
                        particulars = "每隔" + setTime.substring(0, setTime.length() - 1) + "分执行";
                    } else {
                        Cron = "0 0 0/" + setTime.substring(0, 1) + " * * ?";
                        particulars = "每隔" + setTime.substring(0, 1) + "小时执行";
                    }
                } else {
                    throw new RuntimeException("生成Cron是setTime传入了非法参数");
                }
                break;
        }


    }


}
