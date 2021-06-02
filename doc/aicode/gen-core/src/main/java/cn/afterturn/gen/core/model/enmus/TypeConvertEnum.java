package cn.afterturn.gen.core.model.enmus;

/**
 * 数据类型转换
 *
 * @author by jueyue on 18-6-12.
 */
public enum TypeConvertEnum {


    ORACLE_BLOB(DBType.ORACLE, "BLOB", GenFieldType.BYTE_ARR),
    ORACLE_CHAR(DBType.ORACLE, "CHAR", GenFieldType.STRING),
    ORACLE_CLOB(DBType.ORACLE, "CLOB", GenFieldType.STRING),
    ORACLE_DATE(DBType.ORACLE, "DATE", GenFieldType.DATE),
    ORACLE_NUMBER(DBType.ORACLE, "NUMBER", GenFieldType.BIG_DECIMAL),
    ORACLE_LONG(DBType.ORACLE, "LONG", GenFieldType.STRING),
    ORACLE_SMALLINT(DBType.ORACLE, "SMALLINT", GenFieldType.INTEGER),
    ORACLE_TIMESTAMP(DBType.ORACLE, "TIMESTAMP", GenFieldType.DATE),
    ORACLE_RAW(DBType.ORACLE, "RAW", GenFieldType.BYTE_ARR),
    ORACLE_VARCHAR2(DBType.ORACLE, "VARCHAR2", GenFieldType.STRING),


    //----------------------------------------------------------------------
    SQLSERVER_TIMESTAMP(DBType.SQLSERVER, "TIMESTAMP", GenFieldType.DATE),
    SQLSERVER_BIT(DBType.SQLSERVER, "BIT", GenFieldType.BOOLEAN),
    SQLSERVER_CHAR(DBType.SQLSERVER, "CHAR", GenFieldType.STRING),
    SQLSERVER_NCHAR(DBType.SQLSERVER, "NCHAR", GenFieldType.STRING),
    SQLSERVER_DATETIME(DBType.SQLSERVER, "DATETIME", GenFieldType.DATE),
    SQLSERVER_MONEY(DBType.SQLSERVER, "MONEY", GenFieldType.BIG_DECIMAL),
    SQLSERVER_SMALLMONEY(DBType.SQLSERVER, "SMALLMONEY", GenFieldType.BIG_DECIMAL),
    SQLSERVER_DECIMAL(DBType.SQLSERVER, "DECIMAL", GenFieldType.BIG_DECIMAL),
    SQLSERVER_FLOAT(DBType.SQLSERVER, "FLOAT", GenFieldType.DOUBLE),
    SQLSERVER_INT(DBType.SQLSERVER, "INT", GenFieldType.INTEGER),
    SQLSERVER_IMAGE(DBType.SQLSERVER, "IMAGE", GenFieldType.BYTE_ARR),
    SQLSERVER_TEXT(DBType.SQLSERVER, "TEXT", GenFieldType.BYTE_ARR),
    SQLSERVER_BTEXT(DBType.SQLSERVER, "NTEXT", GenFieldType.BYTE_ARR),
    SQLSERVER_XML(DBType.SQLSERVER, "XML", GenFieldType.BYTE_ARR),
    SQLSERVER_NUMERIC(DBType.SQLSERVER, "NUMERIC", GenFieldType.BIG_DECIMAL),
    SQLSERVER_REAL(DBType.SQLSERVER, "REAL", GenFieldType.FLOAT),
    SQLSERVER_SMALLINT(DBType.SQLSERVER, "SMALLINT", GenFieldType.SHORT),
    SQLSERVER_SMALL_DATETIME(DBType.SQLSERVER, "SMALLDATETIME", GenFieldType.DATE),
    SQLSERVER_TINYINT(DBType.SQLSERVER, "TINYINT", GenFieldType.BYTE),
    SQLSERVER_NVARCHAR(DBType.SQLSERVER, "NVARCHAR", GenFieldType.STRING),
    SQLSERVER_VARBINARY(DBType.SQLSERVER, "VARBINARY", GenFieldType.BYTE_ARR),
    SQLSERVER_VARCHAR(DBType.SQLSERVER, "VARCHAR", GenFieldType.STRING),

