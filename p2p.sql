/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 50717
 Source Host           : localhost:3306
 Source Schema         : p2p

 Target Server Type    : MySQL
 Target Server Version : 50717
 File Encoding         : 65001

 Date: 24/06/2019 12:08:59
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` bigint(20) NOT NULL COMMENT '与userinfo id一致',
  `tradePassword` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '交易密码',
  `usableAmount` decimal(18, 4) NOT NULL COMMENT '可用金额',
  `freezedAmount` decimal(18, 4) NOT NULL COMMENT '冻结金额',
  `borrowLimit` decimal(18, 4) NOT NULL COMMENT '最高额度',
  `version` int(11) NOT NULL COMMENT '版本号（乐观锁）',
  `unReceiveInterest` decimal(18, 4) NOT NULL COMMENT '待收利息',
  `unReceivePrincipal` decimal(18, 4) NOT NULL COMMENT '待收本金',
  `unReturnAmount` decimal(18, 4) NOT NULL COMMENT '待还金额',
  `remainBorrowLimit` decimal(18, 4) NOT NULL COMMENT '剩余额度',
  PRIMARY KEY (`id`) USING BTREE,
  CONSTRAINT `a` FOREIGN KEY (`id`) REFERENCES `userinfo` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (41, NULL, 0.0000, 0.0000, 3000.0000, 4, 2.0833, 500.0000, 2050.2070, 1000.0000);
INSERT INTO `account` VALUES (42, NULL, 0.0000, 0.0000, 3000.0000, 1, 50.2070, 2000.0000, 0.0000, 3000.0000);
INSERT INTO `account` VALUES (43, NULL, 0.0000, 0.0000, 3000.0000, 0, 0.0000, 0.0000, 0.0000, 3000.0000);
INSERT INTO `account` VALUES (44, NULL, 0.0000, 0.0000, 3000.0000, 3, 0.0000, 0.0000, 2022.9554, 1000.0000);
INSERT INTO `account` VALUES (46, NULL, 0.0000, 0.0000, 3000.0000, 0, 0.0000, 0.0000, 0.0000, 3000.0000);

-- ----------------------------
-- Table structure for bid
-- ----------------------------
DROP TABLE IF EXISTS `bid`;
CREATE TABLE `bid`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `actualRate` decimal(8, 4) NOT NULL COMMENT '年利率',
  `availableAmount` decimal(18, 4) NOT NULL COMMENT '投资金额',
  `bidRequest_id` bigint(20) NOT NULL COMMENT '对应借款id',
  `bidUser_id` bigint(20) NOT NULL COMMENT '投资人id',
  `bidTime` datetime(0) NOT NULL COMMENT '投资时间',
  `bidRequestTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '借款标题（冗余）',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 14 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bid
-- ----------------------------
INSERT INTO `bid` VALUES (9, 15.0000, 2000.0000, 8, 42, '2019-03-31 22:01:02', '修电脑');
INSERT INTO `bid` VALUES (10, 15.0000, 1000.0000, 9, 41, '2019-04-10 15:37:25', '没钱吃饭了');
INSERT INTO `bid` VALUES (11, 6.0000, 1000.0000, 10, 42, '2019-05-07 15:32:00', '我要借钱');
INSERT INTO `bid` VALUES (12, 5.0000, 500.0000, 13, 41, '2019-05-18 13:06:36', '测试');
INSERT INTO `bid` VALUES (13, 5.0000, 500.0000, 14, 41, '2019-05-18 15:00:11', 'ceshi');

-- ----------------------------
-- Table structure for bidrequest
-- ----------------------------
DROP TABLE IF EXISTS `bidrequest`;
CREATE TABLE `bidrequest`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `version` int(11) NOT NULL,
  `bidRequestType` tinyint(4) NOT NULL COMMENT '借款类型',
  `bidRequestState` tinyint(4) NOT NULL COMMENT '借款状态',
  `bidRequestAmount` decimal(18, 4) NOT NULL COMMENT '借款金额',
  `currentRate` decimal(8, 4) NOT NULL COMMENT '年化利率',
  `monthes2Return` tinyint(4) NOT NULL COMMENT '还款月数',
  `bidCount` int(11) DEFAULT NULL COMMENT '已投标次数',
  `totalRewardAmount` decimal(18, 4) NOT NULL COMMENT '总回报金额（总利息）',
  `currentSum` decimal(18, 4) NOT NULL COMMENT '当前已投标总金额',
  `title` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '标题',
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
  `bidState` bigint(20) DEFAULT NULL COMMENT '交易状态码',
  `disableDate` datetime(0) DEFAULT NULL COMMENT '招标截至日期',
  `createUser_id` bigint(20) NOT NULL COMMENT '借款人',
  `disableDays` tinyint(4) NOT NULL COMMENT '招标天数',
  `minBidAmount` decimal(18, 4) DEFAULT NULL COMMENT '最小借款金额',
  `applyTime` datetime(0) DEFAULT NULL COMMENT '申请时间',
  `publishTime` datetime(0) DEFAULT NULL COMMENT '发标时间',
  `returnType` tinyint(4) DEFAULT NULL COMMENT '还款类型',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 15 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bidrequest
