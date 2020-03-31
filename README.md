# gateway
网关路由

### 实现功能：
  * 路由信息持久化存储
  * 动态更新路由信息
  * 保存转发的请求和响应
### 路由表结构：
  * CREATE TABLE `route` (
    `id` bigint(32) NOT NULL AUTO_INCREMENT,
    `service` varchar(32) NOT NULL COMMENT '服务名',
    `predicates` varchar(1024) NOT NULL COMMENT '路由规则',
    `filters` varchar(1024) DEFAULT NULL COMMENT '过滤规则',
    `priority` int(11) NOT NULL COMMENT '路由顺序',
    `uri` varchar(512) NOT NULL COMMENT '路由地址',
    `memo` varchar(256) DEFAULT NULL COMMENT '描述',
    `gmt_create` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `gmt_modified` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
    PRIMARY KEY (`id`)
  ) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;
  
