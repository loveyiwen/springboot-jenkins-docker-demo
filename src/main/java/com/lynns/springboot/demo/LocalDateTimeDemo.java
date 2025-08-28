package com.lynns.springboot.demo;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

/**
 * LocalDateTime 代码案例演示
 * 展示 LocalDateTime 的各种常用操作
 * 
 * @author lynns
 */
public class LocalDateTimeDemo {

    public static void main(String[] args) {
        System.out.println("=== LocalDateTime 代码案例演示 ===");
        
        // 1. 创建 LocalDateTime 实例
        createLocalDateTimeExamples();
        
        // 2. 格式化和解析
        formatAndParseExamples();
        
        // 3. 日期时间计算
        calculationExamples();
        
        // 4. 比较操作
        comparisonExamples();
        
        // 5. 时区转换
        timeZoneExamples();
        
        // 6. 实际应用场景
        practicalExamples();
    }

    /**
     * 1. 创建 LocalDateTime 实例的各种方法
     */
    private static void createLocalDateTimeExamples() {
        System.out.println("\n--- 1. 创建 LocalDateTime 实例 ---");
        
        // 获取当前日期时间
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间: " + now);
        
        // 指定日期时间
        LocalDateTime specificDateTime = LocalDateTime.of(2024, 12, 25, 10, 30, 45);
        System.out.println("指定时间: " + specificDateTime);
        
        // 从 LocalDate 和 LocalTime 组合
        LocalDate date = LocalDate.of(2024, 6, 15);
        LocalTime time = LocalTime.of(14, 30, 0);
        LocalDateTime combined = LocalDateTime.of(date, time);
        System.out.println("组合时间: " + combined);
        
        // 从字符串解析
        LocalDateTime parsed = LocalDateTime.parse("2024-08-20T16:45:30");
        System.out.println("解析时间: " + parsed);
        
        // 获取今天的开始和结束时间
        LocalDateTime startOfDay = now.toLocalDate().atStartOfDay();
        LocalDateTime endOfDay = now.toLocalDate().atTime(23, 59, 59);
        System.out.println("今天开始: " + startOfDay);
        System.out.println("今天结束: " + endOfDay);
    }

