package org.jsut.service;

import org.jsut.pojo.Category;

import java.util.List;

public interface CategoryService {
    //添加分类
    void add(Category category);
    //查询所有分类,列表查询
    List<Category> list();
    //查询分类详情,根据id
    Category detail(Integer id);
    //删除分类
    void delete(Integer id);
    //修改分类
    void update(Category category);
}
