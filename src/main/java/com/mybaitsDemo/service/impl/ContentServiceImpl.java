package com.mybaitsDemo.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mybaitsDemo.BaseService.BaseService;
import com.mybaitsDemo.baseDao.BaseMapper;
import com.mybaitsDemo.service.ContentService;
import com.mybatisDemo.entity.Content;
import com.mybatisDemo.entity.ContentExample;
import com.mybatisDemo.mapper.ContentMapper;

@Service
public class ContentServiceImpl extends BaseService<ContentExample, Content, Integer> implements ContentService{

	public ContentServiceImpl() {
		// TODO Auto-generated constructor stub
	}

	@Resource
	private ContentMapper contentMapper;
	
	@Override
	public BaseMapper<ContentExample, Content, Integer> getGenericMapper() {
		// TODO Auto-generated method stub
		return daoFactory.getContentMapper();
	}

	@Override
	public void initValue(Content bean) {
		// TODO Auto-generated method stub
		
	}
	
	public List<Content> selectByExampleUnion(ContentExample example){
		return contentMapper.selectByExampleUnion(example);
	}

}
