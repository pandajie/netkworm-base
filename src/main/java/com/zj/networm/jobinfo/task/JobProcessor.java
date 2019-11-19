package com.zj.networm.jobinfo.task;

import com.zj.networm.jobinfo.domain.JobInfo;
import com.zj.networm.jobinfo.util.MathSalary;
import net.bytebuddy.asm.Advice;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

/**
 * @Description
 * @auther zhaojie
 * @create 2019-11-19 18:09
 */
@Component
public class JobProcessor implements PageProcessor {
    private String url = "https://search.51job.com/list/020000,000000,0000,01,9,99,Java,2,1.html?lang=c&stype=&postchannel=0000&workyear=99&cotype=99&degreefrom=99&jobterm=99&companysize=99&providesalary=99&lonlat=0%2C0&radius=-1&ord_field=0&confirmdate=9&fromType=&dibiaoid=0&address=&line=&specialarea=00&from=&welfare=";


    @Autowired
    private SpringDataPipeline springDataPipeline;

    public void process(Page page){
        List<Selectable> list = page.getHtml().css("div#resultList div.el").nodes();
        //解析页面，获取招聘信息详情的url地址

        //判断获取到的集合是否为空，如果为空，表示这是招聘的详情也
        if (CollectionUtils.isEmpty(list)) {
            this.saveJobInfo(page);
            //为空则表示招聘详情也，解析页面，获取招聘信息，保存数据
        }else{
            //如果不为空，表示列表页面,解析出详情也的url地址，放到任务队列中
            for(Selectable selectable:list){
                String jobInfoUrl = selectable.links().toString();
                page.addTargetRequest(jobInfoUrl);
            }

            //获取下一页的url
             String bkUrl =  page.getHtml().css("div.p_in li.bk").nodes().get(1).links().toString();
             //把url放到任务队列中
            page.addTargetRequest(bkUrl);
        }
        //如果为空，表示这是招聘的详情页
    }

    public void saveJobInfo(Page page){
        //创建招聘详情对象，
        JobInfo jobInfo = new JobInfo();
        //解析页面
        Html html = page.getHtml();
        //获取数据，封装到对象中
        jobInfo.setCompanyName(html.css("div.cn p.cname a","text").toString());
        jobInfo.setCompanyAddr(Jsoup.parse(html.css("div.bmsg").nodes().get(1).toString()).text());
        jobInfo.setCompanyInfo(Jsoup.parse(html.css("div.tmsg").toString()).text());
        jobInfo.setJobName(html.css("div.cn h1","text").toString());
        String[] jobMutiInfo  = Jsoup.parse(html.css("div.cn p.ltype").toString().replaceAll("\\u00A0+", "")).text().split("\\|");
        jobInfo.setJobAddr(jobMutiInfo[0].trim());
        jobInfo.setJobInfo(Jsoup.parse(html.css("div.job_msg").toString()).text());
        jobInfo.setUrl(page.getUrl().toString());

        //获取薪薪资
        String salary = html.css("div.cn strong","text").toString();
        Integer[] salarys =  MathSalary.getSalary(salary);
        jobInfo.setSalaryMin(salarys[0]);
        jobInfo.setSalaryMax(salarys[1]);
        String time  = Jsoup.parse(html.css("div.cn p.ltype").regex("\\|[^\\|]*发布").toString()).text();
        jobInfo.setTime(time.substring(1,time.length()-2).trim().replaceAll("\\u00A0+", ""));

        //保存结果集,内存中
        page.putField("jobInfo",jobInfo);
    }

    @Override
    public Site getSite() {
        return Site.me()
                .setCharset("gbk")
                .setTimeOut(10*1000)
                .setCycleRetryTimes(3000)
                .setRetryTimes(3);
    }

    @Scheduled(initialDelay =  1000,fixedDelay = 100*1000)
    public void prcess(){
        Spider spider =  Spider.create(new JobProcessor())
                .addUrl(url)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(100000)))
                .thread(3)
                .addPipeline(springDataPipeline);
        spider.run();
    }
}
