package cn.zz.dgcc.DGIOT.utils;

/**
 * Created by: LT001
 * Date: 2019/11/14 16:43
 * ClassExplain :
 * ->
 */
public enum Quartz {

    SUN,MON,TUE,WED,THU,FRI,SAT;

    public static String getWeek(int index){
        for (Quartz quartz: Quartz.values()) {
            if (quartz.ordinal() == index){
                return quartz.name();
            }
        }
        return "";
    }


}
