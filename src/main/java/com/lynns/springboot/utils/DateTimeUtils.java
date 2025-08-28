package com.lynns.springboot.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Stream;

/**
 * 日期时间工具类
 * 提供常用的LocalDateTime操作方法
 * 
 * @author lynns
 */
public class DateTimeUtils {

    // 常用的日期时间格式
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";
    public static final String CHINESE_DATE_TIME_FORMAT = "yyyy年MM月dd日 HH时mm分ss秒";
    public static final String ISO_DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    // 常用时区
    public static final ZoneId SHANGHAI_ZONE = ZoneId.of("Asia/Shanghai");
    public static final ZoneId NEW_YORK_ZONE = ZoneId.of("America/New_York");
    public static final ZoneId LONDON_ZONE = ZoneId.of("Europe/London");
    public static final ZoneId TOKYO_ZONE = ZoneId.of("Asia/Tokyo");

    /**
     * 获取当前日期时间
     * 
     * @return 当前LocalDateTime
     */
    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    /**
     * 获取今天的开始时间 (00:00:00)
     * 
     * @return 今天开始时间
     */
    public static LocalDateTime startOfToday() {
        return LocalDate.now().atStartOfDay();
    }

    /**
     * 获取今天的结束时间 (23:59:59.999)
     * 
     * @return 今天结束时间
     */
    public static LocalDateTime endOfToday() {
        return LocalDate.now().atTime(23, 59, 59, 999_999_999);
    }

    /**
     * 获取指定日期的开始时间
     * 
     * @param date 指定日期
     * @return 该日期的开始时间
     */
    public static LocalDateTime startOfDay(LocalDate date) {
        return date.atStartOfDay();
    }

    /**
     * 获取指定日期的结束时间
     * 
     * @param date 指定日期
     * @return 该日期的结束时间
     */
    public static LocalDateTime endOfDay(LocalDate date) {
        return date.atTime(23, 59, 59, 999_999_999);
    }

    /**
     * 格式化日期时间
     * 
     * @param dateTime 日期时间
     * @param pattern  格式模式
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * 使用默认格式格式化日期时间
     * 
     * @param dateTime 日期时间
     * @return 格式化后的字符串
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 解析日期时间字符串
     * 
     * @param dateTimeStr 日期时间字符串
     * @param pattern     格式模式
     * @return LocalDateTime对象
     */
    public static LocalDateTime parse(String dateTimeStr, String pattern) {
        if (dateTimeStr == null || dateTimeStr.trim().isEmpty()) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }

    /**
     * 使用默认格式解析日期时间字符串
     * 
     * @param dateTimeStr 日期时间字符串
     * @return LocalDateTime对象
     */
    public static LocalDateTime parse(String dateTimeStr) {
        return parse(dateTimeStr, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * 将LocalDateTime转换为时间戳（秒）
     * 
     * @param dateTime LocalDateTime对象
     * @return 时间戳
     */
    public static long toTimestamp(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toEpochSecond();
    }

    /**
     * 将LocalDateTime转换为时间戳（毫秒）
     * 
     * @param dateTime LocalDateTime对象
     * @return 时间戳（毫秒）
     */
    public static long toTimestampMilli(LocalDateTime dateTime) {
        return dateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 从时间戳创建LocalDateTime（秒）
     * 
     * @param timestamp 时间戳（秒）
     * @return LocalDateTime对象
     */
    public static LocalDateTime fromTimestamp(long timestamp) {
        return LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.systemDefault());
    }

    /**
     * 从时间戳创建LocalDateTime（毫秒）
     * 
     * @param timestampMilli 时间戳（毫秒）
     * @return LocalDateTime对象
     */
    public static LocalDateTime fromTimestampMilli(long timestampMilli) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(timestampMilli), ZoneId.systemDefault());
    }

    /**
     * 计算两个日期时间之间的差值
     * 
     * @param start 开始时间
     * @param end   结束时间
     * @param unit  时间单位
     * @return 差值
     */
    public static long between(LocalDateTime start, LocalDateTime end, ChronoUnit unit) {
        return unit.between(start, end);
    }

    /**
     * 检查日期时间是否在指定范围内
     * 
     * @param dateTime 要检查的日期时间
     * @param start    范围开始时间
     * @param end      范围结束时间
     * @return 是否在范围内
     */
    public static boolean isBetween(LocalDateTime dateTime, LocalDateTime start, LocalDateTime end) {
        return !dateTime.isBefore(start) && !dateTime.isAfter(end);
    }

