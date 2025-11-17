package org.jsut.controller;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.URL;
import org.jsut.pojo.Result;
import org.jsut.pojo.User;
import org.jsut.pojo.UserVO;
import org.jsut.service.UserService;
import org.jsut.utils.JwtUtil;
import org.jsut.utils.Md5Util;
import org.jsut.utils.ThreadLocalUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired  
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    // 注册接口
    @PostMapping("/register")
    public Result regisrer(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        //查询用户
        User u = userService.findByUsername(username);
        if(u == null){
            //注册
            userService.register(username, password);
            return Result.success();
        }
        else {
            //用户已存在
            return Result.error("用户已存在");
        }
    }

    //登录接口
    @PostMapping("/login")
    public Result<String> login(@Pattern(regexp = "^\\S{5,16}$") String username, @Pattern(regexp = "^\\S{5,16}$")String password){
        //根据用户名查询用户
        User loginUser = userService.findByUsername(username);
        //判断用户是否存在
        if (loginUser == null){
            return Result.error("用户不存在");
        }
        //密码校验
        if (Md5Util.getMD5String( password).equals(loginUser.getPassword())){
            //生成token
            Map<String, Object> claims = new HashMap<>();
            claims.put("id",loginUser.getId());
            claims.put("username",loginUser.getUsername());
            String token =JwtUtil.genToken(claims);
            //把token存储到Redis中
            ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
            ops.set(token,token,12, TimeUnit.HOURS);
            return Result.success(token);
        }
        return Result.error("密码错误");
    }

    //用户信息接口
    @GetMapping("/userinfo")
    public Result<UserVO> userinfo(){

        //解析token
        Map<String, Object> map = ThreadLocalUtil.get();
        String username = (String) map.get("username");
        User user = userService.findByUsername(username);

        UserVO uservo = new UserVO();
        BeanUtils.copyProperties(user, uservo);

        return Result.success(uservo);
    }


    //修改用户信息接口
    @PutMapping("/update")
    public Result update(@RequestBody @Validated UserVO user){
        userService.update(user);

        return Result.success();
    }

    @DeleteMapping("/delete")
    public Result delete(){
        userService.delete();
        return Result.success();
    }


    //修改头像接口
    @PatchMapping("/updateAvatar")
    public Result updateAvatar(@RequestParam @URL String avatarUrl){
        userService.updateAvatar(avatarUrl);
        return Result.success();
    }

    //修改密码接口
    @PatchMapping("/updatePwd")
    public Result updatePwd(@RequestBody Map<String, String> params,@RequestHeader("Authorization") String  token){
        //1.校验参数
        String oldPwd = params.get("old_Pwd");
        String newPwd = params.get("new_Pwd");
        String confirmPwd = params.get("confirm_Pwd");
        if (!StringUtils.hasLength(oldPwd)||!StringUtils.hasLength(newPwd)||!StringUtils.hasLength(confirmPwd)){
            return Result.error("缺少参数");
        }

        //原密码是否正确
        //调用userService查询用户
        Map<String, Object> map = ThreadLocalUtil.get() ;
        String username = (String) map.get("username");
        User LoginUser = userService.findByUsername(username);
        if (!Md5Util.getMD5String(oldPwd).equals(LoginUser.getPassword())){
            return Result.error("原密码错误");
        }
        //新密码是否一致
        if (!newPwd.equals(confirmPwd)){
            return Result.error("两次密码不一致");
        }
        //2.调用Service完成密码更新
        userService.updatePwd(username, newPwd);
        //3.删除Redis中的token
        stringRedisTemplate.delete(token);
        return Result.success();
    }
}
