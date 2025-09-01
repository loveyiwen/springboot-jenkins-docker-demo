# 密码加密工具使用说明

这是一个完整的密码加密解决方案，提供多种加密算法和安全功能。

## 功能特性

### 1. 支持的加密算法

- **BCrypt** (推荐用于用户密码存储)
  - 自动生成盐值
  - 防止彩虹表攻击
  - 计算成本可调节

- **SHA-256** (带盐值的哈希)
  - 快速哈希算法
  - 需要单独存储盐值
  - 适用于一般安全需求

- **AES** (对称加密，可解密)
  - 可以解密还原原始密码
  - 需要安全存储密钥
  - 适用于需要解密的场景

- **MD5** (已弃用，仅用于兼容性)
  - 不推荐用于安全场景
  - 存在安全漏洞

### 2. 密码强度检查

- 支持1-5级强度评估
- 检查密码长度、字符类型
- 提供强度改进建议

## API接口

### 启动应用
```bash
mvn spring-boot:run
```

### 访问Swagger文档
```
http://localhost:8080/doc.html
```

### 主要接口

#### 1. BCrypt加密
```
POST /api/password/encrypt/bcrypt
Content-Type: application/json

{
    "password": "MySecurePassword123!"
}
```

#### 2. BCrypt验证
```
POST /api/password/verify/bcrypt
Content-Type: application/json

{
    "rawPassword": "MySecurePassword123!",
    "encodedPassword": "$2a$10$..."
}
```

#### 3. SHA-256加密
```
POST /api/password/encrypt/sha256
Content-Type: application/json

{
    "password": "MySecurePassword123!"
}
```

#### 4. AES加密
```
POST /api/password/encrypt/aes
Content-Type: application/json

{
    "password": "MySecurePassword123!"
}
```

#### 5. AES解密
```
POST /api/password/decrypt/aes
Content-Type: application/json

{
    "encryptedPassword": "encrypted_base64_string",
    "key": "aes_key_base64_string"
}
```

#### 6. 密码强度检查
```
POST /api/password/strength
Content-Type: application/json

{
    "password": "MySecurePassword123!"
}
```

#### 7. 演示接口
```
GET /api/password/demo
```

## Java代码使用示例

### 1. BCrypt加密（推荐用于用户密码）

```java
import com.lynns.springboot.utils.PasswordEncryptionUtil;

// 加密密码
String rawPassword = "userPassword123";
String encryptedPassword = PasswordEncryptionUtil.encryptWithBCrypt(rawPassword);

// 验证密码
boolean isValid = PasswordEncryptionUtil.verifyBCrypt(rawPassword, encryptedPassword);
```

### 2. SHA-256加密

```java
// 生成盐值
String salt = PasswordEncryptionUtil.generateSalt();

// 加密密码
String encryptedPassword = PasswordEncryptionUtil.encryptWithSHA256(rawPassword, salt);

// 验证密码
boolean isValid = PasswordEncryptionUtil.verifySHA256(rawPassword, encryptedPassword, salt);
```

### 3. AES加密（可解密）

```java
// 生成AES密钥
String aesKey = PasswordEncryptionUtil.generateAESKey();

// 加密密码
String encryptedPassword = PasswordEncryptionUtil.encryptWithAES(rawPassword, aesKey);

// 解密密码
String decryptedPassword = PasswordEncryptionUtil.decryptWithAES(encryptedPassword, aesKey);
```

### 4. 密码强度检查

```java
// 检查密码强度
int strength = PasswordEncryptionUtil.checkPasswordStrength("MyPassword123!");
String description = PasswordEncryptionUtil.getPasswordStrengthDescription("MyPassword123!");

System.out.println("密码强度: " + strength + " - " + description);
```

## 安全建议

### 1. 算法选择
- **用户登录密码**: 使用 BCrypt
- **API密钥存储**: 使用 SHA-256 + 盐值
- **需要解密的敏感数据**: 使用 AES
- **避免使用**: MD5 (已不安全)

### 2. 密码策略
- 最小长度: 8位字符
- 包含大小写字母、数字、特殊字符
- 定期更换密码
- 避免使用常见密码

### 3. 存储安全
- BCrypt: 直接存储加密结果
- SHA-256: 分别存储密码哈希和盐值
- AES: 安全存储密钥，考虑使用密钥管理服务

### 4. 传输安全
- 使用HTTPS传输
- 不在日志中记录原始密码
- 使用安全的会话管理

## 性能考虑

- **BCrypt**: 计算成本较高，适合低频验证
- **SHA-256**: 计算快速，适合高频场景
- **AES**: 加解密速度快，适合大量数据

## 错误处理

所有加密方法都包含完整的错误处理，会抛出运行时异常并包含详细错误信息。

## 依赖要求

- Spring Boot 3.4.8+
- Java 17+
- Spring Security Crypto
- Bouncy Castle Provider

## 示例运行

启动应用后，会自动运行演示程序，展示各种密码加密功能的使用效果。查看控制台输出了解详细演示结果。