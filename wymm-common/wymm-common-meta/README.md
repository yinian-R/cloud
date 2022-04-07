## 初始化表

```
CREATE TABLE `system_meta` (
  `id` bigint(20) NOT NULL COMMENT '元数据编码',
  `type` varchar(100) NOT NULL COMMENT '元数据类型',
  `field_value` varchar(50) NOT NULL COMMENT '数值',
  `name` varchar(100) NOT NULL COMMENT '名称',
  `alias_name` varchar(100) DEFAULT NULL COMMENT '别名',
  `parent_id` bigint(20) DEFAULT NULL COMMENT '元数据父编码',
  `meta_index` int(11) DEFAULT NULL COMMENT '排序',
  `create_time` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `idx_type` (`type`),
  KEY `idx_field_value` (`field_value`)
) CHARSET=utf8mb4;
```