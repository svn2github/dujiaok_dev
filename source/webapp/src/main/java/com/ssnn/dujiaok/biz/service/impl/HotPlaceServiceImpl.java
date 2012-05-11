package com.ssnn.dujiaok.biz.service.impl;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.ssnn.dujiaok.biz.exception.GetHotPlaceException;
import com.ssnn.dujiaok.biz.service.HotPlaceService;

public class HotPlaceServiceImpl implements HotPlaceService {

	private String url ;
	
	private int refreshHours ;
	
	private HotPlace hotPlace = null ;
	
	@Override
	public String getHotPlaceContent(boolean enfource) throws GetHotPlaceException {
		if(!isHotPlaceExpire()){
			return hotPlace.getContent() ;
		}
		
		HttpClient client = new HttpClient() ;
		PostMethod post = new PostMethod(url) ;
		
		try {
			int code = client.executeMethod(post) ;
			if(code == HttpStatus.SC_OK){
				String content = post.getResponseBodyAsString() ;
				if(StringUtils.isNotBlank(content)){
					synchronized (this) {
						if(hotPlace == null){
							hotPlace = new HotPlace() ;
						}
						hotPlace.setContent(content);
						hotPlace.setGmtGet(new Date()) ;
					}
					return content ;
				}
			}
			throw new GetHotPlaceException("Get hotplace error code : " + code) ;
		} catch (HttpException e) {
			throw new GetHotPlaceException(e.getMessage() , e) ;
		} catch (IOException e) {
			throw new GetHotPlaceException(e.getMessage() , e) ;
		}
		
	}
	
	private boolean isHotPlaceExpire(){
		if(hotPlace == null){
			return true ;
		}
		Date gmtGet = hotPlace.getGmtGet() ;
		if(gmtGet == null){
			return true ;
		}
		Calendar c = Calendar.getInstance();
		c.add(Calendar.HOUR, refreshHours) ;
		Calendar calNow = Calendar.getInstance() ;
		if(c.after(calNow)){
			return false ;
		}
		return true ;
		
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getRefreshHours() {
		return refreshHours;
	}

	public void setRefreshHours(int refreshHours) {
		this.refreshHours = refreshHours;
	}
	
	
	private static class HotPlace {
		private Date gmtGet ;
		private String content ;
		public Date getGmtGet() {
			return gmtGet;
		}
		public void setGmtGet(Date gmtGet) {
			this.gmtGet = gmtGet;
		}
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
	}
}
