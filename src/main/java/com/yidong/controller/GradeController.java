package com.yidong.controller;



import com.yidong.entity.Grade;
import com.yidong.page.Page;
import com.yidong.service.GradeService;
import com.yidong.util.StringUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/grade")
@Controller
public class GradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 获取年级列表
     * @param model
     * @return
     */
    @RequestMapping(value = "/list",method = RequestMethod.GET)
    public ModelAndView list(ModelAndView model){
        model.setViewName("grade/grade_list");
        return model;
    }

    /**
     * 获取年级列表
     * @param name
     * @param page
     * @return
     */
    @RequestMapping(value = "/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value="name",required=false,defaultValue="") String name,
            Page page
    ){
        Map<String, Object> ret = new HashMap<String, Object>();
        Map<String, Object> queryMap = new HashMap<String, Object>();
        queryMap.put("name", "%"+name+"%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());
        ret.put("rows", gradeService.findList(queryMap));
        ret.put("total", gradeService.getTotal(queryMap));
        return ret;
    }

    /**
     * 添加年级操作
     * @param grade
     * @return
     */
    @RequestMapping(value="/add",method=RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> add(Grade grade){
        Map<String,Object> ret=new HashMap<String, Object>();
        if (StringUtils.isEmpty(grade.getName())){
            ret.put("type","error");
            ret.put("msg","年级名称不能为空! ");
            return ret;
        }
        if (gradeService.add(grade)<=0){
            ret.put("type","error");
            ret.put("msg","年级添加失败! ");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","年级添加成功！");
        return ret;
    }

    /**
     * 修改年级信息
     * @param grade
     * @return
     */
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String ,Object> edit(Grade grade){
        Map<String,Object> ret=new HashMap<String, Object>();
        if (StringUtils.isEmpty(grade.getName())){
            ret.put("type","error");
            ret.put("msg","年级名称不能为空! ");
            return ret;
        }
        if (gradeService.edit(grade)<=0){
            ret.put("type","error");
            ret.put("msg","年级修改失败! ");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","年级修改成功！");
        return ret;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> delete(
            @RequestParam(value = "ids[]",required = true) Integer[] ids
    ){
        Map<String, Object> ret = new HashMap<String, Object>();
        if (ids==null|| ids.length==0){
            ret.put("type", "error");
            ret.put("msg", "请选择要删除的数据！");
            return ret;
        }
        try {
            if (gradeService.delete(StringUtil.joinString(Arrays.asList(ids),","))<=0){
                ret.put("type", "error");
                ret.put("msg", "删除失败！");
                return ret;
            }
        }catch (Exception e){
            ret.put("type", "error");
            ret.put("msg", "该年级下存在班级信息，请勿冲动！");
            return ret;
        }
        ret.put("type", "success");
        ret.put("msg", "年级删除成功！");
        return ret;
    }
}
