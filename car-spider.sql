

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car_brand_temp
-- ----------------------------
DROP TABLE IF EXISTS `car_brand_temp`;
CREATE TABLE `car_brand_temp` (
  `id` int(8) NOT NULL,
  `letter` varchar(2) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `imgurl` varchar(255) DEFAULT NULL,
  `orl` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for car_manufacturer_temp
-- ----------------------------
DROP TABLE IF EXISTS `car_manufacturer_temp`;
CREATE TABLE `car_manufacturer_temp` (
  `id` int(8) NOT NULL,
  `brand_id` int(8) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `orl` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for car_series_temp
-- ----------------------------
DROP TABLE IF EXISTS `car_series_temp`;
CREATE TABLE `car_series_temp` (
  `id` int(8) NOT NULL,
  `manu_id` int(8) DEFAULT NULL,
  `imgurl` varchar(255) DEFAULT NULL,
  `price` varchar(100) DEFAULT NULL,
  `orl` int(10) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for car_type_temp
-- ----------------------------
DROP TABLE IF EXISTS `car_type_temp`;
CREATE TABLE `car_type_temp` (
  `id` int(8) NOT NULL,
  `series_id` int(8) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `group_name` varchar(100) DEFAULT NULL,
  `driving_mode` varchar(255) DEFAULT NULL,
  `transmission` varchar(255) DEFAULT NULL,
  `guide_price` varchar(50) DEFAULT NULL,
  `dealer_price` varchar(50) DEFAULT NULL,
  `second_price` varchar(50) DEFAULT NULL,
  `tax` varchar(50) DEFAULT NULL,
  `state` varchar(50) DEFAULT NULL,
  `orl` int(10) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- ----------------------------
-- Table structure for car_spider_err
-- ----------------------------
DROP TABLE IF EXISTS `car_spider_err`;
CREATE TABLE `car_spider_err` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `method_name` varchar(255) DEFAULT NULL,
  `desp` varchar(2000) DEFAULT NULL,
  `value0` varchar(255) DEFAULT NULL,
  `value1` varchar(255) DEFAULT NULL,
  `value2` varchar(255) DEFAULT NULL,
  `value3` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for nickname_spider
-- ----------------------------
DROP TABLE IF EXISTS `nickname_spider`;
CREATE TABLE `nickname_spider` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `tz` int(1) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81227 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for petrol_price
-- ----------------------------
DROP TABLE IF EXISTS `petrol_price`;
CREATE TABLE `petrol_price` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `city` varchar(50) NOT NULL,
  `price90` varchar(50) DEFAULT NULL,
  `price93` varchar(50) DEFAULT NULL,
  `price97` varchar(50) DEFAULT NULL,
  `price0` varchar(50) DEFAULT NULL,
  `update_time` datetime NOT NULL,
  PRIMARY KEY (`id`),
  KEY `city` (`city`),
  KEY `update_time` (`update_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1637 DEFAULT CHARSET=utf8;


