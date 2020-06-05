package cn.zz.dgcc.DGIOT.utils.MsgBuilder;

/**
 * Created by: YYL
 * Date: 2020/5/6 9:21
 * ClassExplain :制氮机命令生成
 * ->
 */
public class N2DevCommondBuilder extends CommondBuild {
    private N2DevCommondBuilder() {
    }

    private final static N2DevCommondBuilder n2DevCommondBuilder = new N2DevCommondBuilder();

    public static N2DevCommondBuilder getInstance() {
        return n2DevCommondBuilder;
    }

    static String[] n2DevOn = new String[]{"03 06 00 23 00 01 B8 22", "03 06 00 23 00 00 79 E2"};
    static String[] n2DevOff = new String[]{" 03 06 00 24 00 01 09 E3 ", " 03 06 00 24 00 00 C8 23"};
    static String n2DevStatus = "03 03 00 3A 00 01 A5 E5";
    static String n2Purity = "03 03 00 41 00 02 95 FD";
    static String n2Flow = "03 03 00 43 00 02 34 3D";
    static String n2Pressure = "03 03 00 45 00 02 D4 3C ";
    static String n2Temp = "03 03 00 47 00 02 75 FC";

    public static String[] getN2DevOn() {
        return n2DevOn;
    }

    public static String[] getN2DevOff() {
        return n2DevOff;
    }

    public static String getN2DevStatus() {
        return n2DevStatus.replace(" ", "");
    }

    public static String getN2Purity() {
        return n2Purity.replace(" ", "");
    }

    public static String getN2Flow() {
        return n2Flow.replace(" ", "");
    }

    public static String getN2Pressure() {
        return n2Pressure.replace(" ", "");
    }

    public static String getN2Temp() {
        return n2Temp.replace(" ", "");
    }
    //    public static void main(String[] args) {
//        System.out.println(n2DevOn);
//        System.out.println(n2DevOff);
//        System.out.println(n2DevStatus);
//        System.out.println(n2Purity);
//        System.out.println(n2Flow);
//        System.out.println(n2Pressure);
//        System.out.println(n2Temp);
//    }
}
