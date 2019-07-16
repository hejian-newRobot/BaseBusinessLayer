package org.cloud.microservice.business.utils;


import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 日期工具类
 *
 * @author hejian
 */
public class DateUtil {

    /**
     * 日期格式，年份，例如：2004，2008
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";

    // ==格式到年==
    /**
     * 日期格式，年份和月份，例如：200707，200808
     */
    public static final String DATE_FORMAT_YYYYMM = "yyyyMM";


    // ==格式到年月 ==
    /**
     * 日期格式，年份和月份，例如：200707，2008-08
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy-MM";
    /**
     * 日期格式，年月日，例如：050630，080808
     */
    public static final String DATE_FORMAT_YYMMDD = "yyMMdd";


    // ==格式到年月日==
    /**
     * 日期格式，年月日，用横杠分开，例如：06-12-25，08-08-08
     */
    public static final String DATE_FORMAT_YY_MM_DD = "yy-MM-dd";
    /**
     * 日期格式，年月日，例如：20050630，20080808
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    /**
     * 日期格式，年月日，用横杠分开，例如：2006-12-25，2008-08-08
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式，年月日，例如：2016.10.05
     */
    public static final String POINT_YYYY_MMU_DD = "yyyy.MM.dd";
    /**
     * 日期格式，年月日，例如：2016年10月05日
     */
    public static final String CHINA_YYYY_MMU_DD = "yyyy年MM月dd日";
    /**
     * 日期格式，年月日，例如：2016年10月05日 12:00
     */
    public static final String CHINA_YYYYMMDD_HHU_MM = "yyyy年MM月dd日 HH:mm";
    /**
     * 日期格式，年月日，例如：2016100512:00:99
     */
    public static final String YYYY_MMU_DD_HHU_MM_SS1 = "yyyyMMddHH:mm:ss";
    /**
     * 日期格式，年月日时分，例如：200506301210，200808081210
     */
    public static final String YYYY_MMU_DD_HHU_MM = "yyyyMMddHHmm";

    // ==格式到年月日 时分 ==
    /**
     * 日期格式，年月日时分，例如：20001230 12:00，20080808 20:08
     */
    public static final String YYYY_MMU_DD__HHU_MM1 = "yyyyMMdd HH:mm";
    /**
     * 日期格式，年月日时分，例如：2000-12-30 12:00，2008-08-08 20:08
     */
    public static final String YYYY_MMU_DD__HHU_MM2 = "yyyy-MM-dd HH:mm";
    /**
     * 日期格式，年月日时分秒，例如：20001230120000，20080808200808
     */
    public static final String YYYY_MMU_DD_HHU_MM_SS2 = "yyyyMMddHHmmss";


    // ==格式到年月日 时分秒==
    /**
     * 日期格式，年月日时分秒，年月日用横杠分开，时分秒用冒号分开
     * 例如：2005-05-10 23：20：00，2008-08-08 20:08:08
     */
    public static final String YYYY_MMU_DD__HHU_MM_SS = "yyyy-MM-dd HH:mm:ss";
    /**
     * 日期格式，年月日时分秒毫秒，例如：20001230120000123，20080808200808456
     */
    public static final String YYYY_MMU_DD_HHU_MM_SS_SSSU = "yyyyMMddHHmmssSSS";


    // ==格式到年月日 时分秒 毫秒==
    /**
     * 日期格式，月日时分，例如：10-05 12:00
     */
    public static final String MMU_DD__HHU_MM = "MM-dd HH:mm";


