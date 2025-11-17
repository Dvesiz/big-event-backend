package org.jsut.mapper;

import org.apache.ibatis.annotations.*;
import org.jsut.pojo.User;
import org.jsut.pojo.UserVO;

@Mapper
public interface UserMapper {
    // 根据用户名查询用户
    @Select("select * from user where username=#{username}")
    User findByUsername(String username);
    // 添加用户
    @Insert("insert into user(username,password,create_time,update_time)"+
            "values(#{username},#{md5Pwd},now(),now())")
    void add(String username, String md5Pwd);

    @Update("update user set nickname=#{nickname},email=#{email},update_time=#{updateTime} where id=#{id}")
    void update(UserVO user);

    @Update("update user set user_pic=#{avatarUrl},update_time=now() where id=#{id}")
    void updateAvatar(String avatarUrl,Integer id);

    @Update("update user set password=#{md5String},update_time=now() where id=#{id}")
    void updatePwd(String md5String,Integer id);

    @Delete("delete from user where id=#{uid}")
    void delete(Integer  id);
}
