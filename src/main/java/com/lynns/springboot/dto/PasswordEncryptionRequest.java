package com.lynns.springboot.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import jakarta.validation.constraints.NotBlank;

/**
 * 密码加密请求DTO
 * 
 * @author lynns
 */
@Data
@Schema(description = "密码加密请求")
public class PasswordEncryptionRequest {
    
    @NotBlank(message = "密码不能为空")
    @Schema(description = "原始密码", example = "MySecurePassword123!")
    private String password;
    
    @Schema(description = "加密算法类型", example = "BCrypt", allowableValues = {"BCrypt", "SHA-256", "AES", "MD5"})
    private String algorithm;
    
    @Schema(description = "自定义盐值（可选，用于SHA-256）", example = "customSalt123")
    private String salt;
    
    @Schema(description = "自定义密钥（可选，用于AES）", example = "customKey123")
    private String key;
}