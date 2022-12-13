package com.jeasy.common.date;

import com.jeasy.common.Func;
import com.jeasy.common.collection.Twins;
import com.jeasy.common.str.StrKit;
import com.jeasy.exception.KitException;
import org.apache.commons.lang3.time.DateUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * 日期工具类
 *
 * @author taomk
 * @version 1.0
 * @since 2015/05/13 17:34
 */
public class DateKit {

    /**
     * 毫秒
     */
    public static final long MS = 1;
    /**
     * 每秒钟的毫秒数
     */
    public static final long SECOND_MS = MS * 1000;
    /**
     * 每分钟的毫秒数
     */
    public static final long MINUTE_MS = SECOND_MS * 60;
    /**
     * 每小时的毫秒数
     */
    public static final long HOUR_MS = MINUTE_MS * 60;
    /**
     * 每天的毫秒数
     */
    public static final long DAY_MS = HOUR_MS * 24;

    public static final String NORM_YEAR_PATTERN = "yyyy";

    public static final String NORM_YYYYMMDD_PATTERN = "yyyyMMdd";

    public static final String NORM_YYYYMMDDHHMMSS_PATTERN = "yyyyMMddHHmmss";

    /**
     * 标准日期格式
     */
    public static final String NORM_DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 标准时间格式
     */
    public static final String NORM_TIME_PATTERN = "HH:mm:ss";
    /**
     * 标准日期时间格式，精确到分
     */
    public static final String NORM_DATETIME_MINUTE_PATTERN = "yyyy-MM-dd HH:mm";
    /**
     * 标准日期时间格式，精确到秒
     */
    public static final String NORM_DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 标准日期时间格式，精确到毫秒
     */
    public static final String NORM_DATETIME_MS_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    /**
     * HTTP头中日期时间格式
     */
    public static final String HTTP_DATETIME_PATTERN = "EEE, dd MMM yyyy HH:mm:ss z";
    public static final char Y_CHAR = 'y';
    public static final char D_CHAR = 'd';
    public static final char H_CHAR = 'h';
    public static final char M_CHAR = 'm';
    public static final char S_CHAR = 's';
    public static final int NUM_2 = 2;

