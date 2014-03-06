CREATE TABLE `sys_type_key` (
  `typeId` bigint(20) NOT NULL,
  `typeKey` varchar(64) NOT NULL,
  `typeName` varchar(128) DEFAULT NULL,
  `flag` bigint(20) DEFAULT NULL COMMENT '1：系统默认分类(不能删除)，2：自定义添加的分类 ，3: 启用 4：禁用 ',
  `sn` int(11) DEFAULT NULL,
  `type` int(11) DEFAULT NULL COMMENT '0=平铺结构\n            1=树型结构',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统分类键';

CREATE TABLE `sys_gl_type` (
  `typeId` bigint(20) NOT NULL,
  `typeName` varchar(128) NOT NULL COMMENT '分类名称',
  `nodePath` varchar(200) DEFAULT NULL COMMENT '路径',
  `depth` int(11) NOT NULL COMMENT '层次',
  `parentId` bigint(20) DEFAULT NULL COMMENT '父节点',
  `catKey` varchar(64) DEFAULT NULL COMMENT '分类Key',
  `nodeKey` varchar(64) NOT NULL COMMENT '节点的分类Key',
  `sn` bigint(20) NOT NULL COMMENT '序号',
  `type` int(11) DEFAULT NULL COMMENT '类型\n            (0平铺\n            ,1树形)',
  `isLeaf` smallint(6) DEFAULT NULL COMMENT '1=是\n 0=否',
  `nodeCode` varchar(10) DEFAULT NULL COMMENT '子编码',
  `nodeCodeType` smallint(6) DEFAULT NULL COMMENT '子编码类型：0手工录入，1自动生成',
  PRIMARY KEY (`typeId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='总分类表\n用于显示树层次结构的分类\n可以允许任何层次结构';


CREATE TABLE `sys_dic` (
  `dicId` bigint(20) NOT NULL,
  `typeId` bigint(20) DEFAULT NULL COMMENT '分类ID',
  `itemKey` varchar(64) DEFAULT NULL COMMENT '项Key',
  `itemName` varchar(64) NOT NULL COMMENT '项名',
  `itemValue` varchar(500) NOT NULL COMMENT '项值',
  `descp` varchar(512) DEFAULT NULL COMMENT '描述',
  `sn` bigint(20) DEFAULT NULL COMMENT '序号',
  `nodePath` varchar(100) DEFAULT NULL,
  `parentId` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`dicId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='数据字典';

-- example:
select C.typeId,C.typeName, B.catKey, B.typeName,A.itemKey,A.itemName,A.itemValue,A.dicId,A.parentId,A.nodePath,A.dicId from sys_dic A 
	inner join sys_gl_type B on A.typeId=B.typeId 
	inner join sys_type_key C on C.typeKey=B.catKey;
