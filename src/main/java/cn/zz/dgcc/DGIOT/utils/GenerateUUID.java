package cn.zz.dgcc.DGIOT.utils;

import java.util.UUID;

/**
 * Created by: YYL
 * Date: 2019/10/24 13:45
 * ClassExplain :
 * -> uuid生成器
 */
public class GenerateUUID {


    /**
     * 生成一个32位的UUID
     * 标准型式包含32个16进制数字，以连字号分为五段，形式为8-4-4-4-12的36个字符
     * 使用: JDK 版本4, 并去了-
     * @return 32位UUID
     */
    public static String GetUUID(){

        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }


}
