# 乘客用户表
CREATE TABLE `service_passenger_user_info` (
                                               `id` bigint(16) NOT NULL AUTO_INCREMENT,
                                               `passenger_phone` varchar(16) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '电话',
                                               `passenger_name` varchar(16) NOT NULL DEFAULT '' COMMENT '乘客名称',
                                               `passenger_gender` tinyint(2) unsigned NOT NULL DEFAULT '1' COMMENT '0：女，1：男',
                                               `user_state` tinyint(6) ,
                                               `passenger_type` tinyint(3) unsigned NOT NULL DEFAULT '1' COMMENT '用户类型，1：个人用户，2：企业用户',
                                               `register_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
                                               `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                               `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
                                               PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;