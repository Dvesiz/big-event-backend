package org.jsut.service.impl;

import org.jsut.mapper.CategoryMapper;
import org.jsut.pojo.Category;
import org.jsut.service.CategoryService;
import org.jsut.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @Override
    public void add(Category category) {

        //补充属性值
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        category.setCreateUser(id);
        categoryMapper.add(category);
    }

    @Override
    public List<Category> list() {

        Map<String, Object> map = ThreadLocalUtil.get() ;
        Integer UserId = (Integer) map.get("id");
        return (categoryMapper.list(UserId));
    }

    @Override
    public Category detail(Integer id){
        return categoryMapper.detail(id);
    }

    @Override
    public void delete(Integer id){
        categoryMapper.delete(id);
    }

    @Override
    public void update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }
}
