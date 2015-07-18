CREATE DATABASE `goldmine`;

CREATE TABLE `advertiser` (
  `id` char(32) NOT NULL,
  `advertiser_name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(1024) DEFAULT '' COMMENT '描述',
  `created_time` char(19) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;