package cn.zz.dgcc.DGIOT.utils;

/**
 * 系统常量
 * 
 * @author Andy
 *
 */
public class Constant {
	
	
	public static final String APP_NAME ="IGDS";
	
	/*** 第二个数据源的名称 SQL-SQLSERVER */
	public static final String DB_SQL_SERVER = "sqlServer-depot";
	
	/*** 当前组织的粮仓列表+companyId */
	public static final String CACHE_DEPOT_LIST = "CACHE_DEPOT_LIST_";
	

	/*** 格式化时间yyyy-MM-dd */
	public static final String DATE_FORMAT_YMD = "yyyy-MM-dd";
	/*** 格式化时间yyyyMMddHH*/
	public static final String DATE_FORMAT_YMDH = "yyyyMMddHH";
	/*** 格式化时间MMdd */
	public static final String DATE_FORMAT_MD = "MM-dd";
	/*** 格式化时间yyyy-MM-dd HH:mm:ss */
	public static final String DATE_FORMAT_YMDHMS = "yyyy-MM-dd HH:mm:ss";
	/*** 格式化时间yyMMddHHmmss */
	public static final String DATE_FORMAT_YYMDHMS = "yyMMddHHmmss";

	public static final String DATE_FORMAT_MDHM = "MM月-dd日 HH:mm";

	
//
//	/** 定时类型-每天执行 */
//	public static final String TIM_TYPE_DAY = "day";
//	/** 定时类型-每周 */
//	public static final String TIM_TYPE_WEEK = "week";
//	/** 定时类型-指定具体小时时间 */
//	public static final String TIM_TYPE_HOUR = "hour";
//	/** 每天 H点 M分 执行表达式 */
//	public static final String CRON_DAY = "0 M H ? * *";
//	/** 每周W H点 M分 执行表达式 */
//	public static final String CRON_WEEK = "0 M H ? * W";
//	/** 具体某个时间点-每年 */
//	public static final String CRON_HOUR = "S M H D & ?";
//
//	public static final String DEFAULT_DEPOT_ID = "01";
//
//	public static final String YN_Y = "Y";
//	public static final String YN_N = "N";
//	public static final String YN_A = "A";//警告信息中延迟解决的标志
//
//	public static final String JOB_BEAN_GRAIN = "depot.grainJob";
//	public static final String JOB_BEAN_GAS = "depot.grainGas";
//	public static final String JOB_BEAN_PEST = "depot.gasPest";
//	public static final String JOB_BEAN_AREATION = "depot.areationJob";
//	public static final String JOB_BEAN_SYS_GRAIN = "depot.systemGrainJob";
//	public static final String JOB_BEAN_SYS_AREATION = "depot.systemAreationJob";
//	public static final String JOB_BEAN_HERTBEAT = "depot.hertBeatJob";
//	public static final String JOB_BEAN_WEATHER = "depot.weatherJob";
//	public static final String JOB_BEAN_HARDWARE = "depot.hardwareJob";
	
//	/**
//	 * 王树茂-新版粮情错误温度值
//	 */
//	public static final Double ERROR_TEMP_WSM = -100.00;
//
//	/**
//	 * 王树茂-新版粮情组织编码
//	 */
//	public static final String COMPANY_WSM = "0000";
//
//
//	/**
//	 * 仓库类型
//	 */
//	public static final String DEPOT_TYPE_01 = "01";
//
//	public static final String DEPOT_TYPE_02 = "02";
//
//	public static final String DEPOT_TYPE_03 = "03";
	
	
//	public static void main(String[] args) throws Exception {
//    	String key = "FZZY==XXJSGFYXGS";
//        String content = Constant.APP_NAME +"-5012-3-20190820";
//        System.out.println("加密前：" + content);
//        String encrypt = AESUtil.aesEncrypt(content, key);
//        System.out.println("加密后：" + encrypt);
//        String decrypt = AESUtil.aesDecrypt(encrypt, key);
//        System.out.println("解密后：" + decrypt);
//    }

}
