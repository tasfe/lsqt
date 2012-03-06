package com.lsqt.core.file.parser.excel;

import java.io.File;
import java.util.List;

/**
 * 
 * <pre>
 * 业务名: 供第三方应用调用，按照规范的excel单元格批注，解析excel文件件，导入数据到数据库
 * 功能说明: 导入带批注的excel文件数据到数据库
 * 编写日期:	2011-4-21
 * 作者:	袁明敏
 * 
 * 历史记录
 * 1、修改日期：2011-4-21
 *    修改人：袁明敏
 *    修改内容：
 * </pre>
 */
public interface IExcelDataImport {
	
	public static final String EXCEL_DATABLOCK_END_WITH_NATRUE="自然结束";
	
	public static final String EXCEL_DATABLOCK_END_WITH_ROWS_PREFIX="读取";
	public static final String EXCEL_DATABLOCK_END_WITH_ROWS_SUFFIX="行";
	
	public static final String EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_PREFIX=":$$:";
	public static final String EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_MID="配置内容:";
	
	public static final String EXCEL_DATABLOCK_RETURN_DATA_SINGLE_VALUE="single";
	
	/**
	 * 
	 * 方法说明：导入EXCEL模板数据,返回是否成功(不返回EXCEL文件内的数据对象)
	 *
	 * @param file EXCEL模板文件对象
	 * @return 导入成功返回真
	 */
	public boolean importTemplateData(File file) throws ExcelException;
	
	/**
	 * 方法说明：导入EXCEL模板数据前,返回约束字段值
	 * @param file EXCEL模板文件对象
	 * @param isGetExcelData 是否返回EXCEL文件内的数据
	 * @return 
	 * @throws ExcelException
	 */
	List<Object> getExcelReturnData(File file) throws ExcelException;
	
}
