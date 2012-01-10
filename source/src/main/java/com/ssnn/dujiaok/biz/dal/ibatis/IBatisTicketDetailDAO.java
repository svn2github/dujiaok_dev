package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.TicketDetailDAO;
import com.ssnn.dujiaok.model.TicketDetailDO;

public class IBatisTicketDetailDAO extends SqlMapClientDaoSupport implements TicketDetailDAO{

	@Override
	@SuppressWarnings("unchecked")
	public List<TicketDetailDO> queryTicketDetail(int ticketId) {
		return (List<TicketDetailDO>)getSqlMapClientTemplate().queryForList("ticket.queryTicketDetails" , ticketId) ;
	}

	@Override
	public void insertTicketDetail(TicketDetailDO ticketDetail) {
		getSqlMapClientTemplate().insert("ticket.insertTicketDetail", ticketDetail) ;
	}

	@Override
	public void deleteTicketDetails(int ticketId) {
		getSqlMapClientTemplate().delete("ticket.deleteTicketDetails" , ticketId) ;
	}

}
