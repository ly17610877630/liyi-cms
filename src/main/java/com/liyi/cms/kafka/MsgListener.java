package com.liyi.cms.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.liyi.cms.dao.ArticleDao;
import com.liyi.cms.pojo.Article;


//消息监听类，在这个类中要实现一个接口，此时这个类就具备了监听消息的功能1
public class MsgListener implements MessageListener<String, String> {

	@Autowired
	ArticleDao articleDao;
	
	//这个方法就是收消息的方法
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		System.err.println("收到消息");
		String jsonString=data.value();
		//jsonString是一个json类型的字符串，要转成articles文章对象然后保存到mysql
		Article article = JSON.parseObject(jsonString,Article.class);
		//注入articleDao然后就可以直接调用保存方法
		articleDao.insert(article);
		System.err.println("保存了文章对象.........");
	}
}