    /**
     * 获取月份的第一天
     * 
     * @param dateTime 日期时间
     * @return 月份第一天
     */
    public static LocalDateTime firstDayOfMonth(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.firstDayOfMonth()).toLocalDate().atStartOfDay();
    }

    /**
     * 获取月份的最后一天
     * 
     * @param dateTime 日期时间
     * @return 月份最后一天
     */
    public static LocalDateTime lastDayOfMonth(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfMonth()).toLocalDate().atTime(23, 59, 59);
    }

    /**
     * 获取年份的第一天
     * 
     * @param dateTime 日期时间
     * @return 年份第一天
     */
    public static LocalDateTime firstDayOfYear(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.firstDayOfYear()).toLocalDate().atStartOfDay();
    }

    /**
     * 获取年份的最后一天
     * 
     * @param dateTime 日期时间
     * @return 年份最后一天
     */
    public static LocalDateTime lastDayOfYear(LocalDateTime dateTime) {
        return dateTime.with(TemporalAdjusters.lastDayOfYear()).toLocalDate().atTime(23, 59, 59);
    }

    /**
     * 获取下一个指定星期几的日期
     * 
     * @param dateTime  日期时间
     * @param dayOfWeek 星期几
     * @return 下一个指定星期几的日期
     */
    public static LocalDateTime nextDayOfWeek(LocalDateTime dateTime, DayOfWeek dayOfWeek) {
        return dateTime.with(TemporalAdjusters.next(dayOfWeek));
    }

    /**
     * 获取上一个指定星期几的日期
     * 
     * @param dateTime  日期时间
     * @param dayOfWeek 星期几
     * @return 上一个指定星期几的日期
     */
    public static LocalDateTime previousDayOfWeek(LocalDateTime dateTime, DayOfWeek dayOfWeek) {
        return dateTime.with(TemporalAdjusters.previous(dayOfWeek));
    }

    /**
     * 计算年龄
     * 
     * @param birthDate 出生日期
     * @return 年龄信息（年、月、天）
     */
    public static Map<String, Long> calculateAge(LocalDateTime birthDate) {
        LocalDateTime now = LocalDateTime.now();
        
        long years = ChronoUnit.YEARS.between(birthDate, now);
        long months = ChronoUnit.MONTHS.between(birthDate.plusYears(years), now);
        long days = ChronoUnit.DAYS.between(birthDate.plusYears(years).plusMonths(months), now);
        
        Map<String, Long> age = new HashMap<>();
        age.put("years", years);
        age.put("months", months);
        age.put("days", days);
        
        return age;
    }

    /**
     * 计算工作日数量（排除周末）
     * 
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 工作日数量
     */
    public static long calculateWorkingDays(LocalDate startDate, LocalDate endDate) {
        long workingDays = 0;
        LocalDate current = startDate;
        
        while (!current.isAfter(endDate)) {
            DayOfWeek dayOfWeek = current.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            current = current.plusDays(1);
        }
        
        return workingDays;
    }

    /**
     * 生成时间范围列表
     * 
     * @param start          开始时间
     * @param end            结束时间
     * @param unit           时间单位
     * @param intervalAmount 间隔数量
     * @return 时间列表
     */
    public static List<LocalDateTime> generateTimeRange(LocalDateTime start, LocalDateTime end, 
                                                       ChronoUnit unit, int intervalAmount) {
        List<LocalDateTime> timeList = new ArrayList<>();
        LocalDateTime current = start;
        
        while (!current.isAfter(end)) {
            timeList.add(current);
            current = current.plus(intervalAmount, unit);
        }
        
        return timeList;
    }

    /**
     * 时区转换
     * 
     * @param localDateTime 本地时间
     * @param fromZone      源时区
     * @param toZone        目标时区
     * @return 转换结果
     */
    public static Map<String, String> convertTimeZone(LocalDateTime localDateTime, 
                                                     String fromZone, String toZone) {
        ZoneId fromZoneId = ZoneId.of(fromZone);
        ZoneId toZoneId = ZoneId.of(toZone);
        
        ZonedDateTime fromZonedDateTime = localDateTime.atZone(fromZoneId);
        ZonedDateTime toZonedDateTime = fromZonedDateTime.withZoneSameInstant(toZoneId);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        
        Map<String, String> result = new HashMap<>();
        result.put("originalTime", fromZonedDateTime.format(formatter));
        result.put("convertedTime", toZonedDateTime.format(formatter));
        result.put("fromZone", fromZone);
        result.put("toZone", toZone);
        
        return result;
    }

    /**
     * 获取周数（一年中的第几周）
     * 
     * @param dateTime 日期时间
     * @return 周数
     */
    public static int getWeekOfYear(LocalDateTime dateTime) {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return dateTime.get(weekFields.weekOfYear());
    }

    /**
     * 检查是否是工作日
     * 
     * @param dateTime 日期时间
     * @return 是否是工作日
     */
    public static boolean isWorkingDay(LocalDateTime dateTime) {
        DayOfWeek dayOfWeek = dateTime.getDayOfWeek();
        return dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY;
    }

    /**
     * 检查是否是周末
     * 
     * @param dateTime 日期时间
     * @return 是否是周末
     */
    public static boolean isWeekend(LocalDateTime dateTime) {
        return !isWorkingDay(dateTime);
    }

    /**
     * 检查是否是今天
     * 
     * @param dateTime 日期时间
     * @return 是否是今天
     */
    public static boolean isToday(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(LocalDate.now());
    }

    /**
     * 检查是否是昨天
     * 
     * @param dateTime 日期时间
     * @return 是否是昨天
     */
    public static boolean isYesterday(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(LocalDate.now().minusDays(1));
    }

    /**
     * 检查是否是明天
     * 
     * @param dateTime 日期时间
     * @return 是否是明天
     */
    public static boolean isTomorrow(LocalDateTime dateTime) {
        return dateTime.toLocalDate().equals(LocalDate.now().plusDays(1));
    }

    /**
     * 获取两个日期之间的所有日期
     * 
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return 日期列表
     */
    public static List<LocalDate> getDatesBetween(LocalDate startDate, LocalDate endDate) {
        return Stream.iterate(startDate, date -> date.plusDays(1))
                     .limit(ChronoUnit.DAYS.between(startDate, endDate) + 1)
                     .toList();
    }

    /**
     * 获取指定日期所在季度的第一天
     * 
     * @param dateTime 日期时间
     * @return 季度第一天
     */
    public static LocalDateTime firstDayOfQuarter(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue();
        int quarterStartMonth = ((month - 1) / 3) * 3 + 1;
        return dateTime.withMonth(quarterStartMonth).withDayOfMonth(1).toLocalDate().atStartOfDay();
    }

    /**
     * 获取指定日期所在季度的最后一天
     * 
     * @param dateTime 日期时间
     * @return 季度最后一天
     */
    public static LocalDateTime lastDayOfQuarter(LocalDateTime dateTime) {
        int month = dateTime.getMonthValue();
        int quarterEndMonth = ((month - 1) / 3 + 1) * 3;
        return dateTime.withMonth(quarterEndMonth)
                      .with(TemporalAdjusters.lastDayOfMonth())
                      .toLocalDate().atTime(23, 59, 59);
    }

    /**
     * 计算下次执行时间（基于cron表达式简化版本）
     * 
     * @param currentTime 当前时间
     * @param hour        小时
     * @param minute      分钟
     * @return 下次执行时间
     */
    public static LocalDateTime nextExecutionTime(LocalDateTime currentTime, int hour, int minute) {
        LocalDateTime nextExecution = currentTime.toLocalDate().atTime(hour, minute);
        
        // 如果今天的执行时间已过，则安排到明天
        if (!nextExecution.isAfter(currentTime)) {
            nextExecution = nextExecution.plusDays(1);
        }
        
        return nextExecution;
    }

    /**
     * 获取相对时间描述
     * 
     * @param dateTime 日期时间
     * @return 相对时间描述
     */
    public static String getRelativeTimeDescription(LocalDateTime dateTime) {
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(dateTime, now);
        
        if (minutes < 0) {
            // 未来时间
            minutes = Math.abs(minutes);
            if (minutes < 60) {
                return minutes + "分钟后";
            } else if (minutes < 1440) { // 24小时
                return (minutes / 60) + "小时后";
            } else {
                return (minutes / 1440) + "天后";
            }
        } else {
            // 过去时间
            if (minutes < 1) {
                return "刚刚";
            } else if (minutes < 60) {
                return minutes + "分钟前";
            } else if (minutes < 1440) { // 24小时
                return (minutes / 60) + "小时前";
            } else {
                return (minutes / 1440) + "天前";
            }
        }
    }
}