    private static ThreadLocal<SimpleDateFormat> NORM_YEAR_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_YEAR_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> NORM_YYYYMMDD_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_YYYYMMDD_PATTERN);
        }
    };

    private static ThreadLocal<SimpleDateFormat> NORM_YYYYMMDDHHMMSS_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_YYYYMMDDHHMMSS_PATTERN);
        }
    };

    /**
     * 标准日期（不含时间）格式化器
     */
    private static ThreadLocal<SimpleDateFormat> NORM_DATE_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_DATE_PATTERN);
        }
    };
    /**
     * 标准时间格式化器
     */
    private static ThreadLocal<SimpleDateFormat> NORM_TIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_TIME_PATTERN);
        }
    };
    /**
     * 标准日期时间格式化器
     */
    private static ThreadLocal<SimpleDateFormat> NORM_DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_DATETIME_PATTERN);
        }
    };
    /**
     * HTTP日期时间格式化器
     */
    private static ThreadLocal<SimpleDateFormat> HTTP_DATETIME_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(HTTP_DATETIME_PATTERN, Locale.US);
        }
    };

    private static ThreadLocal<SimpleDateFormat> NORM_DATETIME_MS_FORMAT = new ThreadLocal<SimpleDateFormat>() {
        @Override
        synchronized protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat(NORM_DATETIME_MS_PATTERN);
        }
    };

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return NORM_YEAR_FORMAT.get().format(new Date());
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear(Date date) {
        return NORM_YEAR_FORMAT.get().format(date);
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return NORM_DATE_FORMAT.get().format(new Date());
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return NORM_DATE_FORMAT.get().format(date);
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return NORM_YYYYMMDD_FORMAT.get().format(new Date());
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(Date date) {
        return NORM_YYYYMMDD_FORMAT.get().format(date);
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return NORM_DATETIME_FORMAT.get().format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getMsTime() {
        return NORM_DATETIME_MS_FORMAT.get().format(new Date());
    }

    /**
     * 获取YYYYMMDDHHmmss格式
     *
     * @return
     */
    public static String getAllTime() {
        return NORM_YYYYMMDDHHMMSS_FORMAT.get().format(new Date());
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return NORM_DATETIME_FORMAT.get().format(date);
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @Title: compareDate
     * @Description:(日期比较，如果s>=e 返回true 否则返回false)
     */
    public static boolean compareDate(String s, String e) {
        if (Func.isOneEmpty(parseDateByStr(s), parseDateByStr(e))) {
            return false;
        }
        return parseDateByStr(s).getTime() >= parseDateByStr(e).getTime();
    }

    /**
     * 格式化日期 yyyy-MM-dd
     *
     * @return
     */
    public static Date parseDateByStr(String date) {
        try {
            return NORM_DATE_FORMAT.get().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static Date parseTimeByStr(String date) {
        try {
            return NORM_DATETIME_FORMAT.get().parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        try {
            NORM_DATETIME_FORMAT.get().parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s, String pattern) {
        DateFormat fmt = new SimpleDateFormat(pattern);
        try {
            fmt.parse(s);
            return true;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return false;
        }
    }

    /**
     * <li>功能描述：时间相减得到年数
     *
     * @param startTime
     * @param endTime
     * @return
     */
    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = NORM_DATE_FORMAT.get();
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDiffDay(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat(
            "yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
            day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance();
        // 日期减 如果不够减会将月变动
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = NORM_DATETIME_FORMAT.get();
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance();
        // 日期减 如果不够减会将月变动
        canlendar.add(Calendar.DATE, daysInt);
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 按自然月将一段时间切分成若干段小区间
     *
     * @param begin 开始时间
     * @param end   结束时间
     * @return 一个List，其中每个原始是一对时间，指定了这个区间的开始和结束时间（例如2011-01-01 00:00:00到2011-02-01 00:00:00，使 用时因注意半闭半开), 保证升序
     */
    public static List<Twins<Date>> getMonthlyRanges(Date begin, Date end) {
        List<Twins<Date>> ranges = new ArrayList<Twins<Date>>();
        Date rangeBegin = begin;
        Date rangeEnd;
        while (rangeBegin.compareTo(end) < 0) {
            rangeEnd = DateUtils.truncate(rangeBegin, Calendar.MONTH);
            rangeEnd = DateUtils.addMonths(rangeEnd, 1);
            if (rangeEnd.compareTo(end) > 0) {
                rangeEnd = end;
            }
            ranges.add(new Twins<>(rangeBegin, rangeEnd));
            rangeBegin = rangeEnd;
        }
        return ranges;
    }

    /**
     * 获取较大日期
     *
     * @param lhs
     * @param rhs
     * @return
     */
    public static Date max(Date lhs, Date rhs) {
        return lhs.after(rhs) ? lhs : rhs;
    }

    /**
     * 获取较小日期
     *
     * @param lhs
     * @param rhs
     * @return
     */
    public static Date min(Date lhs, Date rhs) {
        return lhs.before(rhs) ? lhs : rhs;
    }

    /**
     * 是否同1分钟
     *
     * @param lhs
     * @param rhs
     * @return
     */
    public static boolean isSameMinute(Date lhs, Date rhs) {
        return Math.abs(lhs.getTime() - rhs.getTime()) < TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES);
    }

    /**
     * 最后一秒
     *
     * @param date
     * @return
     */
    public static Date endsOfDay(Date date) {
        date = DateUtils.truncate(date, Calendar.DATE);
        date = DateUtils.addDays(date, 1);
        date = DateUtils.addSeconds(date, -1);
        return date;
    }

    /**
     * 获得当前日期时间
     * 日期时间格式yyyy-MM-dd HH:mm:ss.SSS
     *
     * @return
     */
    public static String currentDatetimeSSS() {
        return NORM_DATETIME_MS_FORMAT.get().format(now());
    }

    /**
     * 格式化日期时间
     * 日期时间格式yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param date
     * @return
     */
    public static String formatDatetimeSSS(Date date) {
        return NORM_DATETIME_MS_FORMAT.get().format(date);
    }

    /**
     * 获得当前日期时间
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String currentDatetime() {
        return NORM_DATETIME_FORMAT.get().format(now());
    }

    /**
     * 格式化日期时间
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @return
     */
    public static String formatDatetime(Date date) {
        return NORM_DATETIME_FORMAT.get().format(date);
    }

    /**
     * 格式化当前日期
     * 日期时间格式yyyyMMddHHmmss
     *
     * @return
     */
    public static String formatDatetime() {
        return NORM_YYYYMMDDHHMMSS_FORMAT.get().format(now());
    }

    /**
     * 格式化日期时间
     *
     * @param date
     * @param pattern 格式化模式
     * @return
     */
    public static String formatDatetime(Date date, String pattern) {
        SimpleDateFormat customFormat = new SimpleDateFormat(pattern);
        return customFormat.format(date);
    }

    /**
     * 获得当前日期
     * 日期格式yyyy-MM-dd
     *
     * @return
     */
    public static String currentDate() {
        return NORM_DATE_FORMAT.get().format(now());
    }

    /**
     * 获得当前时间
     * <p/>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String currentTime() {
        return NORM_TIME_FORMAT.get().format(now());
    }

    /**
     * 格式化时间
     * <p/>
     * 时间格式HH:mm:ss
     *
     * @return
     */
    public static String formatTime(Date date) {
        return NORM_TIME_FORMAT.get().format(date);
    }

    public static Calendar calendar() {
        Calendar cal = GregorianCalendar.getInstance(Locale.CHINESE);
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        return cal;
    }

    /**
     * 获得当前时间的毫秒数
     *
     * @return
     */
    public static long millis() {
        return System.currentTimeMillis();
    }

    /**
     * 获得当前月份
     *
     * @return
     */
    public static int month() {
        return calendar().get(Calendar.MONTH) + 1;
    }

    /**
     * 获得月份中的第几天
     *
     * @return
     */
    public static int dayOfMonth() {
        return calendar().get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 今天是星期的第几天
     *
     * @return
     */
    public static int dayOfWeek() {
        return calendar().get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 今天是年中的第几天
     *
     * @return
     */
    public static int dayOfYear() {
        return calendar().get(Calendar.DAY_OF_YEAR);
    }

    /**
     * 判断原日期是否在目标日期之前
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isBefore(Date src, Date dst) {
        return src.before(dst);
    }

    /**
     * 判断原日期是否在目标日期之后
     *
     * @param src
     * @param dst
     * @return
     */
    public static boolean isAfter(Date src, Date dst) {
        return src.after(dst);
    }

    /**
     * 判断两日期是否相同
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqual(Date date1, Date date2) {
        return date1.compareTo(date2) == 0;
    }

    /**
     * 判断某个日期是否在某个日期范围
     *
     * @param beginDate 日期范围开始
     * @param endDate   日期范围结束
     * @param src       需要判断的日期
     * @return
     */
    public static boolean between(Date beginDate, Date endDate, Date src) {
        return beginDate.before(src) && endDate.after(src);
    }

    /**
     * 获得当前月的最后一天
     * <p/>
     * HH:mm:ss为0，毫秒为999
     *
     * @return
     */
    public static Date lastDayOfMonth() {
        Calendar cal = calendar();
        // M月置零
        cal.set(Calendar.DAY_OF_MONTH, 0);
        // H置零
        cal.set(Calendar.HOUR_OF_DAY, 0);
        // m置零
        cal.set(Calendar.MINUTE, 0);
        // s置零
        cal.set(Calendar.SECOND, 0);
        // S置零
        cal.set(Calendar.MILLISECOND, 0);
        // 月份+1
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        // 毫秒-1
        cal.set(Calendar.MILLISECOND, -1);
        return cal.getTime();
    }

    /**
     * 获得当前月的第一天
     * <p/>
     * HH:mm:ss SS为零
     *
     * @return
     */
    public static Date firstDayOfMonth() {
        Calendar cal = calendar();
        // M月置1
        cal.set(Calendar.DAY_OF_MONTH, 1);
        // H置零
        cal.set(Calendar.HOUR_OF_DAY, 0);
        // m置零
        cal.set(Calendar.MINUTE, 0);
        // s置零
        cal.set(Calendar.SECOND, 0);
        // S置零
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取周几日期
     *
     * @param week
     * @return
     */
    public static Date weekDay(int week) {
        Calendar cal = calendar();
        cal.set(Calendar.DAY_OF_WEEK, week);
        return cal.getTime();
    }

    /**
     * 获得周五日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date friday() {
        return weekDay(Calendar.FRIDAY);
    }

    /**
     * 获得周六日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date saturday() {
        return weekDay(Calendar.SATURDAY);
    }

    /**
     * 获得周日日期
     * <p/>
     * 注：日历工厂方法{@link #calendar()}设置类每个星期的第一天为Monday，US等每星期第一天为sunday
     *
     * @return
     */
    public static Date sunday() {
        return weekDay(Calendar.SUNDAY);
    }

    /**
     * 将字符串日期时间转换成java.util.Date类型
     * <p/>
     * 日期时间格式yyyy-MM-dd HH:mm:ss
     *
     * @param datetime
     * @return
     */
    public static Date parseDatetime(String datetime) {
        try {
            return NORM_DATETIME_FORMAT.get().parse(datetime);
        } catch (ParseException e) {
            throw new KitException(e);
        }
    }

    /**
     * 根据自定义pattern将字符串日期转换成java.util.Date类型
     *
     * @param datetime
     * @return
     */
    public static Date parseDatetime(String datetime, String pattern) {
        SimpleDateFormat customFormat = new SimpleDateFormat(pattern);
        try {
            return customFormat.parse(datetime);
        } catch (ParseException e) {
            throw new KitException(e);
        }
    }

    /**
     * 获取当前系统时间
     * <p/>
     * 自定义格式
     *
     * @param pattern
     * @return
     * @throws ParseException
     */
    public static String currDate(String pattern) throws ParseException {
        return formatDatetime(new Date(), pattern);
    }

    public static Long convertString2Long(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date).getTime();
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    public static String convertLong2String(Long date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.format(new Date(date));
        } catch (Exception e) {
            throw new KitException(e);
        }
    }

    /**
     * 增加1天
     *
     * @param dt
     * @return
     * @throws Exception
     */
    public static String addOneDay(String dt) throws Exception {
        return addOneDay(dt, "yyyy-MM-dd");
    }

    /**
     * 增加1天
     *
     * @param dt
     * @param ptn
     * @return
     * @throws Exception
     */
    public static String addOneDay(String dt, String ptn) throws Exception {
        Date date = (new SimpleDateFormat(ptn)).parse(dt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, 1);
        return (new SimpleDateFormat(ptn)).format(cal.getTime());
    }

    /**
     * 减少1天
     *
     * @param dt
     * @return
     * @throws Exception
     */
    public static String subOneDay(String dt) throws Exception {
        return subOneDay(dt, "yyyy-MM-dd");
    }

    /**
     * 减少1天
     *
     * @param dt
     * @param ptn
     * @return
     * @throws Exception
     */
    public static String subOneDay(String dt, String ptn) throws Exception {
        Date date = (new SimpleDateFormat(ptn)).parse(dt);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DATE, -1);
        return (new SimpleDateFormat(ptn)).format(cal.getTime());
    }

    /**
     * 日期加减操作
     *
     * @param currentDate 当前日期
     * @param days
     * @return
     */
    public static String getSpecifiedDayBefore(String currentDate, int days) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = NORM_DATE_FORMAT.get().parse(currentDate);
        } catch (ParseException e) {
            throw new KitException(e);
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + days);

        String dayBefore = NORM_DATE_FORMAT.get().format(c.getTime());
        return dayBefore;
    }

    /**
     * 指定模式的时间格式
     *
     * @param pattern
     * @return
     */
    private static SimpleDateFormat getSDFormat(String pattern) {
        return new SimpleDateFormat(pattern);
    }

    /**
     * 当前日历
     *
     * @return 以当地时区表示的系统当前日历
     */
    public static Calendar getCalendar() {
        return Calendar.getInstance();
    }

    /**
     * 指定毫秒数表示的日历
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日历
     */
    public static Calendar getCalendar(long millis) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(millis));
        return cal;
    }

    /**
     * 时间转字符串
     *
     * @return
     */
    public static String date2SStr() {
        Date date = getDate();
        return NORM_YYYYMMDD_FORMAT.get().format(date);
    }

    /**
     * 当前日期
     *
     * @return 系统当前时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 指定毫秒数表示的日期
     *
     * @param millis 毫秒数
     * @return 指定毫秒数表示的日期
     */
    public static Date getDate(long millis) {
        return new Date(millis);
    }

    /**
     * 时间戳转换为字符串
     *
     * @param time
     * @return
     */
    public static String timestamptoStr(Timestamp time) {
        return date2Str(NORM_DATE_FORMAT.get());
    }

    /**
     * 字符串转换时间戳
     *
     * @param str
     * @return
     */
    public static Timestamp str2Timestamp(String str) {
        Date date = str2Date(str, NORM_DATE_FORMAT.get());
        return new Timestamp(date.getTime());
    }

    /**
     * 字符串转换成日期
     *
     * @param str
     * @param sdf
     * @return
     */
    public static Date str2Date(String str, SimpleDateFormat sdf) {
        if (Func.isEmpty(str)) {
            return null;
        }
        Date date = null;
        try {
            date = sdf.parse(str);
            return date;
        } catch (ParseException e) {
            throw new KitException(e);
        }
    }

    /**
     * 当前日期转换为字符串
     *
     * @param dateFormat
     * @return 字符串
     */
    public static String date2Str(SimpleDateFormat dateFormat) {
        Date date = getDate();
        return dateFormat.format(date);
    }

    /**
     * 格式化时间
     *
     * @param data
     * @param format
     * @return
     */
    public static String dataformat(String data, String format) {
        SimpleDateFormat sformat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sformat.parse(data);
        } catch (ParseException e) {
            throw new KitException(e);
        }
        return sformat.format(date);
    }

    /**
     * 日期转换为字符串
     *
     * @param date       日期
     * @param dateFormat 日期格式
     * @return 字符串
     */
    public static String date2Str(Date date, SimpleDateFormat dateFormat) {
        if (Func.isEmpty(date)) {
            return null;
        }
        return dateFormat.format(date);
    }

    /**
     * 日期转换为字符串
     *
     * @param format 日期格式
     * @return 字符串
     */
    public static String getDate(String format) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(date);
    }

    /**
     * 指定毫秒数的时间戳
     *
     * @param millis 毫秒数
     * @return 指定毫秒数的时间戳
     */
    public static Timestamp getTimestamp(long millis) {
        return new Timestamp(millis);
    }

    /**
     * 以字符形式表示的时间戳
     *
     * @param time 毫秒数
     * @return 以字符形式表示的时间戳
     */
    public static Timestamp getTimestamp(String time) {
        return new Timestamp(Long.parseLong(time));
    }

    /**
     * 系统当前的时间戳
     *
     * @return 系统当前的时间戳
     */
    public static Timestamp getTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    /**
     * 指定日期的时间戳
     *
     * @param date 指定日期
     * @return 指定日期的时间戳
     */
    public static Timestamp getTimestamp(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 指定日历的时间戳
     *
     * @param cal 指定日历
     * @return 指定日历的时间戳
     */
    public static Timestamp getCalendarTimestamp(Calendar cal) {
        return new Timestamp(cal.getTime().getTime());
    }

    public static Timestamp gettimestamp() {
        Date dt = new Date();
        DateFormat df = NORM_DATETIME_FORMAT.get();
        String nowTime = df.format(dt);
        Timestamp buydate = Timestamp.valueOf(nowTime);
        return buydate;
    }

    // ////////////////////////////////////////////////////////////////////////////
    // getMillis
    // 各种方式获取的Millis
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 系统时间的毫秒数
     *
     * @return 系统时间的毫秒数
     */
    public static long getMillis() {
        return System.currentTimeMillis();
    }

    /**
     * 指定日历的毫秒数
     *
     * @param cal 指定日历
     * @return 指定日历的毫秒数
     */
    public static long getMillis(Calendar cal) {
        return cal.getTime().getTime();
    }

    /**
     * 指定日期的毫秒数
     *
     * @param date 指定日期
     * @return 指定日期的毫秒数
     */
    public static long getMillis(Date date) {
        return date.getTime();
    }

    /**
     * 指定时间戳的毫秒数
     *
     * @param ts 指定时间戳
     * @return 指定时间戳的毫秒数
     */
    public static long getMillis(Timestamp ts) {
        return ts.getTime();
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatDate
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日
     *
     * @return 默认日期按“年-月-日“格式显示
     */
    public static String formatDate() {
        return NORM_DATE_FORMAT.get().format(getCalendar().getTime());
    }

    /**
     * 获取时间字符串
     */
    public static String getDataString(SimpleDateFormat formatstr) {
        return formatstr.format(getCalendar().getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日“格式显示
     */
    public static String formatDate(Calendar cal) {
        return NORM_DATE_FORMAT.get().format(cal.getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日“格式显示
     */
    public static String formatDate(long millis) {
        return NORM_DATE_FORMAT.get().format(new Date(millis));
    }

    /**
     * 默认日期按指定格式显示
     *
     * @param pattern 指定的格式
     * @return 默认日期按指定格式显示
     */
    public static String formatDate(String pattern) {
        return getSDFormat(pattern).format(getCalendar().getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param cal     指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Calendar cal, String pattern) {
        return getSDFormat(pattern).format(cal.getTime());
    }

    /**
     * 指定日期按指定格式显示
     *
     * @param date    指定的日期
     * @param pattern 指定的格式
     * @return 指定日期按指定格式显示
     */
    public static String formatDate(Date date, String pattern) {
        return getSDFormat(pattern).format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // formatTime
    // 将日期按照一定的格式转化为字符串
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 默认方式表示的系统当前日期，具体格式：年-月-日 时：分
     *
     * @return 默认日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(long millis) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：年-月-日 时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“年-月-日 时：分“格式显示
     */
    public static String formatTime(Calendar cal) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(cal.getTime());
    }

    /**
     * 默认方式表示的系统当前日期，具体格式：时：分
     *
     * @return 默认日期按“时：分“格式显示
     */
    public static String formatShortTime() {
        return new SimpleDateFormat("HH:mm").format(getCalendar().getTime());
    }

    /**
     * 指定毫秒数表示日期的默认显示，具体格式：时：分
     *
     * @param millis 指定的毫秒数
     * @return 指定毫秒数表示日期按“时：分“格式显示
     */
    public static String formatShortTime(long millis) {
        return new SimpleDateFormat("HH:mm").format(new Date(millis));
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param cal 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Calendar cal) {
        return new SimpleDateFormat("HH:mm").format(cal.getTime());
    }

    /**
     * 指定日期的默认显示，具体格式：时：分
     *
     * @param date 指定的日期
     * @return 指定日期按“时：分“格式显示
     */
    public static String formatShortTime(Date date) {
        return new SimpleDateFormat("HH:mm").format(date);
    }

    // ////////////////////////////////////////////////////////////////////////////
    // parseDate
    // parseCalendar
    // parseTimestamp
    // 将字符串按照一定的格式转化为日期或时间
    // ////////////////////////////////////////////////////////////////////////////

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     */
    public static Date parseDate(String src, String pattern) {
        try {
            return getSDFormat(pattern).parse(src);
        } catch (ParseException e) {
            throw new KitException(e);
        }
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的日期
     */
    public static Calendar parseCalendar(String src, String pattern) {
        Date date = parseDate(src, pattern);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    public static String formatAddDate(String src, String pattern, int amount) {
        Calendar cal;
        cal = parseCalendar(src, pattern);
        cal.add(Calendar.DATE, amount);
        return formatDate(cal);
    }

    /**
     * 根据指定的格式将字符串转换成Date 如输入：2003-11-19 11:20:20将按照这个转成时间
     *
     * @param src     将要转换的原始字符窜
     * @param pattern 转换的匹配格式
     * @return 如果转换成功则返回转换后的时间戳
     * @throws ParseException
     */
    public static Timestamp parseTimestamp(String src, String pattern) {
        Date date = parseDate(src, pattern);
        return new Timestamp(date.getTime());
    }

    /**
     * 计算两个时间之间的差值，根据标志的不同而不同
     *
     * @param flag   计算标志，表示按照年/月/日/时/分/秒等计算
     * @param calSrc 减数
     * @param calDes 被减数
     * @return 两个日期之间的差值
     */
    public static int dateDiff(char flag, Calendar calSrc, Calendar calDes) {

        long millisDiff = getMillis(calSrc) - getMillis(calDes);

        if (flag == Y_CHAR) {
            return (calSrc.get(Calendar.YEAR) - calDes.get(Calendar.YEAR));
        }

        if (flag == D_CHAR) {
            return (int) (millisDiff / DAY_MS);
        }

        if (flag == H_CHAR) {
            return (int) (millisDiff / HOUR_MS);
        }

        if (flag == M_CHAR) {
            return (int) (millisDiff / MINUTE_MS);
        }

        if (flag == S_CHAR) {
            return (int) (millisDiff / SECOND_MS);
        }

        return 0;
    }

    /**
     * 当前时间，格式 yyyy-MM-dd HH:mm:ss
     *
     * @return 当前时间的标准形式字符串
     */
    public static Date now() {
        return new Date();
    }

    /**
     * 当前时间long
     *
     * @param isNano 是否为高精度时间
     * @return 时间
     */
    public static long current(boolean isNano) {
        return isNano ? System.nanoTime() : System.currentTimeMillis();
    }

    /**
     * 当前日期，格式 yyyy-MM-dd
     *
     * @return 当前日期的标准形式字符串
     */
    public static String today() {
        return formatDate(new Date());
    }

    /**
     * @return 当前月份
     */
    public static int thisMonth() {
        return month(date());
    }

    /**
     * @return 今年
     */
    public static int thisYear() {
        return year(date());
    }

    /**
     * @return 当前时间
     */
    public static Date date() {
        return new Date();
    }

    /**
     * Long类型时间转为Date
     *
     * @param date Long类型Date（Unix时间戳）
     * @return 时间对象
     */
    public static Date date(long date) {
        return new Date(date);
    }

    /**
     * 转换为Calendar对象
     *
     * @param date 日期对象
     * @return Calendar对象
     */
    public static Calendar toCalendar(Date date) {
        final Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * 获得月份，从1月开始计数
     *
     * @param date 日期
     * @return 月份
     */
    public static int month(Date date) {
        return toCalendar(date).get(Calendar.MONTH) + 1;
    }

    /**
     * 获得年
     *
     * @param date 日期
     * @return 年
     */
    public static int year(Date date) {
        return toCalendar(date).get(Calendar.YEAR);
    }

    /**
     * 获得季节
     *
     * @param date 日期
     * @return 第几个季节
     */
    public static int season(Date date) {
        return toCalendar(date).get(Calendar.MONTH) / 3 + 1;
    }

    /**
     * 获得指定日期年份和季节<br>
     * 格式：[20131]表示2013年第一季度
     *
     * @param date 日期
     * @return Season ，类似于 20132
     */
    public static String yearAndSeason(Date date) {
        return yearAndSeason(toCalendar(date));
    }

    /**
     * 获得指定日期区间内的年份和季节<br>
     *
     * @param startDate 其实日期（包含）
     * @param endDate   结束日期（包含）
     * @return Season列表 ，元素类似于 20132
     */
    public static LinkedHashSet<String> yearAndSeasons(Date startDate, Date endDate) {
        final LinkedHashSet<String> seasons = new LinkedHashSet<String>();
        if (Func.isOneEmpty(startDate, endDate)) {
            return seasons;
        }

        final Calendar cal = Calendar.getInstance();
        cal.setTime(startDate);
        while (true) {
            // 如果开始时间超出结束时间，让结束时间为开始时间，处理完后结束循环
            if (startDate.after(endDate)) {
                startDate = endDate;
            }

            seasons.add(yearAndSeason(cal));

            if (startDate.equals(endDate)) {
                break;
            }

            cal.add(Calendar.MONTH, 3);
            startDate = cal.getTime();
        }

        return seasons;
    }

    // ------------------------------------ Format start ----------------------------------------------

    /**
     * 根据特定格式格式化日期
     *
     * @param date   被格式化的日期
     * @param format 格式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    /**
     * 格式 yyyy-MM-dd HH:mm:ss
     *
     * @param date 被格式化的日期
     * @return 格式化后的日期
     */
    public static String formatDateTime(Date date) {
        if (Func.isEmpty(date)) {
            return null;
        }
        return NORM_DATETIME_FORMAT.get().format(date);
    }

    /**
     * 格式 yyyy-MM-dd
     *
     * @param date 被格式化的日期
     * @return 格式化后的字符串
     */
    public static String formatDate(Date date) {
        if (Func.isEmpty(date)) {
            return null;
        }
        return NORM_DATE_FORMAT.get().format(date);
    }

    /**
     * 格式化为Http的标准日期格式
     *
     * @param date 被格式化的日期
     * @return HTTP标准形式日期字符串
     */
    public static String formatHttpDate(Date date) {
        if (Func.isEmpty(date)) {
            return null;
        }
        return HTTP_DATETIME_FORMAT.get().format(date);
    }
    // ------------------------------------ Format end ----------------------------------------------

    // ------------------------------------ Parse start ----------------------------------------------

    /**
     * 构建Date对象
     *
     * @param dateStr          Date字符串
     * @param simpleDateFormat 格式化器
     * @return DateTime对象
     */
    public static Date parse(String dateStr, SimpleDateFormat simpleDateFormat) {
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (Exception e) {
            throw new KitException(StrKit.format("Parse [{}] with format [{}] error!", dateStr, simpleDateFormat.toPattern()), e);
        }
    }

    /**
     * 将特定格式的日期转换为Date对象
     *
     * @param dateString 特定格式的日期
     * @param format     格式，例如yyyy-MM-dd
     * @return 日期对象
     */
    public static Date parse(String dateString, String format) {
        return parse(dateString, new SimpleDateFormat(format));
    }

    /**
     * 格式yyyy-MM-dd HH:mm:ss
     *
     * @param dateString 标准形式的时间字符串
     * @return 日期对象
     */
    public static Date parseDateTime(String dateString) {
        return parse(dateString, NORM_DATETIME_FORMAT.get());
    }

    /**
     * 格式yyyy-MM-dd
     *
     * @param dateString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseDate(String dateString) {
        return parse(dateString, NORM_DATE_FORMAT.get());
    }

    /**
     * 格式HH:mm:ss
     *
     * @param timeString 标准形式的日期字符串
     * @return 日期对象
     */
    public static Date parseTime(String timeString) {
        return parse(timeString, NORM_TIME_FORMAT.get());
    }

    /**
     * 格式：<br>
     * 1、yyyy-MM-dd HH:mm:ss<br>
     * 2、yyyy-MM-dd<br>
     * 3、HH:mm:ss<br>
     * 4、yyyy-MM-dd HH:mm 5、yyyy-MM-dd HH:mm:ss.SSS
     *
     * @param dateStr 日期字符串
     * @return 日期
     */
    public static Date parse(String dateStr) {
        if (Func.isEmpty(dateStr)) {
            return null;
        }
        dateStr = dateStr.trim();
        int length = dateStr.length();
        try {
            if (length == NORM_DATETIME_PATTERN.length()) {
                return parseDateTime(dateStr);
            } else if (length == NORM_DATE_PATTERN.length()) {
                return parseDate(dateStr);
            } else if (length == NORM_TIME_PATTERN.length()) {
                return parseTime(dateStr);
            } else if (length == NORM_DATETIME_MINUTE_PATTERN.length()) {
                return parse(dateStr, NORM_DATETIME_MINUTE_PATTERN);
            } else if (length >= NORM_DATETIME_MS_PATTERN.length() - NUM_2) {
                return parse(dateStr, NORM_DATETIME_MS_PATTERN);
            }
        } catch (Exception e) {
            throw new KitException(StrKit.format("Parse [{}] with format normal error!", dateStr));
        }

        // 没有更多匹配的时间格式
        throw new KitException(StrKit.format(" [{}] format is not fit for date pattern!", dateStr));
    }
    // ------------------------------------ Parse end ----------------------------------------------

    // ------------------------------------ Offset start ----------------------------------------------

    /**
     * 获取某天的开始时间
     *
     * @param date 日期
     * @return 某天的开始时间
     */
    public static Date getBeginTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取某天的结束时间
     *
     * @param date 日期
     * @return 某天的结束时间
     */
    public static Date getEndTimeOfDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 昨天
     *
     * @return 昨天
     */
    public static Date yesterday() {
        return offsiteDay(new Date(), -1);
    }

    /**
     * 上周
     *
     * @return 上周
     */
    public static Date lastWeek() {
        return offsiteWeek(new Date(), -1);
    }

    /**
     * 上个月
     *
     * @return 上个月
     */
    public static Date lastMouth() {
        return offsiteMonth(new Date(), -1);
    }

    /**
     * 偏移天
     *
     * @param date    日期
     * @param offsite 偏移天数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsiteDay(Date date, int offsite) {
        return offsiteDate(date, Calendar.DAY_OF_YEAR, offsite);
    }

    /**
     * 偏移周
     *
     * @param date    日期
     * @param offsite 偏移周数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsiteWeek(Date date, int offsite) {
        return offsiteDate(date, Calendar.WEEK_OF_YEAR, offsite);
    }

    /**
     * 偏移月
     *
     * @param date    日期
     * @param offsite 偏移月数，正数向未来偏移，负数向历史偏移
     * @return 偏移后的日期
     */
    public static Date offsiteMonth(Date date, int offsite) {
        return offsiteDate(date, Calendar.MONTH, offsite);
    }

    /**
     * 获取指定日期偏移指定时间后的时间
     *
     * @param date          基准日期
     * @param calendarField 偏移的粒度大小（小时、天、月等）使用Calendar中的常数
     * @param offsite       偏移量，正数为向后偏移，负数为向前偏移
     * @return 偏移后的日期
     */
    public static Date offsiteDate(Date date, int calendarField, int offsite) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarField, offsite);
        return cal.getTime();
    }
    // ------------------------------------ Offset end ----------------------------------------------

    /**
     * 判断两个日期相差的时长<br/>
     * 返回 minuend - subtrahend 的差
     *
     * @param subtrahend 减数日期
     * @param minuend    被减数日期
     * @param diffField  相差的选项：相差的天、小时
     * @return 日期差
     */
    public static long diff(Date subtrahend, Date minuend, long diffField) {
        long diff = minuend.getTime() - subtrahend.getTime();
        return diff / diffField;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：纳秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，纳秒
     */
    public static long spendNt(long preTime) {
        return System.nanoTime() - preTime;
    }

    /**
     * 计时，常用于记录某段代码的执行时间，单位：毫秒
     *
     * @param preTime 之前记录的时间
     * @return 时间差，毫秒
     */
    public static long spendMs(long preTime) {
        return System.currentTimeMillis() - preTime;
    }

    /**
     * 格式化成yyMMddHHmm后转换为int型
     *
     * @param date 日期
     * @return int
     */
    public static int toIntSecond(Date date) {
        return Integer.parseInt(format(date, "yyMMddHHmm"));
    }

    /**
     * 计算指定指定时间区间内的周数
     *
     * @param start 开始时间
     * @param end   结束时间
     * @return 周数
     */
    public static int weekCount(Date start, Date end) {
        final Calendar startCalendar = Calendar.getInstance();
        startCalendar.setTime(start);
        final Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(end);

        final int startWeekofYear = startCalendar.get(Calendar.WEEK_OF_YEAR);
        final int endWeekofYear = endCalendar.get(Calendar.WEEK_OF_YEAR);

        int count = endWeekofYear - startWeekofYear + 1;

        if (Calendar.SUNDAY != startCalendar.get(Calendar.DAY_OF_WEEK)) {
            count--;
        }

        return count;
    }

    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日，标准日期字符串
     * @return 年龄
     * @throws Exception
     */
    public static int ageOfNow(String birthDay) {
        return ageOfNow(parse(birthDay));
    }

    /**
     * 生日转为年龄，计算法定年龄
     *
     * @param birthDay 生日
     * @return 年龄
     * @throws Exception
     */
    public static int ageOfNow(Date birthDay) {
        return age(birthDay, date());
    }

    /**
     * 计算相对于dateToCompare的年龄，长用于计算指定生日在某年的年龄
     *
     * @param birthDay      生日
     * @param dateToCompare 需要对比的日期
     * @return 年龄
     * @throws Exception
     */
    public static int age(Date birthDay, Date dateToCompare) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dateToCompare);

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(StrKit.format("Birthday is after date {}!", formatDate(dateToCompare)));
        }

        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthDay);
        int age = year - cal.get(Calendar.YEAR);

        int monthBirth = cal.get(Calendar.MONTH);
        if (month == monthBirth) {
            int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);
            if (dayOfMonth < dayOfMonthBirth) {
                //如果生日在当月，但是未达到生日当天的日期，年龄减一
                age--;
            }
        } else if (month < monthBirth) {
            //如果当前月份未达到生日的月份，年龄计算减一
            age--;
        }

        return age;
    }

    /**
     * 计时器<br>
     * 计算某个过程话费的时间，精确到毫秒
     *
     * @author Looly
     */
    public static class Timer {
        private long time;
        private boolean isNano;

        public Timer() {
            this(false);
        }

        public Timer(boolean isNano) {
            this.isNano = isNano;
            start();
        }

        /**
         * @return 开始计时并返回当前时间
         */
        public long start() {
            time = current(isNano);
            return time;
        }

        /**
         * @return 重新计时并返回从开始到当前的持续时间
         */
        public long durationRestart() {
            long now = current(isNano);
            long d = now - time;
            time = now;
            return d;
        }

        /**
         * @return 从开始到当前的持续时间
         */
        public long duration() {
            return current(isNano) - time;
        }
    }

    // ------------------------------------------------------------------------ Private method start

    /**
     * 获得指定日期年份和季节<br>
     * 格式：[20131]表示2013年第一季度
     *
     * @param cal 日期
     */
    private static String yearAndSeason(Calendar cal) {
        return new StringBuilder().append(cal.get(Calendar.YEAR)).append(cal.get(Calendar.MONTH) / 3 + 1).toString();
    }
    // ------------------------------------------------------------------------ Private method end
}
