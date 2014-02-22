package org.lsqt.components.util.sql;

import java.sql.Types;

public enum SqlType {
	/**
	 * <P>The constant in the Java programming language, sometimes referred
	 * to as a type code, that identifies the generic SQL type 
	 * <code>BIT</code>.
	 */
	BIT("BIT", Types.BIT), 
	
	/**
	 * <P>The constant in the Java programming language, sometimes referred
	 * to as a type code, that identifies the generic SQL type 
	 * <code>TINYINT</code>.
	 */
	TINYINT("TINYINT", Types.TINYINT),
	

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>SMALLINT</code>.
 */
	SMALLINT("SMALLINT", Types.SMALLINT),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>INTEGER</code>.
 */
	INTEGER("INTEGER", Types.INTEGER),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>BIGINT</code>.
 */
	BIGINT("BIGINT", Types.BIGINT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>FLOAT</code>.
 */
	FLOAT("FLOAT", Types.FLOAT),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>REAL</code>.
 */
	REAL("REAL", Types.REAL),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>DOUBLE</code>.
 */
	DOUBLE("DOUBLE", Types.DOUBLE),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>NUMERIC</code>.
 */
	NUMERIC("NUMERIC", Types.NUMERIC),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>DECIMAL</code>.
 */
	DECIMAL("DECIMAL", Types.DECIMAL),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>CHAR</code>.
 */
	CHAR("CHAR", Types.CHAR),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>VARCHAR</code>.
 */
	VARCHAR("VARCHAR", Types.VARCHAR),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>LONGVARCHAR</code>.
 */
	LONGVARCHAR("LONGVARCHAR", Types.LONGVARCHAR),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>DATE</code>.
 */
	DATE("DATE", Types.DATE),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>TIME</code>.
 */
	TIME("TIME", Types.TIME),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>TIMESTAMP</code>.
 */
	TIMESTAMP("TIMESTAMP", Types.TIMESTAMP),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>BINARY</code>.
 */
	BINARY("BINARY", Types.BINARY),


/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>VARBINARY</code>.
 */
	VARBINARY("VARBINARY", Types.VARBINARY),

/**
 * <P>The constant in the Java programming language, sometimes referred
 * to as a type code, that identifies the generic SQL type 
 * <code>LONGVARBINARY</code>.
 */
	LONGVARBINARY("LONGVARBINARY", Types.LONGVARBINARY),

/**
 * <P>The constant in the Java programming language
 * that identifies the generic SQL value 
 * <code>NULL</code>.
 */
	NULL("NULL", Types.NULL),

    /**
     * The constant in the Java programming language that indicates
     * that the SQL type is database-specific and
     * gets mapped to a Java object that can be accessed via
     * the methods <code>getObject</code> and <code>setObject</code>.
     */
	OTHER("OTHER", Types.OTHER),


    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>JAVA_OBJECT</code>.
     * @since 1.2
     */
	JAVA_OBJECT("JAVA_OBJECT", Types.JAVA_OBJECT),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>DISTINCT</code>.
     * @since 1.2
     */
	DISTINCT("DISTINCT", Types.DISTINCT),
	
    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>STRUCT</code>.
     * @since 1.2
     */
	STRUCT("STRUCT", Types.STRUCT),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>ARRAY</code>.
     * @since 1.2
     */
	ARRAY("ARRAY", Types.ARRAY),


    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>BLOB</code>.
     * @since 1.2
     */
	BLOB("BLOB", Types.BLOB),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>CLOB</code>.
     * @since 1.2
     */
	 CLOB("CLOB", Types.CLOB),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type
     * <code>REF</code>.
     * @since 1.2
     */
	 REF("REF", Types.REF),
        
    /**
     * The constant in the Java programming language, somtimes referred to
     * as a type code, that identifies the generic SQL type <code>DATALINK</code>.
     *
     * @since 1.4
     */
	 DATALINK("DATALINK", Types.DATALINK),

    /**
     * The constant in the Java programming language, somtimes referred to
     * as a type code, that identifies the generic SQL type <code>BOOLEAN</code>.
     *
     * @since 1.4
     */
	 BOOLEAN("BOOLEAN", Types.BOOLEAN),
    
    //------------------------- JDBC 4.0 -----------------------------------
    
    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>ROWID</code>
     * 
     * @since 1.6
     *
     */
	 ROWID("ROWID", Types.ROWID),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NCHAR</code>
     *
     * @since 1.6
     */
	 NCHAR("NCHAR", Types.NCHAR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NVARCHAR</code>.
     *
     * @since 1.6
     */
	 
	 NVARCHAR("NVARCHAR", Types.NVARCHAR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>LONGNVARCHAR</code>.
     *
     * @since 1.6
     */
	 LONGNVARCHAR("LONGNVARCHAR", Types.LONGNVARCHAR),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>NCLOB</code>.
     *
     * @since 1.6
     */
	 NCLOB("NCLOB", Types.NCLOB),

    /**
     * The constant in the Java programming language, sometimes referred to
     * as a type code, that identifies the generic SQL type <code>XML</code>.
     *
     * @since 1.6 
     */
	 SQLXML("SQLXML", Types.SQLXML),
    
	//------------------------- 存储过程输入或输出参数  “占位符类型” -----------------------------------
	 INOROUT("INOROUT", -93179);
	
	// 成员变量
	private String name;
	private int value;
	
	// 构造方法
	private SqlType(String name, int value) {
		this.name = name;
		this.value = value;
	}

	public String getName() {  
        return name;  
    }
	
	public int getTypeValue() {  
        return value;  
    }
	
    @Override  
    public String toString() {  
        return this.name+":"+this.getTypeValue();  
    }  
}

