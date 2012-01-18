package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.SelfDriveDetailDAO;
import com.ssnn.dujiaok.model.SelfDriveDetailDO;

public class IBatisSelfDriveDetailDAO extends SqlMapClientDaoSupport implements SelfDriveDetailDAO{

	@Override
	@SuppressWarnings("unchecked")
	public List<SelfDriveDetailDO> querySelfDriveDetails(String selfDriveId) {
		return (List<SelfDriveDetailDO>)getSqlMapClientTemplate().queryForList("selfDrive.querySelfDriveDetails" , selfDriveId) ;
	}

	@Override
	public void insertSelfDriveDetail(SelfDriveDetailDO selfDriveDetail) {
		getSqlMapClientTemplate().insert("selfDrive.insertSelfDriveDetail", selfDriveDetail) ;
	}

	@Override
	public void deleteSelfDriveDetails(String selfDriveId) {
		getSqlMapClientTemplate().delete("selfDrive.deleteSelfDriveDetails" , selfDriveId) ;
	}

}
