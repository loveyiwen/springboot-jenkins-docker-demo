# LocalDateTime 代码案例

这是一个全面的 Java LocalDateTime 代码案例集合，展示了在实际项目中如何使用 LocalDateTime 进行日期时间处理。

## 项目结构

```
src/main/java/com/lynns/springboot/
├── demo/
│   ├── LocalDateTimeDemo.java           # 基础演示类
│   └── LocalDateTimeTestRunner.java     # Spring Boot 测试运行器
├── controller/
│   └── DateTimeController.java          # REST API 控制器
├── utils/
│   └── DateTimeUtils.java               # 工具类
└── dto/
    ├── DateTimeRequest.java             # 请求 DTO
    ├── DateTimeResponse.java            # 响应 DTO
    └── DateTimeAnalysisResponse.java    # 详细分析响应 DTO
```

## 功能特性

### 1. 基础操作演示 (LocalDateTimeDemo.java)

- **创建 LocalDateTime 实例**
  - 获取当前时间
  - 指定具体时间
  - 从 LocalDate 和 LocalTime 组合
  - 从字符串解析
  - 获取一天的开始和结束时间

- **格式化和解析**
  - 多种格式化模式
  - 中文日期格式
  - ISO 标准格式
  - 自定义格式解析

- **日期时间计算**
  - 加减操作 (plusDays, minusHours 等)
  - 使用 ChronoUnit 进行精确计算
  - 计算时间间隔
  - 使用 TemporalAdjusters 进行高级调整

- **比较操作**
  - isAfter, isBefore, isEqual
  - compareTo 方法
  - 时间范围检查

- **时区转换**
  - 多时区时间显示
  - 时间戳转换
  - ZonedDateTime 操作

### 2. 实际应用场景

- **年龄计算**: 根据出生日期计算准确年龄
- **时间范围生成**: 生成工作时间段、统计时间点
- **工作日计算**: 排除周末的业务日计算
- **定时任务调度**: 计算下次执行时间和历史记录

### 3. Spring Boot REST API (DateTimeController.java)

提供以下 API 接口：

- `GET /api/datetime/current` - 获取当前日期时间
- `POST /api/datetime/format` - 格式化日期时间
- `GET /api/datetime/parse` - 解析日期时间字符串
- `GET /api/datetime/calculate` - 计算时间差
- `POST /api/datetime/add` - 日期时间加法运算
- `GET /api/datetime/range` - 生成时间范围
- `GET /api/datetime/working-days` - 计算工作日
- `GET /api/datetime/timezone-convert` - 时区转换
- `GET /api/datetime/age` - 计算年龄

### 4. 工具类 (DateTimeUtils.java)

包含50+个实用方法：

- **格式化工具**: format(), parse()
- **时间戳转换**: toTimestamp(), fromTimestamp()
- **时间边界**: startOfDay(), endOfDay(), firstDayOfMonth()
- **时间计算**: between(), calculateAge(), calculateWorkingDays()
- **时间判断**: isToday(), isWorkingDay(), isWeekend()
- **时区处理**: convertTimeZone()
- **业务场景**: nextExecutionTime(), getRelativeTimeDescription()

## 运行示例

### 1. 运行基础演示

```bash
javac -cp ".:src/main/java" src/main/java/com/lynns/springboot/demo/LocalDateTimeDemo.java
java -cp ".:src/main/java" com.lynns.springboot.demo.LocalDateTimeDemo
```

### 2. 启动 Spring Boot 应用

```bash
mvn spring-boot:run
```

然后访问 Swagger UI: `http://localhost:8080/doc.html`

## 代码示例

### 基础用法

```java
// 创建时间
LocalDateTime now = LocalDateTime.now();
LocalDateTime specific = LocalDateTime.of(2024, 12, 25, 10, 30, 45);

// 格式化
DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
String formatted = now.format(formatter);

// 计算
LocalDateTime future = now.plusDays(7).plusHours(2);
long daysBetween = ChronoUnit.DAYS.between(now, future);

// 比较
boolean isAfter = now.isAfter(specific);
```

### 使用工具类

