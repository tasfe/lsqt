package org.lsqt.components.dao.dbutils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.lsqt.components.dao.dbutils.annotation.Column;
import org.lsqt.components.dao.dbutils.annotation.Id;
import org.lsqt.components.dao.dbutils.annotation.Table;
import org.lsqt.components.dto.DataTable;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.lang.StringUtil;

@SuppressWarnings({"unchecked","rawtypes"})
public class EntityAnnotationUtil {
	
	/**
	 * 获取数据庘的名称
	 * @param entityClazz
	 * @return
	 */
	public static String getDbSchema(Class entityClazz){
		Table table=(Table)entityClazz.getAnnotation(Table.class);
		if(table!=null &&  table.schema()!=null){
			return table.schema();
		}
		System.out.println("没有找到"+entityClazz+"实体映射的表名或schema名称！");
		return null;
	}
	
	/**
	 * 获取实体的表名
	 * @param entityClazz
	 * @return
	 */
	public static String getDbTable(Class entityClazz){
		Table table=(Table)entityClazz.getAnnotation(Table.class);
		return table.name();
	}
	
	/**
	 * 获取实体的主键字段
	 * @param entityClazz
	 * @return
	 */
	public static String getDbIdColumn(Class entityClazz){
		String idColumn=null;
		for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			boolean hasId=false;
			for(Field e:superClass.getDeclaredFields()){
				//1.获取主键
				Id id=e.getAnnotation(Id.class);
				if(id!=null){
					idColumn= StringUtil.isEmpty(id.name()) ? "id":id.name();
					hasId=true;
					break;
				}
			}
			if(hasId)break;
		}
		if(idColumn==null)System.out.println("没有找到"+entityClazz+"主键映射");
		
