package com.yonyou.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.commons.dbcp.BasicDataSource;

import com.yonyou.util.FileLogger;
import com.yonyou.util.ProUtils;

/**
 * @ClassName: JDBCConnection
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年4月28日
 */
public class JDBCConnection extends FileLogger implements BaseConnection {

    // 静态成员变量，支持单态模式
    private static JDBCConnection jdbc = null;

    private static Connection connection = null;
    private static CalciteConnection calciteConnection = null;
    private static BasicDataSource dataSource = null;

    static {
        try {
            dataSource = new BasicDataSource();
            dataSource.setUrl(ProUtils.getProperty("calcite.url"));
            dataSource.setUsername(ProUtils.getProperty("calcite.user"));
            dataSource.setPassword(ProUtils.getProperty("calcite.password"));
            dataSource.setDefaultCatalog(ProUtils.getProperty("calcite.catalog"));
            dataSource.setDriverClassName(ProUtils.getProperty("calcite.class"));
        } catch (Exception e) {
            error_log.error(e.getStackTrace());
            e.printStackTrace();
        }
    }

    private JDBCConnection() {}

    /**
     * 
     * @Title: getInstance
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @return
     */
    public static JDBCConnection getInstance() {
        if (jdbc == null) {
            synchronized (JDBCConnection.class) {
                if (jdbc == null) {
                    jdbc = new JDBCConnection();
                }
            }
        }
        return jdbc;
    }
    @Override
    public CalciteConnection getConnection() {
        try {
            Class.forName(ProUtils.getProperty("calcite.class.path"));
            connection = DriverManager.getConnection(ProUtils.getProperty("calcite.conn"));
            calciteConnection = connection.unwrap(CalciteConnection.class);
            SchemaPlus rootSchema = calciteConnection.getRootSchema();
            JdbcSchema jdbcSchema =
                    JdbcSchema.create(rootSchema, dataSource.getDefaultCatalog(), dataSource, null, null);
            rootSchema.add(dataSource.getDefaultCatalog(), jdbcSchema);
            if (null == connection) {
                error_log.error("Can not load jdbc and get connection.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            error_log.error(e.getStackTrace());
            e.printStackTrace();
        }
        return calciteConnection;
    }
    
    /**
     * 
     * @Title: getRootSchema
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @return
     */
    public static SchemaPlus getRootSchema(){
        SchemaPlus rootSchema = null;
        try {
            Class.forName(ProUtils.getProperty("calcite.class"));
            connection = DriverManager.getConnection(ProUtils.getProperty("calcite.conn"));
            calciteConnection = connection.unwrap(CalciteConnection.class);
            rootSchema = calciteConnection.getRootSchema();
            JdbcSchema jdbcSchema =
                    JdbcSchema.create(rootSchema, dataSource.getDefaultCatalog(), dataSource, null, null);
            rootSchema.add(dataSource.getDefaultCatalog(), jdbcSchema);
            return rootSchema;
        } catch (ClassNotFoundException | SQLException e) {
            error_log.error(e.getStackTrace());
            e.printStackTrace();
        }
        return rootSchema;
    }

    /**
     * 
     * @Title: close
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @param rs
     * @param pst
     * @param conn
     */
    public static void close(ResultSet rs, PreparedStatement pst, Connection conn) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                error_log.error(e.getStackTrace());
            }
            rs = null;
        }
        if (pst != null) {
            try {
                pst.close();
            } catch (SQLException e) {
                error_log.error(e.getStackTrace());
            }
            pst = null;
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                error_log.error(e.getStackTrace());
            }
            conn = null;
        }
    }
}
