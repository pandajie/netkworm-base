package com.zj.networm.jobinfo.service.impl;

import com.zj.networm.jobinfo.dao.JobInfoDao;
import com.zj.networm.jobinfo.domain.JobInfo;
import com.zj.networm.jobinfo.service.JobInfoService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description
 * @auther zhaojie
 * @create 2019-11-19 17:23
 */
@Service
public class JobInfoServiceImpl implements JobInfoService {

    @Autowired
    private JobInfoDao jobInfoDao;

    @Override
    public List<JobInfo> find(JobInfo domain) {
        return jobInfoDao.findAll(Example.of(domain));
    }

    @Override
    public Long save(JobInfo domain) {
        JobInfo param = new JobInfo();
        param.setUrl(domain.getUrl());
        param.setTime(domain.getTime());

        List<JobInfo> list  = this.find(param);
        if(CollectionUtils.isEmpty(list)){
            //如果查询记录结果为空，则表示数据不存在，或者已经更新了，则需要保存数据
            domain =  this.jobInfoDao.saveAndFlush(domain);
        }

        return domain.getId();
    }
}
