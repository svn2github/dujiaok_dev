package com.ssnn.dujiaok.model;

import java.util.ArrayList;
import java.util.List;

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
}