-- ----------------------------
INSERT INTO `bidrequest` VALUES (8, 3, 0, 7, 2000.0000, 15.0000, 3, 1, 50.2070, 2000.0000, '修电脑', '电脑坏了，需要修理', 6, '2019-04-05 22:00:32', 41, 5, NULL, '2019-03-31 22:00:32', '2019-03-31 22:00:32', 0);
INSERT INTO `bidrequest` VALUES (9, 3, 0, 7, 1000.0000, 15.0000, 2, 1, 18.7888, 1000.0000, '没钱吃饭了', '有朋友来玩，把生活费花完了，想借点钱，分期两个月还', 6, '2019-04-13 15:33:08', 44, 3, NULL, '2019-04-10 15:33:08', '2019-04-10 15:33:08', 0);
INSERT INTO `bidrequest` VALUES (10, 1, 0, 3, 1000.0000, 6.0000, 2, 1, 7.5062, 1000.0000, '我要借钱', '请吃饭', 0, '2019-05-09 15:31:07', 41, 2, NULL, '2019-05-07 15:31:07', '2019-05-07 15:31:07', 0);
INSERT INTO `bidrequest` VALUES (11, 1, 0, 2, 1000.0000, 5.0000, 3, 0, 8.3449, 0.0000, '借钱买资料', '本月生活费透支，现需要1000元购买课程资料，会分三期还款，希望大家帮助', 0, '2019-05-11 17:46:19', 44, 3, NULL, '2019-05-08 17:46:19', '2019-05-08 17:46:19', 0);
INSERT INTO `bidrequest` VALUES (12, 0, 0, 3, 500.0000, 5.0000, 1, 0, 2.0833, 0.0000, '借钱买书', '交书本费没钱了，下个月生活费还', 0, '2019-05-22 15:23:28', 44, 5, NULL, '2019-05-17 15:23:28', '2019-05-17 15:23:28', 0);
INSERT INTO `bidrequest` VALUES (13, 3, 0, 7, 500.0000, 5.0000, 1, 1, 2.0833, 500.0000, '测试', '测试', 6, '2019-05-19 13:05:59', 44, 1, NULL, '2019-05-18 13:05:59', '2019-05-18 13:05:59', 0);
INSERT INTO `bidrequest` VALUES (14, 3, 0, 7, 500.0000, 5.0000, 1, 1, 2.0833, 500.0000, 'ceshi', '测试', 6, '2019-05-19 14:59:53', 44, 1, NULL, '2019-05-18 14:59:53', '2019-05-18 14:59:53', 0);

