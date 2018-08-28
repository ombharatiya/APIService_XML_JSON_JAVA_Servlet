package com.he.api;

import javax.servlet.ServletRequest;

public class ParamUtility {
	public static String getValue(ServletRequest request, String paramName, String defaultValue)
	{
	     if(request.getParameter(paramName) != null){
	        return (request.getParameter(paramName));
	    } else{
	        return defaultValue;
	    }
	}
}