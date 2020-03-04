package com.liyi.cms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.liyi.cms.dao.FavoriteDao;
import com.liyi.cms.exception.CMSRuntimeException;
import com.liyi.cms.pojo.Favorite;
import com.liyi.cms.pojo.User;
import com.liyi.maven.common_utils.StringUtil;

@Service
public class FavoriteServiceImpl implements FavoriteService{
	@Autowired
	private FavoriteDao favoriteDao;
	//执行收藏
	@Override
	public int addFavorite(Favorite favorite) throws CMSRuntimeException {
		int i;
		/**
		 * 需要使用上面的isHttpUrl方法判断传入的收藏夹地址是否合法，
		 * 如果合法则保存，否则向上抛出自定义异常(CMSRuntimeException)，页面上显示错误消息。
		 */
		if(!StringUtil.isHttpUrl(favorite.getUrl())) {
			//向上抛出自定义异常(CMSRuntimeException)，页面上显示错误消息
			throw new CMSRuntimeException("url不合法");
		}else {
			 i=favoriteDao.addFavorite(favorite);
		}
		
		return i;
	}
	//展示我的收藏夹
	@Override
	public PageInfo<Favorite> getFavoriteList(int pageNum, int pageSize,User user) {
		PageHelper.startPage(pageNum, pageSize);
		List<Favorite> list=favoriteDao.getFavoriteList(user);
		PageInfo<Favorite> info=new PageInfo<Favorite>(list);
		return info;
	}
	@Override
	public int deleteFavorite(Integer id) {
		return favoriteDao.deleteFavorite(id);
	}
}

