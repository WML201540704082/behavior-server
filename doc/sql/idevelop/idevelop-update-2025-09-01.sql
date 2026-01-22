/*
 Navicat Premium Data Transfer

 Source Server         : idevelop
 Source Server Type    : MySQL
 Source Server Version : 50738
 Source Host           : rm-wk47jx45tm6cu5958.mysql.rds.ops-devcloud.sd.sgcc.com.cn:14306
 Source Schema         : idevelop

 Target Server Type    : MySQL
 Target Server Version : 50738
 File Encoding         : 65001

 Date: 30/04/2025 15:38:41
*/
ALTER TABLE `idevelop`.`idevelop_user`
    ADD COLUMN `is_login` int(6) UNSIGNED NULL DEFAULT 0 COMMENT '是否是第一次登录' AFTER `ext`;
