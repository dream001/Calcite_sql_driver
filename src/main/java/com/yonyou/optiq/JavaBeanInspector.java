package com.yonyou.optiq;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * 
 * @ClassName: JavaBeanInspector
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年4月27日
 */
public class JavaBeanInspector {

  /**
   * Given method, determines if the method is a getter for an eligible field.
   * 
   * @param method
   *          Java Method
   * @return boolean representing eligible or not.
   */
  public static boolean checkMethodEligiblity(Method method) {
    if ((method.getName().startsWith("get"))
        && ((method.getReturnType() == Integer.class)
            || (method.getReturnType() == String.class)
            || (method.getReturnType() == Float.class) || (method
            .getReturnType() == Date.class))) {
      return true;
    } else {
      return false;
    }
  }

}
