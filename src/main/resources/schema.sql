CREATE TABLE `user` (
                        `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
                        `name` varchar(50) NOT NULL COMMENT '名字',
                        `birthday` datetime(3) DEFAULT NULL COMMENT '生日',
                        `active_level` bit(1) DEFAULT NULL COMMENT '是否激活',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='用户';
