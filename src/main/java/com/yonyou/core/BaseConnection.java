package com.yonyou.core;

import org.apache.calcite.jdbc.CalciteConnection;

/**
 * 所有jdbc 都需要实现这个方法
 * @ClassName: BaseConnection
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年4月28日
 */
public interface BaseConnection {

    public CalciteConnection getConnection();
    
}
