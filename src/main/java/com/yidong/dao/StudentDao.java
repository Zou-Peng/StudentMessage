package com.yidong.dao;

import com.yidong.entity.Student;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface StudentDao {
    public Student findByUserName(String username);
    public int add(Student student);
    public int edit(Student student);
    public int delete(String ids);
    public List<Student> findList(Map<String,Object> queryMap);
    public List<Student> findAll();
    public int getTotal(Map<String,Object> queryMap);
}
