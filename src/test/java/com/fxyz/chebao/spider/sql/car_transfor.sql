/*
Navicat MySQL Data Transfer

Source Server         : 53
Source Server Version : 50633
Source Host           : 192.168.0.53:3306
Source Database       : chebao

用于将数据从临时表temp 转入正式表

Date: 2017-07-04 09:02:22
*/

alter table `car_brand` add inter_id int default 0 ;
INSERT into `car_brand`(inter_id,name,letter,sort,imgurl,valid)  SELECT id ,name,letter ,orl,imgurl,0 from car_brand_temp;

alter table `car_sell` add inter_id int default 0 ;
INSERT into car_sell(inter_id,name , brand_id ,sort,valid) SELECT b.id,b.`name`,a.id,b.orl,0 from car_brand a join car_manufacturer_temp b on a.inter_id = b.brand_id ;

alter table `car_series` add inter_id int default 0 ;
INSERT into car_series(inter_id,name,sell_id ,imgurl, sort ,status,valid) SELECT b.id,b.`name`,a.id,b.imgurl,b.orl,if(b.state='在售',0,1),0  from  car_sell a join car_model_temp b on a.inter_id = b.manu_id;

INSERT into `car_engine`(name,series_id,price,sort,STATUS,valid) select b.`name` ,a.id,b.guide_price,b.orl,if(b.state='在售',0,if(b.state='停产在售',6,1)),0 from car_series a join car_type_temp b on a.inter_id = b.series_id;
update car_engine set years =  SUBSTR(name FROM 1 FOR 4) ;
update car_engine set price =  '暂无' where price = '<span>暂无</span>' ;

alter table car_brand drop column inter_id  ;

alter table car_sell drop column inter_id  ;

alter table car_series drop column inter_id  ;




/*
更新图片地址方法
转化完成后并将图片道保存至本地后才能执行
*/
update car_series set imgurl =  concat('/carSeries/',id,'.jpg');
