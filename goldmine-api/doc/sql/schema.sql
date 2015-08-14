CREATE DATABASE `goldmine`;

USE `goldmine`;

CREATE TABLE `advertiser` (
  `id` char(32) NOT NULL,
  `advertiser_name` varchar(64) NOT NULL COMMENT '名称',
  `description` varchar(1024) DEFAULT '' COMMENT '描述',
  `created_time` char(19) NOT NULL COMMENT '创建时间',
  `last_modified` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `log` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `timestamp` bigint(20) NOT NULL COMMENT '时间戳',
  `date` char(10) NOT NULL COMMENT '日期',
  `time` char(12) NOT NULL COMMENT '时间',
  `level` varchar(5) NOT NULL COMMENT '级别',
  `class` varchar(100) NOT NULL COMMENT '类名',
  `line` varchar(5) NOT NULL COMMENT '行数',
  `message` text NOT NULL COMMENT '信息',
  `error` text COMMENT '错误',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;