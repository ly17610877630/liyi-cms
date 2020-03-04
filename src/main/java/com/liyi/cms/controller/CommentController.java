package com.liyi.cms.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.liyi.cms.common.CmsConst;
import com.liyi.cms.common.JsonResult;
import com.liyi.cms.pojo.Comment;
import com.liyi.cms.pojo.User;
import com.liyi.cms.service.CommentService;

@Controller
@RequestMapping("/comment/")
public class CommentController {
	@Autowired
	private CommentService commentService;
	
	/**
	 * @Title: add   
	 * @Description: 添加评论  
	 * @param: @param comment
	 * @param: @param session
	 * @param: @return      
	 * @return: JsonResult      
	 * @throws
	 */
	@RequestMapping("add")
	public @ResponseBody JsonResult add(Comment comment,HttpSession session) {
		User userInfo = (User)session.getAttribute(CmsConst.UserSessionKey);
		if(userInfo==null) {
			return JsonResult.fail(10000, "用户未登录");
		}
		comment.setUserId(userInfo.getId());
		commentService.add(comment);
		return JsonResult.sucess();
	}
}

