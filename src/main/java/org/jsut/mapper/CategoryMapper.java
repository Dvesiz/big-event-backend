package org.jsut.mapper;

import org.apache.ibatis.annotations.*;
import org.jsut.pojo.Category;

import java.util.List;

@Mapper
public interface CategoryMapper {
    //添加分类
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time)"+
    "values(#{categoryName},#{categoryAlias},#{createUser},#{createTime},#{updateTime})")
    void add(Category category);
    //查询分类
    @Select("select * from category where create_user=#{userId}")
    List<Category> list(Integer userId);

    @Select("select * from category where id=#{id}")
    Category detail(Integer id);
    //删除
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
    //更新
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=#{updateTime} where id=#{id}")
    void update(Category category);
}
