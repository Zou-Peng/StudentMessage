package com.yidong.interceptor;

import com.yidong.entity.User;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        String url=request.getRequestURI();
//        System.out.println("进入拦截器，url="+url);
        Object user=request.getSession().getAttribute("user");
        if (user==null){
            //表示未登录状态或登陆失效
            System.out.println("未登录状态或登陆失效, url="+url);
            if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
                //ajax
                Map<String,Object> ret=new HashMap<String, Object>();
                ret.put("type","error");
                ret.put("msg","登陆状态已失效，请重新登录");
                response.getWriter().write(JSONObject.fromObject(ret).toString());
                return false;
            }
            response.sendRedirect(request.getContextPath()+"/system/login");
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
