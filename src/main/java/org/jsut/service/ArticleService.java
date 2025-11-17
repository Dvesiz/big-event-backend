package org.jsut.service;

import org.jsut.pojo.Article;
import org.jsut.pojo.PageBean;

public interface ArticleService  {

    //添加文章
    void add(Article article);
    //条件分页列表查询
    PageBean<Article> list(Integer pageNum, Integer pageSize, Integer categoryId, String state);
    //删除文章
    void delete(Integer id);
    //文章详情
    Article detail(Integer id);
    //修改文章
    void update(Article article);
}
