package com.zj.networm.jobinfo.task;

import com.zj.networm.jobinfo.domain.JobInfo;
import com.zj.networm.jobinfo.service.JobInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Objects;

/**
 * @Description webMagic输出管道，实现数据库的PipeLine
 * @auther zhaojie
 * @create 2019-11-19 19:21
 */
@Component
public class SpringDataPipeline implements Pipeline {

    @Autowired
    private JobInfoService jobInfoService;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //获取封装好的招聘详情
        JobInfo jobInfo = resultItems.get("jobInfo");
        //判断数据是否不为空
        if(Objects.nonNull(jobInfo)){
            jobInfoService.save(jobInfo);
        }
        //如果不为空则把数据保存到数据库中
    }
}
