package com.mybaitsDemo.service;

import java.util.List;

import com.mybaitsDemo.BaseService.IService;
import com.mybatisDemo.entity.Content;
import com.mybatisDemo.entity.ContentExample;

public interface ContentService  extends IService<ContentExample, Content, Integer>{

	
	List<Content> selectByExampleUnion(ContentExample example);
}
