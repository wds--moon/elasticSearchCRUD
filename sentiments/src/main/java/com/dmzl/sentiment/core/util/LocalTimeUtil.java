package com.dmzl.sentiment.core.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import static java.time.temporal.ChronoField.DAY_OF_MONTH;
import static java.time.temporal.ChronoField.DAY_OF_YEAR;
import static java.time.temporal.ChronoUnit.MONTHS;
import static java.time.temporal.ChronoUnit.YEARS;
import static java.time.temporal.TemporalAdjusters.firstDayOfMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextMonth;
import static java.time.temporal.TemporalAdjusters.firstDayOfNextYear;
import static java.time.temporal.TemporalAdjusters.firstDayOfYear;
import static java.time.temporal.TemporalAdjusters.firstInMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;
import static java.time.temporal.TemporalAdjusters.lastDayOfYear;
import static java.time.temporal.TemporalAdjusters.next;
import static java.time.temporal.TemporalAdjusters.nextOrSame;
import static java.time.temporal.TemporalAdjusters.previous;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

/**
 * @Description: 时间格式化工具类(jdk8 实现)
 * 如果要实现add day,hour等直接使用LocalDateTime.now().plusDays(-2)不需要在写工具类方法
 *特别说明一下DateTimeFormatter 代替之前的SimpleDateFormat
 * LocalDateTime.now(ZoneOffset.of("+8")) 可以指定时区,不指定默认是操作系统的默认的时区
 * Instant：表示时刻，不直接对应年月日信息，需要通过时区转换
 * LocalDateTime: 表示与时区无关的日期和时间信息，不直接对应时刻，需要通过时区转换
 * LocalDate：表示与时区无关的日期，与LocalDateTime相比，只有日期信息，没有时间信息
 * LocalTime：表示与时区无关的时间，与LocalDateTime相比，只有时间信息，没有日期信息
 * ZonedDateTime： 表示特定时区的日期和时间
 * ZoneId/ZoneOffset：表示时区
 *
 * @Author: moon
 * @CreateDate: 2018/12/24 0024 09:51
 * @UpdateUser: moon
 * @UpdateDate: 2018/12/24 0024 09:51
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class LocalTimeUtil {

    /**
     * 获取默认时间格式: yyyy-MM-dd HH:mm:ss
     */
    private static final DateTimeFormatter DEFAULT_DATETIME_FORMATTER = TimeFormat.LONG_DATE_PATTERN_LINE.formatter;

    public LocalTimeUtil() {
    }

    /**
     * String 转时间
     *
     * @param timeStr
     * @return
     */
    public static LocalDateTime parseTime(String timeStr) {
        return LocalDateTime.parse(timeStr, DEFAULT_DATETIME_FORMATTER);
    }

    /**
     * String 转时间
     *
     * @param timeStr
     * @param format             时间格式
     * @return
     */
    public static LocalDateTime parseTime(String timeStr, TimeFormat format) {
        return LocalDateTime.parse(timeStr, format.formatter);
    }


    public static LocalDate parseLocalDate(String timeStr, TimeFormat format) {
        return LocalDate.parse(timeStr, format.formatter);
    }


    /**
     * 时间转 String
     *
     * @param time
     * @return
     */
    public static String parseTime(LocalDateTime time) {
        return DEFAULT_DATETIME_FORMATTER.format(time);
    }


    /**
     * 时间转 String
     *
     * @param time
     * @param format            时间格式
     * @return
     */
    public static String parseTime(LocalDateTime time, TimeFormat format) {
        return format.formatter.format(time);
    }


    public static String parseLocalDate(LocalDate time, TimeFormat format) {
        return format.formatter.format(time);
    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static String getCurrentDatetime() {
        return DEFAULT_DATETIME_FORMATTER.format(LocalDateTime.now());
    }


    /**
     * 获取当前时间
     *
     * @param format            时间格式
     * @return
     */
    public static String getCurrentDatetime(TimeFormat format) {
        return format.formatter.format(LocalDateTime.now());
    }
    /**
     * 获取Period（时间段）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static Period localDateDiff(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        return p;
    }


    /**
     * 获取时间间隔，并格式化为XXXX年XX月XX日
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static String localDateDiffFormat(LocalDate lt, LocalDate gt) {
        Period p = Period.between(lt, gt);
        String str = String.format(" %d年 %d月 %d日", p.getYears(), p.getMonths(), p.getDays());
        return str;
    }


    /**
     * 获取Duration（持续时间）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static Duration localTimeDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d;
    }


    /**
     * 获取时间间隔（毫秒）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long millisDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.toMillis();
    }


    /**
     * 获取时间间隔（秒）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long secondDiff(LocalTime lt, LocalTime gt) {
        Duration d = Duration.between(lt, gt);
        return d.getSeconds();
    }


    /**
     * 获取时间间隔（天）
     *
     * @param format
     *            较小时间
     * @param format
     *            较大时间
     * @return
     */
    public static long daysDiff(LocalDate lt, LocalDate gt) {
        long daysDiff = ChronoUnit.DAYS.between(lt, gt);
        return daysDiff;
    }


    /**
     * 创建一个新的日期，它的值为上月的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, 1).plus(-1, MONTHS));


    }
    /**
     * 创建一个新的日期，它的值为上月的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfLastMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum()).plus(-1, MONTHS));


    }
    /**
     * 创建一个新的日期，它的值为当月的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfMonth(LocalDate date) {
        return date.with(firstDayOfMonth());


    }
    /**
     * 创建一个新的日期，它的值为当月的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfMonth(LocalDate date) {
        return date.with(lastDayOfMonth());
    }
    /**
     * 创建一个新的日期，它的值为下月的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfNextMonth(LocalDate date) {
        return date.with(firstDayOfNextMonth());


    }
    /**
     * 创建一个新的日期，它的值为下月的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfNextMonth(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_MONTH, temporal.range(DAY_OF_MONTH).getMaximum()).plus(1, MONTHS));


    }



    /**
     * 创建一个新的日期，它的值为上年的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, 1).plus(-1, YEARS));
    }
    /**
     * 创建一个新的日期，它的值为上年的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfLastYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, temporal.range(DAY_OF_YEAR).getMaximum()).plus(-1, YEARS));
    }


    /**
     * 创建一个新的日期，它的值为当年的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfYear(LocalDate date) {
        return date.with(firstDayOfYear());
    }


    /**
     * 创建一个新的日期，它的值为今年的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfYear(LocalDate date) {
        return date.with(lastDayOfYear());
    }


    /**
     * 创建一个新的日期，它的值为明年的第一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstDayOfNextYear(LocalDate date) {
        return date.with(firstDayOfNextYear());
    }


    /**
     * 创建一个新的日期，它的值为明年的最后一天
     * 
     * @param date
     * @return
     */
    public static LocalDate getLastDayOfNextYear(LocalDate date) {
        return date.with((temporal) -> temporal.with(DAY_OF_YEAR, temporal.range(DAY_OF_YEAR).getMaximum()).plus(1, YEARS));
    }


    /**
     * 创建一个新的日期，它的值为同一个月中，第一个符合星期几要求的值
     * 
     * @param date
     * @return
     */
    public static LocalDate getFirstInMonth(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(firstInMonth(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期
     * 
     * @param date
     * @return
     */
    public static LocalDate getNext(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(next(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期
     * 
     * @param date
     * @return
     */
    public static LocalDate getPrevious(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(previous(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期，如果该日期已经符合要求，直接返回该对象
     * 
     * @param date
     * @return
     */
    public static LocalDate getNextOrSame(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(nextOrSame(dayOfWeek));
    }


    /**
     * 创建一个新的日期，并将其值设定为日期调整后或者调整前，第一个符合指定星 期几要求的日期，如果该日期已经符合要求，直接返回该对象
     * 
     * @param date
     * @return
     */
    public static LocalDate getPreviousOrSame(LocalDate date, DayOfWeek dayOfWeek) {
        return date.with(previousOrSame(dayOfWeek));
    }

    /**
     *  date 转LocalDateTime jdk1.8 提供的方法互转toInstant
     *  默认指定时区
     * @param date
     * @return
     */
    public static LocalDateTime dateConvertToLocalDateTime(Date date) {
        return date.toInstant().atOffset(ZoneOffset.of("+8")).toLocalDateTime();
    }

    /**
     * localDateTime 转 Date jdk1.8 提供的方法互转from
     * 默认指定时区
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeConvertToDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(ZoneOffset.of("+8")));
    }

    /**
     * 验证时间是否是指定格式
     * @param str
     * @param p
     * @return
     */
    public static  boolean isValidDate(String str, DateTimeFormatter p){
        boolean convertSuccess = true;
        try {
            LocalDate parse = LocalDate.parse(str, p);
        }catch (Exception e){
            convertSuccess=false;
        }
        return convertSuccess;
    }
//    public static void main(String[] args) {
//        LocalDateTime localDateTime = LocalTimeUtil.parseTime("2018-12-15 20:00:00");
//        System.out.println(localDateTime.format(TimeFormat.SHORT_DATE_PATTERN_NONE.formatter));
//        System.out.println(TimeFormat.SHORT_DATE_PATTERN_NONE.formatter.format(LocalDateTime.now()));
//        System.out.println(LocalTimeUtil.getFirstDayOfLastMonth(LocalDate.now()));
//        System.out.println(LocalDateTime.now().plusDays(-2).format(TimeFormat.LONG_DATE_PATTERN_NONE.formatter));
//
//        System.out.println(LocalDateTime.now().plusHours(-2).format(TimeFormat.LONG_DATE_PATTERN_NONE.formatter));
//        System.out.println(Clock.system(ZoneId.of("Asia/Tokyo")).instant().toEpochMilli());
//        System.out.println(LocalTimeUtil.isValidDate("2018-12-15", TimeFormat.SHORT_DATE_PATTERN_LINE.formatter));
//        System.out.println(TimeFormat.SHORT_DATE_PATTERN_LINE_HURE.getMsg());
//
//
//    }

}