    /**
     * 2. 格式化和解析示例
     */
    private static void formatAndParseExamples() {
        System.out.println("\n--- 2. 格式化和解析 ---");
        
        LocalDateTime now = LocalDateTime.now();
        
        // 常用格式化
        DateTimeFormatter formatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("标准格式: " + now.format(formatter1));
        
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        System.out.println("中文格式: " + now.format(formatter2));
        
        DateTimeFormatter formatter3 = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        System.out.println("简化格式: " + now.format(formatter3));
        
        // ISO 格式
        System.out.println("ISO格式: " + now.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // 解析不同格式的字符串
        try {
            LocalDateTime parsed1 = LocalDateTime.parse("2024-12-25 10:30:45", formatter1);
            System.out.println("解析结果1: " + parsed1);
            
            LocalDateTime parsed2 = LocalDateTime.parse("2024年12月25日 10时30分45秒", formatter2);
            System.out.println("解析结果2: " + parsed2);
        } catch (Exception e) {
            System.out.println("解析错误: " + e.getMessage());
        }
    }

    /**
     * 3. 日期时间计算示例
     */
    private static void calculationExamples() {
        System.out.println("\n--- 3. 日期时间计算 ---");
        
        LocalDateTime now = LocalDateTime.now();
        System.out.println("当前时间: " + now);
        
        // 加减操作
        LocalDateTime plusDays = now.plusDays(7);
        System.out.println("7天后: " + plusDays);
        
        LocalDateTime minusHours = now.minusHours(3);
        System.out.println("3小时前: " + minusHours);
        
        LocalDateTime plusMonths = now.plusMonths(2);
        System.out.println("2个月后: " + plusMonths);
        
        // 使用 ChronoUnit 进行计算
        LocalDateTime plus90Minutes = now.plus(90, ChronoUnit.MINUTES);
        System.out.println("90分钟后: " + plus90Minutes);
        
        // 计算时间间隔
        LocalDateTime future = now.plusDays(10).plusHours(5).plusMinutes(30);
        long daysBetween = ChronoUnit.DAYS.between(now, future);
        long hoursBetween = ChronoUnit.HOURS.between(now, future);
        long minutesBetween = ChronoUnit.MINUTES.between(now, future);
        
        System.out.println("时间间隔:");
        System.out.println("  相差天数: " + daysBetween);
        System.out.println("  相差小时: " + hoursBetween);
        System.out.println("  相差分钟: " + minutesBetween);
        
        // 使用 TemporalAdjusters 进行高级调整
        LocalDateTime firstDayOfMonth = now.with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDayOfMonth = now.with(TemporalAdjusters.lastDayOfMonth());
        LocalDateTime nextMonday = now.with(TemporalAdjusters.next(DayOfWeek.MONDAY));
        
        System.out.println("本月第一天: " + firstDayOfMonth);
        System.out.println("本月最后一天: " + lastDayOfMonth);
        System.out.println("下个周一: " + nextMonday);
    }

    /**
     * 4. 比较操作示例
     */
    private static void comparisonExamples() {
        System.out.println("\n--- 4. 比较操作 ---");
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime future = now.plusDays(1);
        LocalDateTime past = now.minusDays(1);
        
        System.out.println("现在: " + now);
        System.out.println("未来: " + future);
        System.out.println("过去: " + past);
        
        // 比较方法
        System.out.println("now.isAfter(past): " + now.isAfter(past));
        System.out.println("now.isBefore(future): " + now.isBefore(future));
        System.out.println("now.isEqual(now): " + now.isEqual(now));
        
        // compareTo 方法
        System.out.println("now.compareTo(past): " + now.compareTo(past));
        System.out.println("now.compareTo(future): " + now.compareTo(future));
        System.out.println("now.compareTo(now): " + now.compareTo(now));
        
        // 检查是否在某个时间范围内
        LocalDateTime start = LocalDateTime.of(2024, 1, 1, 0, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 23, 59, 59);
        boolean isInRange = !now.isBefore(start) && !now.isAfter(end);
        System.out.println("是否在2024年内: " + isInRange);
    }

    /**
     * 5. 时区转换示例
     */
    private static void timeZoneExamples() {
        System.out.println("\n--- 5. 时区转换 ---");
        
        LocalDateTime localDateTime = LocalDateTime.now();
        System.out.println("本地时间: " + localDateTime);
        
        // 转换为不同时区的时间
        ZoneId shanghaiZone = ZoneId.of("Asia/Shanghai");
        ZoneId newYorkZone = ZoneId.of("America/New_York");
        ZoneId londonZone = ZoneId.of("Europe/London");
        ZoneId tokyoZone = ZoneId.of("Asia/Tokyo");
        
        // 将 LocalDateTime 转换为 ZonedDateTime
        ZonedDateTime shanghaiTime = localDateTime.atZone(shanghaiZone);
        ZonedDateTime newYorkTime = shanghaiTime.withZoneSameInstant(newYorkZone);
        ZonedDateTime londonTime = shanghaiTime.withZoneSameInstant(londonZone);
        ZonedDateTime tokyoTime = shanghaiTime.withZoneSameInstant(tokyoZone);
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z");
        
        System.out.println("上海时间: " + shanghaiTime.format(formatter));
        System.out.println("纽约时间: " + newYorkTime.format(formatter));
        System.out.println("伦敦时间: " + londonTime.format(formatter));
        System.out.println("东京时间: " + tokyoTime.format(formatter));
        
        // 获取时间戳
        long timestamp = shanghaiTime.toEpochSecond();
        System.out.println("时间戳: " + timestamp);
        
        // 从时间戳恢复
        LocalDateTime fromTimestamp = LocalDateTime.ofEpochSecond(timestamp, 0, ZoneOffset.of("+8"));
        System.out.println("从时间戳恢复: " + fromTimestamp);
    }

    /**
     * 6. 实际应用场景示例
     */
    private static void practicalExamples() {
        System.out.println("\n--- 6. 实际应用场景 ---");
        
        // 场景1: 计算年龄
        calculateAge();
        
        // 场景2: 生成时间范围
        generateTimeRanges();
        
        // 场景3: 工作日计算
        calculateWorkingDays();
        
        // 场景4: 定时任务时间计算
        calculateScheduledTimes();
    }

    /**
     * 计算年龄
     */
    private static void calculateAge() {
        System.out.println("\n场景1: 计算年龄");
        
        LocalDateTime birthDate = LocalDateTime.of(1990, 5, 15, 10, 0, 0);
        LocalDateTime now = LocalDateTime.now();
        
        long years = ChronoUnit.YEARS.between(birthDate, now);
        long months = ChronoUnit.MONTHS.between(birthDate.plusYears(years), now);
        long days = ChronoUnit.DAYS.between(birthDate.plusYears(years).plusMonths(months), now);
        
        System.out.println("出生日期: " + birthDate.toLocalDate());
        System.out.println("当前年龄: " + years + "岁 " + months + "个月 " + days + "天");
    }

    /**
     * 生成时间范围
     */
    private static void generateTimeRanges() {
        System.out.println("\n场景2: 生成时间范围");
        
        LocalDateTime start = LocalDateTime.now().withHour(9).withMinute(0).withSecond(0);
        LocalDateTime end = start.plusHours(8); // 工作8小时
        
        List<LocalDateTime> timeSlots = new ArrayList<>();
        LocalDateTime current = start;
        
        // 每小时生成一个时间点
        while (!current.isAfter(end)) {
            timeSlots.add(current);
            current = current.plusHours(1);
        }
        
        System.out.println("工作时间段:");
        timeSlots.forEach(time -> System.out.println("  " + time.format(DateTimeFormatter.ofPattern("HH:mm"))));
    }

    /**
     * 计算工作日
     */
    private static void calculateWorkingDays() {
        System.out.println("\n场景3: 工作日计算");
        
        LocalDateTime start = LocalDateTime.of(2024, 12, 1, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 12, 31, 18, 0, 0);
        
        long totalDays = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate()) + 1;
        long workingDays = 0;
        
        LocalDate current = start.toLocalDate();
        LocalDate endDate = end.toLocalDate();
        
        while (!current.isAfter(endDate)) {
            DayOfWeek dayOfWeek = current.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                workingDays++;
            }
            current = current.plusDays(1);
        }
        
