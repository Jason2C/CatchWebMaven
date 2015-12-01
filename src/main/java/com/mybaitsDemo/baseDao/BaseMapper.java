package com.mybaitsDemo.baseDao;

import java.util.List;

import org.apache.ibatis.annotations.Param;


public interface BaseMapper<E,B,T> {
	/**
	 * ��ѯ��������
	 * @param example
	 * @return
	 */
	int countByExample(E example);

	/**
	 * ����exampleɾ��
	 * @param example
	 * @return
	 */
	int deleteByExample(E example);

	/**
	 * ��������ɾ��
	 * @param id
	 * @return
	 */
	int deleteByPrimaryKey(T id);

	/**
	 * �����¼�¼��
	 * @param record
	 * @return
	 */
	int insert(B record);

	/**
	 * ������ֵ�ֶ�����Ϊ�¼�¼��
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
	 * ����������ѯ
	 * @param id
	 * @return
	 */
	B selectByPrimaryKey(T id);

	/**
	 * �޸ģ�����example�������޸�record��Ϊ�յ��ֶ�ֵ
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExampleSelective(@Param("record") B record,
			@Param("example") E example);

	/**
	 * �޸ģ�����example�������޸�recordΪ�յ��ֶ�ֵ�����޸�Ϊ��ֵ
	 * @param record
	 * @param example
	 * @return
	 */
	int updateByExample(@Param("record") B record, @Param("example") E example);

	/**
	 * ���������޸Ĳ�Ϊ�յĶ����ֶ�ֵ
	 * @param record
	 * @return
	 */
	int updateByPrimaryKeySelective(B record);

	/**
	 * ���������޸Ķ����ֶ�ֵ
	 * @param record
	 * @return
	 */
	int updateByPrimaryKey(B record);
}
