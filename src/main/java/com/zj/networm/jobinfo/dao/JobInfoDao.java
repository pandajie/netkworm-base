package com.zj.networm.jobinfo.dao;

import com.zj.networm.jobinfo.domain.JobInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Description
 * @auther zhaojie
 * @create 2019-11-19 17:22
 */
public interface JobInfoDao extends JpaRepository<JobInfo,Long> {
}
