package com.lynns.springboot.controller;

import com.lynns.springboot.dto.DateTimeRequest;
import com.lynns.springboot.dto.DateTimeResponse;
import com.lynns.springboot.utils.DateTimeUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 日期时间相关API控制器
 * 展示LocalDateTime在Spring Boot中的实际应用
 * 
 * @author lynns
 */
@Slf4j
@RestController
@RequestMapping("/api/datetime")
@Tag(name = "日期时间API", description = "LocalDateTime相关的API接口")
public class DateTimeController {

    @GetMapping("/current")
    @Operation(summary = "获取当前日期时间", description = "返回当前的日期时间信息")
    public ResponseEntity<DateTimeResponse> getCurrentDateTime() {
        LocalDateTime now = LocalDateTime.now();
        
        DateTimeResponse response = DateTimeResponse.builder()
                .dateTime(now)
                .formatted(now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                .timestamp(DateTimeUtils.toTimestamp(now))
                .dayOfWeek(now.getDayOfWeek().toString())
                .dayOfYear(now.getDayOfYear())
                .weekOfYear(DateTimeUtils.getWeekOfYear(now))
                .build();
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/format")
    @Operation(summary = "格式化日期时间", description = "将日期时间按指定格式进行格式化")
    public ResponseEntity<Map<String, String>> formatDateTime(
            @RequestBody DateTimeRequest request) {
        
        Map<String, String> result = new HashMap<>();
        
        try {
            LocalDateTime dateTime = request.getDateTime();
            String pattern = request.getPattern();
            
            if (pattern == null || pattern.isEmpty()) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            String formatted = dateTime.format(formatter);
            
            result.put("original", dateTime.toString());
            result.put("pattern", pattern);
            result.put("formatted", formatted);
            result.put("success", "true");
            
        } catch (Exception e) {
            log.error("格式化日期时间失败", e);
            result.put("error", e.getMessage());
            result.put("success", "false");
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/parse")
    @Operation(summary = "解析日期时间字符串", description = "将字符串解析为LocalDateTime对象")
    public ResponseEntity<Map<String, Object>> parseDateTime(
            @Parameter(description = "日期时间字符串") @RequestParam String dateTimeStr,
            @Parameter(description = "解析格式，默认为 yyyy-MM-dd HH:mm:ss") @RequestParam(required = false) String pattern) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            if (pattern == null || pattern.isEmpty()) {
                pattern = "yyyy-MM-dd HH:mm:ss";
            }
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime parsed = LocalDateTime.parse(dateTimeStr, formatter);
            
            result.put("input", dateTimeStr);
            result.put("pattern", pattern);
            result.put("parsed", parsed);
            result.put("iso", parsed.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("解析日期时间失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/calculate")
    @Operation(summary = "日期时间计算", description = "计算两个日期时间之间的差值")
    public ResponseEntity<Map<String, Object>> calculateDateTimeDifference(
            @Parameter(description = "开始时间") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            // 计算各种时间单位的差值
            long years = ChronoUnit.YEARS.between(startTime, endTime);
            long months = ChronoUnit.MONTHS.between(startTime, endTime);
            long days = ChronoUnit.DAYS.between(startTime, endTime);
            long hours = ChronoUnit.HOURS.between(startTime, endTime);
            long minutes = ChronoUnit.MINUTES.between(startTime, endTime);
            long seconds = ChronoUnit.SECONDS.between(startTime, endTime);
            
            Map<String, Long> differences = new HashMap<>();
            differences.put("years", years);
            differences.put("months", months);
            differences.put("days", days);
            differences.put("hours", hours);
            differences.put("minutes", minutes);
            differences.put("seconds", seconds);
            
            result.put("startTime", startTime);
            result.put("endTime", endTime);
            result.put("differences", differences);
            result.put("isAfter", endTime.isAfter(startTime));
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("计算日期时间差值失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }

    @PostMapping("/add")
    @Operation(summary = "日期时间加法运算", description = "对指定日期时间进行加法运算")
    public ResponseEntity<Map<String, Object>> addToDateTime(
            @RequestBody Map<String, Object> request) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            String dateTimeStr = (String) request.get("dateTime");
            LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr);
            
            Integer years = (Integer) request.getOrDefault("years", 0);
            Integer months = (Integer) request.getOrDefault("months", 0);
            Integer days = (Integer) request.getOrDefault("days", 0);
            Integer hours = (Integer) request.getOrDefault("hours", 0);
            Integer minutes = (Integer) request.getOrDefault("minutes", 0);
            Integer seconds = (Integer) request.getOrDefault("seconds", 0);
            
            LocalDateTime resultDateTime = dateTime
                    .plusYears(years)
                    .plusMonths(months)
                    .plusDays(days)
                    .plusHours(hours)
                    .plusMinutes(minutes)
                    .plusSeconds(seconds);
            
            result.put("original", dateTime);
            result.put("added", Map.of(
                    "years", years,
                    "months", months,
                    "days", days,
                    "hours", hours,
                    "minutes", minutes,
                    "seconds", seconds
            ));
            result.put("result", resultDateTime);
            result.put("formatted", resultDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("日期时间加法运算失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/range")
    @Operation(summary = "生成时间范围", description = "生成指定时间范围内的时间序列")
    public ResponseEntity<Map<String, Object>> generateTimeRange(
            @Parameter(description = "开始时间") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTime,
            @Parameter(description = "结束时间") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTime,
            @Parameter(description = "时间间隔类型: HOURS, DAYS, WEEKS") 
            @RequestParam String intervalType,
            @Parameter(description = "间隔数量") 
            @RequestParam(defaultValue = "1") int intervalAmount) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            ChronoUnit unit = ChronoUnit.valueOf(intervalType.toUpperCase());
            List<LocalDateTime> timeRange = DateTimeUtils.generateTimeRange(startTime, endTime, unit, intervalAmount);
            
            result.put("startTime", startTime);
            result.put("endTime", endTime);
            result.put("intervalType", intervalType);
            result.put("intervalAmount", intervalAmount);
            result.put("timeRange", timeRange);
            result.put("count", timeRange.size());
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("生成时间范围失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/working-days")
    @Operation(summary = "计算工作日", description = "计算指定日期范围内的工作日数量")
    public ResponseEntity<Map<String, Object>> calculateWorkingDays(
            @Parameter(description = "开始日期") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String startDate,
            @Parameter(description = "结束日期") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String endDate) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            LocalDateTime start = LocalDateTime.parse(startDate + "T00:00:00");
            LocalDateTime end = LocalDateTime.parse(endDate + "T23:59:59");
            
            long totalDays = ChronoUnit.DAYS.between(start.toLocalDate(), end.toLocalDate()) + 1;
            long workingDays = DateTimeUtils.calculateWorkingDays(start.toLocalDate(), end.toLocalDate());
            long weekendDays = totalDays - workingDays;
            
            result.put("startDate", startDate);
            result.put("endDate", endDate);
            result.put("totalDays", totalDays);
            result.put("workingDays", workingDays);
            result.put("weekendDays", weekendDays);
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("计算工作日失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/timezone-convert")
    @Operation(summary = "时区转换", description = "将本地时间转换为不同时区的时间")
    public ResponseEntity<Map<String, Object>> convertTimeZone(
            @Parameter(description = "本地时间") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime localTime,
            @Parameter(description = "源时区ID") 
            @RequestParam(defaultValue = "Asia/Shanghai") String fromZone,
            @Parameter(description = "目标时区ID") 
            @RequestParam String toZone) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            Map<String, String> convertedTimes = DateTimeUtils.convertTimeZone(localTime, fromZone, toZone);
            
            result.put("localTime", localTime);
            result.put("fromZone", fromZone);
            result.put("toZone", toZone);
            result.put("convertedTimes", convertedTimes);
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("时区转换失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }

    @GetMapping("/age")
    @Operation(summary = "计算年龄", description = "根据出生日期计算年龄")
    public ResponseEntity<Map<String, Object>> calculateAge(
            @Parameter(description = "出生日期") 
            @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") String birthDate) {
        
        Map<String, Object> result = new HashMap<>();
        
        try {
            LocalDateTime birth = LocalDateTime.parse(birthDate + "T00:00:00");
            Map<String, Long> age = DateTimeUtils.calculateAge(birth);
            
            result.put("birthDate", birthDate);
            result.put("currentDate", LocalDateTime.now().toLocalDate().toString());
            result.put("age", age);
            result.put("ageString", String.format("%d岁%d个月%d天", 
                    age.get("years"), age.get("months"), age.get("days")));
            result.put("success", true);
            
        } catch (Exception e) {
            log.error("计算年龄失败", e);
            result.put("error", e.getMessage());
            result.put("success", false);
        }
        
        return ResponseEntity.ok(result);
    }
}