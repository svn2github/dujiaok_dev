package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class RoomCheckinDO extends AbstractModel { 
	
	public RoomCheckinDO(List<DetailItemDO> canCheckinList){
		this.canCheckinList = canCheckinList ;
		if(CollectionUtils.isEmpty(canCheckinList)){
			return ;
		}
		BigDecimal total = new BigDecimal("0.00");
		for(DetailItemDO item : canCheckinList){
			BigDecimal price = item.getDetail().getPrice() ;
			total = total.add(price) ;
		}
		this.totalUniPrice = total ;
	}
	
	private List<DetailItemDO> canCheckinList ;

	private BigDecimal totalUniPrice ;

	public List<DetailItemDO> getCanCheckinList() {
		return canCheckinList;
	}

	public void setCanCheckinList(List<DetailItemDO> canCheckinList) {
		this.canCheckinList = canCheckinList;
	}

	public BigDecimal getTotalUniPrice() {
		return totalUniPrice;
	}

	public void setTotalUniPrice(BigDecimal totalUniPrice) {
		this.totalUniPrice = totalUniPrice;
	}


	
	
}
