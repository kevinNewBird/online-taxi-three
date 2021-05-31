# 1.订单表
DROP TABLE IF EXISTS `service_order`;
CREATE TABLE `service_order`
(
    `id`                          int(10) NOT NULL AUTO_INCREMENT,
    `order_number`                varchar(32)                        NOT NULL DEFAULT '' COMMENT '订单号',
    `passenger_info_id`           int(10) unsigned NOT NULL DEFAULT '0' COMMENT '乘客id',
    `passenger_phone`             varchar(64)                        NOT NULL COMMENT '乘客电话',
    `device_code`                 varchar(64)                        NOT NULL DEFAULT '' COMMENT '乘客设备号',
    `driver_id`                   int(10) unsigned DEFAULT NULL COMMENT '司机id',
    `driver_status`               int(2) unsigned DEFAULT '0' COMMENT '司机状态   \r\n0：没有司机接单   \r\n1：司机接单  \r\n2.  去接乘客 \r\n3：司机到达上车点   \r\n4：开始行程   \r\n5：结束行程  \r\n6：发起收款  \r\n7：取消',
    `driver_phone`                varchar(64)                                 DEFAULT NULL COMMENT '司机电话',
    `car_id`                      int(10) unsigned DEFAULT NULL COMMENT '车辆id',
    `plate_number`                varchar(16)                                 DEFAULT NULL COMMENT '车牌号',
    `user_longitude`              varchar(32)                        NOT NULL COMMENT '用户位置经度',
    `user_latitude`               varchar(32)                        NOT NULL COMMENT '用户位置纬度',
    `start_longitude`             varchar(32)                        NOT NULL COMMENT '乘客下单起点经度',
    `start_latitude`              varchar(32)                        NOT NULL COMMENT '乘客下单起点纬度',
    `start_address`               varchar(128)                       NOT NULL COMMENT '起点名称',
    `end_address`                 varchar(128)                       NOT NULL COMMENT '终点地址名称',
    `start_time`                  timestamp NULL DEFAULT NULL COMMENT '乘客下单时间',
    `order_start_time`            timestamp NULL DEFAULT NULL COMMENT '订单开始时间',
    `end_longitude`               varchar(32)                        NOT NULL COMMENT '乘客下单终点经度',
    `end_latitude`                varchar(32)                        NOT NULL COMMENT '乘客下单终点纬度',
    `driver_grab_time`            timestamp NULL DEFAULT NULL COMMENT '司机抢单时间',
    `driver_start_time`           timestamp NULL DEFAULT NULL COMMENT '司机去接乘客出发时间',
    `driver_arrived_time`         timestamp NULL DEFAULT NULL COMMENT '司机到达时间',
    `pick_up_passenger_longitude` varchar(32)                                 DEFAULT '' COMMENT '去接乘客经度',
    `pick_up_passenger_latitude`  varchar(32)                                 DEFAULT '' COMMENT '去接乘客纬度',
    `pick_up_passenger_address`   varchar(300)                                DEFAULT '' COMMENT '去接乘客地点',
    `receive_passenger_time`      timestamp NULL DEFAULT NULL COMMENT '接到乘客时间',
    `receive_passenger_longitude` varchar(32)                        NOT NULL DEFAULT '' COMMENT '接到乘客经度',
    `receive_passenger_latitude`  varchar(32)                        NOT NULL DEFAULT '' COMMENT '接到乘客纬度',
    `passenger_getoff_time`       timestamp NULL DEFAULT NULL COMMENT '乘客下车时间',
    `passenger_getoff_longitude`  varchar(32)                                 DEFAULT NULL COMMENT '乘客下车经度',
    `passenger_getoff_latitude`   varchar(32)                                 DEFAULT NULL COMMENT '乘客下车纬度',
    `other_name`                  varchar(16)                                 DEFAULT NULL COMMENT '他人姓名 （乘车人）',
    `other_phone`                 varchar(64)                                 DEFAULT NULL COMMENT '他人电话　(乘车人）',
    `order_type`                  int(2) unsigned DEFAULT NULL COMMENT '订单类型：1：自己叫车，2：他人叫车',
    `service_type`                int(2) unsigned NOT NULL COMMENT '叫车订单类型，\r\n1：实时订单，\r\n2：预约订单，\r\n3：接机单，\r\n4：送机单，\r\n5：日租，\r\n6：半日租',
    `order_channel`               int(2) unsigned NOT NULL DEFAULT '1' COMMENT '订单渠道 \r\n1.自有订单\r\n2.高德\r\n3.飞猪',
    `status`                      int(2) NOT NULL COMMENT '订单状态 0: 订单预估 1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9.乘客取消订单',
    `transaction_id`              varchar(32)                                 DEFAULT NULL COMMENT '商户交易id',
    `mapping_id`                  varchar(64)                                 DEFAULT NULL COMMENT '订单状态 0: 订单预估 1：订单开始 2：司机接单 3：去接乘客 4：司机到达乘客起点 5：乘客上车，司机开始行程 6：到达目的地，行程结束，未支付 7：发起收款 8: 支付完成 9.订单取消',
    `mapping_number`              varchar(32)                                 DEFAULT NULL COMMENT '关联号码',
    `merchant_id`                 varchar(32)                                 DEFAULT NULL COMMENT '商户id',
    `is_evaluate`                 int(2) NOT NULL DEFAULT '0' COMMENT '乘客是否评价，0：未评价，1：已评价',
    `invoice_type`                int(2) unsigned NOT NULL DEFAULT '1' COMMENT '发票状态：\r\n1：未开票，\r\n2：申请开票，\r\n3：开票中，\r\n4：已开票,\r\n5：退回,',
    `is_annotate`                 int(2) unsigned NOT NULL DEFAULT '0' COMMENT '通知客服状态\r\n0，未通知  \r\n1,  已通知',
    `source`                      varchar(64)                        NOT NULL COMMENT '设备来源\r\n1: ios\r\n2:android\r\n3.other',
    `use_coupon`                  int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否使用优惠券 \r\n0:未使用  1:使用',
    `cancel_order_type`           int(2) unsigned DEFAULT NULL COMMENT '取消原因类型id',
    `pay_type`                    int(1) DEFAULT NULL COMMENT '1:余额\r\n2.微信\r\n3.支付宝',
    `is_paid`                     int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否已支付 0：未支付  1：已支付',
    `is_cancel`                   int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否取消  0：未取消   1：已取消',
    `is_adjust`                   int(2) unsigned NOT NULL DEFAULT '0' COMMENT '调帐状态  0：未调帐  1:调账中 2.调账完毕',
    `is_dissent`                  int(2) unsigned NOT NULL DEFAULT '0' COMMENT '是否疑义订单 0：否  1：是',
    `is_manual`                   int(1) NOT NULL DEFAULT '0' COMMENT '是否人工派单0 否 1 原来无司机, 人工派 2原来有司机，改派',
    `is_following`                int(1) NOT NULL DEFAULT '0' COMMENT '是否是顺风单0否 1是',
    `is_fake_success`             int(1) NOT NULL DEFAULT '0' COMMENT '是否是假成功单0 否 1是',
    `memo`                        varchar(500) CHARACTER SET utf8mb4 NOT NULL DEFAULT '' COMMENT '备忘录',
    `create_time`                 timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create_time',
    `update_time`                 timestamp                          NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update_time',
    `user_feature`                int(2) DEFAULT '0' COMMENT '1：儿童用车\r\n2：女性用车',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1967 DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT COMMENT='订单表';

# 2.城市表
DROP TABLE IF EXISTS `service_order_city`;
CREATE TABLE `service_order_city` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `city_name` varchar(64) NOT NULL COMMENT '城市名称',
    `city_code` varchar(32) NOT NULL COMMENT '城市编码',
    `city_longitude_latitude` varchar(64) NOT NULL COMMENT '城市中心经纬度',
    `order_risk_top` int(4) NOT NULL COMMENT '下单风险上限值',
    `city_status` int(2) NOT NULL DEFAULT '0' COMMENT '是否开通 0未开通 1开通',
    `operator_id` int(11) NOT NULL COMMENT '操作人id',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`),
    UNIQUE KEY `city_code` (`city_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='城市表';

# 3.服务类型表
DROP TABLE IF EXISTS `service_order_servicetype`;
CREATE TABLE `service_order_servicetype` (
    `id` int(11) NOT NULL AUTO_INCREMENT,
    `service_type_name` varchar(64) NOT NULL COMMENT '服务类型名称',
    `service_type_status` int(2) NOT NULL COMMENT '服务类型状态 1开启 0暂停',
    `is_use` char(1) NOT NULL DEFAULT '0' COMMENT '使用状态  1使用 0未使用',
    `operator_id` int(11) NOT NULL COMMENT '操作人id',
    `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='服务类型表';

# 4.渠道管理表
DROP TABLE IF EXISTS `service_order_channel`;
CREATE TABLE `service_order_channel` (
   `id` int(11) NOT NULL AUTO_INCREMENT,
   `channel_name` varchar(64) NOT NULL COMMENT '渠道名称',
   `channel_status` int(2) NOT NULL DEFAULT '0' COMMENT '渠道开启暂停状态 1开启 0暂停',
   `operator_id` int(11) NOT NULL COMMENT '操作人id',
   `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
   `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
   PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='渠道管理表';

# 6.车辆级别列表
DROP TABLE IF EXISTS `service_order_car_level`;
CREATE TABLE `service_order_car_level` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `label` varchar(30) NOT NULL DEFAULT '' COMMENT '车辆级别标签',
  `icon` varchar(500) NOT NULL DEFAULT '' COMMENT '车型图标',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `operator_id` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '操作人ID',
  `enable` tinyint(4) unsigned NOT NULL DEFAULT '0' COMMENT '状态:0未启用1启用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='车辆级别列表';

# 7.计费规则表
DROP TABLE IF EXISTS `service_order_charge_rule`;
CREATE TABLE `service_order_charge_rule` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `city_code` varchar(32) NOT NULL DEFAULT '' COMMENT '城市编码',
  `service_type_id` int(10) NOT NULL COMMENT '服务类型',
  `channel_id` int(10) NOT NULL COMMENT '渠道名称',
  `car_level_id` int(10) NOT NULL COMMENT '车辆级别',
  `lowest_price` double(10,2) NOT NULL COMMENT '基础价',
  `base_price` double(10,2) NOT NULL COMMENT '起步价',
  `base_kilo` double(10,2) NOT NULL COMMENT '基础价格包含公里数',
  `base_minutes` double(10,2) NOT NULL COMMENT '基础价格包含时长数(分钟)',
  `per_kilo_price` double(10,2) NOT NULL COMMENT '超公里单价(每公里单价)',
  `per_minute_price` double(10,2) NOT NULL COMMENT '超时间单价(每分钟单价)',
  `beyond_start_kilo` double(10,2) NOT NULL COMMENT '远途起算公里',
  `beyond_per_kilo_price` double(10,2) NOT NULL COMMENT '远途单价',
  `night_start` time DEFAULT NULL COMMENT '夜间时间段开始',
  `night_end` time DEFAULT NULL COMMENT '夜间时间段结束',
  `night_per_kilo_price` double(10,2) DEFAULT NULL COMMENT '夜间超公里加收单价',
  `night_per_minute_price` double(10,2) DEFAULT NULL COMMENT '夜间超时间加收单价',
  `effective_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' ON UPDATE CURRENT_TIMESTAMP COMMENT '生效时间',
  `active_status` int(11) NOT NULL DEFAULT '0' COMMENT '生效状态 0已失效 1未失效',
  `is_unuse` int(11) NOT NULL DEFAULT '0' COMMENT '是否不可用 0可用 1不可用',
  `creator_id` int(10) NOT NULL DEFAULT '0' COMMENT '创建人id',
  `operator_id` int(10) NOT NULL DEFAULT '0' COMMENT '操作人id',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'create_time',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'update_time',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='计费规则表';

# 8.计费规则时间表
DROP TABLE IF EXISTS `service_order_charge_rule_detail`;
CREATE TABLE `service_order_charge_rule_detail` (
                                          `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
                                          `rule_id` int(10) NOT NULL,
                                          `start` int(2) NOT NULL COMMENT '时间段开始',
                                          `end` int(2) NOT NULL COMMENT '时间段结束',
                                          `per_kilo_price` double(10,2) NOT NULL COMMENT '超公里单价',
  `per_minute_price` double(10,2) NOT NULL COMMENT '超时间单价',
  `is_delete` int(10) NOT NULL DEFAULT '0' COMMENT '是否删除 0未删除 1已删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='计费规则时间段表';

# 9.乘客信息表
DROP TABLE IF EXISTS `service_order_passenger_info`;
CREATE TABLE `service_order_passenger_info` (
  `id` int(16) NOT NULL AUTO_INCREMENT,
  `phone` varchar(64) NOT NULL DEFAULT '' COMMENT '电话',
  `educatioan` varchar(255) DEFAULT '' COMMENT '学历',
  `birthday` date DEFAULT NULL COMMENT '生日',
  `passenger_name` varchar(16) NOT NULL DEFAULT '' COMMENT '乘客名称',
  `register_time` datetime DEFAULT NULL COMMENT '注册时间',
  `balance` decimal(10,2) unsigned NOT NULL DEFAULT '0.00' COMMENT '余额',
  `gender` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '0：女，1：男',
  `head_img` varchar(256) NOT NULL DEFAULT '' COMMENT '头像',
  `passenger_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '用户类型，1：个人用户，2：企业用户',
  `user_level` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '会员等级',
  `register_type` smallint(6) unsigned NOT NULL DEFAULT '0' COMMENT '注册渠道 1 安卓 2 ios',
  `last_login_time` datetime DEFAULT NULL COMMENT '最后一次登录时间',
  `last_login_method` tinyint(3) unsigned NOT NULL DEFAULT '0' COMMENT '上次登陆方式:1,验证码,2密码',
  `last_login_screen_time` datetime DEFAULT NULL COMMENT '上次登录大屏时间',
  `last_login_screen_method` char(1) NOT NULL DEFAULT '' COMMENT '上次登录大屏方式',
  `last_order_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '最后一次下单时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

# 10.订单计费规则镜像表
DROP TABLE IF EXISTS `service_order_rule_mirror`;
CREATE TABLE `service_order_rule_mirror` (
 `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
 `order_id` int(10) NOT NULL COMMENT '订单_id',
 `rule_id` int(10) NOT NULL COMMENT '计价规则id',
 `rule` text NOT NULL COMMENT '规则镜像的json',
 `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
 `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 COMMENT='订单计费规则镜像表';
