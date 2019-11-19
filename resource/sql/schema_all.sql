create database demo default  charset = utf8mb4;

use demo;
drop table ifexsit id_item;
create table id_item(
	id bigint(10) not null auto_increment COMMENT '主键id',
	spu bigint(15) default null comment '商品集合id',
	sku bigint(15) default null comment '商品最小品类单元id',
	titile varchar(100) default null comment '商品标题',
	price bigint(10) default null comment '商品价格',
	pic varchar(200) default null comment '图片',
	url varchar(200) default null comment '商品详细地址',
	create_time datetime default null comment '创建时间',
	update_time datetime default null comment '更新时间',
	primary key (id),
	key sku_idx (`sku`) using btree
) EnGINE = innoDB default CHARSET = utf8mb4 comment= '京东商品表';


create table t_job_info(
	id bigint(10) not null auto_increment,
	company_name varchar(100) default null comment '公司名称',
	company_addr varchar(200) default null comment '公司联系方式',
	company_info text default null comment '公司信息',
	job_name varchar(100) default null comment '职位名称',
	job_addr varchar(100) default null comment '工作地点',
	job_info text default null comment '职位信息',
	salary_min int(10) default null comment '薪酬范围，最小',
	salary_max int(10) default null comment '薪酬范围，最大',
	url varchar(150) default null comment '招聘信息详情页',
	time varchar(150) default null comment '最新发布时间',
	primary key (id)
) EnGINE = innoDB default CHARSET = utf8mb4 comment= '招聘信息表';