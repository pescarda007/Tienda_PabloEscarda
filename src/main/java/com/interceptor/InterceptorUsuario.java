package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.modelo.pojos.Usuario;

@Component
public class InterceptorUsuario implements HandlerInterceptor{
	@Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
    	
    	System.out.println("*****Interceptor Usuario********");
    	
    	if(request.getSession().getAttribute("usuario")!=null) {
    		return true;
    	}
    	response.sendRedirect(request.getContextPath() + "/inicio");
    	return false;
    }
}