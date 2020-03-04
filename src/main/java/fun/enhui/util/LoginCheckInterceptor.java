package fun.enhui.util;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 一个拦截器,对登录进行验证
 */
public class LoginCheckInterceptor extends HandlerInterceptorAdapter {

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断登录
        if(handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod)handler;
            LoginAnnocation la = hm.getMethodAnnotation(LoginAnnocation.class);
            mgrLoginAnnocation mla = hm.getMethodAnnotation(mgrLoginAnnocation.class);
            if(la!=null && UserContext.getCurrent()==null){
                response.sendRedirect("/login.html");
                return false;
            }else if(mla != null && UserContext.getCurrent()==null){
                response.sendRedirect("/mgrlogin.html");
                return false;
            }

        }
        return super.preHandle(request,response,handler);
    }
}
