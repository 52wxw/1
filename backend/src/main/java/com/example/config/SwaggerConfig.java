package com.example.config;

import io.swagger.v3.oas.models.OpenAPI;       // ✅ 新增导入
import io.swagger.v3.oas.models.info.Info;   // ✅ 新增导入
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() { // 确保 OpenAPI 类被正确导入
        return new OpenAPI()
            .info(new Info()
                .title("Task Platform API")
                .version("1.0.0")
                .description("任务平台后端API文档"));
    }
}
