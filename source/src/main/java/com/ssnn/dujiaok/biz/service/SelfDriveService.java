package com.ssnn.dujiaok.biz.service;

import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;

/**
 * 自驾
 * @author shenjia.caosj 2012-1-19
 *
 */
public interface SelfDriveService {

	/**
	 * 获取SelfDrive,不包含detail
	 * @param selfDriveId
	 * @return
	 */
	TicketDO getSelfDrive(String selfDriveId) ;
	
	/**
	 * 获取SelfDrive,包含detail
	 * @param selfDriveId
	 * @return
	 */
	TicketDO getSelfDriveWithDetails(String selfDriveId) ;
	
	
	/**
	 * 创建SelfDrive,包含detail
	 * @param selfDriveId
	 */
	TicketDO createSelfDriveAndDetails(SelfDriveDO selfDrive) ;
	
	
	/**
	 * 创建SelfDrive,包含Detail
	 * @param selfDriveId
	 */
	TicketDO updateSelfDriveAndDetails(SelfDriveDO selfDrive) ;
}
