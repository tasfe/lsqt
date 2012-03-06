package com.lsqt.core.file.parser.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public final class ExcelHelper {
	
	/**
	 * 方法说明：获取POI工作薄对象
	 * 
	 * @param file 文件对象引用
	 * @return 返回POI工作薄对象
	 * @throws ExcelException EXCEL文件不存在或损坏异常
	 */
	public static Workbook getWorkbook(File file) throws ExcelException{
		
		Workbook wb=null;
		try{
			if(file.exists() &&  file.isFile()){
				String fullPath=file.getAbsolutePath();
				
				if(fullPath.endsWith(".xls") ){
					FileInputStream fileIn = new FileInputStream(file);
					wb = new HSSFWorkbook(fileIn);
					
					
				}else if(fullPath.endsWith(".xlsx")){
					FileInputStream fileIn = new FileInputStream(file);
					wb = new XSSFWorkbook(fileIn);
	
				}else{
					throw new ExcelException("ERROR: 不是EXCEL文件!");
				}
				
			}else{
				throw new ExcelException("ERROR: 没有找到文件!");
			}
		}catch(Exception e){
			throw new ExcelException("没有找到EXCEL文件或不是EXCEL文件！");
		}
		return wb;
	}
	
	/**
	 * 通过EXCEL单元格字面坐标，获取单元格的值
	 * @param sheet 工作表对象
	 * @param location 字面坐标，如：A3
	 * @return 单元格内容
	 */
	public static Object getCellValue(Sheet sheet,String location){
		CellReference ref=new CellReference(location);
		return getCellValue(sheet, ref.getCol(), ref.getRow());
	}
	
	/**
	 * 跟据sheet表对象，单元格的列、行坐标获取单元格内容
	 * @param sheet 工作表对象
	 * @param col 列索引
	 * @param row 行索引
	 * @return 单元格内容
	 */
	public static Object getCellValue(Sheet sheet,int col,int row){
		Row   r   = sheet.getRow(row); 
		Cell  cell = r.getCell(col);
		return getCellValue(cell);
	}
	
	/**
	 * 
	 * @param cell POI单元格对象
	 * @return 返回单元格数据对象
	 */
	public static Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_BLANK:
			return "";
			
		case Cell.CELL_TYPE_STRING:
			return cell.getRichStringCellValue().getString();
			
		case Cell.CELL_TYPE_NUMERIC:
			if (DateUtil.isCellDateFormatted(cell)) {
				return cell.getDateCellValue();
			} else {
				return cell.getNumericCellValue();
			}
			
		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_FORMULA:
			return cell.getCellFormula();
			
		default:
			return "";
		}
	}
	
	/***
	 * 方法说明：跟据坐标获取EXCEL行索引
	 * @param location EXCEL单元格坐标
	 * @return 行索引
	 */
	public static int getRow(String location){
		CellReference ref=new CellReference(location);
		return  ref.getRow();
	}
	
	/***
	 * 方法说明：跟据坐标获取EXCEL列索引
	 * @param location EXCEL单元格坐标
	 * @return 列索引
	 */
	public static int getCol(String location){
		CellReference ref=new CellReference(location);
		return  ref.getCol();
	}
	
	public static void printArray(Object [] objs){
		if(objs!=null){
			for(Object e : objs){
				System.out.print(e+"  ");
			}
		}
		System.out.println();
	}
}
