package com.ssnn.dujiaok.web.velocity.toolbox;

import java.util.Collection;

/**
 * 
 * @author langben 2012-1-13
 *
 */
public class CollectionUtilsToolbox {

	/**
	 * 是否包含元素
	 * @param c
	 * @param element
	 * @return
	 */
	public boolean contains(Collection c , Object element){
		if(c == null)
			return false ;
		return c.contains(element) ;
	}
	
	public boolean isEmpty(Collection c){
		if(c == null)
			return true ;
		return c.isEmpty() ;
	}
	
	public int size(Collection c){
		if(c == null){
			return 0 ;
		}
		return c.size() ;
	}
	 
}
