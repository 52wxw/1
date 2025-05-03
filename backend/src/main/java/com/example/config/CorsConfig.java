// backend/src/main/java/com/example/config/CorsConfig.java
package com.example.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        
        // 允许前端域名（精确匹配，或使用 "*" 允许所有，生产环境不建议）
        config.addAllowedOriginPattern("http://192.168.112.155:3000");
        
        // 允许的 HTTP 方法
        config.addAllowedMethod("*"); // 或具体方法：POST, GET, OPTIONS
        
        // 允许的请求头（包含认证头 Authorization）
        config.addAllowedHeader("*"); // 或具体头：Content-Type, Authorization
        
        // 允许携带凭证（如 Cookie、JWT）
        config.setAllowCredentials(true);
        
        // 配置应用路径（匹配所有接口）
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}
