package com.ssnn.dujiaok.biz.dal;

import java.util.List;

import com.ssnn.dujiaok.model.SelfDriveDetailDO;

/**
 * SelfDriveDetailDAO
 * @author shenjia.caosj 2012-1-10
 *
 */
public interface SelfDriveDetailDAO {

	/**
	 * 
	 * @param selfDriveId
	 * @return
	 */
	List<SelfDriveDetailDO> querySelfDriveDetails(String selfDriveId) ;
	
	/**
	 * 
	 * @param selfDriveDetail
	 */
	void insertSelfDriveDetail(SelfDriveDetailDO selfDriveDetail) ;
	
	/**
	 * 
	 * @param selfDriveId
	 */
	void deleteSelfDriveDetails(String selfDriveId) ;
}
