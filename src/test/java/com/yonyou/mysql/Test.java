package com.yonyou.mysql;

import static org.junit.Assert.assertTrue;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.calcite.jdbc.CalciteConnection;
import org.apache.calcite.sql.SqlBasicCall;
import org.apache.calcite.sql.SqlDialect;
import org.apache.calcite.sql.SqlNode;
import org.apache.calcite.sql.SqlNodeList;
import org.apache.calcite.sql.SqlSelect;
import org.apache.calcite.sql.dialect.CalciteSqlDialect;
import org.apache.calcite.sql.fun.SqlStdOperatorTable;
import org.apache.calcite.sql.parser.SqlParseException;
import org.apache.calcite.sql.parser.SqlParser;
import org.apache.calcite.sql.parser.SqlParserPos;
import org.apache.calcite.sql.util.SqlString;
import org.apache.commons.lang.StringEscapeUtils;

import com.yonyou.core.JDBCConnection;


public class Test {

    public static void Test1() throws Exception{

        String sql = "SELECT a FROM tt.tbl";
        SqlParser sqlParser = SqlParser.create(sql);
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlString sqlString = sqlNode.toSqlString(SqlDialect.CALCITE);
        System.out.println("sqlString: " + sqlString.getSql());
        
        SqlParserPos POS = new SqlParserPos(0, 0);
        SqlNodeList keywordList = new SqlNodeList(POS);
        
        // Retrieve relevant projections
        SqlNodeList selectionList = ((SqlSelect) sqlNode).getSelectList();
        SqlNodeList newSelectionList = new SqlNodeList(POS);
        newSelectionList.add(selectionList.get(0));
        
        // Retrieve table
        SqlNode table = ((SqlSelect) sqlNode).getFrom();
        
        // Make Reference Sql
        SqlNode whereClause = null;
        SqlNodeList groupByList = null;
        SqlNode having = null;
        SqlNodeList windowDecls = null;
        SqlNodeList orderByList = null;
        SqlNode offset = null;
        SqlNode fetch = null;
        
        SqlSelect refSelection = new SqlSelect(
              POS,
              keywordList,
              newSelectionList,
              table,
              whereClause,
              groupByList,
              having,
              windowDecls,
              orderByList,
              offset,
              fetch
            );
        
        SqlString sqlString2 = refSelection.toSqlString(SqlDialect.CALCITE);
        System.out.println("sqlString2: " + sqlString2.getSql());
        
        assertTrue(sqlString.equals(sqlString2));
        
        // Make Where clause
        SqlNode[] whereOperands = {selectionList.get(0), (SqlNode) refSelection.clone()};
        SqlNode where = new SqlBasicCall(SqlStdOperatorTable.EQUALS, whereOperands, POS);
     
        SqlSelect refSelection2 = (SqlSelect) refSelection.clone();
        refSelection2.setWhere(where);
        
        SqlString sqlString3 = refSelection2.toSqlString(SqlDialect.CALCITE);
        System.out.println("sqlString3: " + sqlString3.getSql());
      
    }
    
    
    public  static void yey() throws SQLException, SqlParseException{
        CalciteConnection connection = JDBCConnection.getInstance().getConnection();
        Statement statement = connection.createStatement(ResultSet.TYPE_FORWARD_ONLY,
            ResultSet.CONCUR_READ_ONLY);
        
        SqlParser sqlParser = SqlParser.create("select * from spark_caa.test");
        SqlNode sqlNode = sqlParser.parseStmt();
        SqlString sqlString = sqlNode.toSqlString(CalciteSqlDialect.DEFAULT);
        String sql = sqlString.getSql();
        
        ResultSet resultSet = statement.executeQuery(sql);
        int count = 0;
        while (resultSet.next()) {
          count += 1;
        }
        assertTrue(count > 0);
        resultSet.close();
    }
    
    
    public static void sqlInteger(){
        String sql="1' or '1'='1";  
        System.out.println("防SQL注入:"+StringEscapeUtils.escapeSql(sql)); //防SQL注入  
        System.out.println("转义HTML,注意汉字:"+StringEscapeUtils.escapeHtml("<font>chen磊  xing</font>"));    //转义HTML,注意汉字  
        System.out.println("反转义HTML:"+StringEscapeUtils.unescapeHtml("<font>chen磊  xing</font>"));  //反转义HTML  
        System.out.println("转成Unicode编码："+StringEscapeUtils.escapeJava("陈磊兴"));     //转义成Unicode编码  
        System.out.println("转义XML："+StringEscapeUtils.escapeXml("<name>陈磊兴</name>"));   //转义xml  
        System.out.println("反转义XML："+StringEscapeUtils.unescapeXml("<name>陈磊兴</name>"));    //转义xml  
    }
    public static void main(String[] args) throws Exception {
        new Test().yey();
        //sqlInteger();

    }

}
