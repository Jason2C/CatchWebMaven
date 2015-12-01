package com.mybaitsDemo.BaseService;

import java.util.List;

public interface IService<E, B, T> {
	/**
	 * ����������ȡһ������
	 * 
	 * @param id
	 * @return
	 */
	B getById(T id);

	/**
	 * ��������
	 * 
	 * @param b
	 * @throws Exception
	 */
	int save(B bean) throws Exception;

	/**
	 * ��������(������getId()����)
	 * @param bean
	 * @return
	 * @throws Exception 
	 */
	B saveOrUpdate(B bean) throws Exception;

	/**
	 * ����������ȡ��LIST����
	 * 
	 * @param example
	 * @return
	 */
	List<B> selectByExample(E example);

	/**
	 * �������������޸�
	 * 
	 * @param bean
	 * @param key
	 * @return
	 */
	Boolean updateByPrimaryKeySelective(B bean);

	/**
	 * ����example�޸Ĳ�Ϊ�յĶ����ֶ�
	 * @param 
	 * @param example
	 * @return
	 */
	Boolean updateByExampleSelective(B bean, E example);
	
	/**
	 * ����exampleɾ��
	 * @param example
	 * @return
	 */
	Boolean deleteByExample(E example);

	/**
	 * ��������ɾ��
	 * @param id
	 * @return
	 */
	Boolean deleteByPrimaryKey(T id);
	
	/**
	 * ��ѯ��������
	 * @param example
	 * @return
	 */
	int countByExample(E example);


}
