/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50710
Source Host           : localhost:3306
Source Database       : mycloud

Target Server Type    : MYSQL
Target Server Version : 50710
File Encoding         : 65001

Date: 2016-02-25 14:40:26
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tb_feedback`
-- ----------------------------
DROP TABLE IF EXISTS `tb_feedback`;
CREATE TABLE `tb_feedback` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userid` int(20) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  `content` text NOT NULL,
  `platform` varchar(10) DEFAULT NULL,
  `version` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_feedback` (`userid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_feedback
-- ----------------------------

-- ----------------------------
-- Table structure for `tb_note`
-- ----------------------------
DROP TABLE IF EXISTS `tb_note`;
CREATE TABLE `tb_note` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userid` int(20) NOT NULL,
  `folderid` int(20) DEFAULT NULL,
  `note` varchar(100) DEFAULT '',
  `status` int(1) DEFAULT '0' COMMENT '默认显示，1显示，2隐藏',
  `content` text,
  `createtime` datetime DEFAULT NULL,
  `latestupdatetime` datetime DEFAULT NULL,
  `share` int(1) DEFAULT '0' COMMENT '0，不分享，1分享',
  `zan` int(10) DEFAULT '0' COMMENT '点赞的数量',
  PRIMARY KEY (`id`),
  KEY `userid_note` (`userid`),
  KEY `folderid_note_folder` (`folderid`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_note
-- ----------------------------
INSERT INTO `tb_note` VALUES ('17', '2', '15', 'io', '0', 'io new ios  jhnt6rjh6nt6rwb54juuuuuuuuuuuuuuuhbb5hr4t6jnnnnnnnnnnnnnnnnnnnr5', '2016-02-23 10:07:01', '2016-02-25 14:25:15', '1', '0');
INSERT INTO `tb_note` VALUES ('18', '3', '16', 'xixi', '0', 'xixiixixixixixix', '2016-02-23 15:01:20', '2016-02-23 15:01:26', '0', '0');
INSERT INTO `tb_note` VALUES ('19', '3', '16', 'haha', '0', 'hahahahhahaxixiixixixixixix', '2016-02-23 15:04:25', '2016-02-23 15:04:53', '1', '0');
INSERT INTO `tb_note` VALUES ('20', '3', '17', 'xixi', '0', 't6hb5j57n7667', '2016-02-24 09:54:52', '2016-02-24 09:55:05', '0', '0');
INSERT INTO `tb_note` VALUES ('21', '2', '15', 'hahahaha', '0', '这是陈波分享的哈哈哈哈哈哈efgv4vr54uyhjnt6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6ybhe54etrt46r4ytjmny57k67thytjn57hggggggg54fcwqel;jdklwejmkljmfcklewjmklgvjremifgerjmnigvovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvniorejhngvuiorwegvbuwegvbuiosdjklgbdlsjgbkldsgvbklsdnkglnkdlsngkldsngkldsngklsdngklds ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio ovovovovovovovovovovovovovovovovovovovovovovovovobuio4hreioghkdfjljvnklxcnklvnkxcnvklcxnvkmxcnklvnio', '2016-02-24 14:27:06', '2016-02-25 14:30:52', '1', '0');
INSERT INTO `tb_note` VALUES ('22', '2', '15', 'duduudud', '0', 'hrt46hbt4r5j6tu6rt46rt46rt46rt46rt46uyhr4', '2016-02-25 14:38:38', '2016-02-25 14:38:44', '1', '0');

-- ----------------------------
-- Table structure for `tb_note_folder`
-- ----------------------------
DROP TABLE IF EXISTS `tb_note_folder`;
CREATE TABLE `tb_note_folder` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userid` int(20) NOT NULL,
  `folder` varchar(100) NOT NULL,
  `status` int(1) DEFAULT '0' COMMENT '默认显示，1显示，2隐藏',
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `userid_note_folder` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_note_folder
-- ----------------------------
INSERT INTO `tb_note_folder` VALUES ('15', '2', 'java', '0', '2016-02-23 10:06:57');
INSERT INTO `tb_note_folder` VALUES ('16', '3', 'xixi', '0', '2016-02-23 15:01:16');
INSERT INTO `tb_note_folder` VALUES ('17', '3', 'hahah', '0', '2016-02-24 09:54:45');

