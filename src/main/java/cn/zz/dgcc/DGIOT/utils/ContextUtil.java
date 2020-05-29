package cn.zz.dgcc.DGIOT.utils;

import cn.zz.dgcc.DGIOT.entity.User;
import cn.zz.dgcc.DGIOT.utils.Cache.CacheManagerImpl;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;
import java.util.UUID;

//import org.apache.commons.lang.StringUtils;
//import org.apache.commons.lang.math.RandomUtils;
//
//import com.bstek.bdf2.core.business.IUser;
//import com.bstek.bdf2.core.context.ContextHolder;
//import com.bstek.dorado.core.Configure;

/**
 * 系统公用方法
 *
 * @author Andy
 */
public class ContextUtil {

    private static SimpleDateFormat sdf = null;


//	public static void main(String[] args) {
//		log.info(getTimeYMDHMM(null));
//	}

    public static String FormatNum6(int a) {
        DecimalFormat df = new DecimalFormat("000000");
        String rs = df.format(a);
        return rs;
    }


	/**
	 * 格式化数字长度
	 * @param a  被格式化的值
	 * @param b	 数字长度
	 * @return
	 */
    public static String FormatNum(int a, int b) {
        StringBuffer sb = new
                StringBuffer();
        for (int i = 0; i < b; i++) {
            sb.append("0");
        }
        DecimalFormat df = new DecimalFormat(sb.toString());
        String rs = df.format(a);
        return rs;
    }

    /**
     * 根据时间返回MM-dd（02-14）这样的值
     *
     * @param date 如果为null 默认当前时间
     * @return
     */
    public static String dateFormatMd(Date date) {
        if (null == date)
            date = new Date();
        sdf = new SimpleDateFormat(Constant.DATE_FORMAT_MD);
        return sdf.format(date);
    }

    public static String dateFormatMdhm(Date date) {
        if (null == date)
            date = new Date();
        sdf = new SimpleDateFormat(Constant.DATE_FORMAT_MDHM);
        return sdf.format(date);
    }

    /**
     * 根据时间返回yyyy-MM-dd（2017-02-14）这样的值
     *
     * @param date 如果为null 默认当前时间
     * @return
     */
    public static String dateToString(Date date, String fomart) {
        if (null == date)
            date = new Date();
        if (StringUtils.isEmpty(fomart))
            fomart = Constant.DATE_FORMAT_YMD;
        sdf = new SimpleDateFormat(fomart);
        return sdf.format(date);
    }

    /**
     * 根据时间返回指定格式字符串
     *
     * @param date 如果为null 默认当前时间
     * @return
     */
    public static String dateFormaYmd(Date date) {
        if (null == date)
            date = new Date();
        sdf = new SimpleDateFormat(Constant.DATE_FORMAT_YMD);
        return sdf.format(date);
    }

    /**
     * 获取系统时间毫秒值
     *
     * @return
     */
    public static String getCurTimeMillis() {
        return System.currentTimeMillis() + "";
    }

    /**
     * 生成一个动作编号，登录ID+时间戳
     * TODO
     * @return
     */
//	public static String getActionId() {
//		return getLoginUserName() + "_" + getCurTimeMillis();
//	}

    /**
     * 获取一个Map
     *
     * @return
     */
    public static HashMap<String, Object> getMap() {
        return new HashMap<String, Object>();
    }

    /**
     * 生成批次ID，规则：每15分钟内信息为同一个批次
     *
     * @param date
     * @return
     */
    public static String getBatchId(Date date) {
        if (null == date)
            date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int minute = c.get(Calendar.MINUTE);
        return new SimpleDateFormat(Constant.DATE_FORMAT_YMDH).format(date) + "-" + (minute / 15 + 1);
    }

