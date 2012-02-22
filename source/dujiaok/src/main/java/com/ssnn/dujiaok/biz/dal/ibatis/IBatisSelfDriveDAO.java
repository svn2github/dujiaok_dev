package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.SelfDriveDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.SelfDriveDO;
import com.ssnn.dujiaok.util.IntegerUtils;

/**
 * SelfDriveDAO
 * @author shenjia.caosj 2012-1-18
 *
 */
public class IBatisSelfDriveDAO extends SqlMapClientDaoSupport implements SelfDriveDAO{

	@Override
	public SelfDriveDO querySelfDrive(String selfDriveId) {
		return (SelfDriveDO)getSqlMapClientTemplate().queryForObject("selfDrive.querySelfDrive" ,selfDriveId) ;
	}
	
	@Override
	public void insertSelfDrive(SelfDriveDO selfDrive) {
		getSqlMapClientTemplate().insert("selfDrive.insertSelfDrive",selfDrive) ;
	}

	@Override
	public void updateSelfDrive(SelfDriveDO selfDrive) {
		getSqlMapClientTemplate().update("selfDrive.updateSelfDrive" , selfDrive) ;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<SelfDriveDO> querySelfDrives(Map<String, Object> condition,Pagination pagination) {
		condition.put("start", pagination.getStart()-1) ;
		condition.put("size", pagination.getSize()) ;
		return getSqlMapClientTemplate().queryForList("selfDrive.querySelfDrives" , condition) ;
	}

	@Override
	public int countSelfDrives(Map<String, Object> condition) {
		return IntegerUtils.objectToInt(getSqlMapClientTemplate().queryForObject("selfDrive.countSelfDrives" , condition)) ;
	}

	@Override
	public void deleteSelfDrive(String selfDriveId) {
		getSqlMapClientTemplate().delete("selfDrive.deleteSelfDrive" , selfDriveId) ;
	}

}
