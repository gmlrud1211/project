/*
Navicat MariaDB Data Transfer

Source Server         : MYDB
Source Server Version : 100021
Source Host           : localhost:3306
Source Database       : member

Target Server Type    : MariaDB
Target Server Version : 100021
File Encoding         : 65001

Date: 2015-12-15 09:06:27
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for member
-- ----------------------------
DROP TABLE IF EXISTS `member`;
CREATE TABLE `member` (
  `name` varchar(20) NOT NULL,
  `pass` varchar(20) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `hobby` varchar(255) DEFAULT NULL,
  `si` varchar(255) DEFAULT NULL,
  `gu` varchar(255) DEFAULT NULL,
  `gun` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of member
-- ----------------------------
INSERT INTO `member` VALUES ('admin', 'admin', 'admin', null, null, null, null);
INSERT INTO `member` VALUES ('pajamasi', 'pajamasi', 'pajamasi', null, null, null, null);
