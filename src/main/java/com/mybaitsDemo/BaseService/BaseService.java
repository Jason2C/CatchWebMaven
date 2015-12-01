package com.mybaitsDemo.BaseService;

import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.method.P;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.mybaitsDemo.baseDao.BaseMapper;
import com.mybaitsDemo.baseDao.DaoFactory;
import com.mybaitsDemo.callback.CallBackDao;
import com.mybaitsDemo.callback.CallBackObject;

public abstract class BaseService<E, B, T> implements IService<E, B, T> {
	public Logger log = LoggerFactory.getLogger(BaseService.class);
	@Resource
	protected DaoFactory daoFactory;

	public abstract BaseMapper<E, B, T> getGenericMapper();

	private JSONArray aliJsonArray;
	private JSONObject aliJSONObject;

	private int num;

	/**
	 * 初始化个性化的数据
	 * 
	 * @param bean
	 */
	public abstract void initValue(B bean);

	@Override
	@Cacheable(value = "sampleCache1", key = "#entityClass.name +':'+ #id",condition="null != #id")
	public B getById(T id) {
		log.info("getById()方法：");
		return null == id ? null : getGenericMapper().selectByPrimaryKey(id);
	}

	@Override
	public int save(B b) throws Exception {
		if (null != b && !b.equals("")) {
			initValue(b);
			getGenericMapper().insertSelective(b);
		}
		return 0;
	}

	@Override
	public B saveOrUpdate(B b) throws Exception {
		if (null == b) {
			return null;
		}
		Method m = b.getClass().getDeclaredMethod("getId");
		log.info("反射得到的值：{}", m.invoke(b));
		if (null == m.invoke(b)) {
			this.save(b);
		} else {
			this.updateByPrimaryKeySelective(b);
		}
		return null;
	}

	@Override
	@Transactional(readOnly = true)
	public List<B> selectByExample(E example) {
		return getGenericMapper().selectByExample(example);
	}

	@Override
	@CachePut(value = "sampleCache1", key = "#entityClass.name +':'+#record.getId()")
	public Boolean updateByPrimaryKeySelective(B record) {
		num = getGenericMapper().updateByPrimaryKeySelective(record);
		return (num > 0 ? true : false);
	}

	@Override
	public Boolean updateByExampleSelective(B record, E example) {
		num = getGenericMapper().updateByExampleSelective(record, example);
		return (num > 0 ? true : false);
	}

	@Override
	public Boolean deleteByExample(E example) {
		num = getGenericMapper().deleteByExample(example);
		return (num > 0 ? true : false);
	}

	@Override
	@CacheEvict(value="sampleCache1", key="#entityClass.name+':'+#id",beforeInvocation=true)
	public Boolean deleteByPrimaryKey(T id) {
		num = getGenericMapper().deleteByPrimaryKey(id);
		return (num > 0 ? true : false);
	}

	@Override
	public int countByExample(E example) {
		return getGenericMapper().countByExample(example);
	}

	// /////////////////////////////////////////////
	@Transactional(readOnly = true)
	public JSONArray selectByExample(E example, CallBackObject<B> callback) {
		List<B> list = (List<B>) getGenericMapper().selectByExample(example);

		aliJsonArray = new JSONArray(list.size());
		aliJSONObject = new JSONObject(1);
		callback.callbackFun(list, aliJSONObject);
		return aliJsonArray;
	}

	@Transactional(readOnly = true)
	public JSONArray selectByExample(E example, CallBackDao<B> callback) {
		List<B> list = (List<B>) getGenericMapper().selectByExample(example);
		aliJsonArray = new JSONArray(list.size());
		aliJSONObject = new JSONObject(1);
		callback.callbackFun(list, daoFactory);
		return aliJsonArray;
	}
}
