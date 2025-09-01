package com.lynns.springboot.controller;

import com.lynns.springboot.dto.PasswordEncryptionRequest;
import com.lynns.springboot.dto.PasswordEncryptionResponse;
import com.lynns.springboot.utils.PasswordEncryptionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 密码加密控制器
 * 提供密码加密、验证等API接口
 * 
 * @author lynns
 */
@RestController
@RequestMapping("/api/password")
@Tag(name = "密码加密", description = "密码加密相关接口")
@Slf4j
public class PasswordEncryptionController {

    @PostMapping("/encrypt/bcrypt")
    @Operation(summary = "BCrypt加密", description = "使用BCrypt算法加密密码")
    public ResponseEntity<PasswordEncryptionResponse> encryptWithBCrypt(
            @RequestBody PasswordEncryptionRequest request) {
        try {
            String encryptedPassword = PasswordEncryptionUtil.encryptWithBCrypt(request.getPassword());
            int strength = PasswordEncryptionUtil.checkPasswordStrength(request.getPassword());
            String strengthDesc = PasswordEncryptionUtil.getPasswordStrengthDescription(request.getPassword());
            
            PasswordEncryptionResponse response = PasswordEncryptionResponse.builder()
                    .algorithm("BCrypt")
                    .originalPassword(request.getPassword())
                    .encryptedPassword(encryptedPassword)
                    .passwordStrength(strength)
                    .passwordStrengthDescription(strengthDesc)
                    .success(true)
                    .message("BCrypt加密成功")
                    .build();
                    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("BCrypt加密失败", e);
            PasswordEncryptionResponse response = PasswordEncryptionResponse.builder()
                    .success(false)
                    .message("加密失败: " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/verify/bcrypt")
    @Operation(summary = "BCrypt验证", description = "验证BCrypt加密的密码")
    public ResponseEntity<Map<String, Object>> verifyBCrypt(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String rawPassword = request.get("rawPassword");
            String encodedPassword = request.get("encodedPassword");
            
            boolean matches = PasswordEncryptionUtil.verifyBCrypt(rawPassword, encodedPassword);
            
            response.put("success", true);
            response.put("matches", matches);
            response.put("message", matches ? "密码验证成功" : "密码验证失败");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("BCrypt验证失败", e);
            response.put("success", false);
            response.put("message", "验证失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/encrypt/sha256")
    @Operation(summary = "SHA-256加密", description = "使用SHA-256算法加密密码")
    public ResponseEntity<PasswordEncryptionResponse> encryptWithSHA256(
            @RequestBody PasswordEncryptionRequest request) {
        try {
            String salt = PasswordEncryptionUtil.generateSalt();
            String encryptedPassword = PasswordEncryptionUtil.encryptWithSHA256(request.getPassword(), salt);
            int strength = PasswordEncryptionUtil.checkPasswordStrength(request.getPassword());
            String strengthDesc = PasswordEncryptionUtil.getPasswordStrengthDescription(request.getPassword());
            
            PasswordEncryptionResponse response = PasswordEncryptionResponse.builder()
                    .algorithm("SHA-256")
                    .originalPassword(request.getPassword())
                    .encryptedPassword(encryptedPassword)
                    .salt(salt)
                    .passwordStrength(strength)
                    .passwordStrengthDescription(strengthDesc)
                    .success(true)
                    .message("SHA-256加密成功")
                    .build();
                    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("SHA-256加密失败", e);
            PasswordEncryptionResponse response = PasswordEncryptionResponse.builder()
                    .success(false)
                    .message("加密失败: " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/verify/sha256")
    @Operation(summary = "SHA-256验证", description = "验证SHA-256加密的密码")
    public ResponseEntity<Map<String, Object>> verifySHA256(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String rawPassword = request.get("rawPassword");
            String encodedPassword = request.get("encodedPassword");
            String salt = request.get("salt");
            
            boolean matches = PasswordEncryptionUtil.verifySHA256(rawPassword, encodedPassword, salt);
            
            response.put("success", true);
            response.put("matches", matches);
            response.put("message", matches ? "密码验证成功" : "密码验证失败");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("SHA-256验证失败", e);
            response.put("success", false);
            response.put("message", "验证失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/encrypt/aes")
    @Operation(summary = "AES加密", description = "使用AES算法加密密码")
    public ResponseEntity<PasswordEncryptionResponse> encryptWithAES(
            @RequestBody PasswordEncryptionRequest request) {
        try {
            String key = PasswordEncryptionUtil.generateAESKey();
            String encryptedPassword = PasswordEncryptionUtil.encryptWithAES(request.getPassword(), key);
            int strength = PasswordEncryptionUtil.checkPasswordStrength(request.getPassword());
            String strengthDesc = PasswordEncryptionUtil.getPasswordStrengthDescription(request.getPassword());
            
            PasswordEncryptionResponse response = PasswordEncryptionResponse.builder()
                    .algorithm("AES")
                    .originalPassword(request.getPassword())
                    .encryptedPassword(encryptedPassword)
                    .key(key)
                    .passwordStrength(strength)
                    .passwordStrengthDescription(strengthDesc)
                    .success(true)
                    .message("AES加密成功")
                    .build();
                    
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("AES加密失败", e);
            PasswordEncryptionResponse response = PasswordEncryptionResponse.builder()
                    .success(false)
                    .message("加密失败: " + e.getMessage())
                    .build();
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/decrypt/aes")
    @Operation(summary = "AES解密", description = "使用AES算法解密密码")
    public ResponseEntity<Map<String, Object>> decryptWithAES(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String encryptedPassword = request.get("encryptedPassword");
            String key = request.get("key");
            
            String decryptedPassword = PasswordEncryptionUtil.decryptWithAES(encryptedPassword, key);
            
            response.put("success", true);
            response.put("decryptedPassword", decryptedPassword);
            response.put("message", "AES解密成功");
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("AES解密失败", e);
            response.put("success", false);
            response.put("message", "解密失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @PostMapping("/strength")
    @Operation(summary = "密码强度检查", description = "检查密码强度")
    public ResponseEntity<Map<String, Object>> checkPasswordStrength(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        try {
            String password = request.get("password");
            int strength = PasswordEncryptionUtil.checkPasswordStrength(password);
            String strengthDesc = PasswordEncryptionUtil.getPasswordStrengthDescription(password);
            
            response.put("success", true);
            response.put("password", password);
            response.put("strength", strength);
            response.put("strengthDescription", strengthDesc);
            response.put("message", "密码强度检查完成");
            
            // 提供密码强度建议
            if (strength < 3) {
                response.put("suggestions", "建议使用至少8位字符，包含大小写字母、数字和特殊字符");
            } else if (strength < 5) {
                response.put("suggestions", "密码强度良好，可以考虑增加长度或更多字符类型");
            } else {
                response.put("suggestions", "密码强度很好！");
            }
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("密码强度检查失败", e);
            response.put("success", false);
            response.put("message", "检查失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("/demo")
    @Operation(summary = "加密演示", description = "演示各种加密方式")
    public ResponseEntity<Map<String, Object>> demo() {
        Map<String, Object> response = new HashMap<>();
        String demoPassword = "MySecurePassword123!";
        
        try {
            // BCrypt演示
            String bcryptHash = PasswordEncryptionUtil.encryptWithBCrypt(demoPassword);
            boolean bcryptVerify = PasswordEncryptionUtil.verifyBCrypt(demoPassword, bcryptHash);
            
            // SHA-256演示
            String salt = PasswordEncryptionUtil.generateSalt();
            String sha256Hash = PasswordEncryptionUtil.encryptWithSHA256(demoPassword, salt);
            boolean sha256Verify = PasswordEncryptionUtil.verifySHA256(demoPassword, sha256Hash, salt);
            
            // AES演示
            String aesKey = PasswordEncryptionUtil.generateAESKey();
            String aesEncrypted = PasswordEncryptionUtil.encryptWithAES(demoPassword, aesKey);
            String aesDecrypted = PasswordEncryptionUtil.decryptWithAES(aesEncrypted, aesKey);
            
            // 密码强度检查
            int strength = PasswordEncryptionUtil.checkPasswordStrength(demoPassword);
            String strengthDesc = PasswordEncryptionUtil.getPasswordStrengthDescription(demoPassword);
            
            Map<String, Object> demoResults = new HashMap<>();
            demoResults.put("originalPassword", demoPassword);
            demoResults.put("passwordStrength", strength);
            demoResults.put("passwordStrengthDescription", strengthDesc);
            
            Map<String, Object> bcryptDemo = new HashMap<>();
            bcryptDemo.put("encrypted", bcryptHash);
            bcryptDemo.put("verified", bcryptVerify);
            demoResults.put("bcrypt", bcryptDemo);
            
            Map<String, Object> sha256Demo = new HashMap<>();
            sha256Demo.put("salt", salt);
            sha256Demo.put("encrypted", sha256Hash);
            sha256Demo.put("verified", sha256Verify);
            demoResults.put("sha256", sha256Demo);
            
            Map<String, Object> aesDemo = new HashMap<>();
            aesDemo.put("key", aesKey);
            aesDemo.put("encrypted", aesEncrypted);
            aesDemo.put("decrypted", aesDecrypted);
            aesDemo.put("decryptionSuccess", demoPassword.equals(aesDecrypted));
            demoResults.put("aes", aesDemo);
            
            response.put("success", true);
            response.put("message", "加密演示完成");
            response.put("demoResults", demoResults);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("加密演示失败", e);
            response.put("success", false);
            response.put("message", "演示失败: " + e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }
    }
}