package com.yidong.controller;

import com.yidong.entity.Student;
import com.yidong.entity.User;
import com.yidong.service.StudentService;
import com.yidong.service.UserService;
import com.yidong.util.CpachaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/system")
@Controller
public class SystemController {
    @Autowired
    private UserService userService;

    @Autowired
    private StudentService studentService;

    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public ModelAndView index(ModelAndView mv){
        mv.setViewName("sys/index");
        return mv;
    }

    /**
     * 登陆页面
     * @param mv2
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public ModelAndView login(ModelAndView mv2){
        mv2.setViewName("sys/login");
        return mv2;
    }

    //显示验证码
    @RequestMapping(value="/get_cpacha",method=RequestMethod.GET)
    public void getCpacha(HttpServletRequest request,
                          @RequestParam(value="vl",defaultValue="4",required=false) Integer vl,
                          @RequestParam(value="w",defaultValue="98",required=false) Integer w,
                          @RequestParam(value="h",defaultValue="33",required=false) Integer h,
                          HttpServletResponse response) {
        CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
        String generatorVCode = cpachaUtil.generatorVCode();
        request.getSession().setAttribute("loginCpacha", generatorVCode);
        BufferedImage generatorRotateVCodeImage = cpachaUtil.generatorRotateVCodeImage(generatorVCode, true);
        try {
            ImageIO.write(generatorRotateVCodeImage, "gif", response.getOutputStream());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/login_out",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return "redirect:login";
    }


    /**
     * 登陆表单提交
     * @param username
     * @param password
     * @param vcode
     * @param request
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> login(
            @RequestParam(value = "username",required = true)String username,
            @RequestParam(value = "password",required = true)String password,
            @RequestParam(value = "vcode",required = true)String vcode,
            @RequestParam(value="type",required=true) int type,
            HttpServletRequest request
    ) {
        Map<String, Object> ret = new HashMap<String, Object>();
        if (StringUtils.isEmpty(username)) {
            ret.put("type","error");
            ret.put("msg","用户名不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(password)) {
            ret.put("type","error");
            ret.put("msg","密码不能为空！");
            return ret;
        }
        if (StringUtils.isEmpty(vcode)) {
            ret.put("type","error");
            ret.put("msg","验证码不能为空！");
            return ret;
        }
        String loginCpacha=(String)request.getSession().getAttribute("loginCpacha");
        if (StringUtils.isEmpty(loginCpacha)) {
            ret.put("type","error");
            ret.put("msg","长时间未操作，会话已失效，请刷新重试！");
            return ret;
        }
        if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())) {
            ret.put("type","error");
            ret.put("msg","验证码错误！");
            return ret;
        }
        request.getSession().setAttribute("loginCpacha",null);
        //从数据库中去查用户
        if (type==1){
            //管理员
            User user=userService.findByUserName(username);
            if (user==null){
                ret.put("type","error");
                ret.put("msg","不存在该用户！");
                return ret;
            }
            if (!password.equals(user.getPassword())){
                ret.put("type","error");
                ret.put("msg","密码错误！");
                return ret;
            }
//            request.getSession().setAttribute("userType",1);
            request.getSession().setAttribute("user",user);
        }
        if (type==2){
            //学生
            Student student = studentService.findByUserName(username);
            if (student==null){
                ret.put("type","error");
                ret.put("msg","不存在该学生！");
                return ret;
            }
            if (!password.equals(student.getPassword())){
                ret.put("type","error");
                ret.put("msg","密码错误！");
                return ret;
            }
            request.getSession().setAttribute("user",student);
        }
        request.getSession().setAttribute("userType",type);
        ret.put("type","success");
        ret.put("msg","登陆成功！");
        return ret;
    }


}
