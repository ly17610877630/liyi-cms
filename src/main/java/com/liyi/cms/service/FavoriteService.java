package com.liyi.cms.service;

import com.github.pagehelper.PageInfo;
import com.liyi.cms.exception.CMSRuntimeException;
import com.liyi.cms.pojo.Favorite;
import com.liyi.cms.pojo.User;

public interface FavoriteService {
	//执行收藏
	int addFavorite (Favorite favo) throws CMSRuntimeException;
	//展示我的收藏夹
	PageInfo getFavoriteList(int pageNum, int pageSize, User user);
	//删除收藏文章
	int deleteFavorite(Integer id);
}