-- ----------------------------
-- Table structure for `tb_note_history`
-- ----------------------------
DROP TABLE IF EXISTS `tb_note_history`;
CREATE TABLE `tb_note_history` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `noteid` int(20) NOT NULL,
  `content` text,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `noteid` (`noteid`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_note_history
-- ----------------------------
INSERT INTO `tb_note_history` VALUES ('23', '17', null, '2016-02-23 10:07:19');
INSERT INTO `tb_note_history` VALUES ('24', '18', null, '2016-02-23 15:01:26');
INSERT INTO `tb_note_history` VALUES ('25', '19', 'hahahahhaha', '2016-02-23 15:04:53');
INSERT INTO `tb_note_history` VALUES ('26', '17', 'io new ios  ', '2016-02-25 14:25:15');
INSERT INTO `tb_note_history` VALUES ('27', '21', '这是陈波分享的哈哈哈哈哈哈', '2016-02-25 14:25:55');
INSERT INTO `tb_note_history` VALUES ('28', '21', '这是陈波分享的哈哈哈哈哈哈efgv4vr54uyhjnt6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6y', '2016-02-25 14:26:33');
INSERT INTO `tb_note_history` VALUES ('29', '21', '这是陈波分享的哈哈哈哈哈哈efgv4vr54uyhjnt6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6ybhe54etrt46r4', '2016-02-25 14:28:23');
INSERT INTO `tb_note_history` VALUES ('30', '21', '这是陈波分享的哈哈哈哈哈哈efgv4vr54uyhjnt6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6t6ybhe54etrt46r4ytjmny57k67thytjn57', '2016-02-25 14:30:52');

-- ----------------------------
-- Table structure for `tb_user`
-- ----------------------------
DROP TABLE IF EXISTS `tb_user`;
CREATE TABLE `tb_user` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  `nickname` varchar(100) DEFAULT '小云笔记',
  `sex` int(1) DEFAULT '0',
  `age` int(3) DEFAULT '0',
  `area` varchar(100) DEFAULT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `oauthkey` varchar(100) DEFAULT NULL COMMENT '第三方登录的key',
  `oauthtype` varchar(10) DEFAULT NULL COMMENT '第三方登录类型{QQ,WeChat,Sina}',
  `score` int(20) DEFAULT NULL,
  `permission` int(3) DEFAULT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_user
-- ----------------------------
INSERT INTO `tb_user` VALUES ('2', '13286653512', '123456', '陈波', '0', '0', null, '13286653512', null, null, null, null, '2016-02-23 09:15:32');
INSERT INTO `tb_user` VALUES ('3', '13286653511', '123456', null, '0', '0', null, '13286653511', null, null, null, null, '2016-02-23 14:57:58');

-- ----------------------------
-- Table structure for `tb_zan`
-- ----------------------------
DROP TABLE IF EXISTS `tb_zan`;
CREATE TABLE `tb_zan` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userid` int(20) NOT NULL,
  `noteid` int(20) NOT NULL,
  `createtime` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of tb_zan
-- ----------------------------
INSERT INTO `tb_zan` VALUES ('1', '2', '17', '2016-02-24 17:00:28');
INSERT INTO `tb_zan` VALUES ('2', '2', '19', '2016-02-25 11:09:08');
INSERT INTO `tb_zan` VALUES ('3', '3', '17', '2016-02-25 11:09:39');
INSERT INTO `tb_zan` VALUES ('4', '3', '19', '2016-02-25 11:09:42');
INSERT INTO `tb_zan` VALUES ('5', '2', '21', '2016-02-25 14:37:09');
