/*
Navicat MySQL Data Transfer

Source Server         : 53
Source Server Version : 50633
Source Host           : 192.168.0.53:3306
Source Database       : chebao

用于将数据从临时表temp 转入正式表

Date: 2017-07-04 09:02:22
*/
/*品牌*/
TRUNCATE TABLE `car_brand`;
alter table `car_brand` add inter_id int default 0 ;
INSERT into `car_brand`(inter_id,name,letter,sort,imgurl,valid)  SELECT id ,name,letter ,orl,imgurl,0 from car_brand_temp;
/*厂商*/
TRUNCATE TABLE car_manufacturer;
alter table `car_manufacturer` add inter_id int default 0 ;
INSERT into car_manufacturer(inter_id,name , brand_id ,sort,valid,create_time) SELECT b.id,b.`name`,a.id,b.orl,0,now() from car_brand a join car_manufacturer_temp b on a.inter_id = b.brand_id ;
/*车系*/
TRUNCATE TABLE car_series;
alter table `car_series` add inter_id int default 0 ;
INSERT into car_series(inter_id,name,manu_id ,imgurl, sort ,status,valid,create_time) SELECT b.id,b.`name`,a.id,b.imgurl,b.orl,if(b.state='在售',0,1),0 ,now() from  car_manufacturer a join car_series_temp b on a.inter_id = b.manu_id;
/*车型*/
TRUNCATE TABLE car_type;
alter table `car_type` add inter_id int default 0 ;
INSERT into `car_type`(inter_id,name,series_id,price,sort,STATUS,valid) select b.id,b.`name` ,a.id,b.guide_price,b.orl,if(b.state='在售',0,if(b.state='停产在售',6,1)),0 from car_series a join car_type_temp b on a.inter_id = b.series_id;
update car_type set years =  SUBSTR(name FROM 1 FOR 4) ;
update car_type set price =  '暂无' where price = '<span>暂无</span>' ;
/*配置条目*/
TRUNCATE TABLE car_config_item;
alter table `car_config_item` add inter_id int default 0 ;
	/*一级条目*/
INSERT into `car_config_item`(inter_id,name,level,sort,valid,create_time) select a.id,a.`name`,a.`level`,a.sort,0,now() from  car_config_item_temp a where a.`level`=1;
		/*二级条目*/
INSERT into `car_config_item`(inter_id,name,level,sort,valid,pid,create_time) select a.id,a.`name`,a.`level`,a.sort,0,
(SELECT max(id) from car_config_item where name=a.p_name) pid,,now()
from  car_config_item_temp a where a.`level`=2;
/*配置详参*/
TRUNCATE TABLE car_config;
INSERT into car_config(item_id,type_id,value) select b.id item_id,a.type_id,a.`value` from  car_config_temp a  join car_config_item b on a.item_name = b.name;

/*删除中间字段*/
alter table car_brand drop column inter_id  ;

alter table car_manufacturer drop column inter_id  ;

alter table car_series drop column inter_id  ;

alter table car_type drop column inter_id  ;

alter table car_config_item drop column inter_id  ;

alter table car_config drop column inter_id  ;

/*
更新图片地址方法
转化完成后并将图片道保存至本地后才能执行
*/
update car_series set imgurl =  concat('/carSeries/',id,'.jpg');
