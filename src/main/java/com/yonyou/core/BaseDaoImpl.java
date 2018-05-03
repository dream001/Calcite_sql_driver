package com.yonyou.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.calcite.adapter.java.ReflectiveSchema;
import org.apache.calcite.schema.Schema;

public class BaseDaoImpl implements BaseDao{

    private Connection conn = JDBCConnection.getInstance().getConnection();
    
    @Override
    public List<Map<String, Object>> query(String sql, Object... params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            int paramsIndex = 1;
//            if(null != params){
//                for(Object p : params){
//                    pst.setObject(paramsIndex++, p);
//                }
//            }
            rs = pst.executeQuery();
            ResultSetMetaData rst = rs.getMetaData();
            int column = rst.getColumnCount();
            List<Map<String,Object>> rstList = new ArrayList<Map<String,Object>>();
            while(rs.next()){
                Map<String,Object> m = new HashMap<String,Object>();
                for(int i=1;i<=column;i++){
                    m.put(rst.getColumnName(i), rs.getObject(i));
                }
                rstList.add(m);
            }
            return rstList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally{
            JDBCConnection.close(rs, pst, conn);
        }
    }

    @Override
    public List<Map<String, Object>> query(String sql, List<Object> params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            int paramsIndex = 1;
//            if(null != params){
//                for(Object p : params){
//                    pst.setObject(paramsIndex++, p);
//                }
//            }
            rs = pst.executeQuery();
            ResultSetMetaData rst = rs.getMetaData();
            int column = rst.getColumnCount();
            List<Map<String,Object>> rstList = new ArrayList<Map<String,Object>>();
            while(rs.next()){
                Map<String,Object> m = new HashMap<String,Object>();
                for(int i=1;i<=column;i++){
                    m.put(rst.getColumnName(i), rs.getObject(i));
                }
                rstList.add(m);
            }
            return rstList;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }finally{
            JDBCConnection.close(rs, pst, conn);
        }
    }

    @Override
    public long queryByPage(String sql, Object... params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            int paramsIndex = 1;
            if(null != params){
                for(Object p : params){
                    pst.setObject(paramsIndex++, p);
                }
            }
            rs = pst.executeQuery();
            while(rs.next()){
                return Long.valueOf(rs.getLong(1));
            }
            return 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

    @Override
    public boolean insert(String sql, Object... params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            int paramsIndex = 1;
            if(null != params){
                for(Object p : params){
                    pst.setObject(paramsIndex++, p);
                }
            }
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            JDBCConnection.close(rs, pst, conn);
        }
    }

    @Override
    public boolean update(String sql, Object... params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            int paramsIndex = 1;
            if(null != params){
                for(Object p : params){
                    pst.setObject(paramsIndex++, p);
                }
            }
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            JDBCConnection.close(rs, pst, conn);
        }
    }

    @Override
    public boolean delete(String sql, Object... params) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            int paramsIndex = 1;
            if(null != params){
                for(Object p : params){
                    pst.setObject(paramsIndex++, p);
                }
            }
            pst.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            JDBCConnection.close(rs, pst, conn);
        }
    }
    
    
    public Boolean execute(String sql) {
        PreparedStatement pst = null;
        ResultSet rs = null;
        try {
            pst = conn.prepareStatement(sql);
            return pst.executeUpdate() >=1?true:false;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }finally{
            JDBCConnection.close(rs, pst, conn);
        }
    }
    
    
    public static void main(String[] args) {
    }

}
