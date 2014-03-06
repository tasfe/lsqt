-- 事实表：数据源定义
CREATE TABLE `sys_datasource` (
  `ID` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(50) DEFAULT NULL COMMENT '数据源名称',
  `alias` varchar(20) DEFAULT NULL COMMENT '别名(唯一)',
  `driverName` varchar(100) DEFAULT NULL COMMENT '驱动名称',
  `url` varchar(100) DEFAULT NULL COMMENT '数据库URL',
  `userName` varchar(20) DEFAULT NULL COMMENT '用户名',
  `password` varchar(20) DEFAULT NULL COMMENT '密码',
  `dbType` varchar(20) DEFAULT NULL COMMENT '数据库类型：数据字典维护',
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='系统数据源管理';

-- 事实表：数据库表（视图）定义
CREATE TABLE `bpm_form_table` (
  `tableId` bigint(20) NOT NULL,
  `tableName` varchar(128) NOT NULL COMMENT '表名',
  `entityName` varchar(128) COMMENT '映射的pojo实体类名',
  `dsId` bigint(20) NOT NULL COMMENT '(新)',
  `dsAlias` varchar(128) not null COMMENT '所属数据源的别名'
  `isDbView`  smallint(6)  DEFAULT 0 COMMENT '(新)是否是视图\n  1=视图\n  0=表\n',
  `dbViewSql`  varchar(2000) DEFAULT NULL COMMENT '(新)视图定义的sql语句',
  `tableDesc` varchar(128) DEFAULT NULL COMMENT '描述',
  `versionNo` int(11) DEFAULT NULL COMMENT '版本号',
  `isPublished` smallint(6) DEFAULT NULL COMMENT '是否已发布\n  1=已发布\n  0=未发布,草稿状态',
  `isGenInDb` smallint(6) DEFAULT NULL COMMENT '(新)是否生成物理表或视图\n  1=已生成\n  0=未生成',
  `publishedBy` varchar(100) DEFAULT NULL COMMENT '发布人',
  `publishTime` datetime DEFAULT NULL COMMENT '发布时间',
  PRIMARY KEY (`tableId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- 表的字段定义（表与字段的一对多关系）
CREATE TABLE `bpm_form_field` (
  `fieldId` bigint(20) NOT NULL,
  `tableId` bigint(20) DEFAULT NULL COMMENT '所属表ID',
  `fieldName` varchar(128) NOT NULL COMMENT '字段名称',
  `fieldType` varchar(128) NOT NULL COMMENT '字段类型，数字：number 字符：varchar 日期：date',
  `isRequired` smallint(6) DEFAULT NULL COMMENT '是否必填',
  `isList` smallint(6) DEFAULT '1' COMMENT '是否列表显示',
  `isQuery` smallint(6) DEFAULT '1' COMMENT '是否查询显示',
  `fieldDesc` varchar(128) DEFAULT NULL COMMENT '字段说明',
  `charLen` bigint(20) DEFAULT NULL COMMENT '字符长度',
  `intLen` bigint(20) DEFAULT NULL COMMENT '整数长度',
  `decimalLen` bigint(20) DEFAULT NULL COMMENT '小数长度',
  `dictType` varchar(100) DEFAULT NULL COMMENT '数据字典类别',
  `isDeleted` smallint(6) DEFAULT NULL COMMENT '是否删除\n            1=删除\n            0=未删除',
  `validRule` varchar(64) DEFAULT NULL COMMENT '验证规则',
  -- `originalName` varchar(128) DEFAULT NULL COMMENT '字段原名',
  `sn` int(11) DEFAULT NULL COMMENT '排列顺序',
  `valueFrom` smallint(6) DEFAULT NULL COMMENT '值来源\n            0 - 表单\n            1 - 脚本',
  `script` varchar(1000) DEFAULT NULL COMMENT '默认脚本',
  `controlType` smallint(6) DEFAULT NULL COMMENT '0,无特殊控件\n   1,数据字典\n  2,用户选择器\n 3.组织选择器\n   4.岗位选择器  5.自定义选择器id',
  `isHidden` smallint(6) DEFAULT NULL,
 -- `isFlowVar` smallint(6) DEFAULT NULL,
 -- `serialnum` varchar(20) DEFAULT NULL,
  `options` varchar(1000) DEFAULT NULL COMMENT '选项',
  `ctlProperty` varchar(2000) DEFAULT NULL COMMENT '控件属性',
  `dialogId` bigint(20) DEFAULT NULL COMMENT '外键显示的列表配置id,该 配置存储在bpm_form_dialog表中(一对一的关系)',
  PRIMARY KEY (`fieldId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='表单字段';