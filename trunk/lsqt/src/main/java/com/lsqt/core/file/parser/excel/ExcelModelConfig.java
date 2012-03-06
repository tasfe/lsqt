package com.lsqt.core.file.parser.excel;

public class ExcelModelConfig {
	/**数据块编号**/
	private String dataBlockId;
	/**数据块所存在的sheet名称表里**/
	private String sheetName ;
	/**需要导入的表名**/
	private String tableName;
	/**EXCEL列映射**/
	private String[] colMappings;
	/**单元格映射**/
	private String[] cellMappings;
	/**数据导入时,格试化数据格试,如日期格试,小数点格试等**/
	private String[] formats;
	/**数据导入后,返回数据的配置(数据导入后,跟据批注配置信息从EXCEL内抓取出来的数据.)**/
	private String[] returnData;
	
	
	public String[] getReturnData() {
		return returnData;
	}
	public void setReturnData(String[] returnData) {
		this.returnData = returnData;
	}
	public String[] getFormats() {
		return formats;
	}
	public void setFormats(String[] formats) {
		this.formats = formats;
	}

	
	
	public String getDataBlockId() {
		return dataBlockId;
	}
	public void setDataBlockId(String dataBlockId) {
		this.dataBlockId = dataBlockId;
	}
	public String[] getColMappings() {
		return colMappings;
	}
	public void setColMappings(String[] colMappings) {
		this.colMappings = colMappings;
	}
	public String[] getCellMappings() {
		return cellMappings;
	}
	public void setCellMappings(String[] cellMappings) {
		this.cellMappings = cellMappings;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
}
