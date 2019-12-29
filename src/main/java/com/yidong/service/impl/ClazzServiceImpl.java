package com.yidong.service.impl;

import com.yidong.dao.ClazzDao;
import com.yidong.entity.Clazz;
import com.yidong.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    private ClazzDao clazzDao;
    @Override
    public int add(Clazz clazz) {
        return clazzDao.add(clazz);
    }

    @Override
    public int edit(Clazz clazz) {
        return clazzDao.edit(clazz);
    }

    @Override
    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    @Override
    public List<Clazz> findList(Map<String, Object> queryMap) {
        return clazzDao.findList(queryMap);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return clazzDao.getTotal(queryMap);
    }
}
