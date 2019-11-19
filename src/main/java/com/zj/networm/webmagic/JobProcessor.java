package com.zj.networm.webmagic;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.scheduler.BloomFilterDuplicateRemover;
import us.codecraft.webmagic.scheduler.QueueScheduler;
import us.codecraft.webmagic.scheduler.Scheduler;

/**布隆过滤器:
 *
 *
 * @Description PageProcessor页面处理类
 * @auther zhaojie
 * @create 2019-11-19 14:41
 */
public class JobProcessor implements PageProcessor {


    /**
     * 解析页面
     * @param page page
     */
    @Override
    public void process(Page page) {
        //解析返回的数据据page，并吧解析的结果放到resultItems中
        page.putField("div",page.getHtml().css("div#J_cate").links().all());

        page.putField("div2",page.getHtml().xpath("//div[@id=news_div]/ul/li/div/a"));

        page.putField("div3",page.getHtml().css("div#news_div a").regex(".*江苏.*").all());

        //page.putField("div3",page.getHtml().css("div#news_div a").regex(".*江苏.*").links().all());


    }

    /**
     * Site配置信息
     */
    private Site site = Site.me()
            .setCharset("utf8")
            .setTimeOut(10000)
            .setRetryTimes(3)
            .setRetrySleepTime(3000)
            .setSleepTime(3) ;

    @Override
    public Site getSite() {
        return site;
    }

    /**
     * 执行爬虫
     * Scheduler去重方式
     * 1.HashSet，使用方便。
     * 占用内存高，性能低
     * 2.Redis去重，速度快，不占用爬虫服务器资源，可以处理大数据量的数据爬取
     * 3.布隆过滤器:
     * 占用内存小，也适合大量数据的去重操作
     * 有误判的可能，没有重复的可能判定重复，重复数据一定会判定重复
     *
     *
     * @param args
     */
    public static void main(String[] args) {

        //Spider 全局管理，任务调度等
        //Scheduler 用于保存任务，分布式时可以使用RedisScheduler
        //Pipeline 设置输出方式，默认控制台输出
        //DuplicateRemover 去重处理器，默认为HashSet占用内存较大，BloomFilterDuplicateRemover 布隆过滤器，占用内存小，但是容易遗漏任务
        Spider spider = Spider.create(new JobProcessor()).
                addUrl("http://www.jd.com")
                .thread(5)
                .setScheduler(new QueueScheduler().setDuplicateRemover(new BloomFilterDuplicateRemover(10000)));
                //.addPipeline(new FilePipeline("D:/datas/webmagic/htmls"));
        Scheduler scheduler = spider.getScheduler();

        spider.run();
    }
}
