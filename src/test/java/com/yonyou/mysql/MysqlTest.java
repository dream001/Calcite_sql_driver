package com.yonyou.mysql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.plan.RelOptUtil;
import org.apache.calcite.rel.RelNode;
import org.apache.calcite.rel.RelRoot;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.util.SqlString;
import org.apache.calcite.tools.FrameworkConfig;
import org.apache.calcite.tools.Frameworks;
import org.apache.calcite.tools.Planner;

import com.yonyou.core.BaseDao;
import com.yonyou.core.BaseDaoImpl;
import com.yonyou.core.JDBCConnection;

public class MysqlTest {


    private static Boolean checkSql(String sql){
        SchemaPlus rootSchema = JDBCConnection.getRootSchema();
        Frameworks.ConfigBuilder configBuilder = Frameworks.newConfigBuilder();
        configBuilder.defaultSchema(rootSchema);
        FrameworkConfig frameworkConfig = configBuilder.build();
        SqlParser.ConfigBuilder paresrConfig = SqlParser.configBuilder(frameworkConfig.getParserConfig());
        paresrConfig.setCaseSensitive(false).setConfig(paresrConfig.build());
        Planner planner = Frameworks.getPlanner(frameworkConfig);
        SqlNode sqlNode = null;
        RelRoot relRoot = null;
        try {
            sqlNode = planner.parse(sql);
            planner.validate(sqlNode);
            relRoot = planner.rel(sqlNode);
        } catch (Exception e) {
            e.printStackTrace();
        }
        RelNode relNode = relRoot.project();
        System.out.print(RelOptUtil.toString(relNode));
        return true;
    } 
    
    public static void query() {
        BaseDao dao = new BaseDaoImpl();
        String sql = "select * from \"spark_caa\".\"spark_caa\"";       
        System.out.println(sql);
        List<String> o = null;
        if(checkSql(sql)){
            List<Map<String, Object>> list = dao.query(sql, o);
            for (Map<String, Object> map : list) {
                System.out.println(map.toString());
            }
        }
    }

    public static void insert() {
        BaseDao dao = new BaseDaoImpl();
        String sql = "insert into \"spark_caa\".\"test\" (\"id\",\"name\") values (34,\'9999\'),(35,\'44\')";
        boolean list = dao.execute(sql);
        System.out.println(list);
    }
    
    
    public static void updata() {
        BaseDao dao = new BaseDaoImpl();
        String sql = "update  \"spark_caa\".\"test\" set \"name\"=\'888\' where \"id\"=3";
        boolean list = dao.execute(sql);
        System.out.println(list);
    }
    
    
    public static void delete() {
        BaseDao dao = new BaseDaoImpl();
        String sql = "delete  from \"spark_caa\".\"test\" where \"id\"=3";
        boolean list = dao.execute(sql);
        System.out.println(list);
    }
    
    
    public static void query1() throws SqlParseException {
        String sql = "select * from \"spark_caa\".\"test\"";
        System.out.println(sql);
        CalciteConnection calciteConnection = JDBCConnection.getInstance().getConnection();
        Statement statement;
        try {
            statement = calciteConnection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while(resultSet.next()){
                int id = resultSet.getInt(1);
                String name = resultSet.getString(2);
                System.out.println("id:"+id+" 姓名："+name);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    
    public static void main(String[] args) throws SqlParseException {
        query1();
    }

}
