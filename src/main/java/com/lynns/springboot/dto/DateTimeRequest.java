package com.lynns.springboot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 日期时间请求DTO
 * 
 * @author lynns
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "日期时间请求对象")
public class DateTimeRequest {

    @Schema(description = "日期时间", example = "2024-12-25T10:30:45")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;

    @Schema(description = "格式化模式", example = "yyyy-MM-dd HH:mm:ss")
    private String pattern;

    @Schema(description = "时区ID", example = "Asia/Shanghai")
    private String zoneId;

    @Schema(description = "操作类型", example = "ADD")
    private String operationType;

    @Schema(description = "年数", example = "1")
    private Integer years;

    @Schema(description = "月数", example = "2")
    private Integer months;

    @Schema(description = "天数", example = "3")
    private Integer days;

    @Schema(description = "小时数", example = "4")
    private Integer hours;

    @Schema(description = "分钟数", example = "30")
    private Integer minutes;

    @Schema(description = "秒数", example = "45")
    private Integer seconds;
}