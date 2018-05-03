package com.yonyou.core;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;
import com.yonyou.util.Constant;
import com.yonyou.util.FileLogger;

/**
 * 返回信息公共接口
 * @ClassName: Message
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年4月27日
 */
public class Message extends FileLogger{

    /**
     * 返回信息处理
     * @Title: callMessage
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @param data
     * @param hasValidData
     * @return
     */
    public Map<String, Object> callMessage(Object data,boolean hasValidData) {
        if (hasValidData) {
            return callSuccessMessage(data);
        } else {
           return callFailMessage(data);
        }
    }
    
    /**
     * 返回成功信息处理
     * @Title: callSuccessMessage
     * @Description: TODO(方法简要描述，必须以句号为结束)
     * @author: caozq
     * @since: (开始使用的版本)
     * @param data
     * @return
     */
    public Map<String, Object> callSuccessMessage(Object data) {
        Map<String, Object> returnMap = new HashMap<String, Object>();
        returnMap.put("code", Constant.STATUS_OK);
        returnMap.put("msg", "success");
        returnMap.put("data", data);
        info_log.info("返回结果：" + JSONObject.toJSONString(returnMap));
        return returnMap;
    }

	/**
	 * 返回失败信息处理
	 * @Title: callFailMessage
	 * @Description: TODO(方法简要描述，必须以句号为结束)
	 * @author: caozq
	 * @since: (开始使用的版本)
	 * @param data
	 * @return
	 */
	public Map<String, Object> callFailMessage(Object data) {
	    Map<String, Object> returnMap = new HashMap<String, Object>();
	    returnMap.put("code", Constant.STATUS_INNER_ERROR);
        returnMap.put("msg", "data is null");
        returnMap.put("data", null);
        info_log.info("返回结果：" + JSONObject.toJSONString(returnMap));
        return returnMap;
	}

}