    // ==特殊格式==
    /**
     * 日期格式，天/月 ,比如 :四月十二号 : 12/4
     */
    public static final String D_M = "d/M";
    /**
     * 时间格式 小时：分钟 ,比如 十三点十五分 ：13:15
     */
    public static final String HHU_MM = "HH:mm";
    public final static DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern(DateUtil.CHINA_YYYYMMDD_HHU_MM);
    public final static DateTimeFormatter FORMAT_113 = DateTimeFormatter.ofPattern(DateUtil.YYYY_MMU_DD_HHU_MM_SS1);
    public final static DateTimeFormatter FORMAT_112 = DateTimeFormatter.ofPattern(DateUtil.YYYY_MMU_DD__HHU_MM_SS);
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);


    /* ************工具方法***************   */

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param pattern      String类型格式
     * @param defaultValue 默认值为当前时间Date
     * @return 格式化后的字符串
     */
    public static String format(ZonedDateTime time, String pattern, final ZonedDateTime defaultValue) {
        try {
            return format(time, pattern);
        } catch (Exception e) {
            if (defaultValue != null) {
                return format(defaultValue, pattern);
            } else {
                return format(ZonedDateTime.now(), pattern);
            }
        }
    }

    /**
     * 格式化Date时间
     *
     * @param time         Date类型时间
     * @param timeFormat   String类型格式
     * @param defaultValue 默认时间值String类型
     * @return 格式化后的字符串
     */
    public static String format(ZonedDateTime time, String timeFormat, final String defaultValue) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(timeFormat);
            return formatter.format(time);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化时间戳{@see Timestamp} 如果格式化抛出异常将会返回空串
     *
     * @param timestamp  时间戳
     * @param timeFormat 格式化模板
     * @return 返回一个格式化后的字符串
     * @see Timestamp
     */
    public static String format(Timestamp timestamp, String timeFormat) {
        return format(timestamp, timeFormat, StringUtils.EMPTY);
    }

    public static String format(Timestamp timestamp, String timeFormat, String defaultValue) {
        if (timestamp == null
                || StringUtils.isEmpty(timeFormat)) {
            return StringUtils.EMPTY;
        }
        ZoneId zone = ZoneId.systemDefault();
        ZonedDateTime zonedDateTime = ZonedDateTime.ofInstant(timestamp.toInstant(),
                zone);
        return format(zonedDateTime, timeFormat, defaultValue);
    }

    /**
     * 格式化String时间
     *
     * @param time          String类型时间
     * @param formatPattern String类型格式
     * @return 格式化后的Date日期
     */
    public static ZonedDateTime parse(String time, String formatPattern) {
        if (StringUtils.isEmpty(time)) {
            return null;
        }

        DateTimeFormatter deteFormatter = DateTimeFormatter.ofPattern(formatPattern);
        //需要确认时区 因为使用的 ZonedDateTime 时区信息从ZoneId中拿到
        return LocalDate.parse(time, deteFormatter).atStartOfDay(ZoneId.systemDefault());
    }

    /**
     * 格式化String时间
     *
     * @param strTime       String类型时间
     * @param formatPattern String类型格式
     * @param defaultValue  异常时返回的默认值
     * @return 返回格式化时间
     */
    public static ZonedDateTime parse(String strTime, String formatPattern,
                                      ZonedDateTime defaultValue) {
        try {
            return parse(strTime, formatPattern);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 格式化日期对象 通过 formatPattern
     *
     * @param date          日期对象
     * @param formatPattern 格式化模板
     * @return 返回格式化结果 如果格式化抛出异常将会返回空串
     * @see StringUtils
     * {@link #format(ZonedDateTime, String, String)}
     */
    public static String format(ZonedDateTime date, String formatPattern) {
        return format(date, formatPattern, StringUtils.EMPTY);
    }

    /**
     * 格式化日期对象 通过 formatPattern
     *
     * @param date       日期对象
     * @param dateFormat 格式化日期的工具类
     * @return 返回格式化结果
     */
    public static String format(ZonedDateTime date, DateTimeFormatter dateFormat) {
        return dateFormat.format(date);
    }

    public static String format(String target, String fromPattern, String toPattern) {
        if (StringUtils.isEmpty(target)
                || StringUtils.isEmpty(fromPattern)
                || StringUtils.isEmpty(toPattern)) {
            logger.error("method signature: format(String target, String fromPattern, String toPattern) :format 字符串=" + target + "失败!!返回原值 cause:入参有空值 目标字符串 = " + target + " fromPattern = " + fromPattern + " toPattern = " + toPattern);
            return target;
        }
        ZonedDateTime dateTime = parse(target, fromPattern);
        return format(dateTime, toPattern);
    }
}
