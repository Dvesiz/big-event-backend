package org.jsut.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.jsut.pojo.Result;
import org.jsut.utils.JwtUtil;
import org.jsut.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //令牌验证
        String token = request.getHeader("Authorization");
        //解析token
        try {
            //从redis中获取相同的token
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            String redisToken = ops.get(token);
            if (redisToken == null || !redisToken.equals(token)){
                throw new RuntimeException("用户未登录");
            }
            Map<String, Object> claims = JwtUtil.parseToken(token);
            //存储ThreadLocal
            ThreadLocalUtil.set(claims);
            //放行
            return true;
        } catch (Exception e) {
            //http 状态码 401
            response.setStatus(401);
            //拦截
            return false;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal,防止内存泄露
        ThreadLocalUtil.remove();
    }
}