-- ----------------------------
-- Table structure for iplog
-- ----------------------------
DROP TABLE IF EXISTS `iplog`;
CREATE TABLE `iplog`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '登录ip',
  `state` tinyint(4) NOT NULL COMMENT '登录状态  1成功  2失败',
  `userName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `loginTime` datetime(0) NOT NULL COMMENT '登录时间\r\n',
  `userType` tinyint(4) DEFAULT NULL COMMENT '用户类型  0后台  1前台',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 455 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of iplog
-- ----------------------------
INSERT INTO `iplog` VALUES (276, '0:0:0:0:0:0:0:1', 2, 'huenhui', '2019-03-31 20:45:06', 1);
INSERT INTO `iplog` VALUES (277, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 20:45:31', 1);
INSERT INTO `iplog` VALUES (278, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-03-31 21:06:29', 0);
INSERT INTO `iplog` VALUES (279, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 21:07:24', 1);
INSERT INTO `iplog` VALUES (280, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 21:25:31', 1);
INSERT INTO `iplog` VALUES (281, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-03-31 21:50:48', 1);
INSERT INTO `iplog` VALUES (282, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 21:58:17', 1);
INSERT INTO `iplog` VALUES (283, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-03-31 22:00:52', 1);
INSERT INTO `iplog` VALUES (284, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 22:01:50', 1);
INSERT INTO `iplog` VALUES (285, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 22:20:21', 1);
INSERT INTO `iplog` VALUES (286, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-03-31 22:28:11', 1);
INSERT INTO `iplog` VALUES (287, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 22:29:07', 1);
INSERT INTO `iplog` VALUES (288, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-03-31 22:37:49', 1);
INSERT INTO `iplog` VALUES (289, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 12:37:46', 1);
INSERT INTO `iplog` VALUES (290, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-09 12:38:56', 0);
INSERT INTO `iplog` VALUES (291, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 12:39:31', 1);
INSERT INTO `iplog` VALUES (292, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 15:10:01', 1);
INSERT INTO `iplog` VALUES (293, '0:0:0:0:0:0:0:1', 2, 'aaaa', '2019-04-09 15:12:10', 1);
INSERT INTO `iplog` VALUES (294, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-04-09 15:12:17', 1);
INSERT INTO `iplog` VALUES (295, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-09 15:14:50', 0);
INSERT INTO `iplog` VALUES (296, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 15:15:41', 1);
INSERT INTO `iplog` VALUES (297, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 16:39:01', 1);
INSERT INTO `iplog` VALUES (298, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-09 16:39:25', 1);
INSERT INTO `iplog` VALUES (299, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 20:20:10', 1);
INSERT INTO `iplog` VALUES (300, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-09 21:37:36', 1);
INSERT INTO `iplog` VALUES (301, '0:0:0:0:0:0:0:1', 2, 'aaaaaaaaa', '2019-04-10 14:55:46', 1);
INSERT INTO `iplog` VALUES (302, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-10 15:02:44', 1);
INSERT INTO `iplog` VALUES (303, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-10 15:03:07', 1);
INSERT INTO `iplog` VALUES (304, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-10 15:07:42', 1);
INSERT INTO `iplog` VALUES (305, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-10 15:11:43', 1);
INSERT INTO `iplog` VALUES (306, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-10 15:11:55', 1);
INSERT INTO `iplog` VALUES (307, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-10 15:18:45', 0);
INSERT INTO `iplog` VALUES (308, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-10 15:23:22', 1);
INSERT INTO `iplog` VALUES (309, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-10 15:36:15', 1);
INSERT INTO `iplog` VALUES (310, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-10 15:38:38', 1);
INSERT INTO `iplog` VALUES (311, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-10 15:40:02', 1);
INSERT INTO `iplog` VALUES (312, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-10 15:40:31', 1);
INSERT INTO `iplog` VALUES (313, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-10 15:43:02', 0);
INSERT INTO `iplog` VALUES (314, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 17:17:28', 1);
INSERT INTO `iplog` VALUES (315, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-11 17:18:22', 0);
INSERT INTO `iplog` VALUES (316, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 17:23:29', 1);
INSERT INTO `iplog` VALUES (317, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 19:08:58', 1);
INSERT INTO `iplog` VALUES (318, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-04-11 19:12:23', 1);
INSERT INTO `iplog` VALUES (319, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 19:34:17', 1);
INSERT INTO `iplog` VALUES (320, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 19:36:00', 1);
INSERT INTO `iplog` VALUES (321, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 20:13:28', 1);
INSERT INTO `iplog` VALUES (322, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-11 21:39:30', 1);
INSERT INTO `iplog` VALUES (323, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 11:37:13', 1);
INSERT INTO `iplog` VALUES (324, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-12 11:37:44', 1);
INSERT INTO `iplog` VALUES (325, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-12 12:01:40', 1);
INSERT INTO `iplog` VALUES (326, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 12:51:44', 1);
INSERT INTO `iplog` VALUES (327, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-12 12:51:56', 1);
INSERT INTO `iplog` VALUES (328, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 13:43:59', 1);
INSERT INTO `iplog` VALUES (329, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-12 13:44:08', 1);
INSERT INTO `iplog` VALUES (330, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-12 13:45:12', 1);
INSERT INTO `iplog` VALUES (331, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 14:34:47', 1);
INSERT INTO `iplog` VALUES (332, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 14:54:18', 1);
INSERT INTO `iplog` VALUES (333, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-12 14:54:28', 1);
INSERT INTO `iplog` VALUES (334, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-04-12 14:57:15', 1);
INSERT INTO `iplog` VALUES (335, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-04-12 15:00:34', 1);
INSERT INTO `iplog` VALUES (336, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-04-12 15:21:50', 1);
INSERT INTO `iplog` VALUES (337, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 15:29:52', 0);
INSERT INTO `iplog` VALUES (338, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:04:52', 0);
INSERT INTO `iplog` VALUES (339, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:14:14', 0);
INSERT INTO `iplog` VALUES (340, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:15:35', 0);
INSERT INTO `iplog` VALUES (341, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:22:41', 0);
INSERT INTO `iplog` VALUES (342, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:35:16', 0);
INSERT INTO `iplog` VALUES (343, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:36:13', 0);
INSERT INTO `iplog` VALUES (344, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:39:10', 0);
INSERT INTO `iplog` VALUES (345, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:40:37', 0);
INSERT INTO `iplog` VALUES (346, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:43:57', 0);
INSERT INTO `iplog` VALUES (347, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:45:35', 0);
INSERT INTO `iplog` VALUES (348, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 16:47:17', 0);
INSERT INTO `iplog` VALUES (349, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 17:04:07', 0);
INSERT INTO `iplog` VALUES (350, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 17:18:00', 0);
INSERT INTO `iplog` VALUES (351, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 17:26:27', 0);
INSERT INTO `iplog` VALUES (352, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 17:35:52', 0);
INSERT INTO `iplog` VALUES (353, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 18:29:08', 0);
INSERT INTO `iplog` VALUES (354, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 18:33:45', 0);
INSERT INTO `iplog` VALUES (355, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 18:38:31', 0);
INSERT INTO `iplog` VALUES (356, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 18:40:22', 0);
INSERT INTO `iplog` VALUES (357, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-12 18:44:46', 0);
INSERT INTO `iplog` VALUES (358, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 18:46:06', 1);
INSERT INTO `iplog` VALUES (359, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 19:00:49', 1);
INSERT INTO `iplog` VALUES (360, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 21:03:08', 1);
INSERT INTO `iplog` VALUES (361, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-12 21:07:19', 1);
INSERT INTO `iplog` VALUES (362, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-04-13 11:54:24', 1);
INSERT INTO `iplog` VALUES (363, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-04-13 11:59:51', 1);
INSERT INTO `iplog` VALUES (364, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-16 16:51:38', 1);
INSERT INTO `iplog` VALUES (365, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-16 19:40:43', 1);
INSERT INTO `iplog` VALUES (366, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-04-16 20:37:01', 0);
INSERT INTO `iplog` VALUES (367, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-16 20:52:25', 1);
INSERT INTO `iplog` VALUES (368, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-21 15:25:06', 1);
INSERT INTO `iplog` VALUES (369, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-04-21 15:39:26', 1);
INSERT INTO `iplog` VALUES (370, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-21 15:47:11', 1);
INSERT INTO `iplog` VALUES (371, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-04-21 15:47:12', 1);
INSERT INTO `iplog` VALUES (372, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-04-21 15:47:31', 1);
INSERT INTO `iplog` VALUES (373, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-04-21 15:48:30', 1);
INSERT INTO `iplog` VALUES (374, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-04-21 15:54:24', 1);
INSERT INTO `iplog` VALUES (375, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-05 15:00:18', 1);
INSERT INTO `iplog` VALUES (376, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-05 15:05:10', 0);
INSERT INTO `iplog` VALUES (377, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-05 15:06:57', 0);
INSERT INTO `iplog` VALUES (378, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-06 11:14:25', 0);
INSERT INTO `iplog` VALUES (379, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-06 11:20:36', 1);
INSERT INTO `iplog` VALUES (380, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-06 14:51:24', 1);
INSERT INTO `iplog` VALUES (381, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-06 15:19:35', 0);
INSERT INTO `iplog` VALUES (382, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-06 16:02:56', 1);
INSERT INTO `iplog` VALUES (383, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-06 17:02:27', 1);
INSERT INTO `iplog` VALUES (384, '127.0.0.1', 1, 'huenhui', '2019-05-06 17:42:43', 1);
INSERT INTO `iplog` VALUES (385, '127.0.0.1', 1, 'huenhui', '2019-05-07 15:30:26', 1);
INSERT INTO `iplog` VALUES (386, '127.0.0.1', 1, 'dupeng', '2019-05-07 15:31:44', 1);
INSERT INTO `iplog` VALUES (387, '127.0.0.1', 1, 'admin', '2019-05-07 15:38:34', 0);
INSERT INTO `iplog` VALUES (388, '127.0.0.1', 1, 'huenhui', '2019-05-07 15:39:59', 1);
INSERT INTO `iplog` VALUES (389, '127.0.0.1', 2, 'dupeng', '2019-05-07 15:48:04', 1);
INSERT INTO `iplog` VALUES (390, '127.0.0.1', 2, 'dupeng', '2019-05-07 15:48:11', 1);
INSERT INTO `iplog` VALUES (391, '127.0.0.1', 1, 'dupeng', '2019-05-07 15:48:20', 1);
INSERT INTO `iplog` VALUES (392, '127.0.0.1', 1, 'admin', '2019-05-07 16:09:59', 0);
INSERT INTO `iplog` VALUES (393, '0:0:0:0:0:0:0:1', 2, 'aaaaaaaaa', '2019-05-08 13:31:39', 1);
INSERT INTO `iplog` VALUES (394, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-08 13:49:02', 1);
INSERT INTO `iplog` VALUES (395, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-08 14:34:34', 1);
INSERT INTO `iplog` VALUES (396, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-08 14:35:53', 1);
INSERT INTO `iplog` VALUES (397, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-08 15:01:47', 1);
INSERT INTO `iplog` VALUES (398, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-08 15:48:35', 1);
INSERT INTO `iplog` VALUES (399, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-08 15:48:37', 1);
INSERT INTO `iplog` VALUES (400, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-08 16:22:51', 0);
INSERT INTO `iplog` VALUES (401, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-08 17:34:18', 1);
INSERT INTO `iplog` VALUES (402, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-08 17:34:29', 1);
INSERT INTO `iplog` VALUES (403, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-08 17:36:07', 1);
INSERT INTO `iplog` VALUES (404, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-08 17:41:38', 1);
INSERT INTO `iplog` VALUES (405, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-08 17:42:56', 1);
INSERT INTO `iplog` VALUES (406, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-08 17:47:21', 1);
INSERT INTO `iplog` VALUES (407, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-08 17:51:28', 0);
INSERT INTO `iplog` VALUES (408, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-08 17:56:03', 1);
INSERT INTO `iplog` VALUES (409, '0:0:0:0:0:0:0:1', 2, 'aaa', '2019-05-09 17:16:09', 1);
INSERT INTO `iplog` VALUES (410, '0:0:0:0:0:0:0:1', 2, 'aaa', '2019-05-09 17:20:37', 1);
INSERT INTO `iplog` VALUES (411, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-09 18:03:40', 1);
INSERT INTO `iplog` VALUES (412, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-09 18:19:48', 1);
INSERT INTO `iplog` VALUES (413, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-09 18:29:14', 1);
INSERT INTO `iplog` VALUES (414, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-09 18:48:18', 1);
INSERT INTO `iplog` VALUES (415, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-09 19:05:56', 1);
INSERT INTO `iplog` VALUES (416, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-09 19:08:07', 1);
INSERT INTO `iplog` VALUES (417, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-09 19:21:27', 0);
INSERT INTO `iplog` VALUES (418, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-16 19:02:17', 1);
INSERT INTO `iplog` VALUES (419, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-16 19:03:07', 1);
INSERT INTO `iplog` VALUES (420, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-16 19:28:37', 1);
INSERT INTO `iplog` VALUES (421, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-16 19:29:26', 1);
INSERT INTO `iplog` VALUES (422, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-16 19:30:53', 1);
INSERT INTO `iplog` VALUES (423, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-05-16 19:31:53', 1);
INSERT INTO `iplog` VALUES (424, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-05-17 14:21:41', 1);
INSERT INTO `iplog` VALUES (425, '0:0:0:0:0:0:0:1', 1, 'ceshi', '2019-05-17 14:40:15', 1);
INSERT INTO `iplog` VALUES (426, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-17 14:42:22', 0);
INSERT INTO `iplog` VALUES (427, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-17 14:42:59', 1);
INSERT INTO `iplog` VALUES (428, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-17 14:45:29', 1);
INSERT INTO `iplog` VALUES (429, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-17 14:55:02', 0);
INSERT INTO `iplog` VALUES (430, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-17 15:01:34', 1);
INSERT INTO `iplog` VALUES (431, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-17 15:04:17', 1);
INSERT INTO `iplog` VALUES (432, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-17 15:04:29', 1);
INSERT INTO `iplog` VALUES (433, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-17 15:11:03', 1);
INSERT INTO `iplog` VALUES (434, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-17 15:12:39', 1);
INSERT INTO `iplog` VALUES (435, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-17 15:15:39', 1);
INSERT INTO `iplog` VALUES (436, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-17 15:22:00', 1);
INSERT INTO `iplog` VALUES (437, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-17 15:27:18', 1);
INSERT INTO `iplog` VALUES (438, '0:0:0:0:0:0:0:1', 1, 'admin', '2019-05-17 15:57:51', 0);
INSERT INTO `iplog` VALUES (439, '127.0.0.1', 1, 'huenhui', '2019-05-17 20:31:58', 1);
INSERT INTO `iplog` VALUES (440, '127.0.0.1', 1, 'huenhui', '2019-05-17 21:09:05', 1);
INSERT INTO `iplog` VALUES (441, '127.0.0.1', 1, 'huenhui', '2019-05-17 22:11:16', 1);
INSERT INTO `iplog` VALUES (442, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-18 10:37:05', 1);
INSERT INTO `iplog` VALUES (443, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-18 10:39:56', 1);
INSERT INTO `iplog` VALUES (444, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-18 10:40:34', 1);
INSERT INTO `iplog` VALUES (445, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-05-18 10:51:15', 1);
INSERT INTO `iplog` VALUES (446, '0:0:0:0:0:0:0:1', 1, 'dupeng', '2019-05-18 10:51:59', 1);
INSERT INTO `iplog` VALUES (447, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-18 13:00:00', 1);
INSERT INTO `iplog` VALUES (448, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-18 13:06:09', 1);
INSERT INTO `iplog` VALUES (449, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-18 13:06:54', 1);
INSERT INTO `iplog` VALUES (450, '0:0:0:0:0:0:0:1', 1, 'baokexin', '2019-05-18 13:09:54', 1);
INSERT INTO `iplog` VALUES (451, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-18 13:39:51', 1);
INSERT INTO `iplog` VALUES (452, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-18 14:56:33', 1);
INSERT INTO `iplog` VALUES (453, '0:0:0:0:0:0:0:1', 1, 'liuhe', '2019-05-18 14:57:00', 1);
INSERT INTO `iplog` VALUES (454, '0:0:0:0:0:0:0:1', 1, 'huenhui', '2019-05-18 15:52:01', 1);

-- ----------------------------
-- Table structure for logininfo
-- ----------------------------
DROP TABLE IF EXISTS `logininfo`;
CREATE TABLE `logininfo`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '用户名',
  `password` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '密码',
  `state` tinyint(4) DEFAULT NULL COMMENT '账户状态  0正常  1锁定',
  `userType` tinyint(4) DEFAULT NULL COMMENT '用户类型  0后台  1前台',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 47 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of logininfo
-- ----------------------------
INSERT INTO `logininfo` VALUES (40, 'admin', '21232F297A57A5A743894A0E4A801FC3', 0, 0);
INSERT INTO `logininfo` VALUES (41, 'huenhui', '3CCC61401F8ABDD91611952A75BEDF95', 0, 1);
INSERT INTO `logininfo` VALUES (42, 'dupeng', 'DE1942DFB73B9BEC05AB547EC80543F6', 0, 1);
INSERT INTO `logininfo` VALUES (43, 'baokexin', 'BE0E869AA1126F50868D8782DD66E259', 0, 1);
INSERT INTO `logininfo` VALUES (44, 'liuhe', '252974C7C317ABA0497BBB9276B9D60B', 0, 1);
INSERT INTO `logininfo` VALUES (46, 'ceshi', 'CC17C30CD111C7215FC8F51F8790E0E1', 0, 1);

-- ----------------------------
-- Table structure for mailverify
-- ----------------------------
DROP TABLE IF EXISTS `mailverify`;
CREATE TABLE `mailverify`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `userinfo_id` bigint(20) NOT NULL COMMENT '用户id',
  `sendDate` datetime(0) NOT NULL COMMENT '发送时间',
  `uuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '唯一标识',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱号',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of mailverify
