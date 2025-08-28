package com.lynns.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 日期时间响应DTO
 * 
 * @author lynns
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "日期时间响应对象")
public class DateTimeResponse {

    @Schema(description = "日期时间", example = "2024-12-25T10:30:45")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    @Schema(description = "格式化后的日期时间", example = "2024-12-25 10:30:45")
    private String formatted;

    @Schema(description = "时间戳（秒）", example = "1703476245")
    private Long timestamp;

    @Schema(description = "时间戳（毫秒）", example = "1703476245000")
    private Long timestampMilli;

    @Schema(description = "星期几", example = "MONDAY")
    private String dayOfWeek;

    @Schema(description = "一年中的第几天", example = "359")
    private Integer dayOfYear;

    @Schema(description = "一年中的第几周", example = "52")
    private Integer weekOfYear;

    @Schema(description = "月份", example = "12")
    private Integer month;

    @Schema(description = "年份", example = "2024")
    private Integer year;

    @Schema(description = "是否是工作日", example = "true")
    private Boolean isWorkingDay;

    @Schema(description = "是否是周末", example = "false")
    private Boolean isWeekend;

    @Schema(description = "是否是今天", example = "true")
    private Boolean isToday;

    @Schema(description = "相对时间描述", example = "2小时前")
    private String relativeDescription;

    @Schema(description = "时区信息", example = "Asia/Shanghai")
    private String zoneId;

    @Schema(description = "季度", example = "4")
    private Integer quarter;
}