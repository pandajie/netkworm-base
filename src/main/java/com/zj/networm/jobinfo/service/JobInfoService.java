package com.zj.networm.jobinfo.service;

import com.zj.networm.jobinfo.domain.JobInfo;

import java.util.List;

/**
 * @Description
 * @auther zhaojie
 * @create 2019-11-19 17:23
 */
public interface JobInfoService{

    List<JobInfo> find(JobInfo domain);

    Long save(JobInfo domain);
}
