package com.util;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.wrapper.DefaultObjectWrapperFactory;
import org.apache.ibatis.reflection.wrapper.ObjectWrapperFactory;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.RowBounds;

/**
 * @author Mr.Object
 *
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class})})
public class PageInterceptor1 implements Interceptor {
    private static final Log logger = LogFactory.getLog(PageInterceptor1.class);
    private static final ObjectFactory DEFAULT_OBJECT_FACTORY = new DefaultObjectFactory();
    private static final ObjectWrapperFactory DEFAULT_OBJECT_WRAPPER_FACTORY = new DefaultObjectWrapperFactory();
    private static String defaultDialect = "mysql"; // 鏁版嵁搴撶被鍨�榛樿涓簃ysql)
    private static String defaultPageSqlId = ".*Page$"; // 闇�鎷︽埅鐨処D(姝ｅ垯鍖归厤)
    private static String dialect = ""; // 鏁版嵁搴撶被鍨�榛樿涓簃ysql)
    private static String pageSqlId = ""; // 闇�鎷︽埅鐨処D(姝ｅ垯鍖归厤)

    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();
        MetaObject metaStatementHandler = MetaObject.forObject(statementHandler, DEFAULT_OBJECT_FACTORY,
                DEFAULT_OBJECT_WRAPPER_FACTORY);
        // 鍒嗙浠ｇ悊瀵硅薄閾�鐢变簬鐩爣绫诲彲鑳借澶氫釜鎷︽埅鍣ㄦ嫤鎴紝浠庤�褰㈡垚澶氭浠ｇ悊锛岄�杩囦笅闈㈢殑涓ゆ寰幆鍙互鍒嗙鍑烘渶鍘熷鐨勭殑鐩爣绫�
        while (metaStatementHandler.hasGetter("h")) {
            Object object = metaStatementHandler.getValue("h");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        // 鍒嗙鏈�悗涓�釜浠ｇ悊瀵硅薄鐨勭洰鏍囩被
        while (metaStatementHandler.hasGetter("target")) {
            Object object = metaStatementHandler.getValue("target");
            metaStatementHandler = MetaObject.forObject(object, DEFAULT_OBJECT_FACTORY, DEFAULT_OBJECT_WRAPPER_FACTORY);
        }
        Configuration configuration = (Configuration) metaStatementHandler.getValue("delegate.configuration");
        dialect = configuration.getVariables().getProperty("dialect");
        if (null == dialect || "".equals(dialect)) {
            logger.warn("Property dialect is not setted,use default 'mysql' ");
            dialect = defaultDialect;
        }
        pageSqlId = configuration.getVariables().getProperty("pageSqlId");
        if (null == pageSqlId || "".equals(pageSqlId)) {
            logger.warn("Property pageSqlId is not setted,use default '.*Page$' ");
            pageSqlId = defaultPageSqlId;
        }
        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        // 鍙噸鍐欓渶瑕佸垎椤电殑sql璇彞銆傞�杩嘙appedStatement鐨処D鍖归厤锛岄粯璁ら噸鍐欎互Page缁撳熬鐨凪appedStatement鐨剆ql
        if (mappedStatement.getId().matches(pageSqlId)) {
            BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
            Object parameterObject = boundSql.getParameterObject();
            if (parameterObject == null) {
                throw new NullPointerException("parameterObject is null!");
            } else {
                PageParameter page = (PageParameter) metaStatementHandler
                        .getValue("delegate.boundSql.parameterObject.parameter");
                String sql = boundSql.getSql();
                // 閲嶅啓sql
                String pageSql = buildPageSql(sql, page);
                metaStatementHandler.setValue("delegate.boundSql.sql", pageSql);
                // 閲囩敤鐗╃悊鍒嗛〉鍚庯紝灏变笉闇�mybatis鐨勫唴瀛樺垎椤典簡锛屾墍浠ラ噸缃笅闈㈢殑涓や釜鍙傛暟
                metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                Connection connection = (Connection) invocation.getArgs()[0];
                // 閲嶈鍒嗛〉鍙傛暟閲岀殑鎬婚〉鏁扮瓑
                setPageParameter(sql, connection, mappedStatement, boundSql, page);
            }
        }
        // 灏嗘墽琛屾潈浜ょ粰涓嬩竴涓嫤鎴櫒
        return invocation.proceed();
    }

    /**
     * 浠庢暟鎹簱閲屾煡璇㈡�鐨勮褰曟暟骞惰绠楁�椤垫暟锛屽洖鍐欒繘鍒嗛〉鍙傛暟<code>PageParameter</code>,杩欐牱璋冪敤鑰呭氨鍙敤閫氳繃 鍒嗛〉鍙傛暟
     * <code>PageParameter</code>鑾峰緱鐩稿叧淇℃伅銆�
     * 
     * @param sql
     * @param connection
     * @param mappedStatement
     * @param boundSql
     * @param page
     */
    private void setPageParameter(String sql, Connection connection, MappedStatement mappedStatement,
            BoundSql boundSql, PageParameter page) {
        // 璁板綍鎬昏褰曟暟
        String countSql = "select count(0) from (" + sql + ") as total";
        PreparedStatement countStmt = null;
        ResultSet rs = null;
        try {
            countStmt = connection.prepareStatement(countSql);
          //鐢变簬璇ョ墿鐞嗗垎椤典笉鏀寔mybatis鐨�foreach>鏍囩锛宻o瀵硅鍒嗛〉鍋氫竴涓嬫洿鏀�
           BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
            Field metaParamsField = ReflectUtil.getFieldByFieldName(boundSql, "metaParameters");
            if (metaParamsField != null) {
                MetaObject mo = (MetaObject) ReflectUtil.getValueByFieldName(boundSql, "metaParameters");
                ReflectUtil.setValueByFieldName(countBS, "metaParameters", mo);
            }
            setParameters(countStmt, mappedStatement, countBS, boundSql.getParameterObject());
            rs = countStmt.executeQuery();
            int totalCount = 0;
            if (rs.next()) {
                totalCount = rs.getInt(1);
            }
            page.setTotalCount(totalCount);
            int totalPage = totalCount / page.getPageSize() + ((totalCount % page.getPageSize() == 0) ? 0 : 1);
            page.setTotalPage(totalPage);

        } catch (SQLException e) {
            logger.error("Ignore this exception", e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
            try {
                countStmt.close();
            } catch (SQLException e) {
                logger.error("Ignore this exception", e);
            }
        }

    }

    /**
     * 瀵筍QL鍙傛暟(?)璁惧�
     * 
     * @param ps
     * @param mappedStatement
     * @param boundSql
     * @param parameterObject
     * @throws java.sql.SQLException
     */
    private void setParameters(PreparedStatement ps, MappedStatement mappedStatement, BoundSql boundSql,
            Object parameterObject) throws SQLException {
        ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, boundSql);
        parameterHandler.setParameters(ps);
    }

    /**
     * 鏍规嵁鏁版嵁搴撶被鍨嬶紝鐢熸垚鐗瑰畾鐨勫垎椤祍ql
     * 
     * @param sql
     * @param page
     * @return
     */
    private String buildPageSql(String sql, PageParameter page) {
        if (page != null) {
            StringBuilder pageSql = new StringBuilder();
            if ("mysql".equals(dialect)) {
                pageSql = buildPageSqlForMysql(sql, page);
            } else if ("oracle".equals(dialect)) {
                pageSql = buildPageSqlForOracle(sql, page);
            } else {
                return sql;
            }
            return pageSql.toString();
        } else {
            return sql;
        }
    }

    /**
     * mysql鐨勫垎椤佃鍙�
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForMysql(String sql, PageParameter page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        pageSql.append(sql);
        pageSql.append(" limit " + beginrow + "," + page.getPageSize());
        return pageSql;
    }

    /**
     * 鍙傝�hibernate鐨勫疄鐜板畬鎴恛racle鐨勫垎椤�
     * 
     * @param sql
     * @param page
     * @return String
     */
    public StringBuilder buildPageSqlForOracle(String sql, PageParameter page) {
        StringBuilder pageSql = new StringBuilder(100);
        String beginrow = String.valueOf((page.getCurrentPage() - 1) * page.getPageSize());
        String endrow = String.valueOf(page.getCurrentPage() * page.getPageSize());

        pageSql.append("select * from ( select temp.*, rownum row_id from ( ");
        pageSql.append(sql);
        pageSql.append(" ) temp where rownum <= ").append(endrow);
        pageSql.append(") where row_id > ").append(beginrow);
        return pageSql;
    }

    public Object plugin(Object target) {
        // 褰撶洰鏍囩被鏄疭tatementHandler绫诲瀷鏃讹紝鎵嶅寘瑁呯洰鏍囩被锛屽惁鑰呯洿鎺ヨ繑鍥炵洰鏍囨湰韬�鍑忓皯鐩爣琚唬鐞嗙殑娆℃暟
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        } else {
            return target;
        }
    }

    public void setProperties(Properties properties) {
    }

}