    //---------------------------------------------------------------------
    DB2_BIGINT(DBType.DB2, "BIGINT", GenFieldType.LONG),
    DB2_BLOB(DBType.DB2, "BLOB", GenFieldType.BYTE_ARR),
    DB2_CHARACTER(DBType.DB2, "CHARACTER", GenFieldType.STRING),
    DB2_GRAPHIC(DBType.DB2, "GRAPHIC", GenFieldType.STRING),
    DB2_CLOB(DBType.DB2, "CLOB", GenFieldType.STRING),
    DB2_DATE(DBType.DB2, "DATE", GenFieldType.DATE),
    DB2_DECIMAL(DBType.DB2, "DECIMAL", GenFieldType.BIG_DECIMAL),
    DB2_DOUBLE(DBType.DB2, "DOUBLE", GenFieldType.DOUBLE),
    DB2_INTEGER(DBType.DB2, "INTEGER", GenFieldType.INTEGER),
    DB2_LONGVARGRAPHIC(DBType.DB2, "LONGVARGRAPHIC", GenFieldType.BYTE_ARR),
    DB2_LONGVARCHAR(DBType.DB2, "LONGVARCHAR", GenFieldType.BYTE_ARR),
    DB2_REAL(DBType.DB2, "REAL", GenFieldType.LONG),
    DB2_SMALLINT(DBType.DB2, "SMALLINT", GenFieldType.SHORT),
    DB2_TIME(DBType.DB2, "TIME", GenFieldType.DATE),
    DB2_TIMESTAMP(DBType.DB2, "TIMESTAMP", GenFieldType.DATE),
    DB2_VARGRAPHIC(DBType.DB2, "VARGRAPHIC", GenFieldType.STRING),
    DB2_VARCHAR(DBType.DB2, "VARCHAR", GenFieldType.STRING),

    //------------------------------------------------------------
    MYSQL_BIGINT(DBType.MYSQL, "BIGINT", GenFieldType.LONG),
    MYSQL_TINYBLOB(DBType.MYSQL, "TINYBLOB", GenFieldType.BYTE_ARR),
    MYSQL_BIT(DBType.MYSQL, "BIT", GenFieldType.BOOLEAN),
    MYSQL_ENUM(DBType.MYSQL, "ENUM", GenFieldType.STRING),
    MYSQL_SET(DBType.MYSQL, "SET", GenFieldType.STRING),
    MYSQL_CHAR(DBType.MYSQL, "CHAR", GenFieldType.STRING),
    MYSQL_DATE(DBType.MYSQL, "DATE", GenFieldType.DATE),
    MYSQL_YEAR(DBType.MYSQL, "YEAR", GenFieldType.DATE),
    MYSQL_DECIMAL(DBType.MYSQL, "DECIMAL", GenFieldType.BIG_DECIMAL),
    MYSQL_NUMERIC(DBType.MYSQL, "NUMERIC", GenFieldType.BIG_DECIMAL),
    MYSQL_DOUBLE(DBType.MYSQL, "DOUBLE", GenFieldType.DOUBLE),
    MYSQL_REAL(DBType.MYSQL, "REAL", GenFieldType.DOUBLE),
    MYSQL_MEDIUMINT(DBType.MYSQL, "MEDIUMINT", GenFieldType.INTEGER),
    MYSQL_BLOB(DBType.MYSQL, "BLOB", GenFieldType.BYTE_ARR),
    MYSQL_MEDIUMBLOB(DBType.MYSQL, "MEDIUMBLOB", GenFieldType.BYTE_ARR),
    MYSQL_LONGBLOB(DBType.MYSQL, "LONGBLOB", GenFieldType.BYTE_ARR),
    MYSQL_FLOAT(DBType.MYSQL, "FLOAT", GenFieldType.FLOAT),
    MYSQL_INT(DBType.MYSQL, "INT", GenFieldType.INTEGER),
    MYSQL_SMALLINT(DBType.MYSQL, "SMALLINT", GenFieldType.SHORT),
    MYSQL_TIME(DBType.MYSQL, "TIME", GenFieldType.DATE),
    MYSQL_TIMESTAMP(DBType.MYSQL, "TIMESTAMP", GenFieldType.DATE),
    MYSQL_DATETIME(DBType.MYSQL, "DATETIME", GenFieldType.DATE),
    MYSQL_TINYINT(DBType.MYSQL, "TINYINT", GenFieldType.BOOLEAN),
    MYSQL_VARBINARY(DBType.MYSQL, "VARBINARY", GenFieldType.BYTE_ARR),
    MYSQL_BINARY(DBType.MYSQL, "BINARY", GenFieldType.BYTE_ARR),
    MYSQL_VARCHAR(DBType.MYSQL, "VARCHAR", GenFieldType.STRING),
    MYSQL_TINYTEXT(DBType.MYSQL, "TINYTEXT", GenFieldType.STRING),
    MYSQL_TEXT(DBType.MYSQL, "TEXT", GenFieldType.STRING);


    private DBType db;
    private String dbType;
    private GenFieldType type;

    TypeConvertEnum(DBType db, String dbType, GenFieldType type) {
        this.db = db;
        this.dbType = dbType;
        this.type = type;
    }

    public static String getTypeByDb(DBType db, String dbType) {
        TypeConvertEnum[] types = TypeConvertEnum.values();
        for (int i = 0; i < types.length; i++) {
            if (types[i].db.equals(db) && types[i].dbType.equalsIgnoreCase(dbType)) {
                return types[i].type.getType();
            }
        }
        return null;
    }

    public DBType getDb() {
        return db;
    }

    public String getDbType() {
        return dbType;
    }

    public GenFieldType getType() {
        return type;
    }

}