-- ----------------------------
INSERT INTO `mailverify` VALUES (4, 41, '2019-03-31 21:08:47', '057205a4eb314c81b1bfc218de31207c', '717253212@qq.com');
INSERT INTO `mailverify` VALUES (5, 44, '2019-04-10 15:26:19', 'eef99025c5b342bfbacd4cef28307c3d', '717253212@qq.com');

-- ----------------------------
-- Table structure for paymentschedule
-- ----------------------------
DROP TABLE IF EXISTS `paymentschedule`;
CREATE TABLE `paymentschedule`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `deadLine` datetime(0) NOT NULL COMMENT '本期还款截止期限',
  `payDate` datetime(0) DEFAULT NULL COMMENT '还款时间',
  `totalAmount` decimal(18, 4) NOT NULL COMMENT '本期还款总金额',
  `principal` decimal(18, 4) NOT NULL COMMENT '本期还款本金',
  `interest` decimal(18, 4) NOT NULL COMMENT '本期还款总利息',
  `monthIndex` tinyint(4) NOT NULL COMMENT '第几期 (即第几个月)',
  `state` tinyint(4) NOT NULL COMMENT '本期还款状态',
  `bidRequestType` tinyint(4) NOT NULL COMMENT '借款类型',
  `returnType` tinyint(4) NOT NULL COMMENT '还款方式',
  `bidrequest_id` bigint(20) NOT NULL COMMENT '对应借款',
  `borrowUser_id` bigint(20) NOT NULL COMMENT '还款人',
  `bidRequestTitle` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '借款名称',
  `investUser_id` bigint(20) DEFAULT NULL COMMENT '收款人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of paymentschedule
