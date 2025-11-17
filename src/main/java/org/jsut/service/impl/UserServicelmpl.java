package org.jsut.service.impl;

import org.jsut.mapper.UserMapper;
import org.jsut.pojo.User;
import org.jsut.pojo.UserVO;
import org.jsut.service.UserService;
import org.jsut.utils.Md5Util;
import org.jsut.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;


@Service
public class UserServicelmpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Override
    public User findByUsername(String username) {
        User u = userMapper.findByUsername(username);
        return u;
    }

    @Override
    public void register(String username, String password) {
        //加密
        String md5Pwd = Md5Util.getMD5String(password);
        //添加
        userMapper.add(username, md5Pwd);
    }

    @Override
    public void update(UserVO user) {
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
    }

    @Override
    public void updateAvatar(String avatarUrl){

        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");

        userMapper.updateAvatar(avatarUrl, id);
    }

    @Override
    public void updatePwd(String oldPwd, String newPwd){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        userMapper.updatePwd(Md5Util.getMD5String(newPwd), id);
    }

    @Override
    public void delete(){
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer uid = (Integer) map.get("id");
        userMapper.delete(uid);
    }
}
