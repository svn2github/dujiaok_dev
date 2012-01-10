package com.ssnn.dujiaok.biz.dal.ibatis;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.model.TicketDO;

public class IBatisTicketDAO extends SqlMapClientDaoSupport implements TicketDAO {

	@Override
	public TicketDO queryTicket(int ticketId) {
		return (TicketDO)getSqlMapClientTemplate().queryForObject("ticket.queryTicket", ticketId) ;
	}

	@Override
	public void insertTicket(TicketDO ticket) {
		getSqlMapClientTemplate().insert("ticket.insertTicket", ticket) ;
	}

}