-- ----------------------------
INSERT INTO `paymentschedule` VALUES (17, '2019-04-30 22:20:47', NULL, 683.4023, 658.4023, 25.0000, 1, 3, 0, 0, 8, 41, '修电脑', 42);
INSERT INTO `paymentschedule` VALUES (18, '2019-05-30 22:20:47', NULL, 683.4023, 670.1163, 13.2860, 2, 2, 0, 0, 8, 41, '修电脑', 42);
INSERT INTO `paymentschedule` VALUES (19, '2019-06-30 22:20:47', NULL, 683.4023, 674.9953, 8.4070, 3, 2, 0, 0, 8, 41, '修电脑', 42);
INSERT INTO `paymentschedule` VALUES (20, '2019-05-10 15:40:09', NULL, 509.3944, 496.8944, 12.5000, 1, 2, 0, 0, 9, 44, '没钱吃饭了', 41);
INSERT INTO `paymentschedule` VALUES (21, '2019-06-10 15:40:09', NULL, 509.3944, 503.1056, 6.2888, 2, 2, 0, 0, 9, 44, '没钱吃饭了', 41);
INSERT INTO `paymentschedule` VALUES (22, '2019-06-18 13:07:15', '2019-05-18 13:08:25', 502.0833, 500.0000, 2.0833, 1, 1, 0, 0, 13, 44, '测试', 41);
INSERT INTO `paymentschedule` VALUES (23, '2019-06-18 15:00:36', NULL, 502.0833, 500.0000, 2.0833, 1, 0, 0, 0, 14, 44, 'ceshi', 41);

