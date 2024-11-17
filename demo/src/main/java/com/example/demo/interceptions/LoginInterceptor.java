package com.example.demo.interceptions;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

// import org.springframework.data.redis.core.StringRedisTemplate;
// import org.springframework.data.redis.core.ValueOperations;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.demo.exception.TokenErrorException;
import com.example.demo.utils.InMemoryStore;
import com.example.demo.utils.JwtUtil;
import com.example.demo.utils.ThreadLocalUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {
    // private final StringRedisTemplate sRedisTemplate; // NOTE: Redis数据库
    private final InMemoryStore inMemoryStore;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler)
            throws Exception {
        // 令牌验证
        String token = request.getHeader("Authorization");
        if ("OPTIONS".equals(request.getMethod())) {
            // OPTIONS 不会携带 Authorization ，因此不应该检查，否则会返回401导致预检请求失败
            System.out.println("预检:" + new Date().toString());
            return false;
        }

        try {
            // 过期、篡改 会校验失败
            Map<String, Object> userInfo = JwtUtil.parseToken(token);
            // 存在线程局部变量中，供后序访问，ThreadLocal 存储的值是线程安全的
            ThreadLocalUtil.set(userInfo);

            // 校验是否与 Redis 中的 token 一致
            // ValueOperations<String, String> op = sRedisTemplate.opsForValue();
            // if (token == null || !token.equals(op.get(userInfo.get("username")))) {
            // throw new TokenErrorException("登录状态已失效，请重新登录");
            // }
            if (token == null || !token.equals(inMemoryStore.get((String) userInfo.get("username")))) {
                throw new TokenErrorException("登录状态已失效，请重新登录");
            }
            return true;
        } catch (Exception e) {
            response.setStatus(401);
            response.setCharacterEncoding("UTF-8");
            String msg = new String(e.getMessage().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
            response.getWriter().write(msg);
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response,
            @NonNull Object handler,
            @Nullable Exception ex) throws Exception {
        // 防止内存泄漏
        ThreadLocalUtil.remove();
    }

}
