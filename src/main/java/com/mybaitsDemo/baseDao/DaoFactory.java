package com.mybaitsDemo.baseDao;

import javax.annotation.Resource;

import com.mybatisDemo.mapper.ContentMapper;

public class DaoFactory {
	@Resource
	private ContentMapper contentMapper;

	public ContentMapper getContentMapper() {
		return contentMapper;
	}

}
