package com.lynns.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 密码加密响应DTO
 * 
 * @author lynns
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "密码加密响应")
public class PasswordEncryptionResponse {
    
    @Schema(description = "是否成功", example = "true")
    private boolean success;
    
    @Schema(description = "响应消息", example = "加密成功")
    private String message;
    
    @Schema(description = "加密算法", example = "BCrypt")
    private String algorithm;
    
    @Schema(description = "原始密码", example = "MySecurePassword123!")
    private String originalPassword;
    
    @Schema(description = "加密后的密码")
    private String encryptedPassword;
    
    @Schema(description = "盐值（SHA-256使用）")
    private String salt;
    
    @Schema(description = "密钥（AES使用）")
    private String key;
    
    @Schema(description = "密码强度等级（1-5）", example = "5")
    private int passwordStrength;
    
    @Schema(description = "密码强度描述", example = "非常强")
    private String passwordStrengthDescription;
    
    @Schema(description = "加密时间戳")
    @Builder.Default
    private long timestamp = System.currentTimeMillis();
}