        System.out.println("总天数: " + totalDays);
        System.out.println("工作日: " + workingDays);
        System.out.println("周末: " + (totalDays - workingDays));
    }

    /**
     * 定时任务时间计算
     */
    private static void calculateScheduledTimes() {
        System.out.println("\n场景4: 定时任务时间计算");
        
        LocalDateTime now = LocalDateTime.now();
        
        // 计算下次执行时间 (每天凌晨2点)
        LocalDateTime nextSchedule = now.toLocalDate().plusDays(1).atTime(2, 0, 0);
        if (now.getHour() < 2) {
            nextSchedule = now.toLocalDate().atTime(2, 0, 0);
        }
        
        long minutesUntilNext = ChronoUnit.MINUTES.between(now, nextSchedule);
        
        System.out.println("当前时间: " + now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("下次执行: " + nextSchedule.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        System.out.println("距离下次执行还有: " + minutesUntilNext + " 分钟");
        
        // 计算过去7天的每日任务执行时间
        System.out.println("\n过去7天的任务执行时间:");
        for (int i = 6; i >= 0; i--) {
            LocalDateTime taskTime = now.minusDays(i).toLocalDate().atTime(2, 0, 0);
            System.out.println("  " + taskTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) + 
                             " (" + taskTime.getDayOfWeek().toString() + ")");
        }
    }
}