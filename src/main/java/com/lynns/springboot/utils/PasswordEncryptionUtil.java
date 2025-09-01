package com.lynns.springboot.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码加密工具类
 * 提供多种密码加密方式：BCrypt、SHA-256、AES等
 * 
 * @author lynns
 */
public class PasswordEncryptionUtil {
    
    private static final PasswordEncoder bcryptEncoder = new BCryptPasswordEncoder();
    private static final String AES_ALGORITHM = "AES";
    private static final String AES_TRANSFORMATION = "AES/ECB/PKCS5Padding";
    
    /**
     * 使用BCrypt加密密码（推荐用于用户密码存储）
     * BCrypt是一种安全的哈希算法，内置盐值，防止彩虹表攻击
     * 
     * @param rawPassword 原始密码
     * @return 加密后的密码
     */
    public static String encryptWithBCrypt(String rawPassword) {
        if (rawPassword == null || rawPassword.isEmpty()) {
            throw new IllegalArgumentException("密码不能为空");
        }
        return bcryptEncoder.encode(rawPassword);
    }
    
    /**
     * 验证BCrypt加密的密码
     * 
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @return 是否匹配
     */
    public static boolean verifyBCrypt(String rawPassword, String encodedPassword) {
        if (rawPassword == null || encodedPassword == null) {
            return false;
        }
        return bcryptEncoder.matches(rawPassword, encodedPassword);
    }
    
    /**
     * 使用SHA-256加密密码（带盐值）
     * 
     * @param password 原始密码
     * @param salt 盐值
     * @return 加密后的密码（Base64编码）
     */
    public static String encryptWithSHA256(String password, String salt) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String saltedPassword = password + salt;
            byte[] hash = digest.digest(saltedPassword.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256算法不可用", e);
        }
    }
    
    /**
     * 生成随机盐值
     * 
     * @return Base64编码的盐值
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }
    
    /**
     * 验证SHA-256加密的密码
     * 
     * @param rawPassword 原始密码
     * @param encodedPassword 加密后的密码
     * @param salt 盐值
     * @return 是否匹配
     */
    public static boolean verifySHA256(String rawPassword, String encodedPassword, String salt) {
        String encrypted = encryptWithSHA256(rawPassword, salt);
        return encrypted.equals(encodedPassword);
    }
    
    /**
     * 生成AES密钥
     * 
     * @return Base64编码的AES密钥
     */
    public static String generateAESKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM);
            keyGenerator.init(256);
            SecretKey secretKey = keyGenerator.generateKey();
            return Base64.getEncoder().encodeToString(secretKey.getEncoded());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("AES算法不可用", e);
        }
    }
    
    /**
     * 使用AES加密密码（对称加密，可解密）
     * 
     * @param password 原始密码
     * @param key Base64编码的AES密钥
     * @return 加密后的密码（Base64编码）
     */
    public static String encryptWithAES(String password, String key) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = cipher.doFinal(password.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(encryptedBytes);
        } catch (Exception e) {
            throw new RuntimeException("AES加密失败", e);
        }
    }
    
    /**
     * 使用AES解密密码
     * 
     * @param encryptedPassword 加密后的密码（Base64编码）
     * @param key Base64编码的AES密钥
     * @return 解密后的原始密码
     */
    public static String decryptWithAES(String encryptedPassword, String key) {
        try {
            byte[] keyBytes = Base64.getDecoder().decode(key);
            SecretKeySpec secretKey = new SecretKeySpec(keyBytes, AES_ALGORITHM);
            
            Cipher cipher = Cipher.getInstance(AES_TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            
            byte[] encryptedBytes = Base64.getDecoder().decode(encryptedPassword);
            byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
            return new String(decryptedBytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException("AES解密失败", e);
        }
    }
    
    /**
     * 使用MD5加密密码（不推荐用于安全要求高的场景）
     * 
     * @param password 原始密码
     * @return 加密后的密码（十六进制字符串）
     */
    @Deprecated
    public static String encryptWithMD5(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(password.getBytes(StandardCharsets.UTF_8));
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5算法不可用", e);
        }
    }
    
    /**
     * 密码强度检查
     * 
     * @param password 密码
     * @return 密码强度等级（1-5，5为最强）
     */
    public static int checkPasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return 0;
        }
        
        int score = 0;
        
        // 长度检查
        if (password.length() >= 8) score++;
        if (password.length() >= 12) score++;
        
        // 字符类型检查
        boolean hasLower = password.matches(".*[a-z].*");
        boolean hasUpper = password.matches(".*[A-Z].*");
        boolean hasDigit = password.matches(".*\\d.*");
        boolean hasSpecial = password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
        
        if (hasLower) score++;
        if (hasUpper) score++;
        if (hasDigit) score++;
        if (hasSpecial) score++;
        
        // 最多5分
        return Math.min(score, 5);
    }
    
    /**
     * 获取密码强度描述
     * 
     * @param password 密码
     * @return 强度描述
     */
    public static String getPasswordStrengthDescription(String password) {
        int strength = checkPasswordStrength(password);
        switch (strength) {
            case 0:
            case 1:
                return "非常弱";
            case 2:
                return "弱";
            case 3:
                return "中等";
            case 4:
                return "强";
            case 5:
                return "非常强";
            default:
                return "未知";
        }
    }
}