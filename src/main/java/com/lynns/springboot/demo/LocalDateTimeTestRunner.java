package com.lynns.springboot.demo;

import com.lynns.springboot.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * LocalDateTime 测试运行器
 * 在应用启动时自动运行所有示例
 * 
 * @author lynns
 */
@Slf4j
@Component
public class LocalDateTimeTestRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("=== LocalDateTime 测试开始 ===");
        
        // 测试基础操作
        testBasicOperations();
        
        // 测试工具类方法
        testUtilityMethods();
        
        // 测试实际业务场景
        testBusinessScenarios();
        
        log.info("=== LocalDateTime 测试完成 ===");
    }

    /**
     * 测试基础操作
     */
    private void testBasicOperations() {
        log.info("--- 测试基础操作 ---");
        
        // 创建LocalDateTime
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime specific = LocalDateTime.of(2024, 12, 25, 15, 30, 45);
        LocalDateTime parsed = LocalDateTime.parse("2024-06-15T14:30:00");
        
        log.info("当前时间: {}", now);
        log.info("指定时间: {}", specific);
        log.info("解析时间: {}", parsed);
        
        // 格式化
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH时mm分ss秒");
        log.info("中文格式: {}", now.format(formatter));
        
        // 计算
        LocalDateTime future = now.plusDays(30).plusHours(5);
        long daysBetween = ChronoUnit.DAYS.between(now, future);
        long hoursBetween = ChronoUnit.HOURS.between(now, future);
        
        log.info("30天5小时后: {}", future);
        log.info("相差天数: {}", daysBetween);
        log.info("相差小时: {}", hoursBetween);
        
        // 比较
        log.info("现在是否在指定时间之后: {}", now.isAfter(specific));
        log.info("现在是否在解析时间之前: {}", now.isBefore(parsed));
    }

    /**
     * 测试工具类方法
     */
    private void testUtilityMethods() {
        log.info("--- 测试工具类方法 ---");
        
        LocalDateTime now = LocalDateTime.now();
        
        // 测试格式化和解析
        String formatted = DateTimeUtils.format(now);
        LocalDateTime parsedBack = DateTimeUtils.parse(formatted);
        log.info("格式化: {} -> 解析回: {}", formatted, parsedBack);
        
        // 测试时间戳转换
        long timestamp = DateTimeUtils.toTimestamp(now);
        LocalDateTime fromTimestamp = DateTimeUtils.fromTimestamp(timestamp);
        log.info("时间戳: {} -> 转换回: {}", timestamp, fromTimestamp);
        
        // 测试日期范围
        LocalDateTime start = DateTimeUtils.startOfToday();
        LocalDateTime end = DateTimeUtils.endOfToday();
        log.info("今天开始: {}", start);
        log.info("今天结束: {}", end);
        
        // 测试月份边界
        LocalDateTime firstDay = DateTimeUtils.firstDayOfMonth(now);
        LocalDateTime lastDay = DateTimeUtils.lastDayOfMonth(now);
        log.info("本月第一天: {}", firstDay);
        log.info("本月最后一天: {}", lastDay);
        
        // 测试年龄计算
        LocalDateTime birthDate = LocalDateTime.of(1990, 3, 15, 10, 0, 0);
        Map<String, Long> age = DateTimeUtils.calculateAge(birthDate);
        log.info("出生日期: {} -> 年龄: {}岁{}个月{}天", 
                birthDate.toLocalDate(), age.get("years"), age.get("months"), age.get("days"));
        
        // 测试工作日计算
        LocalDate startDate = LocalDate.of(2024, 12, 1);
        LocalDate endDate = LocalDate.of(2024, 12, 31);
        long workingDays = DateTimeUtils.calculateWorkingDays(startDate, endDate);
        log.info("2024年12月工作日: {}", workingDays);
        
        // 测试时间范围生成
        List<LocalDateTime> timeRange = DateTimeUtils.generateTimeRange(
                now, now.plusDays(7), ChronoUnit.DAYS, 1);
        log.info("未来7天时间序列: {}", timeRange.size());
        timeRange.forEach(time -> log.info("  {}", time.toLocalDate()));
        
        // 测试相对时间描述
        String relativeDesc = DateTimeUtils.getRelativeTimeDescription(now.minusHours(2));
        log.info("2小时前的相对描述: {}", relativeDesc);
        
        // 测试时区转换
        Map<String, String> timeZoneConversion = DateTimeUtils.convertTimeZone(
                now, "Asia/Shanghai", "America/New_York");
        log.info("时区转换: {}", timeZoneConversion);
    }

    /**
     * 测试实际业务场景
     */
    private void testBusinessScenarios() {
        log.info("--- 测试业务场景 ---");
        
        // 场景1: 会议安排
        testMeetingSchedule();
        
        // 场景2: 任务截止时间
        testTaskDeadline();
        
        // 场景3: 数据统计时间范围
        testDataStatisticsTimeRange();
        
        // 场景4: 定时任务调度
        testScheduledTaskTiming();
    }

    /**
     * 测试会议安排场景
     */
    private void testMeetingSchedule() {
        log.info("场景1: 会议安排");
        
        LocalDateTime now = LocalDateTime.now();
        
        // 安排下周一上午10点的会议
        LocalDateTime nextMonday = DateTimeUtils.nextDayOfWeek(now, DayOfWeek.MONDAY);
        LocalDateTime meetingTime = nextMonday.withHour(10).withMinute(0).withSecond(0);
        
        long daysUntilMeeting = ChronoUnit.DAYS.between(now.toLocalDate(), meetingTime.toLocalDate());
        String relativeTime = DateTimeUtils.getRelativeTimeDescription(meetingTime);
        
        log.info("当前时间: {}", DateTimeUtils.format(now));
        log.info("会议时间: {}", DateTimeUtils.format(meetingTime));
        log.info("距离会议: {}天", daysUntilMeeting);
        log.info("相对时间: {}", relativeTime);
        
        // 检查是否是工作日
        boolean isWorkingDay = DateTimeUtils.isWorkingDay(meetingTime);
        log.info("会议是否在工作日: {}", isWorkingDay);
    }

    /**
     * 测试任务截止时间场景
     */
    private void testTaskDeadline() {
        log.info("场景2: 任务截止时间");
        
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime deadline = now.plusDays(5).withHour(23).withMinute(59).withSecond(59);
        
        long hoursRemaining = ChronoUnit.HOURS.between(now, deadline);
        long daysRemaining = ChronoUnit.DAYS.between(now, deadline);
        
        log.info("任务截止时间: {}", DateTimeUtils.format(deadline));
        log.info("剩余时间: {}天 ({}小时)", daysRemaining, hoursRemaining);
        
        // 计算完成进度（假设任务是7天前开始的）
        LocalDateTime startTime = now.minusDays(7);
        long totalHours = ChronoUnit.HOURS.between(startTime, deadline);
        long elapsedHours = ChronoUnit.HOURS.between(startTime, now);
        double progress = (double) elapsedHours / totalHours * 100;
        
        log.info("任务进度: {:.1f}%", progress);
        
        // 判断是否紧急（剩余时间少于总时间的20%）
        boolean isUrgent = hoursRemaining < totalHours * 0.2;
        log.info("任务是否紧急: {}", isUrgent);
    }

    /**
     * 测试数据统计时间范围场景
     */
    private void testDataStatisticsTimeRange() {
        log.info("场景3: 数据统计时间范围");
        
        LocalDateTime now = LocalDateTime.now();
        
        // 本周统计
        LocalDateTime startOfWeek = now.with(DayOfWeek.MONDAY).toLocalDate().atStartOfDay();
        LocalDateTime endOfWeek = now.with(DayOfWeek.SUNDAY).toLocalDate().atTime(23, 59, 59);
        
        log.info("本周统计范围: {} 到 {}", 
                DateTimeUtils.format(startOfWeek), DateTimeUtils.format(endOfWeek));
        
        // 本月统计
        LocalDateTime startOfMonth = DateTimeUtils.firstDayOfMonth(now);
        LocalDateTime endOfMonth = DateTimeUtils.lastDayOfMonth(now);
        
        log.info("本月统计范围: {} 到 {}", 
                DateTimeUtils.format(startOfMonth), DateTimeUtils.format(endOfMonth));
        
        // 本季度统计
        LocalDateTime startOfQuarter = DateTimeUtils.firstDayOfQuarter(now);
        LocalDateTime endOfQuarter = DateTimeUtils.lastDayOfQuarter(now);
        
        log.info("本季度统计范围: {} 到 {}", 
                DateTimeUtils.format(startOfQuarter), DateTimeUtils.format(endOfQuarter));
        
        // 生成每日统计时间点
        List<LocalDateTime> dailyPoints = DateTimeUtils.generateTimeRange(
                startOfMonth, endOfMonth, ChronoUnit.DAYS, 1);
        log.info("本月每日统计点数量: {}", dailyPoints.size());
    }

    /**
     * 测试定时任务调度场景
     */
    private void testScheduledTaskTiming() {
        log.info("场景4: 定时任务调度");
        
        LocalDateTime now = LocalDateTime.now();
        
        // 每天凌晨2点执行的任务
        LocalDateTime nextExecution = DateTimeUtils.nextExecutionTime(now, 2, 0);
        long minutesUntilNext = ChronoUnit.MINUTES.between(now, nextExecution);
        
        log.info("当前时间: {}", DateTimeUtils.format(now));
        log.info("下次执行时间: {}", DateTimeUtils.format(nextExecution));
        log.info("距离下次执行: {}分钟", minutesUntilNext);
        
        // 计算过去一周的执行时间
        log.info("过去一周的执行记录:");
        for (int i = 7; i >= 1; i--) {
            LocalDateTime pastExecution = now.minusDays(i).toLocalDate().atTime(2, 0);
            log.info("  {} - {}", DateTimeUtils.format(pastExecution), 
                    pastExecution.getDayOfWeek().toString());
        }
        
        // 计算未来一周的执行时间
        log.info("未来一周的执行计划:");
        for (int i = 1; i <= 7; i++) {
            LocalDateTime futureExecution = now.plusDays(i).toLocalDate().atTime(2, 0);
            log.info("  {} - {}", DateTimeUtils.format(futureExecution), 
                    futureExecution.getDayOfWeek().toString());
        }
    }
}