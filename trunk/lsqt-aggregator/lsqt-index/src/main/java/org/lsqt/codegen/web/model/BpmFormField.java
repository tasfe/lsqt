package org.lsqt.codegen.web.model;

import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Table;


/**
 * 对象功能:bpm_form_field Model对象
 * 开发公司:前海股权交易中心
 * 开发人员:Sky
 * 创建时间:2014-02-28
 */
@Table(name="bpm_form_field",schema="oaonsite")
public class BpmFormField {
	
	@Id(name="fieldId")
	private Long fieldId;
	
	@Column(name="tableId")
	private Long tableId;
  
	@Column(name="fieldName")
	private String fieldName;
	
	@Column(name="fieldType")
	private String fieldType;
	
	@Column(name="isRequired")
	private Boolean isRequired; //是否必填
	
	@Column(name="isList")
	private Boolean isList; //是否列表显示
	
	@Column(name="isQuery")
	private Boolean isQuery ; //是否查询显示
	
	@Column(name="fieldDesc")
	private String fieldDesc; //字段说明
	
	@Column(name="charLen")
	private Integer charLen ; //字符长度
	
	@Column(name="intLen")
	private Integer intLen; //整数长度
	
	@Column(name="decimalLen")
	private Integer decimalLen ;//小数长度
	
	@Column(name="dictType")
	private String dictType; //字典类别
	
	@Column(name="isDeleted")
	private Boolean isDeleted ;//是否已删除
	
	@Column(name="validRule")
	private String validRule; //验证规则
	
	@Column(name="sn")
	private Integer sn; //排序
	
	@Column(name="valueFrom")
	private Integer valueFrom ; //值来源\n  0 - 表单\n   1 - 脚本
	
	@Column(name="script")
	private String script ; //默认脚本
	
	@Column(name="controlType")
	private Integer controlType ;	//0,无特殊控件\n   1,数据字典\n  2,用户选择器\n 3.组织选择器\n   4.岗位选择器  5.自定义选择器id
	
	@Column(name="isHidden")
	private Boolean isHidden ; // 是否隐藏
	
	@Column(name="options")
	private String options; //选项，（每行一个）
	
	@Column(name="ctlProperty")
	private String ctlProperty ; //控件属性json
	
	@Column(name="dialogId")
	private Long dialogId ; // 选择器id

	public Long getFieldId() {
		return fieldId;
	}

	public Long getTableId() {
		return tableId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldType() {
		return fieldType;
	}

	public Boolean getIsRequired() {
		return isRequired;
	}

	public Boolean getIsList() {
		return isList;
	}

	public Boolean getIsQuery() {
		return isQuery;
	}

	public String getFieldDesc() {
		return fieldDesc;
	}

	public Integer getCharLen() {
		return charLen;
	}

	public Integer getIntLen() {
		return intLen;
	}

	public Integer getDecimalLen() {
		return decimalLen;
	}

	public String getDictType() {
		return dictType;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public String getValidRule() {
		return validRule;
	}

	public Integer getSn() {
		return sn;
	}

	public Integer getValueFrom() {
		return valueFrom;
	}

	public String getScript() {
		return script;
	}

	public Integer getControlType() {
		return controlType;
	}

	public Boolean getIsHidden() {
		return isHidden;
	}

	public String getOptions() {
		return options;
	}

	public String getCtlProperty() {
		return ctlProperty;
	}

	public Long getDialogId() {
		return dialogId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}

	public void setIsRequired(Boolean isRequired) {
		this.isRequired = isRequired;
	}

	public void setIsList(Boolean isList) {
		this.isList = isList;
	}

	public void setIsQuery(Boolean isQuery) {
		this.isQuery = isQuery;
	}

	public void setFieldDesc(String fieldDesc) {
		this.fieldDesc = fieldDesc;
	}

	public void setCharLen(Integer charLen) {
		this.charLen = charLen;
	}

	public void setIntLen(Integer intLen) {
		this.intLen = intLen;
	}

	public void setDecimalLen(Integer decimalLen) {
		this.decimalLen = decimalLen;
	}

	public void setDictType(String dictType) {
		this.dictType = dictType;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setValidRule(String validRule) {
		this.validRule = validRule;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public void setValueFrom(Integer valueFrom) {
		this.valueFrom = valueFrom;
	}

	public void setScript(String script) {
		this.script = script;
	}

	public void setControlType(Integer controlType) {
		this.controlType = controlType;
	}

	public void setIsHidden(Boolean isHidden) {
		this.isHidden = isHidden;
	}

	public void setOptions(String options) {
		this.options = options;
	}

	public void setCtlProperty(String ctlProperty) {
		this.ctlProperty = ctlProperty;
	}

	public void setDialogId(Long dialogId) {
		this.dialogId = dialogId;
	}
}