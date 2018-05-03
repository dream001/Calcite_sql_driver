package com.yonyou.mysql;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Set;

import org.apache.calcite.adapter.jdbc.JdbcSchema;
import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.schema.Table;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;
import org.apache.commons.dbcp.BasicDataSource;

import com.yonyou.util.FileLogger;
import com.yonyou.util.ProUtils;


public class MysqlQuery extends FileLogger {

    public void query() throws ClassNotFoundException, SQLException {

        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(ProUtils.getProperty("calcite.mysql.url"));
        dataSource.setUsername(ProUtils.getProperty("calcite.mysql.user"));
        dataSource.setPassword(ProUtils.getProperty("calcite.mysql.password"));
        dataSource.setDefaultCatalog(ProUtils.getProperty("calcite.mysql.catalog"));
        dataSource.setDriverClassName(ProUtils.getProperty("calcite.mysql.class"));
               
        Class.forName(ProUtils.getProperty("calcite.class"));
        Connection connection = DriverManager.getConnection(ProUtils.getProperty("calcite.conn"));
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        
        JdbcSchema jdbcSchema = JdbcSchema.create(rootSchema,  dataSource.getDefaultCatalog(), dataSource,  null, null);       
        rootSchema.add(dataSource.getDefaultCatalog(), jdbcSchema);
        
        
        
        Set<String> set = jdbcSchema.getTableNames();
        for(String str : set){
            System.out.println(str);
        }
        
        Table table = jdbcSchema.getTable("test");
        table.getJdbcTableType();
        for(String str : set){
            System.out.println(str);
        }
        
        
        Statement statement = connection.createStatement();
        String mysql_sql = "select * from \"spark_caa\".\"spark_caa\"";
        
        
        ResultSet resultSet = statement.executeQuery(mysql_sql);

        final StringBuilder buf = new StringBuilder();
        while (resultSet.next()) {
            int n = resultSet.getMetaData().getColumnCount();
            for (int i = 1; i <= n; i++) {
                buf.append(i > 1 ? "; " : "").append(resultSet.getMetaData().getColumnLabel(i)).append("=")
                        .append(resultSet.getObject(i));
            }
            System.out.println(buf.toString());
            buf.setLength(0);
        }
        resultSet.close();
        statement.close();
        connection.close();
    }
    
    
    public void insert() throws ClassNotFoundException, SQLException{
        
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setUrl(ProUtils.getProperty("calcite.mysql.url"));
        dataSource.setUsername(ProUtils.getProperty("calcite.mysql.user"));
        dataSource.setPassword(ProUtils.getProperty("calcite.mysql.password"));
        dataSource.setDefaultCatalog(ProUtils.getProperty("calcite.mysql.catalog"));
        dataSource.setDriverClassName(ProUtils.getProperty("calcite.mysql.class"));
               
        Class.forName(ProUtils.getProperty("calcite.class"));
        Connection connection = DriverManager.getConnection(ProUtils.getProperty("calcite.conn"));
        CalciteConnection calciteConnection = connection.unwrap(CalciteConnection.class);
        SchemaPlus rootSchema = calciteConnection.getRootSchema();
        
        JdbcSchema jdbcSchema =
                JdbcSchema.create(rootSchema, dataSource.getDefaultCatalog(), dataSource, null, null);
        rootSchema.add(dataSource.getDefaultCatalog(), jdbcSchema);
        
        Frameworks.ConfigBuilder configBuilder = Frameworks.newConfigBuilder();
        configBuilder.defaultSchema(rootSchema);

        FrameworkConfig frameworkConfig = configBuilder.build();

        SqlParser.ConfigBuilder paresrConfig = SqlParser.configBuilder(frameworkConfig.getParserConfig());

        paresrConfig.setCaseSensitive(false).setConfig(paresrConfig.build());

        Planner planner = Frameworks.getPlanner(frameworkConfig);

        SqlNode sqlNode = null;
        RelRoot relRoot = null;
        try {
            String sql = "select * from \"spark_caa\".\"test\"";
            sqlNode = planner.parse(sql);
            planner.validate(sqlNode);
            relRoot = planner.rel(sqlNode);
        } catch (Exception e) {
            e.printStackTrace();
        }

        RelNode relNode = relRoot.project();
        System.out.print(RelOptUtil.toString(relNode));

    }
    
    
    

    public static void main(String[] args) {
        try {
            new MysqlQuery().query();
        } catch (ClassNotFoundException | SQLException e) {
            error_log.error(e.getStackTrace());
            e.printStackTrace();
        }
    }
}
