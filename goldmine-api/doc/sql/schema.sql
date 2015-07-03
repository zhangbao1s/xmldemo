DROP TABLE IF EXISTS `advertiser`;
CREATE TABLE `advertiser` (
  `id` char(32) NOT NULL,
  `advertiser_name` varchar(64) NOT NULL DEFAULT '' COMMENT '名称',
  `description` varchar(1024) DEFAULT '' COMMENT '描述',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;