package com.ssnn.dujiaok.biz.dal;

import java.util.List;
import java.util.Map;

import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.SelfDriveDO;

/**
 * 自驾DAO
 * @author shenjia.caosj 2012-1-18
 *
 */
public interface SelfDriveDAO {

	/**
	 * 
	 * @param selfDriveId
	 * @return
	 */
	SelfDriveDO querySelfDrive(String selfDriveId) ;
	/**
	 * 
	 * @param selfDrive
	 */
	void insertSelfDrive(SelfDriveDO selfDrive) ;
	
	/**
	 * 
	 * @param selfDrive
	 */
	void updateSelfDrive(SelfDriveDO selfDrive) ;
	
	/**
	 * 查询SelfDrive
	 * @param condition 条件  key=字段名，value=字段值
	 * @param pagination 分页BEAN
	 * @return
	 */
	List<SelfDriveDO> querySelfDrives(Map<String,Object> condition , Pagination pagination) ;
	
	/**
	 * 
	 * @param condition
	 * @return
	 */
	int countSelfDrives(Map<String,Object> condition) ;
	
	/**
	 * 
	 * @param selfDriveId
	 */
	void deleteSelfDrive(String selfDriveId) ;
}
