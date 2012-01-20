package com.ssnn.dujiaok.biz.dal.ibatis;

import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

import com.ssnn.dujiaok.biz.dal.TicketDAO;
import com.ssnn.dujiaok.biz.page.Pagination;
import com.ssnn.dujiaok.model.TicketDO;
import com.ssnn.dujiaok.util.IntegerUtils;

public class IBatisTicketDAO extends SqlMapClientDaoSupport implements TicketDAO {

	@Override
	public TicketDO queryTicket(String ticketId) {
		return (TicketDO)getSqlMapClientTemplate().queryForObject("ticket.queryTicket", ticketId) ;
	}

	@Override
	public void insertTicket(TicketDO ticket) {
		getSqlMapClientTemplate().insert("ticket.insertTicket", ticket) ;
	}
	
	@Override
	public void updateTicket(TicketDO ticket) {
		getSqlMapClientTemplate().update("ticket.updateTicket", ticket) ;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<TicketDO> queryTickets(Map<String, Object> condition,Pagination pagination) {
		condition.put("start", pagination.getStart()-1) ;
		condition.put("size", pagination.getSize()) ;
		return getSqlMapClientTemplate().queryForList("ticket.queryTickets", condition);
	}

	@Override
	public int countTickets(Map<String, Object> condition) {
		return IntegerUtils.objectToInt(getSqlMapClientTemplate().queryForObject("ticket.countTickets", condition));
	}

}
