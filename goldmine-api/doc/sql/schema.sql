CREATE DATABASE `goldmine`;

CREATE TABLE `advertiser` (
  `id` char(32) NOT NULL,
  `advertiser_name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(1024) DEFAULT '' COMMENT '描述',
  `created_time` char(19) DEFAULT NULL COMMENT '创建时间',
  `last_modified` datetime DEFAULT NULL COMMENT '最后修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;