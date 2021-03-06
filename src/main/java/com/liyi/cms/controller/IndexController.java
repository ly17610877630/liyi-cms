package com.liyi.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.liyi.cms.pojo.Article;
import com.liyi.cms.pojo.Category;
import com.liyi.cms.pojo.Channel;
import com.liyi.cms.pojo.Comment;
import com.liyi.cms.pojo.Link;
import com.liyi.cms.pojo.Picture;
import com.liyi.cms.pojo.Slide;
import com.liyi.cms.pojo.User;
import com.liyi.cms.service.ArticleService;
import com.liyi.cms.service.CommentService;
import com.liyi.cms.service.LinkService;
import com.liyi.cms.service.SlideService;
import com.liyi.cms.service.UserService;
import com.liyi.maven.common_utils.DateUtil;

@Controller
public class IndexController {
	@Autowired
	private ArticleService articleService;
	@Autowired
	private SlideService slideService;
	@Autowired
	private UserService userService;
	@Autowired
	private CommentService commentService;
	@Autowired
	private LinkService linkService;

	/**
	 * @Title: index @Description: 首页 @param: @param model @param: @return @return:
	 * String @throws
	 */
	@RequestMapping("/")
	public String index(Model model) {
		hot(model, 1);
		return "index";
	}

	/**
	 * @Title: hot @Description: 热门分页 @param: @param model @param: @param
	 * pageNum @param: @return @return: String @throws
	 */
	@RequestMapping("/hot/{pageNum}.html")
	public String hot(Model model, @PathVariable Integer pageNum) {

		Thread t1 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 频道 **/
				List<Channel> channelList = articleService.getChannelAll();
				model.addAttribute("channelList", channelList);
			}
		});

		Thread t2 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 焦点图 **/
				List<Slide> slideList = slideService.getAll();
				model.addAttribute("slideList", slideList);
			}
		});

		Thread t3 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 热点文章 **/
				PageInfo<Article> pageInfo = articleService.getHotList(pageNum, 4);
				model.addAttribute("pageInfo", pageInfo);
			}
		});

		Thread t4 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 最新文章 **/
				List<Article> newArticleList = articleService.getNewList(6);
				model.addAttribute("newArticleList", newArticleList);
			}
		});

		Thread t5 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 最热文章 **/
				List<Article> hotArticleList = articleService.hotArticleList();
				model.addAttribute("hotArticleList", hotArticleList);
			}
		});

		Thread t6 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 24小时热文 **/
				List<Article> select24Article = articleService.select24Article(DateUtil.getDate24Hour());
				model.addAttribute("select24Article", select24Article);
			}
		});

		Thread t7 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 最新图片 **/
				PageInfo<Picture> selectImg = articleService.selectImg(pageNum, 10);
				model.addAttribute("selectImg", selectImg);
			}
		});
		
		Thread t8 = new Thread(new Runnable() {

			@Override
			public void run() {
				/** 友情链接 **/
				List<Link> linkList = linkService.getLinkListAll();
				model.addAttribute("linkList", linkList);
			}
		});

		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		t6.start();
		t7.start();
		t8.start();

		try {
			t1.join();
			t2.join();
			t3.join();
			t4.join();
			t5.join();
			t6.join();
			t7.join();
			t8.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		/** 频道 **/
		/*
		 * List<Channel> channelList = articleService.getChannelAll();
		 * model.addAttribute("channelList", channelList);
		 *//** 焦点图 **/
		/*
		 * List<Slide> slideList = slideService.getAll();
		 * model.addAttribute("slideList", slideList);
		 *//** 热点文章 **/
		/*
		 * PageInfo<Article> pageInfo = articleService.getHotList(pageNum,4);
		 * model.addAttribute("pageInfo", pageInfo);
		 *//** 最新文章 **/
		/*
		 * List<Article> newArticleList = articleService.getNewList(6);
		 * model.addAttribute("newArticleList", newArticleList);
		 *//** 最热文章 **/
		/*
		 * List<Article> hotArticleList = articleService.hotArticleList();
		 * model.addAttribute("hotArticleList", hotArticleList);
		 *//** 24小时热文 **/
		/*
		 * List<Article> select24Article =
		 * articleService.select24Article(DateUtil.getDate24Hour());
		 * model.addAttribute("select24Article", select24Article);
		 *//** 友情链接 **//*
						 * List<Link> linkList = linkService.getLinkListAll();
						 * model.addAttribute("linkList", linkList);
						 */
		return "index";
	}

	/**
	 * @Title: channel @Description: 频道页 @param: @param model @param: @param
	 * channelId @param: @param cateId @param: @param
	 * pageNum @param: @return @return: String @throws
	 */
	@RequestMapping("/{channelId}/{cateId}/{pageNum}.html")
	public String channel(Model model, @PathVariable Integer channelId, @PathVariable Integer cateId,
			@PathVariable Integer pageNum) {
		List<Channel> channelList = articleService.getChannelAll();
		List<Slide> slideList = slideService.getAll();
		PageInfo<Article> pageInfo = articleService.getList(channelId, cateId, pageNum, 6);
		List<Category> cateList = articleService.getCateListByChannelId(channelId);
		Channel channel = articleService.getChannelByChannelId(channelId);
		List<Article> newArticleList = articleService.getNewList(6);
		PageInfo<Article> hotList = articleService.getHotList(1, 3);
		List<Article> hotArticleList = articleService.hotArticleList();
		List<Article> select24Article = articleService.select24Article(DateUtil.getDate24Hour());
		/** 最新图片 **/
		PageInfo<Picture> selectImg = articleService.selectImg(pageNum, 10);
		model.addAttribute("selectImg", selectImg);
		model.addAttribute("channelList", channelList);
		model.addAttribute("cateList", cateList);
		model.addAttribute("slideList", slideList);
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("channel", channel);
		model.addAttribute("newArticleList", newArticleList);
		model.addAttribute("hotList", hotList);
		model.addAttribute("hotArticleList", hotArticleList);
		model.addAttribute("select24Article", select24Article);
		/** 友情链接 **/
		List<Link> linkList = linkService.getLinkListAll();
		model.addAttribute("linkList", linkList);
		return "index";
	}

	/**
	 * @Title: articleDetail @Description: 文章详情页 @param: @param id @param: @param
	 * model @param: @return @return: String @throws
	 */
	@RequestMapping("/article/detail/{id}.html")
	public String articleDetail(@PathVariable Integer id,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, Model model) {
		Article article = articleService.getById(id);
		User user = userService.getById(article.getUser_id());
		article.setNickname(user.getNickname());
		model.addAttribute("article", article);
		/** 查询相关文章 **/
		List<Article> relArticelList = articleService.getRelArticelList(article.getChannel_id(),
				article.getCategory_id(), article.getId(), 3);
		model.addAttribute("relArticelList", relArticelList);
		/** 设置文章点击量，若点击量大于20成为热点文章 **/
		articleService.setHitsAndHot(id);
		/** 评论列表 **/
		PageInfo<Comment> commentPageInfo = commentService.getPageInfo(article.getId(), pageNum, 10);
		model.addAttribute("pageInfo", commentPageInfo);
		return "article-detail";
	}
}
