package com.mybatisDemo.mapper;

import java.util.List;

import com.mybaitsDemo.baseDao.BaseMapper;
import com.mybatisDemo.entity.Content;
import com.mybatisDemo.entity.ContentExample;

public interface ContentMapper extends BaseMapper<ContentExample, Content, Integer> {

	  List<Content> selectByExampleUnion(ContentExample example);

	
}