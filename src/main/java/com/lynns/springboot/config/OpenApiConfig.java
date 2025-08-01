package com.lynns.springboot.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Spring Boot Jenkins Docker Demo API")
                        .version("1.0.0")
                        .description("这是一个Spring Boot + Jenkins + Docker的示例项目API文档")
                        .contact(new Contact()
                                .name("开发团队")
                                .email("developer@example.com")
                                .url("https://github.com/example"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));
    }
}