```java
// 获取时间边界
LocalDateTime startOfToday = DateTimeUtils.startOfToday();
LocalDateTime endOfMonth = DateTimeUtils.lastDayOfMonth(now);

// 计算年龄
Map<String, Long> age = DateTimeUtils.calculateAge(birthDate);

// 工作日计算
long workingDays = DateTimeUtils.calculateWorkingDays(startDate, endDate);

// 时区转换
Map<String, String> conversion = DateTimeUtils.convertTimeZone(
    now, "Asia/Shanghai", "America/New_York");
```

### API 调用示例

```bash
# 获取当前时间
curl -X GET "http://localhost:8080/api/datetime/current"

# 计算时间差
curl -X GET "http://localhost:8080/api/datetime/calculate?startTime=2024-01-01%2000:00:00&endTime=2024-12-31%2023:59:59"

# 时区转换
curl -X GET "http://localhost:8080/api/datetime/timezone-convert?localTime=2024-12-25%2010:30:00&toZone=America/New_York"
```

## 实际业务场景

### 1. 会议安排系统
```java
// 安排下周一上午10点的会议
LocalDateTime nextMonday = DateTimeUtils.nextDayOfWeek(now, DayOfWeek.MONDAY);
LocalDateTime meetingTime = nextMonday.withHour(10).withMinute(0);
```

### 2. 任务管理系统
```java
// 计算任务进度
long totalHours = ChronoUnit.HOURS.between(startTime, deadline);
long elapsedHours = ChronoUnit.HOURS.between(startTime, now);
double progress = (double) elapsedHours / totalHours * 100;
```

### 3. 数据统计系统
```java
// 生成统计时间范围
LocalDateTime startOfMonth = DateTimeUtils.firstDayOfMonth(now);
LocalDateTime endOfMonth = DateTimeUtils.lastDayOfMonth(now);
List<LocalDateTime> dailyPoints = DateTimeUtils.generateTimeRange(
    startOfMonth, endOfMonth, ChronoUnit.DAYS, 1);
```

### 4. 定时任务调度
```java
// 计算下次执行时间
LocalDateTime nextExecution = DateTimeUtils.nextExecutionTime(now, 2, 0); // 每天凌晨2点
```

## 核心知识点

### LocalDateTime vs 其他时间类

- **LocalDateTime**: 不包含时区信息的本地日期时间
- **ZonedDateTime**: 包含时区信息的日期时间
- **Instant**: UTC时间戳
- **LocalDate**: 只有日期部分
- **LocalTime**: 只有时间部分

### 常用格式化模式

- `yyyy-MM-dd HH:mm:ss` - 标准格式
- `yyyy年MM月dd日 HH时mm分ss秒` - 中文格式
- `yyyy/MM/dd HH:mm` - 简化格式
- `yyyy-MM-dd'T'HH:mm:ss` - ISO格式

### 时间计算单位 (ChronoUnit)

- YEARS, MONTHS, WEEKS, DAYS
- HOURS, MINUTES, SECONDS
- MILLIS, MICROS, NANOS

### TemporalAdjusters 常用方法

- `firstDayOfMonth()` - 月份第一天
- `lastDayOfMonth()` - 月份最后一天
- `next(DayOfWeek)` - 下一个指定星期
- `firstDayOfYear()` - 年份第一天

## 最佳实践

1. **使用 LocalDateTime 而不是 Date**: 更好的API设计和类型安全
2. **时区处理**: 需要时区转换时使用 ZonedDateTime
3. **格式化**: 使用 DateTimeFormatter 而不是 SimpleDateFormat
4. **不可变性**: LocalDateTime 是不可变的，操作返回新实例
5. **null 检查**: 在工具方法中进行 null 检查
6. **业务封装**: 将常用操作封装到工具类中

## 性能考虑

- LocalDateTime 操作比 Date 更高效
- 避免频繁的字符串解析和格式化
- 缓存常用的 DateTimeFormatter 实例
- 对于高频操作，考虑使用时间戳

这个案例集合提供了 LocalDateTime 在实际项目中的全面应用示例，可以直接用于生产环境。