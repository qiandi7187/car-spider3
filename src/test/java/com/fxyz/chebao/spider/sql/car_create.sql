/*
Navicat MySQL Data Transfer

Source Server         : 53
Source Server Version : 50633
Source Host           : 192.168.0.53:3306
Source Database       : chebao

Target Server Type    : MYSQL
Target Server Version : 50633
File Encoding         : 65001

Date: 2017-07-04 09:08:18
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car_brand
-- ----------------------------
DROP TABLE IF EXISTS `car_brand`;
CREATE TABLE `car_brand` (
  `id` int(4) NOT NULL AUTO_INCREMENT COMMENT '品牌id',
  `name` varchar(30) NOT NULL COMMENT '品牌名称',
  `letter` varchar(1) NOT NULL COMMENT '首字母',
  `sort` int(4) NOT NULL COMMENT '品牌排序',
  `imgurl` varchar(255) NOT NULL COMMENT '品牌图片',
  `valid` int(1) DEFAULT '0' COMMENT '删除状态：0未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=256 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car_manufacturer
-- ----------------------------
DROP TABLE IF EXISTS `car_manufacturer`;
CREATE TABLE `car_manufacturer` (
  `id` int(5) NOT NULL AUTO_INCREMENT COMMENT '厂商id',
  `name` varchar(30) NOT NULL COMMENT '厂商名称',
  `brand_id` int(4) NOT NULL COMMENT '品牌id',
  `valid` int(1) DEFAULT '0' COMMENT '删除状态：0未删除，1：已删除',
  `sort` int(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=512 DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car_series
-- ----------------------------
DROP TABLE IF EXISTS `car_series`;
CREATE TABLE `car_series` (
  `id` int(6) NOT NULL AUTO_INCREMENT COMMENT '车系id',
  `name` varchar(50) NOT NULL COMMENT '车系名称',
  `sell_id` int(5) NOT NULL COMMENT '厂商id',
  `status` int(1) DEFAULT NULL COMMENT '0 在售 1 停售',
  `sort` int(6) NOT NULL COMMENT '车系排序',
  `imgurl` varchar(255) NOT NULL COMMENT '车系图片',
  `valid` int(1) DEFAULT '0' COMMENT '删除状态：0未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4096 DEFAULT CHARSET=utf8;


SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for car_type
-- ----------------------------
DROP TABLE IF EXISTS `car_type`;
CREATE TABLE `car_type` (
  `id` int(7) NOT NULL AUTO_INCREMENT COMMENT '型号id',
  `name` varchar(100) NOT NULL COMMENT '型号名称',
  `series_id` int(6) NOT NULL COMMENT '车系id',
  `sort` int(7) DEFAULT NULL,
  `years` int(4) NOT NULL COMMENT '年代款',
  `price` varchar(50) DEFAULT NULL,
  `status` int(1) DEFAULT '1' COMMENT '销售状态 0：在售 1：停售   6:停产在售',
  `valid` int(1) DEFAULT '0' COMMENT '删除状态：0未删除，1：已删除',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32768 DEFAULT CHARSET=utf8;


