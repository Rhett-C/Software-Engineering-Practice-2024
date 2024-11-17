package com.example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.example.demo.interceptions.LoginInterceptor;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    private final LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        // 注册拦截器，并排除login、register
        if (loginInterceptor != null)
            registry.addInterceptor(loginInterceptor).excludePathPatterns("/user/login", "/user/register");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 预检请求（Preflight Request）是在进行跨域资源共享（Cross-Origin Resource
        // Sharing，CORS）时，由浏览器自动发送的一种特殊类型的HTTP请求。
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:5173")// 默认是全部，写*是错的
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                // 是否允许携带证书、凭据（TLS , Cookies...），客户端也要设置，如XMLHttpRequest：xhr.withCredentials =
                // true, 这样浏览器便会在预检请求携带证书
                .allowCredentials(true)
                // 允许浏览器缓存预检请求，避免每次CORS请求都发送预检请求
                .maxAge(3600);
    }
}
