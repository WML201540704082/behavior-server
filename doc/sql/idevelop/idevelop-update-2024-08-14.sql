-- Date: 2024-08-09 已完成
-- SQL语句:

CREATE TABLE `idevelop_update_cmdb_count`
(
    `id`                varchar(20) NOT NULL  PRIMARY KEY   COMMENT '主键',
    `data_Date`       varchar(10)  DEFAULT NULL COMMENT '日期yyyy-MM-dd',
    `total`      int(12)   DEFAULT 0 COMMENT '查询Mongo总数据量',
    `update_count`       int(12)   DEFAULT 0 COMMENT '累计更新CMDB数量',
    `deal_count`      int(12)  DEFAULT 0 COMMENT '已处理数据量',
    `new_device_count`     int(12)  DEFAULT 0 COMMENT '新发现设备数据量',
    `mid_device_count` int(12)       DEFAULT 0 COMMENT '用mid能查到的设备数量',
    `ip_mac_device_count`        int(12)   DEFAULT 0 COMMENT '用ip+mac能查到的设备数量（不包括mid能查到的）',
    `mac_device_count`    int(12) DEFAULT 0 COMMENT '用mac能查到的设备数量（不包括mid、ip+mac能查到的）',
    `ip_device_count`    int(12) DEFAULT 0 COMMENT '用ip能查到的设备数量（不包括mid、ip+mac能查到的）',
    `begin_time`    datetime  COMMENT '开始时间',
    `end_time`    datetime COMMENT '结束时间',
    `is_deleted` int(1) DEFAULT 0 COMMENT '0有效'
)  COMMENT = '天擎同步cmdb数据统计';
