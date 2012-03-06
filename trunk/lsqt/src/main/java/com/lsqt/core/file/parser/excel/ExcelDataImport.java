package com.lsqt.core.file.parser.excel;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


/**
 * 
 * **/
public class ExcelDataImport implements IExcelDataImport{
	
	/**
	 * 方法说明：判断EXCEL模板文件的数据块信息是否满足应有的合法的json规范
	 * @param configStr
	 * @return
	 */
	public static boolean isExcelJsonConfig(String configStr){
		//1.判断是否有开头标识
		//2.判断是否有配置内容
		//3.处理成纯JSON数据格式
		if(configStr == null){
			return false;
		}
		if("".equals(configStr)){
			return false;
		}
		
		if(configStr.indexOf(IExcelDataImport.EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_PREFIX)==-1){
			return false;
		}
		
		if(configStr.indexOf(IExcelDataImport.EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_MID)==-1){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 处理成纯JSON数据格式
	 * @param configStr EXCEL模板内的EXCEL配置信息
	 * @return
	 */
	public static String prepareExcelJsonConfig(String configStr){
		int jsonIndex=configStr.lastIndexOf(IExcelDataImport.EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_MID);
		configStr=configStr.substring(jsonIndex, configStr.length());
		
		if(configStr.startsWith(IExcelDataImport.EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_MID)){
			configStr=configStr.substring( IExcelDataImport.EXCEL_DATABLOCK_TEMPLATE_CONFIG_FLAG_MID.length(), configStr.length());
		}
		return configStr;
	}
	
	
	/**
	 * 方法说明：跟据EXCEL模板文件，获取模板文件的数据块配置信息
	 * @param file Excel文件对象引用
	 * @return 获取多个数据块的JSON配置信息
	 * @throws ExcelException 
	 * @throws Exception 
	 */
	public static String[] getExcelJsonConfigs(File file) throws ExcelException {
		List<String> list=new ArrayList<String>();
		try {
			Workbook wb=ExcelHelper.getWorkbook(file);
			int cnt=wb.getNumberOfSheets();
			for(int i=0;i<cnt;i++){
				Sheet sheet=wb.getSheetAt(i);
				for (Row row : sheet) {
			        for (Cell cell : row) {
			        	org.apache.poi.ss.usermodel.Comment  comment=cell.getCellComment() ;
			        	if(comment!=null){
			        		String content=comment.getString().getString();
			        		if(isExcelJsonConfig(content)){
			        			list.add(prepareExcelJsonConfig(content));
			        			
			        			System.out.println(prepareExcelJsonConfig(content));
			        		}
			        	}
			        }
			 }
			}
		} catch (Exception e) {
			throw new ExcelException("Excel文件损坏或批注信息错误!");
		}
		return list.toArray(new String[list.size()]);
	}
	
	/**
	 * @param configs 跟据Excel文件模板批注配置信息转成bean的表示
	 * @return 返回多个数据块模型对象
	 * @throws ExcelException  EXCEL模板配置信息异常
	 */
	public static List<ExcelModelConfig> getExcelModelConfigs(String[] configs) throws ExcelException{
		
		List<ExcelModelConfig> list=new ArrayList<ExcelModelConfig>();
		
		ObjectMapper objectMapper = new ObjectMapper();
		for(String json: configs){
			try {
				ExcelModelConfig ee = objectMapper.readValue(json,ExcelModelConfig.class);
				list.add(ee);
			} catch (Exception e) {
				
				e.printStackTrace();
				throw new ExcelException("EXCEL模板配置信息解析出错!");
			} 
		}
		return list;
	}
	
	/**
	 * 处理并过滤列映射
	 * @return
	 */
	private static String[]  filterMapping(String[] sourceMapping){
		final String leftTag="{";
		final String rightTag="}";
		
		List<String> list=new ArrayList<String>();
		
		for(String e: sourceMapping){
			if(! (leftTag.equals(e) || rightTag.equals(e)) ){
				list.add(e);
			}
			
		}
		String [] arrayStr=new String[list.size()];
		list.toArray(arrayStr);
		return arrayStr;
	}
	

	/**
	 * 方法说明：跟据EXCEL数据块配置信息，预处理insert的SQL语句
	 * @param e
	 * @return
	 */
	public static String prepareSQL(ExcelModelConfig e) {
		StringBuffer sql=new StringBuffer();
		sql.append("insert into "+e.getTableName()+" (") ;
		
		StringBuffer columnFiledDb=new StringBuffer();
		StringBuffer columnValueDb=new StringBuffer();
		
		
		
		//第一步：处理列映射字段SQL
		String[] colMappings=e.getColMappings();
		colMappings=filterMapping(colMappings);
		
		List<String> list=new ArrayList<String>();
		for(int t=0;t<colMappings.length;t++ ){
			if(t%2==0){
				list.add(colMappings[t]);
			}
		}
		
		
		
		//第二步：处理单元格映射字段SQL
		String [] cellMapping=filterMapping(e.getCellMappings());
		for(int t=0;t<cellMapping.length;t++ ){
			if(t%2==0){
				list.add(cellMapping[t]);
			}
		}
		String [] colMap=new String [list.size()];
		list.toArray(colMap);
		
		for(int i=0;i<colMap.length;i++){
			columnFiledDb.append(" "+colMap[i]);
			columnValueDb.append(" ?");
			
			if(colMap.length-1!=i){
				columnFiledDb.append(", ");
				columnValueDb.append(", ");
			}
		}
		
		sql.append(columnFiledDb);
		sql.append(" ) values (" );
		sql.append(columnValueDb);
		sql.append(" ) ");
		return sql.toString();
	}
	
	/**
	 * 方法说明: 跟据Excel批注信息,直导数据入库
	 * @param file EXCEl文件引用
	 * @return boolean 导入成功返回真
	 */
	public boolean importTemplateData(File file) throws ExcelException{
		boolean isImportOk=false;
		
		List<List<Object>> dataTable=new ArrayList<List<Object>>();
		
		Workbook wb=ExcelHelper.getWorkbook(file);
		
		String [] configs=getExcelJsonConfigs(file);
		List<ExcelModelConfig> list= getExcelModelConfigs(configs);
		for(int t=0;t<list.size();t++){
			//第一步：先处理列映射数据
			//第二步：再处理单元格映射数据 (因组装SQL时是按该顺序)
			String sql=prepareSQL(list.get(t));
			System.out.println(sql);
			
			
			//第三步：跟据列映射、单元格映射配置获取EXCEL单元格值
			String[] colMappings=list.get(t).getColMappings();
			colMappings=filterMapping(colMappings);
			List<String> colMapLables=new ArrayList<String>();
			List<String> colFiled=new ArrayList<String>();
			for(int i=0;i<colMappings.length;i++ ){
				if(i%2==1){
					colMapLables.add(colMappings[i]);  // 列头坐标
				}else{ 
					colFiled.add(colMappings[i]); // 列头字段
				}
			}
			if(colMapLables.size()!= colFiled.size()){
				throw new ExcelException("列映射缺少坐标或数据库字段映射!");
			}
			
			
			Sheet sheet=wb.getSheet(list.get(t).getSheetName());
			for(int n=0;n<colMapLables.size();n++){
				if(colMapLables.get(n).indexOf("-")>0){ //处理合并的列数据
					
					
				}else{
					List<Object> colData=getColMappingData(sheet, colMapLables.get(n));
					colData=formatColData(colData,colFiled.get(n),list.get(t));
					dataTable.add(colData);  //列数据表格已建立
				}
			}
			
			//单元格数据转列数据，组装dataTable
			String [] cellMappings=list.get(t).getCellMappings();
			System.out.println(cellMappings);
			cellMappings=filterMapping(cellMappings);
			
			List<List<Object>> cellDataTable=buildDataCellMappingToColMapping(dataTable,cellMappings,sheet,list.get(t));
			
			
			//第四步：转化数据为二维数组表格
			for(List<Object> e: cellDataTable){
				dataTable.add(e);
			}
			
			
			//二维结果集，行转列
			int rowIndex=0;
			List<List<Object>> data=new ArrayList<List<Object>>();
			for(int p=0;p<dataTable.size();p++){
				if(dataTable.get(0).size()>0 && rowIndex!=dataTable.get(0).size()-1){
					List<Object> myRowEles=new ArrayList<Object>();
					for(int i=0;i<dataTable.size();i++){
						Object ele=dataTable.get(i).get(rowIndex);
						myRowEles.add(ele);
					}
					rowIndex++;
					data.add(myRowEles);
				}
			}
			
			Object [][] mydata=convert(data);
			String [][] mydataStirng=convertString(mydata);
			try {
				if(ExcelDBHelper.hasDataSource()){
					isImportOk=ExcelDBHelper.batch(sql, mydataStirng);
				}else{
					throw new ExcelException("EXCEL导入数据源没有找到,请检查数据源配置!");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
				throw new ExcelException("数据导入出错,请联系管理员检查EXCEL配置！"+e1.getMessage());
			}
			
		}
		return isImportOk;
	}
	
	/**
	 * 方法说明: 跟据Excel批注信息,直导数据入库,并返回
	 * @param file EXCEL文件引用
	 * @param isGetExcelData 是否获取EXCEL内数据
	 * @return 返回用户关心EXCEL文件内的数据(用户关心的excel数据,在批注内配置)
	 * @throws ExcelException
	 */
	public Object importTemplateData(File file,boolean isGetExcelData) throws ExcelException{
		List<Object> returnDatas = getExcelReturnData(file);
		
		
		if(isGetExcelData){
			importTemplateData(file);
			return returnDatas;
		}else{
			importTemplateData(file);
			return returnDatas;
		}
	}

	public List<Object> getExcelReturnData(File file) throws ExcelException {
		final String dateFormatFunctionLeftTag="date.format(";
		final String dateFormatFunctionRightTag=")";
		
		List<Object> returnDatas=new ArrayList<Object>();
		
		Workbook wb=ExcelHelper.getWorkbook(file);
		
		String [] configs=getExcelJsonConfigs(file);
		List<ExcelModelConfig> list= getExcelModelConfigs(configs);
		for(int t=0;t<list.size();t++){
			String [] returnDataConfig=filterMapping(list.get(t).getReturnData());
			
			
			Sheet sheet=wb.getSheet(list.get(t).getSheetName());
			
			for(int i=0;i<returnDataConfig.length;i++){
				if(i%2==0){
					if(IExcelDataImport.EXCEL_DATABLOCK_RETURN_DATA_SINGLE_VALUE.equalsIgnoreCase(returnDataConfig[i])){
						if( i-1<returnDataConfig.length ){
							String location=returnDataConfig[i+1];
							String[] excelLocation=location.replace(dateFormatFunctionLeftTag, "").replace(dateFormatFunctionRightTag,"").split(",");
							Object value=ExcelHelper.getCellValue(sheet, excelLocation[1]);
							SimpleDateFormat df=new SimpleDateFormat(excelLocation[0]);
							if(value instanceof Date){
								returnDatas.add(df.format((Date)value));
							}
							
						}
					}
				}
			}
		}
		return returnDatas;
	}
	
	
	/****
	 * 方法说明:格式化列数据
	 * @param colData 列数据
	 * @param colMappingLable 需要格式化的label列,值为: A1,B1,C1...
	 * @param model EXCEL配置模型
	 * @return 格式化后的列数据
	 * @throws ExcelException 
	 */
	private static List<Object>  formatColData(List<Object> colData,String colMappingLable,ExcelModelConfig model) throws ExcelException{
		final String dateFormatTag="date.format.";
		
		String[] dataFormats=model.getFormats();
		dataFormats=filterMapping(dataFormats);
		System.out.println(dataFormats);
		
		List<Object> list=new ArrayList<Object>();
		for(Object e: colData){
			
			for(int i=0;i<dataFormats.length;i++){
				String f=dataFormats[i];
				if(f!= null && colMappingLable != null){
					if (f.trim().equals(colMappingLable.trim())) {
						
						if(i-1<dataFormats.length){
							String fmt=dataFormats[i+1];
							
							if(fmt.startsWith(dateFormatTag)){ //日期格试化
								String apply=fmt.replace(dateFormatTag, "");
								SimpleDateFormat sdf=new SimpleDateFormat(apply);
								if(e!=null && e.toString().length()>0){
									try{
										
										if(e instanceof Date){
											String value=sdf.format(e);
											list.add(value);
										}else{
											list.add(e);
										}
										
									}catch(Exception ex){
										ex.printStackTrace();
										throw new ExcelException("日期格式转化错误");
									}
								}else{
									list.add("");
								}
							}
						}
					}
					
				}
			}
		}
		if(list.size()==0){
			return colData;
		}
		return list;
	}

	/**
	 * 将二维数据List表示转二维数组表示
	 * @param list
	 * @return
	 */
	private static Object [][] convert(List<List<Object>> list){
		List<Object []> rows=new ArrayList<Object[]>();
		
		for(int i=0;i<list.size();i++){
			rows.add(list.get(i).toArray());
		}
		Object [][] dt=new Object[list.size()][];
		rows.toArray(dt);
		return dt;
	}
	
	/**
	 * 方法说明:转化为字符数组对象
	 * @param data
	 * @return
	 */
	public static String [][] convertString(Object [][] data){
		String [][] mydata=null;
		if(data != null){
			mydata=new String[data.length][];
			for(int i=0;i<data.length;i++){
				Object [] row=data[i];
				String [] rowString=new String[row.length];
				
				for(int t=0;t<row.length;t++){
					if(row[t]!=null){
						rowString[t]=row[t].toString();
					}else{
						rowString[t]="";
					}
				}
				mydata[i]=rowString;
			}
		}
		return mydata;
	}
	
	/***
	 * 构建数据单元格映射转列映射，组装数据表格
	 * @param list
	 * @param cellMapLables 
	 * @param sheet 
	 * @param model 数据块模型
	 * @return
	 * @throws ExcelException
	 */
	public static List<List<Object>> buildDataCellMappingToColMapping(List<List<Object>> list,String [] cellMappings,Sheet sheet,ExcelModelConfig model) throws ExcelException{
		final String formValueTag="form.";
		final String excelValueTag="excel.";
		final String idValueTag="uuid.";
		
		List<List<Object>> dataTable=new ArrayList<List<Object>>();
		
		
		
		//获取最大长度的列
		List<Integer> colSizes=new ArrayList<Integer>();
		for(List<Object> e: list){
			colSizes.add(Integer.valueOf(e.size()));
		}
		Collections.sort(colSizes);
		int maxSize=-1;
		if(colSizes.size()>0){
			maxSize=colSizes.get(0);
		}
		
		//构建每个单元格映射的数据列
		List<String> mapingLables=new ArrayList<String>();
		List<String> mapingFileds=new ArrayList<String>();
		for(int i=0;i<cellMappings.length;i++ ){
			if(i%2==1){
				mapingLables.add(cellMappings[i]);
			}else{
				mapingFileds.add(cellMappings[i]);
			}
		}
		if(mapingLables.size()!=mapingFileds.size()){
			throw new ExcelException("单元格映射配置错误");
		}
		
		for(int i=0;i<mapingLables.size();i++){
			List<Object> convertedColValues=new ArrayList<Object>();
			String label=mapingLables.get(i);
			
			if(label.startsWith(formValueTag)){
				
			}else if(label.startsWith(excelValueTag)){
				//注：合并单元格的数据为左上角单元格坐标，在此不做跨度解析
				for(int t=0;t<maxSize;t++){
					convertedColValues.add(ExcelHelper.getCellValue(sheet,label.substring(excelValueTag.length(),label.length())));
					convertedColValues=formatColData(convertedColValues,mapingFileds.get(i),model);
				}
				dataTable.add(convertedColValues);
			}else if(label.startsWith(idValueTag)){
				
				for(int t=0;t<maxSize;t++){
					 UUID uuid = UUID.randomUUID();
					 convertedColValues.add(uuid.toString().replace("-", ""));
				}
				dataTable.add(convertedColValues);
			}else{
				throw new ExcelException("Excel配置信息解析错误，单元映射格解析错误！");
			}
		}
		
		return dataTable;
	}

	/**
	 * 得到列映射的数据
	 * @param sheet
	 * @param location
	 * @return
	 */
	public static List<Object> getColMappingData(Sheet sheet,String location){
		List<Object> list=new ArrayList<Object>();
		
		int fixCol=ExcelHelper.getCol(location);
		int beginRow=ExcelHelper.getRow(location);
		
		for(int i=beginRow;i<sheet.getLastRowNum();i++){
			Cell cell=sheet.getRow(i).getCell(fixCol);
			Object cellValue=ExcelHelper.getCellValue(cell);
			
			System.out.println(cellValue);
			
			list.add(cellValue);
		}
		
		
		return list;
	}
}
