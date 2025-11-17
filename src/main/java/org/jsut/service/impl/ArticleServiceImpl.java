package org.jsut.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.jsut.mapper.ArticleMapper;
import org.jsut.pojo.Article;
import org.jsut.pojo.PageBean;
import org.jsut.service.ArticleService;
import org.jsut.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private  ArticleMapper articleMapper;

    @Override
    public void add(Article article) {
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer id = (Integer) map.get("id");
        article.setCreateUser(id);
        articleMapper.add(article);
    }

    @Override
    public PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state) {
        //1.创建PageBean对象
        PageBean<Article> pageBean = new PageBean<>();

        //2.开启分页查询PageHelper(Mybatis分页插件)
        PageHelper.startPage(pageNum, pageSize);

        //3.调用mapper查询
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> as = articleMapper.list(userId, categoryId, state);
        //Page中提供了方法，可以获取PageHelper分页查询后，得到的总记录数和当前页数据
        Page<Article> p = (Page<Article>) as;

        //4.封装数据到PageBean对象中
        pageBean.setTotal(p.getTotal());
        pageBean.setItems(p.getResult());
        return pageBean;

    }

    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);

    }

    @Override
    public Article detail(Integer id) {
        Article article = articleMapper.detail(id);
        if(article != null){
            return article;
        }
        return null;
    }

    @Override
    public void update(Article article) {
        article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

}
