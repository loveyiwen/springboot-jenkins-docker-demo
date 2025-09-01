package com.lynns.springboot.demo;

import com.lynns.springboot.utils.PasswordEncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * 密码加密演示类
 * 在应用启动时演示各种密码加密功能
 * 
 * @author lynns
 */
@Component
@Slf4j
public class PasswordEncryptionDemo implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        log.info("=== 密码加密工具演示开始 ===");
        
        String[] testPasswords = {
            "123456",           // 弱密码
            "password",         // 弱密码
            "Password123",      // 中等密码
            "MySecurePass123!", // 强密码
            "Sup3r$3cur3P@ssw0rd2024!" // 超强密码
        };
        
        for (String password : testPasswords) {
            demonstratePasswordEncryption(password);
            log.info(""); // 空行分隔
        }
        
        log.info("=== 密码加密工具演示结束 ===");
    }
    
    private void demonstratePasswordEncryption(String password) {
        log.info("测试密码: {}", password);
        
        // 1. 密码强度检查
        int strength = PasswordEncryptionUtil.checkPasswordStrength(password);
        String strengthDesc = PasswordEncryptionUtil.getPasswordStrengthDescription(password);
        log.info("密码强度: {} - {}", strength, strengthDesc);
        
        try {
            // 2. BCrypt加密演示
            log.info("--- BCrypt加密演示 ---");
            String bcryptHash = PasswordEncryptionUtil.encryptWithBCrypt(password);
            log.info("BCrypt加密结果: {}", bcryptHash);
            
            boolean bcryptVerify = PasswordEncryptionUtil.verifyBCrypt(password, bcryptHash);
            log.info("BCrypt验证结果: {}", bcryptVerify ? "✓ 验证成功" : "✗ 验证失败");
            
            // 3. SHA-256加密演示
            log.info("--- SHA-256加密演示 ---");
            String salt = PasswordEncryptionUtil.generateSalt();
            String sha256Hash = PasswordEncryptionUtil.encryptWithSHA256(password, salt);
            log.info("生成的盐值: {}", salt);
            log.info("SHA-256加密结果: {}", sha256Hash);
            
            boolean sha256Verify = PasswordEncryptionUtil.verifySHA256(password, sha256Hash, salt);
            log.info("SHA-256验证结果: {}", sha256Verify ? "✓ 验证成功" : "✗ 验证失败");
            
            // 4. AES加密演示
            log.info("--- AES加密演示 ---");
            String aesKey = PasswordEncryptionUtil.generateAESKey();
            String aesEncrypted = PasswordEncryptionUtil.encryptWithAES(password, aesKey);
            log.info("生成的AES密钥: {}", aesKey);
            log.info("AES加密结果: {}", aesEncrypted);
            
            String aesDecrypted = PasswordEncryptionUtil.decryptWithAES(aesEncrypted, aesKey);
            log.info("AES解密结果: {}", aesDecrypted);
            log.info("AES解密验证: {}", password.equals(aesDecrypted) ? "✓ 解密成功" : "✗ 解密失败");
            
            // 5. MD5加密演示（不推荐）
            log.info("--- MD5加密演示（不推荐用于安全场景） ---");
            String md5Hash = PasswordEncryptionUtil.encryptWithMD5(password);
            log.info("MD5加密结果: {}", md5Hash);
            
        } catch (Exception e) {
            log.error("密码加密演示失败: {}", e.getMessage(), e);
        }
    }
}