package cn.zz.dgcc.DGIOT.utils;

/**
 * Created by: YYL
 * Date: 2020/7/30 9:32
 * ClassExplain :
 * ->
 */
public class TESTUtils {

    public static void main(String[] args) {
        String str = insStr("AAB07B090C010200FF02D801D601D201D001CD01CC01C701C201BE01DC01D701D201CE01CE01C901C701C101C101DA01D401D301CC01CC01C401C501C201BD01DA01D701D301CD01CC01CB01C601C101BC01D101CA01C501C201C001BF01BC01B901B601D001C801C501BF01BE01BB01B901B901B401CF01C901C401C001BE01BB01BA01B601B501CF01C801C201C001BE01BB01B901B701B401DB01D701D401CF01CD01C901C901C401C201DC01D601D401D101CB01C801C601C301BF01DD01D901D301D201CB01CB01C601C301C001DF01D901D401D001CD01CB01C801C201C001AAAA39013FFFFFFFAAAAAAAA0FC4FFFFB76DEFEF");
        System.err.println(str);
    }

    public static String insStr(String str){
        StringBuffer strBuf = new StringBuffer();
        for(int i=0;i<str.length();i++){
            strBuf.append(str.charAt(i));
            if(i%2!=0){
                strBuf.append(" ");
            }
        }
        return strBuf.toString();
    }
}
