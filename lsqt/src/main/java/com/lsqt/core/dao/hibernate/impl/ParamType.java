package com.lsqt.core.dao.hibernate.impl;


/***
 * 该类提供存储过程带参数类型的设置
 * @author 袁明敏
 *
 */
public class ParamType {

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>BIT</code>.
			 */
			        public final static int BIT             =  java.sql.Types.BIT;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>TINYINT</code>.
			 */
			        public final static int TINYINT         =  java.sql.Types.TINYINT;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>SMALLINT</code>.
			 */
			        public final static int SMALLINT        =   java.sql.Types.SMALLINT;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>INTEGER</code>.
			 */
			        public final static int INTEGER         =   java.sql.Types.INTEGER;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>BIGINT</code>.
			 */
			        public final static int BIGINT          =  java.sql.Types.BIGINT;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>FLOAT</code>.
			 */
			        public final static int FLOAT           =   java.sql.Types.FLOAT;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>REAL</code>.
			 */
			        public final static int REAL            =   java.sql.Types.REAL;


			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>DOUBLE</code>.
			 */
			        public final static int DOUBLE          =   java.sql.Types.DOUBLE;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>NUMERIC</code>.
			 */
			        public final static int NUMERIC         =   java.sql.Types.NUMERIC;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>DECIMAL</code>.
			 */
			        public final static int DECIMAL         =   java.sql.Types.DECIMAL;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>CHAR</code>.
			 */
			        public final static int CHAR            =   java.sql.Types.CHAR;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>VARCHAR</code>.
			 */
			        public final static int VARCHAR         =  java.sql.Types.VARCHAR;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>LONGVARCHAR</code>.
			 */
			        public final static int LONGVARCHAR     =  java.sql.Types.LONGVARCHAR;


			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>DATE</code>.
			 */
			        public final static int DATE            =  java.sql.Types.DATE;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>TIME</code>.
			 */
			        public final static int TIME            =  java.sql.Types.TIME;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>TIMESTAMP</code>.
			 */
			        public final static int TIMESTAMP       =  java.sql.Types.TIMESTAMP;


			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>BINARY</code>.
			 */
			        public final static int BINARY          =  java.sql.Types.BINARY;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>VARBINARY</code>.
			 */
			        public final static int VARBINARY       =  java.sql.Types.VARBINARY;

			/**
			 * <P>The constant in the Java programming language, sometimes referred
			 * to as a type code, that identifies the generic SQL type
			 * <code>LONGVARBINARY</code>.
			 */
			        public final static int LONGVARBINARY   =  java.sql.Types.LONGVARBINARY;

			/**
			 * <P>The constant in the Java programming language
			 * that identifies the generic SQL value
			 * <code>NULL</code>.
			 */
			        public final static int NULL            =   java.sql.Types.NULL;

			    /**
			     * The constant in the Java programming language that indicates
			     * that the SQL type is database-specific and
			     * gets mapped to a Java object that can be accessed via
			     * the methods <code>getObject</code> and <code>setObject</code>.
			     */
			        public final static int OTHER           = java.sql.Types.OTHER;



			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>JAVA_OBJECT</code>.
			     * @since 1.2
			     */
			        public final static int JAVA_OBJECT         = java.sql.Types.JAVA_OBJECT;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>DISTINCT</code>.
			     * @since 1.2
			     */
			        public final static int DISTINCT            = java.sql.Types.DISTINCT;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>STRUCT</code>.
			     * @since 1.2
			     */
			        public final static int STRUCT              = java.sql.Types.STRUCT;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>ARRAY</code>.
			     * @since 1.2
			     */
			        public final static int ARRAY               = java.sql.Types.ARRAY;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>BLOB</code>.
			     * @since 1.2
			     */
			        public final static int BLOB                = java.sql.Types.BLOB;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>CLOB</code>.
			     * @since 1.2
			     */
			        public final static int CLOB                = java.sql.Types.CLOB;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type
			     * <code>REF</code>.
			     * @since 1.2
			     */
			        public final static int REF                 = java.sql.Types.REF;

			    /**
			     * The constant in the Java programming language, somtimes referred to
			     * as a type code, that identifies the generic SQL type <code>DATALINK</code>.
			     *
			     * @since 1.4
			     */
			    public final static int DATALINK = java.sql.Types.DATALINK;

			    /**
			     * The constant in the Java programming language, somtimes referred to
			     * as a type code, that identifies the generic SQL type <code>BOOLEAN</code>.
			     *
			     * @since 1.4
			     */
			    public final static int BOOLEAN = java.sql.Types.BOOLEAN;

			    //------------------------- JDBC 4.0 -----------------------------------

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type <code>ROWID</code>
			     *
			     * @since 1.6
			     *
			     */
			    public final static int ROWID = java.sql.Types.ROWID;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type <code>NCHAR</code>
			     *
			     * @since 1.6
			     */
			    public static final int NCHAR = java.sql.Types.NCHAR;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type <code>NVARCHAR</code>.
			     *
			     * @since 1.6
			     */
			   public static final int NVARCHAR = java.sql.Types.NVARCHAR;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type <code>LONGNVARCHAR</code>.
			     *
			     * @since 1.6
			     */
			   public static final int LONGNVARCHAR = java.sql.Types.LONGVARCHAR;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type <code>NCLOB</code>.
			     *
			     * @since 1.6
			     */
			   public static final int NCLOB = java.sql.Types.NCLOB;

			    /**
			     * The constant in the Java programming language, sometimes referred to
			     * as a type code, that identifies the generic SQL type <code>XML</code>.
			     *
			     * @since 1.6
			     */
			    public static final int SQLXML = java.sql.Types.SQLXML;
			    
			    
			    public static final  Integer HOLDER = new Integer(-79777797);
			    public static Object newHolder(){
			    	return HOLDER;
			    }

}
