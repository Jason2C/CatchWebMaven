package com.mybaitsDemo.callback;

import java.util.List;

import com.mybaitsDemo.baseDao.DaoFactory;


public interface CallBackDao<T> {
	 void callbackFun(List<T> list, DaoFactory daoFactory);
}
