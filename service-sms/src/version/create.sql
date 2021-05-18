

-- ----------------------------
-- Table structure for tbl_sms_template
-- ----------------------------
-- 短信发送模板
DROP TABLE IF EXISTS `service_sms_template`;
CREATE TABLE `tbl_sms_template` (
                                    `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
                                    `template_id` varchar(16) NOT NULL DEFAULT '' COMMENT '短信模板ID',
                                    `template_name` varchar(128) DEFAULT NULL,
                                    `content` varchar(512) NOT NULL DEFAULT '' COMMENT '模板内容',
                                    `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
                                    `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                    `template_type` tinyint(1) NOT NULL DEFAULT '3' COMMENT '模板类型（1：营销；2：通知；3：订单）',
                                    PRIMARY KEY (`id`),
                                    UNIQUE KEY `TEMPLATE_ID` (`template_id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

-- ----------------------------
-- Records of tbl_sms_template
-- ----------------------------
INSERT INTO `tbl_sms_template` VALUES ('5', 'SMS_144145499', '登录验证码', '手机验证码：${code}，10分钟内有效。如非本人操作，请忽略。', '2018-09-07 15:00:11', '2018-09-12 13:24:14', '3');

-- 短信发送记录表
create table service_sms_record
(
    id           int auto_increment
        primary key,
    phone_number varchar(16)  default ''                not null comment '乘客手机号',
    sms_content  varchar(512) default ''                not null comment '短信内容',
    send_time    datetime                               not null comment '发送时间',
    operator     varchar(255) default ''                not null comment '操作人',
    send_flag    tinyint(1)                             not null comment '发送状态 0:失败  1: 成功',
    send_number  int                                    null,
    create_time  timestamp    default CURRENT_TIMESTAMP null,
    update_time  timestamp    default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP
) ENGINE=InnoDB AUTO_INCREMENT=1 charset = utf8mb4;
