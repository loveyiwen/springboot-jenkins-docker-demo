package com.lynns.springboot.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Tag(name = "Hello Controller", description = "简单的示例接口")
public class HelloController {

    @GetMapping("/hello")
    @Operation(summary = "问候接口", description = "返回简单的问候信息")
    public Map<String, Object> hello() {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello, Spring Boot!");
        result.put("timestamp", System.currentTimeMillis());
        result.put("status", "success");
        return result;
    }

    @GetMapping("/hello/{name}")
    @Operation(summary = "个性化问候", description = "根据姓名返回个性化问候")
    public Map<String, Object> helloWithName(
            @Parameter(description = "用户姓名", required = true)
            @PathVariable String name) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "Hello, " + name + "!");
        result.put("timestamp", System.currentTimeMillis());
        result.put("status", "success");
        return result;
    }

    @PostMapping("/user")
    @Operation(summary = "创建用户", description = "创建一个新用户")
    public Map<String, Object> createUser(@RequestBody Map<String, String> user) {
        Map<String, Object> result = new HashMap<>();
        result.put("message", "User created successfully");
        result.put("user", user);
        result.put("id", System.currentTimeMillis());
        result.put("status", "success");
        return result;
    }

    @GetMapping("/health")
    @Operation(summary = "健康检查", description = "检查应用程序健康状态")
    public Map<String, Object> health() {
        Map<String, Object> result = new HashMap<>();
        result.put("status", "UP");
        result.put("timestamp", System.currentTimeMillis());
        result.put("version", "1.0.0");
        return result;
    }
}