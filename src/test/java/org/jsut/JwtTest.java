package org.jsut;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtTest {

    @Test
    public void testGen(){

        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("username","张三");
        //生成JWT 令牌
        String token = JWT.create()
                .withClaim("user",claims)//添加载荷，不能存放私密信息
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添加过期时间
                .sign(Algorithm.HMAC256("dhynb"));//指定算法配置密钥

        System.out.println(token);
    }
    //解析JWT令牌
    @Test
    public void testParse(){
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyIjp7ImlkIjoxLCJ1c2VybmFtZSI6IuW8oOS4iSJ9LCJleHAiOjE3NjI3MzE2OTd9.sIgNS9hH-lGNBvh-Wbpt5CLKPLK5cZT31e5ifa7B5eM";

        JWTVerifier jwtVerifier =JWT.require(Algorithm.HMAC256("dhynb")).build();

        DecodedJWT decodedJWT = jwtVerifier.verify(token);//验证token，如果验证成功，返回解码的token

        Map<String, Claim> claims = decodedJWT.getClaims();
        System.out.println(claims.get("user"));
        //如果篡改头部和载荷，会报异常
        //如果密钥错误，会报异常
        //token 过期，会报异常
    }
}
