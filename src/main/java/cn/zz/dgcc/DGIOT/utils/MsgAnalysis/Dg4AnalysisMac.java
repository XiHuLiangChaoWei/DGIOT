package cn.zz.dgcc.DGIOT.utils.MsgAnalysis;

import cn.zz.dgcc.DGIOT.utils.ContextUtil;
import cn.zz.dgcc.DGIOT.utils.MsgBuilder.BytesUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Dg4AnalysisMac {


    public static JSONObject analysis(String str){
        JSONObject json = new JSONObject();
        String content = str.replace(" ","");

        //时间
//        String a = content.substring(2*2,2*7+2);
        String time = parseTimes(content.substring(2*2,2*7+2));

        //循环码
//        String b = content.substring(2*8,2*8+2);
        String xhm = content.substring(2*8,2*8+2);
        Integer.parseInt(xhm,16);


        //分机地址
        String fjdz = content.substring(2*9,2*9+2);
        int f = Integer.parseInt(content.substring(2*9,2*9+2),16);

        //温度点 低字节在前高字节在后
        String wendu = content.substring(2*10,2*1033+2);
        List<Double> templ = parseTemps(wendu);
        System.out.println("温度："+templ);
        //温湿度
        String wenshidu = content.substring(2*1034,2*1045+2);
        parseTHPoint(wenshidu);
        System.out.println("温湿度："+parseTHPoint(wenshidu));

        //电池电压
        String dianya = content.substring(1060*2,1061*2+2);
        String dy = BytesUtil.tran_LH(dianya);
        int d= BytesUtil.hexToInt(dianya);

      return json;
    }

    static JSONArray parseTHPoint(String ths) {
        JSONArray ja = new JSONArray();
        String indexH = null;
        String indexT = null;
        for (int i = 0; i < ths.length(); i += 6) {
            indexH = ths.substring(i, i + 2);
            indexT = ths.substring(i + 2, i + 6);
            JSONObject jo = new JSONObject();
            jo.put("shidu", (double) BytesUtil.hexToInt(indexH));
            jo.put("wendu", ((double) BytesUtil.hexToInt(indexT) / 10));
            ja.add(jo);
        }
        System.err.println(ja);
        return ja;
    }

    static List<Double> parseTemps(String temps) {
        List<Double> list = new ArrayList<>();
        String index;
        for (int i = 0; i < temps.length(); i += 4) {
            //高低转换
            index = BytesUtil.tran_LH(temps.substring(i, i + 4));
            int rs = BytesUtil.hexToInt(index);
            if(rs==-1){
                continue;
            }
            list.add(rs * 0.0625);
        }
        return list;
    }

    private static String parseTimes(String time) {
        if (time.length() > 12) {
            throw new RuntimeException("解析时间数据时，长度出错");
        }
        if ("FFFFFFFFFFFF".equals(time)) {
            return time;
        }
        StringBuffer rs = new StringBuffer();
        String index = null;
        String[] t = new String[]{"年", "月", "日", "时", "分", "秒"};
        int x = 0;
        for (int i = 0; i < time.length(); i += 2) {
            index = time.substring(i, i + 2);
            x = Integer.parseInt(index, 16);
            index = ContextUtil.FormatNum(x, 2);
            rs.append(index + t[i / 2]);
        }
        return rs.toString();
    }
    public static void main(String[] args) {
        String str1 = "AA B0 FF FF FF FF FF FF 00 03 13 02 10 02 0E 02 08 02 FF 01 FC 01 F5 01 F1 01 EA 01 16 02 11 02 0A 02 06 02 FD 01 F9 01 F3 01 EB 01 E6 01 13 02 0D 02 0B 02 07 02 01 02 F8 01 F3 01 EA 01 E6 01 14 02 10 02 0E 02 07 02 01 02 FB 01 F4 01 ED 01 E5 01 1F 02 19 02 13 02 0B 02 05 02 02 02 FA 01 F4 01 F3 01 1E 02 15 02 0F 02 07 02 02 02 FC 01 F5 01 EE 01 E7 01 1E 02 17 02 10 02 09 02 03 02 FB 01 F6 01 ED 01 E7 01 1F 02 17 02 0E 02 09 02 02 02 FB 01 F7 01 EB 01 E9 01 29 02 24 02 1B 02 16 02 10 02 0B 02 05 02 00 02 F8 01 2A 02 25 02 1B 02 14 02 10 02 0A 02 03 02 F9 01 F1 01 2D 02 24 02 1B 02 14 02 0C 0208 02 03 02 F9 01 F0 01 2D 02 23 02 1A 02 13 02 0B 02 05 02 02 02 F8 01 F0 01 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FFFF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 30 01 70 FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF FF 01 10 10 01 00 03 66 9A EF EF";
        String str = str1.replace(" ", "");
//        analysis(str);
        String wendu = str.substring(2*10,2*1033+2);
        List<Double> templ = parseTemps(wendu);
        System.out.println("温度："+templ);
        String di = str.substring(20, 22);
        String gao = str.substring(22, 24);


        String index;
        index = BytesUtil.tran_LH(di + gao);
        int rs = BytesUtil.hexToInt(index);


        int [][][] arrays=new int[4][3][2];//在栈空间创建一个空间

        for(int i=0;i<arrays.length;i++) {
            for(int i1=0;i1<arrays[i].length;i1++) {
                for(int i2=0;i2<arrays[i][i1].length;i2++) {
                    System.out.print(templ);
                }
                System.out.println();//二维换行
            }
            System.out.println();//三维换行
        }
    }

}
