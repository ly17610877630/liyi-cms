package com.liyi.cms.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.liyi.cms.pojo.Article;
import com.liyi.cms.pojo.Picture;

public interface ArticleDao extends BaseDao<Article> {
	@Select("SELECT id from cms_article")
	List<Integer> selectIdList();

	//查询热点文章
	public List<Article> hotArticleList();
	
	//查询24小时内文章
	public List<Article> select24Article(@Param("createTime")String createTime);
	
	//添加图片
	int addImg(Picture picture);
	
	//查询图片
	List<Picture> selectImg();
}
