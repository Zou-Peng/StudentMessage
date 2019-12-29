package com.yidong.service;

import com.yidong.entity.Grade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface GradeService {
    public int add(Grade grade);
    public int edit(Grade grade);
    public int delete(String ids);
    public List<Grade> findList(Map<String,Object> queryMap);
    public List<Grade> findAll();
    public int getTotal(Map<String,Object> queryMap);
}
