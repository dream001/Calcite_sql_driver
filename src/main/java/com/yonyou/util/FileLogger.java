package com.yonyou.util;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;

/**
 * 
 * @ClassName: FileLogger
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年3月22日
 */
public abstract class FileLogger {

    protected static  Logger error_log = FileLogger.getFileLogger("error_log");
    protected static  Logger info_log = FileLogger.getFileLogger("info_log");
    
    
    /**
     * 获取INFO 类型的日志
     * @Title: getFileInfoLogger
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @param name
     * @return
     */
    public static Logger getFileLogger(String name){
        return FileLogger.getFileLogger(name, Level.INFO, false, 20, "10MB");
    }
    
    /**
     * 获取具体的日志
     * @Title: getFileLogger
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @param name
     * @param level
     * @param isAdditivity
     * @param maxBackups
     * @param maxFileSize
     * @return
     */
    public static synchronized Logger getFileLogger(String name, Level level, boolean isAdditivity, int maxBackups, String maxFileSize) {
        Logger logger = Logger.getLogger(name);
        if (logger.getAllAppenders().hasMoreElements()){
            return logger;
        }
        
        logger.removeAllAppenders();
        logger.setLevel(level);
        logger.setAdditivity(isAdditivity);
        // 生成新的Appender
        RollingFileAppender appender = new RollingFileAppender();
        
        // log的输出形式
        PatternLayout layout = new PatternLayout();
        layout.setConversionPattern("[%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} $$thread=[%t] %M(%L):%m%n");
        appender.setLayout(layout);
        // log输出路径  生产环境走自己的，开发环境走默认的
        String logPath = ProUtils.getProperty("log.path");
        File dir = new File(logPath);
        if(!dir.exists()){
            logPath = System.getProperty("user.dir");
        }
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd");
        appender.setFile(logPath + "/sql-driver/" + name + matter.format(new Date()) +".log");
        appender.setMaxBackupIndex(maxBackups);
        appender.setMaxFileSize(maxFileSize);
        appender.setEncoding("UTF-8");
        appender.setAppend(true);
        // 适用当前配置
        appender.activateOptions();
        // 将新的Appender加到Logger中
        logger.addAppender(appender);
        return logger;
    }
    
    
    public static void main(String[] args) {
        String logPath = ProUtils.getProperty("log.path");
        
              
        SimpleDateFormat matter=new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(logPath + "/spark_caa/" + "000" + matter.format(new Date()) +".log");
    }
    
}
