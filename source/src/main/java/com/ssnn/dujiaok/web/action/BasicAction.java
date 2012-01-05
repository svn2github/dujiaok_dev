package com.ssnn.dujiaok.web.action;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class BasicAction extends ActionSupport{

	public static final String MEMBER_NOT_LOGIN = "memberLogin" ;
	
	protected HttpServletRequest getRequest() {
        return ServletActionContext.getRequest();  
    }
	
	protected HttpServletResponse getResponse(){
		return ServletActionContext.getResponse() ;
	}
	
	protected Map<String,Object> getSession(){
		return ActionContext.getContext().getSession() ;
	}
	
	protected HttpSession getHttpSession(){
		return getRequest().getSession() ;
	}
	/**
     * 
     * @return
     */
    protected Map<String,Object> getParameters(){
        ActionContext actionContext = ActionContext.getContext();
    	Map<String,Object> parameters = (Map<String,Object>)actionContext.getParameters();    
    	return parameters;
    }
    
    public String getActionError(){
    	Collection<String> errors = getActionErrors() ;
    	if(errors == null || errors.isEmpty()){
    		return null ;
    	}
    	return errors.iterator().next() ;
    }
    
    public String getActionMessage(){
    	Collection<String> messages = getActionMessages() ;
    	if(messages == null || messages.isEmpty()){
    		return null ;
    	}
    	return messages.iterator().next() ;
    }
}
