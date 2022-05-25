package com.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.modelo.pojos.Usuario;

@Component
public class InterceptorEmple implements HandlerInterceptor{
	@Override 
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception { 
    	
    	System.out.println("*****Interceptor Empleado********");
    	
    	if(request.getSession().getAttribute("usuario")!=null ) {
    		Usuario usuario = (Usuario)request.getSession().getAttribute("usuario");
    		if(usuario.getRol().getId()==2 ||  usuario.getRol().getId()==3) {
    			return true;
    		}	
    	}
    	response.sendRedirect(request.getContextPath() + "/inicio");
    	return false;
    }
}