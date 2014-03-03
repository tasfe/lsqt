package org.lsqt.components.dao;

import java.util.Date;

import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Main;
import org.lsqt.components.dao.dbutils.annotation.Table;

@Table(name="bpm_form_table",schema="oaonsite")
public class BpmFormTable {
	
	@Id(name="tableId")
	private Long tableId;
	
	@Column(name="tableName")
	private String tableName;
	
	@Column(name="entityName")
	private String entityName; //表对映的实体类名.包的全径，如：com.qhee.oa.SysUser
	
	@Column(name="tableDesc")
	private String tableDesc ;
	
	@Column(name="versionNo")
	private String versionNo;
	
	@Column(name="isPublished")
	private Boolean isPublished; //是否已发布\n  1=已发布\n  0=未发布
	
	@Column(name="isGen")
	private Boolean isGen; //是否生成物理表\n  1=已生成\n  0=未生成
	
	@Column(name="publishedBy")
	private String publishedBy; //发布用户的名字
	
	@Column(name="publishTime")
	private Date publishTime;
	
	@Column(name="dsId")  //当前表所属的数据源
	private Long dsId;
	
	//当前表属于的数据源
	@Main(fk="dsId")
	private SysDataSource sysDataSource;
	
	@Column(name="dsAlias") //当前表所属的数据源别名
	private String dsAlias;
	
	@Column(name="isDbView") //是否是视图
	private Boolean isDbView;
	
	@Column(name="dbViewSql") //isDbView=true ，视图的SQL字串
	private String dbViewSql;
	
	@Column(name="isGenInDb") // 是否生成到数据庘
	private Boolean isGenInDb;
	
	public Long getTableId() {
		return tableId;
	}

	public String getTableName() {
		return tableName;
	}

	public String getTableDesc() {
		return tableDesc;
	}

	public String getVersionNo() {
		return versionNo;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public Boolean getIsGen() {
		return isGen;
	}

	public String getPublishedBy() {
		return publishedBy;
	}

	public Date getPublishTime() {
		return publishTime;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableDesc(String tableDesc) {
		this.tableDesc = tableDesc;
	}

	public void setVersionNo(String versionNo) {
		this.versionNo = versionNo;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public void setIsGen(Boolean isGen) {
		this.isGen = isGen;
	}

	public void setPublishedBy(String publishedBy) {
		this.publishedBy = publishedBy;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public Long getDsId() {
		return dsId;
	}

	public String getDsAlias() {
		return dsAlias;
	}

	public Boolean getIsDbView() {
		return isDbView;
	}

	public String getDbViewSql() {
		return dbViewSql;
	}

	public Boolean getIsGenInDb() {
		return isGenInDb;
	}

	public void setDsId(Long dsId) {
		this.dsId = dsId;
	}

	public void setDsAlias(String dsAlias) {
		this.dsAlias = dsAlias;
	}

	public void setIsDbView(Boolean isDbView) {
		this.isDbView = isDbView;
	}

	public void setDbViewSql(String dbViewSql) {
		this.dbViewSql = dbViewSql;
	}

	public void setIsGenInDb(Boolean isGenInDb) {
		this.isGenInDb = isGenInDb;
	}

	public SysDataSource getSysDataSource() {
		return sysDataSource;
	}

	public void setSysDataSource(SysDataSource sysDataSource) {
		this.sysDataSource = sysDataSource;
	}
}
