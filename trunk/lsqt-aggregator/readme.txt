
lsqt-bpm:		spring3.0.3 + activiti5.10 + jsp

lsqt-forum: 	spring3.0.3 + mybatis3.1.1 + jsp + ehcache

lsqt-content: 	spring3.0.3 + wicket+hibernate3.x

lsqt-uum:		spring3.0.3 + dbutil + jsp

lsqt-rule:		spring3.0.3 + dbutil + jsp + drools5

lsqt-shopping: 	spring3.0.3 + dbutil + velocity

lsqt-pm:		spring3.0.3	+ dbutil + freemarker

lsqt-report: 	spring3.0.3 + dbutil + jsp + (fushionchart+openflashchart)

lsqt-search: 	spring3.0.3 + Lucene3.5 + jsp  需考虑权限与分页查询

lsqt-doc:		spring3.0.3 + dbutil + Lucene3.5 + jsp + 阿里文件服务器管理文档 （并参考一度文档管理）
	支持多维度、多维度的权限

lsqt-etl:		spring3.0.3 + dbutil + jsp + quartz 简单的抽取、转化、清洗 （复杂的需结合kattle工具做）

codegen(在线版): freemarker + 自定义表 + 规则(drools/groovy) + easyUI 界面 	
codegen(线下版):	freemarker/velocity 生成  dbutil+springmvc+jsp 结构

分布式环境下：dbutils 执行一系列事物性的SQL语句

