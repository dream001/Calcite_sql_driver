package com.yonyou.core;

import java.util.List;
import java.util.Map;

public interface BaseDao {

    public List<Map<String,Object>> query(String sql,Object...params);
    
    public List<Map<String,Object>> query(String sql,List<Object> params);
    
    public long queryByPage(String sql,Object...params);
    
    public boolean insert(String sql,Object...params);
    
    public boolean update(String sql,Object...params);
    
    public boolean delete(String sql,Object...params);
    
    public Boolean execute(String sql);
}
