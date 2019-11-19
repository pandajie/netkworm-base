package com.zj.networm.demo.service;

import com.zj.networm.demo.dao.DemoDao;
import com.zj.networm.demo.domain.DemoDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DemoService {

    @Autowired
    private DemoDao demoDao;

    public List<DemoDomain> findAll(){
        return demoDao.findAll();
    }
}
