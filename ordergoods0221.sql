/*
 Navicat Premium Data Transfer

 Source Server         : localhost
 Source Server Type    : MySQL
 Source Server Version : 80031
 Source Host           : localhost:3306
 Source Schema         : ordergoods0221

 Target Server Type    : MySQL
 Target Server Version : 80031
 File Encoding         : 65001

 Date: 21/02/2023 15:35:52
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for tb_sys_cart
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_cart`;
CREATE TABLE `tb_sys_cart`  (
  `id` bigint unsigned NOT NULL COMMENT '编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '用户ID',
  `user_name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户名称',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品ID',
  `goods_num` int(0) NULL DEFAULT NULL COMMENT '商品数量',
  `goods_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '商品名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '下单时商品价格',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `field0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段2',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段3',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段4',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段5',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 4 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '购物车表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for tb_sys_category
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_category`;
CREATE TABLE `tb_sys_category`  (
  `id` bigint unsigned NOT NULL COMMENT '编号',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL COMMENT '编码',
  `name` varchar(12) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `parent_code` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '父级编码-备用',
  `parent_id` bigint(0) NULL DEFAULT NULL COMMENT '父级ID-备用',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `field0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段1',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段2',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段3',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段4',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段5',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 2 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '分类表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_category
-- ----------------------------
INSERT INTO `tb_sys_category` VALUES (1, '0001', '玩具', NULL, NULL, '玩具', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:25:57', NULL);
INSERT INTO `tb_sys_category` VALUES (2, '0002', '零食', NULL, NULL, '零食', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:26:07', NULL);

-- ----------------------------
-- Table structure for tb_sys_file
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_file`;
CREATE TABLE `tb_sys_file`  (
  `id` bigint unsigned NOT NULL,
  `file_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '原始文件名',
  `object_id` bigint(0) NULL DEFAULT NULL COMMENT '所属对象ID',
  `category_code` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文件分类编码：0000-用户头像；0001--商品图片',
  `file_type` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '附件类型',
  `file_url` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文件保存路径',
  `file_size` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '文件大小单位KB',
  `save_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '实际保存的文件名字',
  `field0` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '图片主题',
  `field1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段1',
  `field2` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段2',
  `field3` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段3',
  `field4` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段4',
  `field5` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段5',
  `field6` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 12 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '附件表，存放文件' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_file
-- ----------------------------
INSERT INTO `tb_sys_file` VALUES (1, '397675c6507c7cab.jpg', 1, '0002', 'image/jpeg', 'D://javasoftinstall//ordergoods/file\\0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg', '1484', '0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', NULL);
INSERT INTO `tb_sys_file` VALUES (2, '397675c6507c7cab.jpg', 1, '0003', 'image/jpeg', 'D://javasoftinstall//ordergoods/file\\21f207563a6042e38c69dd7afd73e06f.jpg', '1484', '21f207563a6042e38c69dd7afd73e06f.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', NULL);
INSERT INTO `tb_sys_file` VALUES (3, '397675c6507c7cab.jpg', 1, '0004', 'image/jpeg', 'D://javasoftinstall//ordergoods/file\\2c77472442144c19abe9e04551e3fae4.jpg', '1484', '2c77472442144c19abe9e04551e3fae4.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', NULL);
INSERT INTO `tb_sys_file` VALUES (4, '397675c6507c7cab.jpg', 1, '0005', 'image/jpeg', 'D://javasoftinstall//ordergoods/file\\4461b2a8171d4a9897d5fbde34568ede.jpg', '1484', '4461b2a8171d4a9897d5fbde34568ede.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', NULL);
INSERT INTO `tb_sys_file` VALUES (5, '397675c6507c7cab.jpg', 1, '0006', 'image/jpeg', 'D://javasoftinstall//ordergoods/file\\eb8c3d107ccd4864bf7b5e55dd64df16.jpg', '1484', 'eb8c3d107ccd4864bf7b5e55dd64df16.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', NULL);
INSERT INTO `tb_sys_file` VALUES (8, '汉服11.jpg', 13, '0002', 'image/jpeg', 'D://myfilemapping//ordergoods/file\\28e475b46cf5456ba51136ac3571b388.jpg', '490628', '28e475b46cf5456ba51136ac3571b388.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-19 11:36:30', NULL);
INSERT INTO `tb_sys_file` VALUES (9, '汉服12.jpg', 13, '0003', 'image/jpeg', 'D://myfilemapping//ordergoods/file\\25613ba267db4f1ea504fcf770b1565e.jpg', '482178', '25613ba267db4f1ea504fcf770b1565e.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-19 11:36:30', NULL);
INSERT INTO `tb_sys_file` VALUES (10, '汉服13.jpg', 13, '0004', 'image/jpeg', 'D://myfilemapping//ordergoods/file\\617df9de478c4f96946ddd10baee12f8.jpg', '480571', '617df9de478c4f96946ddd10baee12f8.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-19 11:36:30', NULL);
INSERT INTO `tb_sys_file` VALUES (11, '汉服14.jpg', 13, '0005', 'image/jpeg', 'D://myfilemapping//ordergoods/file\\0bcad925c98a4b05bfa1a11d5e7cf113.jpg', '487752', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-19 11:36:30', NULL);
INSERT INTO `tb_sys_file` VALUES (12, '汉服15.jpg', 13, '0006', 'image/jpeg', 'D://myfilemapping//ordergoods/file\\c16855f6ebc14beb9bd7b765a2464c54.jpg', '463688', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-19 11:36:30', NULL);

-- ----------------------------
-- Table structure for tb_sys_goods
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_goods`;
CREATE TABLE `tb_sys_goods`  (
  `id` bigint unsigned NOT NULL COMMENT '编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名称',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '编号',
  `category_id` bigint(0) NULL DEFAULT NULL COMMENT '分类ID ',
  `category_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '分类名称',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '价格',
  `stock` int(0) NULL DEFAULT 9999 COMMENT '库存',
  `state` int(0) NULL DEFAULT 1 COMMENT '状态：0 下架；1 上架',
  `main_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '主图',
  `sub_pic1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '副图1',
  `sub_pic2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '副图2',
  `sub_pic3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '副图3',
  `sub_pic4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '副图4',
  `sub_pic5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '副图5',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '详情介绍',
  `field0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '保存返回给小程序时候的下单购买数量',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '10' COMMENT '当前有效折扣',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段2',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段3',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段4',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段5',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 33 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '商品表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_goods
-- ----------------------------
INSERT INTO `tb_sys_goods` VALUES (13, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (14, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (15, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (16, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (17, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (18, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (19, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (20, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (21, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (22, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (23, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (24, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (25, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (26, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (27, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (28, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (29, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9998, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', '1', '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (30, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9998, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', '1', '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (31, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (32, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9999, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', NULL, '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');
INSERT INTO `tb_sys_goods` VALUES (33, '新中式禅意茶服旗袍', NULL, 1, '玩具', 179.00, 9998, 1, '28e475b46cf5456ba51136ac3571b388.jpg', '25613ba267db4f1ea504fcf770b1565e.jpg', '617df9de478c4f96946ddd10baee12f8.jpg', '0bcad925c98a4b05bfa1a11d5e7cf113.jpg', 'c16855f6ebc14beb9bd7b765a2464c54.jpg', NULL, NULL, '新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙', '1', '9', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 21:28:05', '2023-02-19 11:36:30');

-- ----------------------------
-- Table structure for tb_sys_order
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_order`;
CREATE TABLE `tb_sys_order`  (
  `id` bigint unsigned NOT NULL COMMENT '编号',
  `code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单编号',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '下单用户ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '下单用户名称',
  `state` int(0) NULL DEFAULT 0 COMMENT '订单状态：0 待付款；1.待发货；2.已发货；3.已评价；',
  `money` decimal(10, 2) NULL DEFAULT NULL COMMENT '总价-最后实际支付价格',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '买家备注',
  `address` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单对应的地址',
  `appraise` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户订单评价',
  `field0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '用户评分',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '保存用户收货地址ID',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段2',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段3',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段4',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段5',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_order
-- ----------------------------
INSERT INTO `tb_sys_order` VALUES (3, 'M202104240903531300', 30, '小默', 3, 161.10, NULL, '长安街101号', '', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:03:53', NULL);
INSERT INTO `tb_sys_order` VALUES (4, 'M202104240908051300', 30, '小默', 3, 161.10, NULL, '长安街101号', '很棒', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:08:05', NULL);
INSERT INTO `tb_sys_order` VALUES (5, 'M202104240910321157', 30, '小默', 3, 161.10, NULL, '长安街101号', '很棒', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:10:32', NULL);
INSERT INTO `tb_sys_order` VALUES (13, 'M202302211450591506', 30, '小默', 3, 161.10, NULL, '北京市,北京市,朝阳区 朝阳区望京100号', '很快，效率很高', '5', '16', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 14:50:59', NULL);
INSERT INTO `tb_sys_order` VALUES (17, 'M202302211518131772', 30, '小默', 1, 161.10, NULL, '北京市,北京市,西城区 广西门外大街110号', NULL, NULL, '11', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:18:13', NULL);
INSERT INTO `tb_sys_order` VALUES (18, 'M202302211523231290', 30, '小默', 1, 161.10, NULL, '北京市,北京市,西城区 西直门110号', NULL, NULL, '15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:23:23', NULL);
INSERT INTO `tb_sys_order` VALUES (19, 'M202302211527261892', 30, '小默', 1, 966.60, NULL, '北京市,北京市,西城区 西直门110号', NULL, NULL, '15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:27:26', NULL);
INSERT INTO `tb_sys_order` VALUES (20, 'M202302211532591594', 30, '小默', 3, 483.30, NULL, '北京市,北京市,西城区 西直门110号', '', '5', '15', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:32:59', NULL);

-- ----------------------------
-- Table structure for tb_sys_order_item
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_order_item`;
CREATE TABLE `tb_sys_order_item`  (
  `id` bigint unsigned NOT NULL COMMENT '编号',
  `order_id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单ID',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '会员ID',
  `user_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '会员名称',
  `goods_id` bigint(0) NULL DEFAULT NULL COMMENT '商品ID',
  `price` decimal(10, 2) NULL DEFAULT NULL COMMENT '商品单价',
  `number` int(0) NULL DEFAULT NULL COMMENT '购买数量',
  `state` int(0) NULL DEFAULT 0 COMMENT '订单状态：0 待付款；1.待发货；2.已发货；3.已评价；',
  `remark` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '备注',
  `field0` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '保存对应商品快照信息',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '订单评价',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '评分',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段3',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段4',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段5',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '订单明细表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_order_item
-- ----------------------------
INSERT INTO `tb_sys_order_item` VALUES (3, '3', 30, '小默', 11, 161.10, 1, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"盈佳智能机器狗 儿童玩具男孩故事机 百科问答早教机 小孩1-3-6岁宝宝婴儿玩具女孩编程学习机\",\"createTime\":\"2021-04-20 21:28:05\",\"field0\":\"1\",\"field1\":\"9\",\"id\":11,\"mainPic\":\"0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg\",\"name\":\"盈佳智能机器狗\",\"price\":179.00,\"state\":1,\"stock\":9998,\"subPic1\":\"21f207563a6042e38c69dd7afd73e06f.jpg\",\"subPic2\":\"2c77472442144c19abe9e04551e3fae4.jpg\",\"subPic3\":\"4461b2a8171d4a9897d5fbde34568ede.jpg\",\"subPic4\":\"eb8c3d107ccd4864bf7b5e55dd64df16.jpg\"}', '', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:03:53', NULL);
INSERT INTO `tb_sys_order_item` VALUES (4, '4', 30, '小默', 11, 161.10, 1, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"盈佳智能机器狗 儿童玩具男孩故事机 百科问答早教机 小孩1-3-6岁宝宝婴儿玩具女孩编程学习机\",\"createTime\":\"2021-04-20 21:28:05\",\"field0\":\"2\",\"field1\":\"9\",\"id\":11,\"mainPic\":\"0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg\",\"name\":\"盈佳智能机器狗\",\"price\":179.00,\"state\":1,\"stock\":9997,\"subPic1\":\"21f207563a6042e38c69dd7afd73e06f.jpg\",\"subPic2\":\"2c77472442144c19abe9e04551e3fae4.jpg\",\"subPic3\":\"4461b2a8171d4a9897d5fbde34568ede.jpg\",\"subPic4\":\"eb8c3d107ccd4864bf7b5e55dd64df16.jpg\"}', '很棒', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:08:05', NULL);
INSERT INTO `tb_sys_order_item` VALUES (5, '5', 30, '小默', 10, 161.10, 1, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"盈佳智能机器狗 儿童玩具男孩故事机 百科问答早教机 小孩1-3-6岁宝宝婴儿玩具女孩编程学习机\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":10,\"mainPic\":\"0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg\",\"name\":\"盈佳智能机器狗\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"21f207563a6042e38c69dd7afd73e06f.jpg\",\"subPic2\":\"2c77472442144c19abe9e04551e3fae4.jpg\",\"subPic3\":\"4461b2a8171d4a9897d5fbde34568ede.jpg\",\"subPic4\":\"eb8c3d107ccd4864bf7b5e55dd64df16.jpg\"}', '很棒', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:10:32', NULL);
INSERT INTO `tb_sys_order_item` VALUES (6, '6', 30, '小默', 11, 161.10, 1, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"盈佳智能机器狗 儿童玩具男孩故事机 百科问答早教机 小孩1-3-6岁宝宝婴儿玩具女孩编程学习机\",\"createTime\":\"2021-04-20 21:28:05\",\"field0\":\"3\",\"field1\":\"9\",\"id\":11,\"mainPic\":\"0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg\",\"name\":\"盈佳智能机器狗\",\"price\":179.00,\"state\":1,\"stock\":9996,\"subPic1\":\"21f207563a6042e38c69dd7afd73e06f.jpg\",\"subPic2\":\"2c77472442144c19abe9e04551e3fae4.jpg\",\"subPic3\":\"4461b2a8171d4a9897d5fbde34568ede.jpg\",\"subPic4\":\"eb8c3d107ccd4864bf7b5e55dd64df16.jpg\"}', '很棒', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:34:05', NULL);
INSERT INTO `tb_sys_order_item` VALUES (7, '7', 30, '小默', 8, 179.00, 5, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"盈佳智能机器狗 儿童玩具男孩故事机 百科问答早教机 小孩1-3-6岁宝宝婴儿玩具女孩编程学习机\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":8,\"mainPic\":\"0b9d7c9ca9c844b8b99da6f2e0a5f891.jpg\",\"name\":\"盈佳智能机器狗\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"21f207563a6042e38c69dd7afd73e06f.jpg\",\"subPic2\":\"2c77472442144c19abe9e04551e3fae4.jpg\",\"subPic3\":\"4461b2a8171d4a9897d5fbde34568ede.jpg\",\"subPic4\":\"eb8c3d107ccd4864bf7b5e55dd64df16.jpg\"}', '很棒', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-24 09:34:24', NULL);
INSERT INTO `tb_sys_order_item` VALUES (8, '8', 30, '小默', 33, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":33,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-19 11:47:18', NULL);
INSERT INTO `tb_sys_order_item` VALUES (12, '11', 30, '小默', 31, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":31,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 10:56:56', NULL);
INSERT INTO `tb_sys_order_item` VALUES (13, '12', 30, '小默', 33, 161.10, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":33,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 13:40:44', NULL);
INSERT INTO `tb_sys_order_item` VALUES (14, '13', 30, '小默', 29, 161.10, 1, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":29,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', '很快，效率很高', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 14:50:59', NULL);
INSERT INTO `tb_sys_order_item` VALUES (15, '14', 30, '小默', 31, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":31,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 14:53:09', NULL);
INSERT INTO `tb_sys_order_item` VALUES (16, '15', 30, '小默', 28, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":28,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:10:16', NULL);
INSERT INTO `tb_sys_order_item` VALUES (17, '16', 30, '小默', 29, 179.00, 1, 0, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field0\":\"1\",\"field1\":\"9\",\"id\":29,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9998,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:13:27', NULL);
INSERT INTO `tb_sys_order_item` VALUES (18, '17', 30, '小默', 31, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":31,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:18:13', NULL);
INSERT INTO `tb_sys_order_item` VALUES (19, '18', 30, '小默', 28, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":28,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:23:23', NULL);
INSERT INTO `tb_sys_order_item` VALUES (20, '19', 30, '小默', 29, 179.00, 5, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field0\":\"1\",\"field1\":\"9\",\"id\":29,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9998,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:27:26', NULL);
INSERT INTO `tb_sys_order_item` VALUES (21, '19', 30, '小默', 32, 179.00, 1, 1, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":32,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', NULL, '1', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:27:26', NULL);
INSERT INTO `tb_sys_order_item` VALUES (22, '20', 30, '小默', 31, 179.00, 2, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field1\":\"9\",\"id\":31,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9999,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', '', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:32:59', NULL);
INSERT INTO `tb_sys_order_item` VALUES (23, '20', 30, '小默', 33, 179.00, 1, 3, NULL, '{\"categoryId\":1,\"categoryName\":\"玩具\",\"content\":\"新中式禅意茶服旗袍汉元素两件套改良汉服文艺国风少女温柔连衣裙\",\"createTime\":\"2021-04-20 21:28:05\",\"field0\":\"1\",\"field1\":\"9\",\"id\":33,\"mainPic\":\"28e475b46cf5456ba51136ac3571b388.jpg\",\"name\":\"新中式禅意茶服旗袍\",\"price\":179.00,\"state\":1,\"stock\":9998,\"subPic1\":\"25613ba267db4f1ea504fcf770b1565e.jpg\",\"subPic2\":\"617df9de478c4f96946ddd10baee12f8.jpg\",\"subPic3\":\"0bcad925c98a4b05bfa1a11d5e7cf113.jpg\",\"subPic4\":\"c16855f6ebc14beb9bd7b765a2464c54.jpg\",\"updateTime\":\"2023-02-19 11:36:30\"}', '', '5', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2023-02-21 15:32:59', NULL);

-- ----------------------------
-- Table structure for tb_sys_user
-- ----------------------------
DROP TABLE IF EXISTS `tb_sys_user`;
CREATE TABLE `tb_sys_user`  (
  `id` bigint unsigned NOT NULL COMMENT '编号',
  `name` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '名字',
  `mobile` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '手机号',
  `code` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '登录账号',
  `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '02eabd39ff61f1b6e9ea27cfb96b3acc' COMMENT '密码',
  `state` int(0) NULL DEFAULT 1 COMMENT '1 有效；2 冻结',
  `type` int(0) NULL DEFAULT 2 COMMENT '用户角色：1 管理员；2 会员',
  `sex` varchar(5) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT '1' COMMENT '性别 ：1 男 2 女',
  `score` int(0) NULL DEFAULT 0 COMMENT '用户积分',
  `age` int(0) NULL DEFAULT NULL COMMENT '年龄',
  `address` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '地址',
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '邮箱',
  `born_date` datetime(0) NULL DEFAULT NULL COMMENT '出生日期',
  `qq_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT 'qq号码',
  `wx_number` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信号码',
  `wx_nick` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信昵称',
  `wx_avatarurl` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL COMMENT '微信头像',
  `wx_openid` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '微信openid',
  `field0` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field1` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '保存用户头像-saveName',
  `field2` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field3` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field4` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field5` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field6` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field7` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field8` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `field9` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL COMMENT '冗余字段',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_mobile`(`mobile`) USING BTREE,
  UNIQUE INDEX `unique_code`(`code`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci COMMENT = '用户表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_sys_user
-- ----------------------------
INSERT INTO `tb_sys_user` VALUES (1, '管理员', '13813813800', 'admin', '02eabd39ff61f1b6e9ea27cfb96b3acc', 1, 1, '1', 0, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 16:02:11', NULL);
INSERT INTO `tb_sys_user` VALUES (21, 'jane', '13813813801', '0001', '02eabd39ff61f1b6e9ea27cfb96b3acc', 1, 0, '1', 0, NULL, NULL, '1274654983@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '271c50d0050f4abb9690775353e169af.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 16:02:11', NULL);
INSERT INTO `tb_sys_user` VALUES (29, 'lucy', '13813813802', 'lucy', '02eabd39ff61f1b6e9ea27cfb96b3acc', 1, 0, '1', 20, NULL, NULL, '12348722@qq.com', NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2741e0086dd24ca1bd583cb95959acfb.jpg', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 16:02:11', NULL);
INSERT INTO `tb_sys_user` VALUES (30, '小默', '13413413425', NULL, '02eabd39ff61f1b6e9ea27cfb96b3acc', 1, 2, '2', 2971, 28, '长安街101号', NULL, NULL, NULL, NULL, 'Janus', 'https://thirdwx.qlogo.cn/mmopen/vi_32/Q0j4TwGTfTLTNJyqODJibgfLTprKHa3icjicicAicdp065bIR9aoluyTyhnO7Y1IA24kFf9llroo5mUcAOY2EbRIKCQ/132', 'oKvsZ0ZqbINaLAX5FKuck00oO-BI', NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, NULL, '2021-04-20 16:02:11', '2023-02-21 09:24:16');

-- ----------------------------
-- Table structure for tb_user_address
-- ----------------------------
DROP TABLE IF EXISTS `tb_user_address`;
CREATE TABLE `tb_user_address`  (
  `id` bigint unsigned NOT NULL,
  `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '所属用户名称',
  `user_id` bigint(0) NULL DEFAULT NULL COMMENT '所属用户ID',
  `mobile` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人手机号',
  `receiver_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '收货人名称',
  `post_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '邮编',
  `is_default` int(0) NULL DEFAULT 0 COMMENT '是否默认：0 否；1 是',
  `province_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '省份名称',
  `province_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '省份编码',
  `city_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地市名称',
  `city_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '地市编码',
  `area_name` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '区域名称',
  `area_code` varchar(20) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '区域编码',
  `address` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '完整详细地址',
  `remark` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '备注',
  `field0` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL,
  `field1` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NULL DEFAULT NULL COMMENT '冗余字段1',
  `create_time` datetime(0) NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb3 COLLATE = utf8mb3_general_ci COMMENT = '用户收货地址表' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of tb_user_address
-- ----------------------------
INSERT INTO `tb_user_address` VALUES (1, 'jane', 2, '13413413400', '白雪', '124547547@qq.com', 1, NULL, NULL, NULL, NULL, NULL, NULL, '北京海淀长安街101', NULL, NULL, NULL, '2023-02-18 18:47:38', NULL);
INSERT INTO `tb_user_address` VALUES (4, 'jane', 2, '13413413400', '白雪', '124547547@qq.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, '北京海淀长安街101', NULL, NULL, NULL, '2023-02-18 18:47:38', NULL);
INSERT INTO `tb_user_address` VALUES (5, 'jane', 2, '13413413400', '白雪', '124547547@qq.com', 0, NULL, NULL, NULL, NULL, NULL, NULL, '北京海淀长安街101', NULL, NULL, NULL, '2023-02-18 18:47:38', NULL);
INSERT INTO `tb_user_address` VALUES (11, NULL, 30, '13913831542', '李洁', '100032', 0, '北京市', '110000', '北京市', '110100', '西城区', '110102', '北京市,北京市,西城区 广西门外大街110号', NULL, NULL, NULL, '2023-02-21 13:18:49', NULL);
INSERT INTO `tb_user_address` VALUES (15, '小默', 30, '13413513400', '张冰', '100032', 1, '北京市', '110000', '北京市', '110100', '西城区', '110102', '北京市,北京市,西城区 西直门110号', NULL, NULL, NULL, '2023-02-21 13:20:15', NULL);
INSERT INTO `tb_user_address` VALUES (16, '小默', 30, '13813813874', '李爽', '100020', 0, '北京市', '110000', '北京市', '110100', '朝阳区', '110105', '北京市,北京市,朝阳区 朝阳区望京100号', NULL, NULL, NULL, '2023-02-21 13:20:54', NULL);
INSERT INTO `tb_user_address` VALUES (17, '小默', 30, '13813813874', '李爽', '100020', 0, '北京市', '110000', '北京市', '110100', '朝阳区', '110105', '北京市,北京市,朝阳区 朝阳区望京100号', NULL, NULL, NULL, '2023-02-21 13:20:54', NULL);

SET FOREIGN_KEY_CHECKS = 1;
