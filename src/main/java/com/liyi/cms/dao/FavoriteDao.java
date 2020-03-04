package com.liyi.cms.dao;

import java.util.List;

import com.liyi.cms.pojo.Favorite;
import com.liyi.cms.pojo.User;
/**
 * 
 * @ClassName: FavoriteDao 
 * @Description: TODO
 * @author:liyi 
 * @date: 2020年2月17日 下午8:29:58
 */
public interface FavoriteDao {
	//执行收藏
	int addFavorite(Favorite favorite);
	//展示我的收藏夹k
	List<Favorite> getFavoriteList(User user);
	//删除收藏文章
	int deleteFavorite(Integer id);
}