		return idColumn;
	}
	
	/**
	 * 获取实体bean所有字段
	 * @param entityClazz -
	 * @return -
	 */
	public static List<String> getDbColumns(Class entityClazz){
		List<String> sqlColoumns=new ArrayList<String>();
		for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			for(Field e:superClass.getDeclaredFields()){
				
				/*1.获取主键字段*/
				Id pk=e.getAnnotation(Id.class);
				if(pk!=null){
					sqlColoumns.add(StringUtil.isEmpty(pk.name()) ? "id":pk.name());
					continue;
				}
				
				
				//2.获取字段
				Column columns=e.getAnnotation(Column.class);
				if(columns!=null){
					if(StringUtil.isEmpty(columns.name())){
						sqlColoumns.add(e.getName());
					}else{
						sqlColoumns.add(columns.name());
					}
				}
			}
		}
		return sqlColoumns;
	}
	
	/**
	 * 跟据属性名获取数据库列名
	 * @param entityClazz -
	 * @param entityPropertis -
	 * @return list
	 */
	public static List<String> getDbColumnByProperty(Class entityClazz,String ... entityPropertis){
		List<String> dbColoumns=new ArrayList<String>();
		
		List<String> temp=Arrays.asList(entityPropertis);
		for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
			
			for(Field e:superClass.getDeclaredFields()){
				Column c=e.getAnnotation(Column.class);
				if(c!=null && temp.contains(e.getName())){
					String col=StringUtil.isEmpty(c.name()) ? e.getName(): c.name();
					dbColoumns.add(col);
				}
			}
		}
		return dbColoumns;
	}
	
	/**
	 * 跟据数据庘的一条记录，还原实体bean
	 * @param record
	 * @param entityClazz
	 * @return
	 */
	public static <T> T toEntity(Class<T> entityClazz,Map<String,Object> recordMap){
		T entity;
		try {
			entity = entityClazz.newInstance();
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		} 
		
		for(String calumn:recordMap.keySet()){
			for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				for(Field e:superClass.getDeclaredFields()){
					
					//赋值主键
					Id pk=e.getAnnotation(Id.class);
					if(pk!=null && pk.name().equalsIgnoreCase(calumn)){
						BeanUtil.forceSetProperty(entity, e.getName(), recordMap.get(calumn));
						break;
					 
					}
					
					//赋值其它字段
					Column c=e.getAnnotation(Column.class);
					if(c!=null && c.name().equalsIgnoreCase(calumn)){
						BeanUtil.forceSetProperty(entity, e.getName(), recordMap.get(calumn));
						break;
					 
					}
				}
			}
		}
		return entity;
	}
	
	/**
	 * 跟据数据庘的多条记录，还原实体bean
	 * @param entityClazz
	 * @param dataTable
	 * @return
	 */
	public static <T> List<T> toEntityList(Class<T> entityClazz,DataTable dataTable){
		List<T> temp=new ArrayList<T>();
		try{
			Object [] hd=dataTable.getDataHead();
			
			for(Object [] row: dataTable.getDataBody()){
				T entity = entityClazz.newInstance();
				for(int i=0;i<hd.length;i++){
					String column=hd[i]==null ? "":hd[i].toString();
					initProperty(entity, column, row[i]);
				}
				temp.add(entity);
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return temp;
	}
	
	/**
	 * 跟据实体域的db注解从dataTable取数据初使化域值
	 * @param entity
	 * @param column
	 * @param value
	 * @return
	 */
	private static boolean initProperty(Object entity,String column,Object value){
		boolean isOk=false;
		for (Class superClass = entity.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
			for(Field e:superClass.getDeclaredFields()){
				
				//赋值主键
				Id pk=e.getAnnotation(Id.class);
				if(pk!=null && pk.name().equalsIgnoreCase(column)){
					BeanUtil.forceSetProperty(entity, e.getName(), value);
					return true;
				 
				}
				
				//赋值其它字段
				Column c=e.getAnnotation(Column.class);
				if(c!=null && c.name().equalsIgnoreCase(column)){
					BeanUtil.forceSetProperty(entity, e.getName(), value);
					return true;
				 
				}
			}
		}
		return isOk;
	}
	
	
	/**
	 * 获取实体bean的主键值
	 * @param entity
	 * @return
	 */
	public static Object getEntityIdValue(Object entity){
		try{
			Class clazz=entity.getClass();
			for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				for(Field e:superClass.getDeclaredFields()){
					//1.获取主键
					Id id=e.getAnnotation(Id.class);
					if(id!=null){
						Method m=BeanUtil.getSetterGetterMap(clazz,true).get(e);
						return m.invoke(entity, null);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getEntityIdProperty(Object entity){
		try{
			Class clazz=entity.getClass();
			for (Class superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
				for(Field e:superClass.getDeclaredFields()){
					//1.获取主键
					Id id=e.getAnnotation(Id.class);
					if(id!=null){
						return e.getName();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取实体字段与字段值映射
	 * @param entity
	 * @return
	 */
	public static Map<String,Object> getEntityColumnValueMap(Object entity){
		Map valueMap=new LinkedHashMap();
		
			Class entityClazz=entity.getClass();
			Table table=(Table)entityClazz.getAnnotation(Table.class);
			if(table!=null){
				
				Map<Field,Method> getterMap=BeanUtil.getSetterGetterMap(entityClazz, true) ;
				for (Class superClass = entityClazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
					for(Field e:superClass.getDeclaredFields()){
						
						//1.获取主键
						Id id=e.getAnnotation(Id.class);
						if(id!=null){
							valueMap.put(getDbIdColumn(entityClazz), getEntityIdValue(entity));
							continue;
						}
						
						//2.获取字段(不含主键)
						Column columns=e.getAnnotation(Column.class);
						if(columns!=null){
							String temp=StringUtil.isEmpty(columns.name()) ? e.getName():columns.name();
							try{
								valueMap.put(temp,getterMap.get(e).invoke(entity, null));
							}catch(Exception ex){
								ex.printStackTrace();
							}
						}
					}
				}
			}
		return valueMap;
	}
}
