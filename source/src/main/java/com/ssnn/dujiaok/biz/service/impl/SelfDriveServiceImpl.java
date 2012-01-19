package com.ssnn.dujiaok.biz.service.impl;

import com.ssnn.dujiaok.biz.dal.SelfDriveDAO;
import com.ssnn.dujiaok.biz.dal.SelfDriveDetailDAO;
import com.ssnn.dujiaok.biz.service.SelfDriveService;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.model.TicketDO;

/**
 * 
 * @author shenjia.caosj 2012-1-19
 *
 */
public class SelfDriveServiceImpl implements SelfDriveService{

	private SelfDriveDAO selfDriveDAO ;
	
	private SelfDriveDetailDAO selfDriveDetailDAO ;
	
	public void setSelfDriveDAO(SelfDriveDAO selfDriveDAO) {
		this.selfDriveDAO = selfDriveDAO;
	}

	public void setSelfDriveDetailDAO(SelfDriveDetailDAO selfDriveDetailDAO) {
		this.selfDriveDetailDAO = selfDriveDetailDAO;
	}

	@Override
	public TicketDO getSelfDrive(String selfDriveId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TicketDO getSelfDriveWithDetails(String selfDriveId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TicketDO createSelfDriveAndDetails(SelfDriveDO selfDrive) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TicketDO updateSelfDriveAndDetails(SelfDriveDO selfDrive) {
		// TODO Auto-generated method stub
		return null;
	}

}
