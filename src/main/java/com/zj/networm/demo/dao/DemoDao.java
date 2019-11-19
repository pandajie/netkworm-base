package com.zj.networm.demo.dao;

import com.zj.networm.demo.domain.DemoDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemoDao extends JpaRepository<DemoDomain,Long> {
}
