package com.liyi.cms.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyi.cms.dao.ArticleDao;
import com.liyi.cms.dao.CategoryDao;
import com.liyi.cms.dao.ChannelDao;
import com.liyi.cms.dao.UserDao;
import com.liyi.cms.pojo.Article;
import com.liyi.cms.pojo.Category;
import com.liyi.cms.pojo.Channel;
import com.liyi.cms.pojo.Picture;
import com.liyi.cms.pojo.User;
import com.liyi.maven.common_utils.RandomUtil;

@Service
public class ArticleServiceImpl implements ArticleService{
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ChannelDao channelDao;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private UserDao userDao;

	@Override
	public List<Channel> getChannelAll() {
		return channelDao.select(null);
	}

	@Override
	public List<Category> getCateListByChannelId(Integer channelId) {
		Category category = new Category();
		category.setChannel_id(channelId);
		return categoryDao.select(category);
	}

	@Override
	public Article getById(Integer id) {
		return articleDao.selectById(id);
	}

	@Override
	public boolean save(Article article) {
		
		if(article.getId()==null) {
			/** 设置默认值 **/
			article.setHits(0);
			article.setHot(0);
			article.setDeleted(0);
			article.setTousuCnt(0);
			article.setCommentCnt(0);
			article.setCreated(new Date());
			article.setUpdated(new Date());
			/** 新增 **/
			articleDao.insert(article);
		}else {
			/** 修改 **/
			article.setUpdated(new Date());
			Article a = articleDao.selectById(article.getId());
			a.setTitle(article.getTitle());
			a.setPicture(article.getPicture());
			a.setChannel_id(article.getChannel_id());
			a.setCategory_id(article.getCategory_id());
			a.setContent(article.getContent());
			a.setStatus(article.getStatus());
			articleDao.update(a);
		}
		return true;
	}

	@Override
	public PageInfo<Article> getPageInfo(Article article, Integer pageNum, Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articleList = articleDao.select(article);
		/** 设置频道和分类的名称 **/
		articleList.forEach(a->{
			Channel channel = channelDao.selectById(a.getChannel_id());
			a.setChannel_name(channel.getName());
			Category cate = categoryDao.selectById(a.getCategory_id());
			a.setCategory_name(cate.getName());
			User user = userDao.selectById(a.getUser_id());
			a.setNickname(user.getNickname());
		});
		return new PageInfo<>(articleList);
	}

	@Override
	public boolean deleteById(Integer id) {
		Article article = articleDao.selectById(id);
		article.setDeleted(1);
		return articleDao.update(article)>0;
	}
	@Override
	public boolean deleteByIds(String ids) {
		String[] idArr = ids.split(",");
		for(String id:idArr) {
			deleteById(Integer.parseInt(id));
		}
		return true;
	}

	@Override
	public PageInfo<Article> getHotList(int pageNum, int pageSize) {
		Article article = new Article();
		article.setStatus(1);
		article.setHot(1);
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articleList = articleDao.select(article);
		articleList.forEach(a->{
			User user = userDao.selectById(a.getUser_id());
			a.setNickname(user.getNickname());
		});
		return new PageInfo<>(articleList);
	}

	@Override
	public PageInfo<Article> getList(Integer channelId, Integer cateId, Integer pageNum, Integer pageSize) {
		Article article = new Article();
		article.setStatus(1);
		article.setChannel_id(channelId);
		if(cateId>0) {
			article.setCategory_id(cateId);
		}
		PageHelper.startPage(pageNum, pageSize);
		List<Article> articleList = articleDao.select(article);
		articleList.forEach(a->{
			User user = userDao.selectById(a.getUser_id());
			a.setNickname(user.getNickname());
		});
		return new PageInfo<>(articleList);
	}

	@Override
	public Channel getChannelByChannelId(Integer channelId) {
		return channelDao.selectById(channelId);
	}

	@Override
	public boolean check(Article article) {
		Article article2 = articleDao.selectById(article.getId());
		article2.setStatus(article.getStatus());
		return articleDao.update(article2)>0;
	}

	@Override
	public void setHitsAndHot(Integer id) {
		Article article = articleDao.selectById(id);
		article.setHits(article.getHits()+1);
		if(article.getHits()>=20) {
			article.setHot(1);
		}
		articleDao.update(article);
	}

	@Override
	public List<Article> getNewList(Integer pageSize) {
		PageHelper.startPage(1, pageSize);
		Article article = new Article();
		article.setStatus(1);
		return articleDao.select(article);
	}

	@Override
	public List<Article> getRelArticelList(Integer channelId, Integer cateId, Integer articleId, Integer pageSize) {
		Article article = new Article();
		article.setChannel_id(channelId);
//		article.setCategory_id(cateId);
		article.setId(articleId);
		PageHelper.startPage(1, pageSize);
		List<Article> articleList = articleDao.select(article);
		return articleList;
	}

	@Override
	public boolean updateCommentCnt(Integer id) {
		Article article = articleDao.selectById(id);
		article.setCommentCnt(article.getCommentCnt()+1);
		return articleDao.update(article)>0;
	}

	@Override
	public Integer getRandomArticleId() {
		List<Integer> articleIdList = articleDao.selectIdList();
		int random = RandomUtil.random(0, articleIdList.size()-1);
		return articleIdList.get(random);
	}
	
	//查询24小时内文章  >=createTime
	@Override
	public List<Article> select24Article(String createTime) {
		return articleDao.select24Article(createTime);
	}

	@Override
	public List<Article> hotArticleList() {
		return articleDao.hotArticleList();
	}

	@Override
	public int saveImg(Picture picture) {
		return articleDao.addImg(picture);
	}

	@Override
	public PageInfo<Picture> selectImg(Integer pageNum,Integer pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		List<Picture> list = articleDao.selectImg();
		return new PageInfo<Picture>(list);
	}
}