-- ----------------------------
-- Table structure for realauth
-- ----------------------------
DROP TABLE IF EXISTS `realauth`;
CREATE TABLE `realauth`  (
  `id` tinyint(20) UNSIGNED ZEROFILL NOT NULL AUTO_INCREMENT,
  `realName` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '真实姓名',
  `sex` tinyint(4) NOT NULL COMMENT '性别',
  `idNumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '证件号码',
  `stuNumber` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学生号码',
  `state` tinyint(4) NOT NULL COMMENT '状态',
  `card_image1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证正面地址',
  `card_image2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '身份证反面地址',
  `stu_image1` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学生证正面地址',
  `stu_image2` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学生证反面地址',
  `remark` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '审核备注',
  `auditTime` datetime(0) DEFAULT NULL COMMENT '审核时间',
  `applyTime` datetime(0) NOT NULL COMMENT '申请时间',
  `auditor_id` bigint(20) DEFAULT NULL COMMENT '审核人',
  `applier_id` bigint(20) NOT NULL COMMENT '申请人',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of realauth
-- ----------------------------
INSERT INTO `realauth` VALUES (00000000000000000007, '胡恩会', 0, '130123199704251512', '15111034', 1, '/upload/82941b09-c790-43dc-8c46-0619bf829b95.jpg', '/upload/a3affcee-3617-41ae-9f63-a18e89c6fcab.jpg', '/upload/3c287548-1e60-46ae-9d0c-6f7a8e5c9e11.jpg', '/upload/ed633d86-39da-48cd-9b56-b13e2cea22ce.jpg', '信息统一', '2019-03-31 21:07:04', '2019-03-31 21:05:10', 40, 41);
INSERT INTO `realauth` VALUES (00000000000000000008, '刘鹤', 1, '130123199703031512', '15111035', 1, '/upload/e2353db6-3611-46a3-973d-83567a6e7e7c.PNG', '/upload/e753b2d6-625b-4876-8748-621a8565380d.PNG', '/upload/9f1f81b5-e364-4e29-9cdb-b354bac8ebcf.PNG', '/upload/4725448a-f7fa-476b-af48-25ec91d80bd4.PNG', '通过', '2019-04-10 15:21:08', '2019-04-10 15:17:40', 40, 44);
INSERT INTO `realauth` VALUES (00000000000000000009, '杜鹏', 0, '13012319970421151X', '15111028', 0, '/upload/4673ba0f-9319-47e8-85bf-b681aa23f661.jpg', '/upload/d30122db-88bb-4533-92f3-dfa027eeaff5.jpg', '/upload/19ed0b7f-9cf7-4300-8988-152abf347514.jpg', '/upload/dcdd055e-f7af-4144-9561-0f31f925b0da.jpg', NULL, NULL, '2019-05-17 14:54:23', NULL, 42);

-- ----------------------------
-- Table structure for studentinfo
-- ----------------------------
DROP TABLE IF EXISTS `studentinfo`;
CREATE TABLE `studentinfo`  (
  `stuid` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学号',
  `stuname` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  PRIMARY KEY (`stuid`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of studentinfo
-- ----------------------------
INSERT INTO `studentinfo` VALUES ('123', '测试');
INSERT INTO `studentinfo` VALUES ('1234', '测试');
INSERT INTO `studentinfo` VALUES ('15111001', '艾子方');
INSERT INTO `studentinfo` VALUES ('15111003', '霸宝');
INSERT INTO `studentinfo` VALUES ('15111010', '陈士卿');
INSERT INTO `studentinfo` VALUES ('15111014', '崔嘉蕊');
INSERT INTO `studentinfo` VALUES ('15111019', '杜鹏');
INSERT INTO `studentinfo` VALUES ('15111028', '郭生男');
INSERT INTO `studentinfo` VALUES ('15111030', '郝杰');
INSERT INTO `studentinfo` VALUES ('15111034', '胡恩会');
INSERT INTO `studentinfo` VALUES ('15111036', '黄海涛');
INSERT INTO `studentinfo` VALUES ('15111037', '黄培华');
INSERT INTO `studentinfo` VALUES ('15111060', '刘旭');
INSERT INTO `studentinfo` VALUES ('15111064', '卢畅畅');
INSERT INTO `studentinfo` VALUES ('15111066', '马凯');
INSERT INTO `studentinfo` VALUES ('15111073', '祁亚宁');
INSERT INTO `studentinfo` VALUES ('15111079', '宋崇汇');
INSERT INTO `studentinfo` VALUES ('15111085', '孙崇惠');
INSERT INTO `studentinfo` VALUES ('15111091', '田双利');
INSERT INTO `studentinfo` VALUES ('15111099', '王帅');
INSERT INTO `studentinfo` VALUES ('15111107', '吴中达');
INSERT INTO `studentinfo` VALUES ('15111108', '武昊然');
INSERT INTO `studentinfo` VALUES ('15111130', '张伟');
INSERT INTO `studentinfo` VALUES ('15111138', '赵一');
INSERT INTO `studentinfo` VALUES ('15111144', '邹安');

-- ----------------------------
-- Table structure for systemdictionary
-- ----------------------------
DROP TABLE IF EXISTS `systemdictionary`;
CREATE TABLE `systemdictionary`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `sn` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典分类编码',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典分类名称',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of systemdictionary
-- ----------------------------
INSERT INTO `systemdictionary` VALUES (1, 'Computer Science and Technology', '计算机科学与技术');
INSERT INTO `systemdictionary` VALUES (2, 'Economic management', '经济管理');

-- ----------------------------
-- Table structure for systemdictionaryitem
-- ----------------------------
DROP TABLE IF EXISTS `systemdictionaryitem`;
CREATE TABLE `systemdictionaryitem`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `parentId` bigint(20) NOT NULL COMMENT '所属分类id',
  `title` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '字典明细名称',
  `sequence` tinyint(4) NOT NULL COMMENT '字典明细顺序号',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `system`(`parentId`) USING BTREE,
  CONSTRAINT `system` FOREIGN KEY (`parentId`) REFERENCES `systemdictionary` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE = InnoDB AUTO_INCREMENT = 6 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of systemdictionaryitem
-- ----------------------------
INSERT INTO `systemdictionaryitem` VALUES (1, 1, '软件工程', 1);
INSERT INTO `systemdictionaryitem` VALUES (2, 1, '计算机科学与技术', 2);
INSERT INTO `systemdictionaryitem` VALUES (3, 1, '物联网', 3);
INSERT INTO `systemdictionaryitem` VALUES (4, 1, '电子商务', 4);
INSERT INTO `systemdictionaryitem` VALUES (5, 2, '财务管理', 1);

-- ----------------------------
-- Table structure for userinfo
-- ----------------------------
DROP TABLE IF EXISTS `userinfo`;
CREATE TABLE `userinfo`  (
  `id` bigint(20) NOT NULL,
  `version` int(11) NOT NULL COMMENT '版本',
  `bitState` bigint(20) NOT NULL COMMENT '用户状态码',
  `realName` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '真实姓名',
  `idNumber` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '身份证号',
  `phoneNumber` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '手机号',
  `email` varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '邮箱',
  `department_id` bigint(20) DEFAULT NULL COMMENT '院系',
  `profession_id` bigint(20) DEFAULT NULL COMMENT '专业',
  `studyId` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '学号',
  `clazz` int(11) DEFAULT NULL COMMENT '班级',
  `realAuthId` bigint(20) DEFAULT NULL COMMENT '实名认证id',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of userinfo
-- ----------------------------
INSERT INTO `userinfo` VALUES (41, 13, 47, '胡恩会', '130123199704251512', '18894961902', '717253212@qq.com', 1, 1, '15111034', 1501, 7);
INSERT INTO `userinfo` VALUES (42, 3, 1, '杜鹏', NULL, '18811703021', NULL, NULL, NULL, '15111028', 1501, 9);
INSERT INTO `userinfo` VALUES (43, 3, 4, NULL, NULL, NULL, NULL, 1, 1, NULL, NULL, NULL);
INSERT INTO `userinfo` VALUES (44, 17, 15, '刘鹤', '130123199703031512', '18894961902', '717253212@qq.com', 1, 2, '15111025', 1501, 8);
INSERT INTO `userinfo` VALUES (46, 1, 1, '测试', NULL, '123', NULL, NULL, NULL, '123', NULL, NULL);

SET FOREIGN_KEY_CHECKS = 1;