    public static String getTimeYMDHMM(Date date) {
        if (null == date) {
            date = new Date();
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int minute = c.get(Calendar.MINUTE);
        return new SimpleDateFormat(Constant.DATE_FORMAT_YYMDHMS).format(date);
    }

    /**
     * 生成设备名，规则：每15分钟内信息为同一个批次
     */
    public static String getTimeYMDH(Date date) {
        if (null == date)
            date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int minute = c.get(Calendar.MINUTE);
        return new SimpleDateFormat(Constant.DATE_FORMAT_YMDH).format(date) + (minute / 15 + 1);
    }

    /**
     * 生成yyyyMMddHHmmss+随机数的ID
     *
     * @param reg 随机数范围 默认10以内
     * @return
     */
    public static String getCurTimeId(Integer reg) {
        if (null == reg)
            reg = 10;
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + RandomUtils.nextInt(0, reg);
    }

    /**
     * UUID生成规则
     *
     * @return
     */
    public static String getUUID() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 根据当前登陆人获取组织编号，如果没有登陆人获取系统默认的组织编号
     *
     * @param defaultTag
     *            根据当前登陆人获取失败后是否获取默认组织编号
     * @return
     */
//	public static String getCompanyId(boolean defaultTag) {
//		IUser user = ContextHolder.getLoginUser();
//		if (null != user)
//			return user.getCompanyId();
//		if (defaultTag)
//			return Configure.getString("default.companyId");
//		return null;
//	}

    /**
     * 转为十六进制串，不足4位，补足
     *
     * @param value
     * @return
     */
    public static String toHexString(int value) {
        String tempHex = Integer.toHexString(value);
        if (tempHex.length() < 2) {
            tempHex = "0" + tempHex;
        }
        if (tempHex.length() < 4) {
            tempHex = "0" + tempHex;
        }
        if (tempHex.length() < 4) {
            tempHex = "0" + tempHex;
        }
        return tempHex.toUpperCase();
    }

    public static String toHexString(String value) {
        return toHexString(Integer.valueOf(value));
    }

    /**
     * 获取登陆人帐号
     * TODO
     * @return
     */
//	public static String getLoginUserName() {
//		IUser user = ContextHolder.getLoginUser();
//		return null == user ? "" : user.getUsername();
//	}

    /**
     * 获取当前时间当天0点0分,:2018-01-12 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getcurZero(Date date) {
        if (null == date)
            date = new Date();
        long time = date.getTime();
        long zero = time / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 今天零点零分零秒的毫秒数
        return new Timestamp(zero);
    }

    /**
     * 获取当前时间，第二天的零点:2018-01-12 00:00:00
     *
     * @param date
     * @return
     */
    public static Date getNextZero(Date date) {
        if (null == date)
            date = new Date();
        long next = date.getTime() + 24 * 60 * 60 * 1000;// 明天的这一时间的毫秒数
        long zero = next / (1000 * 3600 * 24) * (1000 * 3600 * 24) - TimeZone.getDefault().getRawOffset();// 零点零分零秒的毫秒数
        return new Timestamp(zero);
    }


    public static int getOrderId(int sysId) {
        return sysId % 65535;
    }

    /**
     * 求两个时间相差的小时个数
     *
     * @param startTime
     * @return
     */
    public static double getMiuesHours(Date startTime, Date endTime) {
        long start = startTime.getTime();
        long end = endTime.getTime();
        double hours = (double) ((end - start) / (1000 * 60 * 60));
        return hours;
    }

    /**
     * 求两个时间相差的分钟
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static long getMinuteDifference(Date startTime, Date endTime) {

        long start = startTime.getTime();
        long end = endTime.getTime();
        long hours = ((end - start) / (1000 * 60));
        return hours;
    }

    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    public static String getNextId(String conpanyId, String parentId, String endId, int format) {
        try {

            int endNum = 0;
            if (StringUtils.isNotEmpty(endId)) {
                endNum = Integer.valueOf(endId.substring(endId.length() - 3));
            }
            String endStr = ("" + (endNum + format + 1)).substring(1);
            if (StringUtils.isNotEmpty(parentId) && (!"0".equals(parentId))) {
                parentId = parentId.substring(parentId.lastIndexOf("_") + 1);
                return conpanyId + "_" + parentId + "_" + endStr;
            } else {
                return conpanyId + "_" + endStr;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
