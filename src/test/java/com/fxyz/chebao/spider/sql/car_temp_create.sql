/*
Navicat MySQL Data Transfer

Source Server         : 53
Source Server Version : 50633
Source Host           : 192.168.0.53:3306
Source Database       : chebao

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2017-07-04 09:02:22
*/

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



SET FOREIGN_KEY_CHECKS=0;

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


SET FOREIGN_KEY_CHECKS=0;

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


SET FOREIGN_KEY_CHECKS=0;
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


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car_config_temp
-- ----------------------------
DROP TABLE IF EXISTS `car_config_temp`;
CREATE TABLE `car_config_temp` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `item_type_name` varchar(255) DEFAULT NULL,
  `item_name` varchar(255) DEFAULT NULL,
  `type_id` int(8) DEFAULT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2983507 DEFAULT CHARSET=utf8;



SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for car_config_item_temp
-- ----------------------------
DROP TABLE IF EXISTS `car_config_item_temp`;
CREATE TABLE `car_config_item_temp` (
  `id` int(8) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `level` int(1) DEFAULT NULL,
  `p_name` varchar(255) DEFAULT NULL,
  `sort` int(8) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=846 DEFAULT CHARSET=utf8;
