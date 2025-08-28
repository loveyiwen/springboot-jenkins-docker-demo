package com.lynns.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 日期时间分析响应DTO
 * 包含详细的日期时间分析信息
 * 
 * @author lynns
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "日期时间分析响应对象")
public class DateTimeAnalysisResponse {

    @Schema(description = "原始日期时间")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime originalDateTime;

    @Schema(description = "基本信息")
    private BasicInfo basicInfo;

    @Schema(description = "格式化结果")
    private Map<String, String> formattedResults;

    @Schema(description = "时间计算结果")
    private CalculationResults calculationResults;

    @Schema(description = "业务分析")
    private BusinessAnalysis businessAnalysis;

    @Schema(description = "时区信息")
    private List<TimeZoneInfo> timeZoneInfos;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "基本信息")
    public static class BasicInfo {
        @Schema(description = "年份")
        private Integer year;
        
        @Schema(description = "月份")
        private Integer month;
        
        @Schema(description = "日")
        private Integer day;
        
        @Schema(description = "小时")
        private Integer hour;
        
        @Schema(description = "分钟")
        private Integer minute;
        
        @Schema(description = "秒")
        private Integer second;
        
        @Schema(description = "星期几")
        private String dayOfWeek;
        
        @Schema(description = "一年中的第几天")
        private Integer dayOfYear;
        
        @Schema(description = "一年中的第几周")
        private Integer weekOfYear;
        
        @Schema(description = "季度")
        private Integer quarter;
        
        @Schema(description = "时间戳（秒）")
        private Long timestamp;
        
        @Schema(description = "时间戳（毫秒）")
        private Long timestampMilli;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "计算结果")
    public static class CalculationResults {
        @Schema(description = "月份第一天")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime firstDayOfMonth;
        
        @Schema(description = "月份最后一天")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastDayOfMonth;
        
        @Schema(description = "年份第一天")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime firstDayOfYear;
        
        @Schema(description = "年份最后一天")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastDayOfYear;
        
        @Schema(description = "季度第一天")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime firstDayOfQuarter;
        
        @Schema(description = "季度最后一天")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime lastDayOfQuarter;
        
        @Schema(description = "下个周一")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime nextMonday;
        
        @Schema(description = "上个周五")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        private LocalDateTime previousFriday;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "业务分析")
    public static class BusinessAnalysis {
        @Schema(description = "是否是工作日")
        private Boolean isWorkingDay;
        
        @Schema(description = "是否是周末")
        private Boolean isWeekend;
        
        @Schema(description = "是否是今天")
        private Boolean isToday;
        
        @Schema(description = "是否是昨天")
        private Boolean isYesterday;
        
        @Schema(description = "是否是明天")
        private Boolean isTomorrow;
        
        @Schema(description = "相对时间描述")
        private String relativeDescription;
        
        @Schema(description = "距离现在的天数")
        private Long daysFromNow;
        
        @Schema(description = "距离现在的小时数")
        private Long hoursFromNow;
        
        @Schema(description = "距离现在的分钟数")
        private Long minutesFromNow;
        
        @Schema(description = "是否在工作时间内（9-18点）")
        private Boolean inBusinessHours;
        
        @Schema(description = "年龄信息（如果是生日）")
        private Map<String, Long> ageInfo;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "时区信息")
    public static class TimeZoneInfo {
        @Schema(description = "时区ID")
        private String zoneId;
        
        @Schema(description = "时区名称")
        private String zoneName;
        
        @Schema(description = "该时区的时间")
        private String time;
        
        @Schema(description = "与本地时间的偏移（小时）")
        private Integer offsetHours;
    }
}