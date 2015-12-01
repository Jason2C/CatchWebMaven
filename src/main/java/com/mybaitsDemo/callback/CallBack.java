package com.mybaitsDemo.callback;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface CallBack<T> {
	void callbackFun(List<T> list, JSONObject jsonObject);
}
