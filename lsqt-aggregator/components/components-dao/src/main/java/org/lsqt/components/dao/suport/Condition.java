package org.lsqt.components.dao.suport;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import org.hibernate.type.Type;


/**
 * 分页时用到的where条件Bean
 * 调用范例:
 * 		Page<User> page=new Page<User>(1,1);
 *		page.addConditions(Condition.getInstance().like("name", "张",MatchWay.ANYWHERE).in("name", new Object[]{"王"}));
 *		page.addOrderProperties("name", true);
 *		page = userService.loadPage(page);
 *
 * @author 袁明敏
 * @since 1.0
 *
 */
@SuppressWarnings("unchecked")
public final class Condition implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7280187028091359263L;

	/**
	 * 
	 */
	public Condition() {
	}

	private List expressions = new ArrayList();
	
	/**
	 * 
	 * 方法说明：条件sql，hibernate条件查询：Restrictions.sqlRestriction(sql, values, types)
	 *
	 * @param sql 原生sql语句
	 * @param values sql 参数值
	 * @param types sql 参数类型
	 * @return 返回条件对象
	 */
	public Condition sql(String sql, Object[] values, Type[] types) {
		
		expressions.add(Restrictions.sqlRestriction(sql, values, types));
		return this;
	}

	/**
	 * Apply an "equal" constraint to the identifier property
	 * @param value id值
	 * @return Condition 返回条件对象
	 */
	public  Condition idEq(Object value) {
		expressions.add(Restrictions.idEq(value));
		return this;
	}
	
	/**
	 * Apply a constraint expressed in SQL, with the given JDBC
	 * parameter. Any occurrences of <tt>{alias}</tt> will be replaced
	 * by the table alias.
	 *
	 * @param sql sql语句串
	 * @param value 参数值
	 * @param type 参数类型
	 * @return Condition
	 */
	public Condition sql(String sql, Object value, Type type) {
		expressions.add(Restrictions.sqlRestriction( sql,value,type ));
		return this;
	}

	/**
	 * Apply a constraint expressed in SQL. Any occurrences of <tt>{alias}</tt>
	 * will be replaced by the table alias.
	 *
	 * @param sql sql 语句
	 * 
	 * @return Condition
	 */
	public Condition sql(String sql) {
		expressions.add( Restrictions.sqlRestriction(sql));
		return this;
	}


	/**
	 * 方法说明：
	 * Apply an "equal" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 返回条件对象
	 */
	public Condition eq(String propertyName, Object value) {
		expressions.add( Restrictions.eq(propertyName, value));
		return this;
	}
	/**
	 * Apply a "not equal" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 返回条件对象
	 */
	public Condition ne(String propertyName, Object value) {
		expressions.add( Restrictions.ne(propertyName, value));
		return this;
	}
	/**
	 * Apply a "like" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 条件对象
	 */
	public  Condition like(String propertyName, Object value) {
		expressions.add( Restrictions.like(propertyName, value));
		return this;
	}
	/**
	 * Apply a "like" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param matchWay 匹配方式
	 * @return Condition 返回条件对象
	 */
	public Condition like(String propertyName, String value, MatchWay matchWay) {
		switch(matchWay){
		case START:
			expressions.add(Restrictions.like( propertyName,value,MatchMode.START ));
			break;
		case END:
			expressions.add(Restrictions.like( propertyName,value,MatchMode.END ));
			break;
		case EXACT:
			expressions.add(Restrictions.like( propertyName,value,MatchMode.EXACT ));
		default:
			expressions.add(Restrictions.like( propertyName,value,MatchMode.ANYWHERE ));
			break;
		}
		return this;
	}
	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
	 * operator
	 *
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @param matchWay 模糊查询匹配方式
	 * @return Condition 返回条件对象
	 */
	public Condition ilike(String propertyName, String value, MatchWay matchWay) {
		switch(matchWay){
		case START:
			expressions.add(Restrictions.ilike( propertyName,value,MatchMode.START ));
			break;
		case END:
			expressions.add(Restrictions.ilike( propertyName,value,MatchMode.END ));
			break;
		case EXACT:
			expressions.add(Restrictions.ilike( propertyName,value,MatchMode.EXACT ));
		default:
			expressions.add(Restrictions.ilike( propertyName,value,MatchMode.ANYWHERE ));
			break;
		}
		return this;
	}
	/**
	 * A case-insensitive "like", similar to Postgres <tt>ilike</tt>
	 * operator
	 *
	 * @param propertyName 属性名称
	 * @param value 属性值 
	 * @return Condition 返回条件对象
	 */
	public  Condition ilike(String propertyName, Object value) {
		expressions.add(Restrictions.ilike( propertyName,value));
		return this;
	}
	/**
	 * Apply a "greater than" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 返回条件对象
	 */
	public Condition gt(String propertyName, Object value) {
		expressions.add(Restrictions.gt(propertyName, value));
		return this;
	}
	/**
	 * Apply a "less than" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 返回条件对象
	 */
	public Condition lt(String propertyName, Object value) {
		expressions.add(Restrictions.lt(propertyName, value));
		return this;
	}
	/**
	 * Apply a "less than or equal" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 返回条件对象
	 */
	public Condition le(String propertyName, Object value) {
		expressions.add(Restrictions.le(propertyName, value));
		return this;
	}
	/**
	 * Apply a "greater than or equal" constraint to the named property
	 * @param propertyName 属性名称
	 * @param value 属性值
	 * @return Condition 返回条件对象
	 */
	public Condition ge(String propertyName, Object value) {
		expressions.add(Restrictions.le(propertyName,value));
		return this;
	}
	/**
	 * Apply a "between" constraint to the named property
	 * @param propertyName 属性名称
	 * @param lo value 左上限
	 * @param hi value 右上限
	 * @return Condition 返回条件对象
	 */
	public Condition between(String propertyName, Object lo, Object hi) {
		expressions.add(Restrictions.between(propertyName, lo, hi));
		return this;
	}
	/**
	 * Apply an "in" constraint to the named property
	 * @param propertyName 属性名称
	 * @param values 属性值数组
	 * @return Condition 返回条件对象
	 */
	public Condition in(String propertyName, Object[] values) {
		expressions.add(Restrictions.in(propertyName,values));
		return this;
	}
	/**
	 * Apply an "in" constraint to the named property
	 * @param propertyName 属性名称
	 * @param values 属性的枚举值
	 * @return Condition 返回条件对象
	 */
	public Condition in(String propertyName, Collection values) {
		int pageSize=800;
	
		
		//ORACLE SQL语句in超过1000个的处理
		if(values.size()<pageSize){
			expressions.add(Restrictions.in(propertyName,values));
		}else{
			
			//去掉重复对象
			Set<Object> set=new HashSet<Object>(values);
			List<Object> list=new ArrayList<Object>(set);
			


			double cnt=(double)values.size()/(double)pageSize;  	
			int p=(int)Math.ceil(cnt);		
			
			
			HashMap<Integer,List> map=new HashMap<Integer,List>();
			for(int i=1;i<=p-1;i++){
				map.put(i, list.subList((i-1)*pageSize, i*pageSize));
				
			}
			map.put(p, list.subList((p-1)*pageSize, list.size()));
			
		
			Criterion c=null;
			for(int i=1;i<=map.size();i++){
				c= Restrictions.or(Restrictions.in(propertyName,map.get(i)), Restrictions.in(propertyName,map.get(i)));
				
			}
			expressions.add(c);
			
			set=null;
		}
		return this;
	}
	/**
	 * Apply an "is null" constraint to the named property
	 * @param propertyName 属性名称
	 * @return Condition 返回条件对象
	 */
	public Condition isNull(String propertyName) {
		expressions.add(Restrictions.isNull(propertyName));
		return this;
	}
	/**
	 * Apply an "equal" constraint to two properties
	 * @param propertyName 属性名称
	 * @param otherPropertyName 属性名称
	 * @return Condition 返回条件对象
	 */
	public Condition eqProperty(String propertyName, String otherPropertyName) {
		expressions.add(Restrictions.eqProperty(propertyName,otherPropertyName));
		return this;
	}
	/**
	 * Apply a "not equal" constraint to two properties
	 * @param propertyName 属性名称
	 * @param otherPropertyName 属性名称
	 * @return Condition 返回条件对象
	 */
	public Condition neProperty(String propertyName, String otherPropertyName) {
		expressions.add(Restrictions.neProperty(propertyName,otherPropertyName));
		return this;
	}
	/**
	 * Apply a "less than" constraint to two properties
	 * @param propertyName 属性名称
	 * @param otherPropertyName 属性名称
	 * @return 返回条件对象
	 */
	public Condition ltProperty(String propertyName, String otherPropertyName) {
		expressions.add(Restrictions.ltProperty(propertyName,otherPropertyName));
		return this;
	}
	/**
	 * Apply a "less than or equal" constraint to two properties
	 * @param propertyName 属性名称
	 * @param otherPropertyName 属性名称
	 * @return 返回条件对象
	 */
	public Condition leProperty(String propertyName, String otherPropertyName) {
		expressions.add(Restrictions.leProperty(propertyName,otherPropertyName));
		return this;
	}
	/**
	 * Apply a "greater than" constraint to two properties
	 * @param propertyName 属性名称
	 * @param otherPropertyName 属性名称
	 * @return 返回条件对象
	 */
	public Condition gtProperty(String propertyName, String otherPropertyName) {
		expressions.add(Restrictions.gtProperty(propertyName,otherPropertyName));
		return this;
	}
	/**
	 * Apply a "greater than or equal" constraint to two properties
	 * @param propertyName 属性名称
	 * @param otherPropertyName 属性名称
	 * @return 返回条件对象
	 */
	public Condition geProperty(String propertyName, String otherPropertyName) {
		expressions.add(Restrictions.geProperty(propertyName,otherPropertyName));
		return this;
	}
	/**
	 * Apply an "is not null" constraint to the named property
	 * @param propertyName 属性名称
	 * @return Condition 返回条件值
	 */
	public Condition isNotNull(String propertyName) {
		expressions.add(Restrictions.isNotNull(propertyName));
		return this;
	}
	
	
	/**
	 * Return the conjuction of two expressions
	 *
	 * @param lhs
	 * @param rhs
	 * @return Condition
	
	public Condition and(Criterion lhs, Criterion rhs) {
		expressions.add(Expression.and(lhs, rhs));
		return this;
	} 
	*/
	
	/**
	 * Return the disjuction of two expressions
	 *
	 * @param lhs
	 * @param rhs
	 * @return Condition
	
	public static LogicalExpression or(Condition lhs, Condition rhs) {
		return null;
	}
	 */
	
	/**
	 * Return the negation of an expression
	 *
	 * @param expression
	 * @return Condition
	
	public static Condition not(Condition expression) {
		return null;
	}
	 */
	
	/**
	 * Apply a constraint expressed in SQL, with the given JDBC
	 * parameters. Any occurrences of <tt>{alias}</tt> will be
	 * replaced by the table alias.
	 *
	 * @param sql 带参的sql语句
	 * @param values 参数值
	 * @param types 参数值类型
	 * @return Condition 返回条件对象
	 */
	public Condition sqlRestriction(String sql, Object[] values, Type[] types) {
		expressions.add(Restrictions.sqlRestriction(sql, values, types));
		return this;
	}
	/**
	 * Apply a constraint expressed in SQL, with the given JDBC
	 * parameter. Any occurrences of <tt>{alias}</tt> will be replaced
	 * by the table alias.
	 * 
	 * @param sql 带参的sql语句
	 * @param value 参数值
	 * @param type 参数类型
	 * @return Condition 返回条件对象
	 */
	public Condition sqlRestriction(String sql, Object value, Type type) {
		expressions.add(Restrictions.sqlRestriction(sql, value, type));
		return this;
	}
	/**
	 * Apply a constraint expressed in SQL. Any occurrences of <tt>{alias}</tt>
	 * will be replaced by the table alias.
	 *
	 * @param sql 原生sql语句
	 * @return Condition 返回条件值
	 */
	public Condition sqlRestriction(String sql) {
		expressions.add(Restrictions.sqlRestriction(sql));
		return this;
	}

	/**
	 * Group expressions together in a single conjunction (A and B and C...)
	 *
	 * @return Condition
	 */
	public Condition conjunction() {
		expressions.add(Restrictions.conjunction());
		return this;
	}

	/**
	 * Group expressions together in a single disjunction (A or B or C...)
	 *
	 * @return Condition
	 */
	public Condition disjunction() {
		expressions.add(Restrictions.disjunction());
		return this;
	}

	/**
	 * Apply an "equals" constraint to each property in the
	 * key set of a <tt>Map</tt>
	 *
	 * @param propertyNameValues a map from property names to values
	 * @return Condition
	 */
	public Condition allEq(Map propertyNameValues) {
		expressions.add(Restrictions.allEq(propertyNameValues));
		return this;
	}
	
	/**
	 * Constrain a collection valued property to be empty
	 * @param propertyName 属性名称
	 * @return 返回条件对象
	 */
	public Condition isEmpty(String propertyName) {
		expressions.add(Restrictions.isEmpty(propertyName));
		return this;
	}

	/**
	 * Constrain a collection valued property to be non-empty
	 * @param propertyName 属性名称
	 * @return 返回条件值
	 */
	public Condition isNotEmpty(String propertyName) {
		expressions.add(Restrictions.isNotEmpty(propertyName));
		return this;
	}
	
	/**
	 * Constrain a collection valued property by size
	 * @param propertyName 属性名称
	 * @param size 集合大小值
	 * @return 返回条件对象
	 */
	public Condition sizeEq(String propertyName, int size) {
		expressions.add(Restrictions.sizeEq(propertyName,size));
		return this;
	}
	
	/**
	 * Constrain a collection valued property by size
	 * @param propertyName 属性名
	 * @param size 集合的大小
	 * @return 返回条件值
	 */
	public Condition sizeNe(String propertyName, int size) {
		expressions.add(Restrictions.sizeNe(propertyName,size));
		return this;
	}
	
	/**
	 * Constrain a collection valued property by size
	 * @param propertyName 属性名
	 * @param size 集合大小
	 * @return 返回条件值
	 */
	public Condition sizeGt(String propertyName, int size) {
		expressions.add(Restrictions.sizeGt(propertyName,size));
		return this;
	}
	
	/**
	 * Constrain a collection valued property by size
	 * @param propertyName 属性名
	 * @param size 集合大小
	 * @return 返回条件值
	 */
	public Condition sizeLt(String propertyName, int size) {
		expressions.add(Restrictions.sizeLt(propertyName,size));
		return this;
	}
	
	/**
	 * Constrain a collection valued property by size
	 * @param propertyName 属性名
	 * @param size 集合大小
	 * @return 返回条件值
	 */
	public Condition sizeGe(String propertyName, int size) {
		expressions.add(Restrictions.sizeGe(propertyName,size));
		return this;
	}
	
	/**
	 * Constrain a collection valued property by size
	 * @param propertyName 属性名
	 * @param size 集合大小
	 * @return 返回条件值
	 */
	public Condition sizeLe(String propertyName, int size) {
		expressions.add(Restrictions.sizeLe(propertyName,size));
		return this;
	}
}
