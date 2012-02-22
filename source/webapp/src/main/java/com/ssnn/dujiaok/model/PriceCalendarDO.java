package com.ssnn.dujiaok.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

public class PriceCalendarDO {

	private String severtime;

	private List<Item> data = new ArrayList<Item>();


	public String getSevertime() {
		return severtime;
	}

	public void setSevertime(String severtime) {
		this.severtime = severtime;
	}

	public List<Item> getData() {
		return data;
	}

	public void setData(List<Item> data) {
		this.data = data;
	}

	public static class Item implements Comparable<Item> {
		
		
		public Item(int id, String name) {
			super();
			this.id = id;
			this.name = name;
		}

		private int id;

		private String name;

		public int getId() {
			return id;
		}

		public void setId(int id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public int compareTo(Item o) {
			if (o == null) {
				return 1;
			}
			return ((Integer) this.getId()).compareTo(o.getId());
		}

		@Override
		public boolean equals(Object obj) {
			if (!(obj instanceof Item)) {
				return false;
			}
			return this.getId() == ((Item) obj).getId();
		}
	}
	
	/**
	 * 排序，检查重复
	 */
	public void complete(){
		if(CollectionUtils.isEmpty(this.data)){
			return ;
		}
		Collections.sort(this.data) ;
		Item prev = null ;
		for(Iterator<Item> i = this.data.iterator() ;i.hasNext() ;){
			Item cur = i.next() ;
			if(prev != null){
				if(cur.equals(prev)){
					i.remove() ;
				}
			}
			prev = cur ;
		}
	}
}
