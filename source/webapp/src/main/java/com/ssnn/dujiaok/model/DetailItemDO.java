package com.ssnn.dujiaok.model;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.time.DateUtils;

/**
 * ProductDetailItem
 * 
 * @author shenjia.caosj 2012-3-22
 * 
 */
public class DetailItemDO implements Comparable<DetailItemDO> {

	public DetailItemDO(int detailId, Date date, BigDecimal bottomPrice) {
		super();
		this.detailId = detailId;
		this.date = date;
		this.bottomPrice = bottomPrice;
	}

	private int detailId;

	private Date date;

	private BigDecimal bottomPrice;

	public int getDetailId() {
		return detailId;
	}

	public void setDetailId(int detailId) {
		this.detailId = detailId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public BigDecimal getBottomPrice() {
		return bottomPrice;
	}

	public void setBottomPrice(BigDecimal bottomPrice) {
		this.bottomPrice = bottomPrice;
	}

	@Override
	public int compareTo(DetailItemDO o) {
		if(o == null){
			return 1 ;
		}
		if(this.equals(o)){
			return 0 ;
		}
		if(this.getDate().after(o.getDate())){
			return 1 ;
		}
		return -1 ;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == null || !(obj instanceof DetailItemDO)){
			return false ;
		}
		return DateUtils.isSameDay(this.getDate(), ((DetailItemDO)obj).getDate()) ;
	}
}
