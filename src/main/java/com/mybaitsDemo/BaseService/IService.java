package com.mybaitsDemo.BaseService;

import java.util.List;

public interface IService<E, B, T> {
	/**
	 * 根据主键获取一个对象
	 * 
	 * @param id
	 * @return
	 */
	B getById(T id);

	/**
	 * 新增保存
	 * 
	 * @param b
	 * @throws Exception
	 */
	int save(B bean) throws Exception;

	/**
	 * 保存或更新(必须有getId()方法)
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	B saveOrUpdate(B bean) throws Exception;

	/**
	 * 根据条件获取到LIST集合
	 * 
	 * @param example
	 * @return
	 */
	List<B> selectByExample(E example);

	/**
	 * 根据主键进行修改
	 * 
	 * @param bean
	 * @param key
	 * @return
	 */
	Boolean updateByPrimaryKeySelective(B bean);

	/**
	 * 根据example修改不为空的对象字段
	 * @param 
	 * @param example
	 * @return
	 */
	Boolean updateByExampleSelective(B bean, E example);
	
	/**
	 * 根据example删除
	 * @param example
	 * @return
	 */
	Boolean deleteByExample(E example);

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	Boolean deleteByPrimaryKey(T id);
	
	/**
	 * 查询数据总数
	 * @param example
	 * @return
	 */
	int countByExample(E example);


}
