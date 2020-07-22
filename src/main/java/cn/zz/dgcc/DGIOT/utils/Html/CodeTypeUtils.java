package cn.zz.dgcc.DGIOT.utils.Html;

/**
 * Created by: LT001
 * Date: 2019/10/24 15:10
 * ClassExplain :
 * -> 项目中所用到的静态变量
 */
public class CodeTypeUtils {
    //web登录相关：
    /** 验证码，Hash类型， 后面跟着cookie Id */
    public static final String CAPTCHA = "captcha:";
    /** 验证码，field，验证码内容*/
    public static final String CAPTCHA_CODE = "code";
    /** 验证码，field，验证码是否已经验证过 */
    public static final String CAPTCHA_CHECKED = "checked";
    /** 验证码失效时间，分钟 */
    public static final int CAPTCHA_EXPIRED = 2;

}
