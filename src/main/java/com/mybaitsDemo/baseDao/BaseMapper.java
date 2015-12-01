package com.mybaitsDemo.baseDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface BaseMapper<E,B,T> {
	/**
	 * 查询数据总数
	 * @param example
	 * @return
	 */
	int countByExample(E example);

	/**
	 * 根据example删除
	 * @param example
	 * @return
	 */
	int deleteByExample(E example);

	/**
	 * 根据主键删除
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(T id);

	/**
	 * 插入新记录，
	 * @param record
	 * @return
	 */
	int insert(B record);

	/**
	 * 插入有值字段数据为新记录，
	 * @param record
	 * @return
	 */
	int insertSelective(B record);

	/**
	 * 
	 * @param example
	 * @return
	 */
	List<B> selectByExample(E example);
	
	/**
	 * 根据主键查询
	 * @param id
	 * @return
	 */
	B selectByPrimaryKey(T id);

	/**
	 * 修改，根据example的条件修改record不为空的字段值
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExampleSelective(@Param("record") B record,
			@Param("example") E example);

	/**
	 * 修改，根据example的条件修改record为空的字段值将被修改为空值
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExample(@Param("record") B record, @Param("example") E example);

	/**
	 * 根据主键修改不为空的对象字段值
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(B record);

	/**
	 * 根据主键修改对象字段值
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(B record);
}
