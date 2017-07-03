
alter table `vehicle_brand` add inter_id int default 0 ;
INSERT into `vehicle_brand`(inter_id,name,letter,sort,imgurl,valid)  SELECT id ,name,letter ,orl,imgurl,0 from car_brand_temp;

alter table `vehicle_sell` add inter_id int default 0 ;
INSERT into vehicle_sell(inter_id,name , brand_id ,sort,valid) SELECT b.id,b.`name`,a.id,b.orl,0 from vehicle_brand a join car_manufacturer_temp b on a.inter_id = b.brand_id ;

alter table `vehicle_series` add inter_id int default 0 ;
INSERT into vehicle_series(inter_id,name,sell_id ,imgurl, sort ,status,valid) SELECT b.id,b.`name`,a.id,b.imgurl,b.orl,if(b.state='在售',0,1),0  from  vehicle_sell a join car_model_temp b on a.inter_id = b.manu_id;

INSERT into `vehicle_engine`(name,series_id,price,sort,STATUS,valid) select b.`name` ,a.id,b.guide_price,b.orl,if(b.state='在售',0,if(b.state='停产在售',6,1)),0 from vehicle_series a join car_type_temp b on a.inter_id = b.model_id;
update vehicle_engine set years =  SUBSTR(name FROM 1 FOR 4) ;

alter table vehicle_brand drop column inter_id  ; 

alter table vehicle_sell drop column inter_id  ; 

alter table vehicle_series drop column inter_id  ; 