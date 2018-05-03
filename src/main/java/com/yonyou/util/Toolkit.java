package com.yonyou.util;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import net.sf.json.util.JSONTokener;

/**
 * 工具类
 * @ClassName: Toolkit
 * @Description: TODO(类简要描述，必须以句号为结束)
 * @author caozq
 * @date 2018年4月27日
 */
public class Toolkit {
    private static final int RADIX = 16;
    private static final String SEED = "0933910847463829827159347601486730416058";

    public static final String NUMBER = "NUMBER";
    public static final String STRING = "STRING";

    private static final Logger logger = LogManager.getLogger(Toolkit.class);

    public static boolean isEmpty(String s) {
        if (null != s && s.trim().length() > 0) {
            return false;
        }
        return true;
    }

    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    /**
     * 提供精确的减法运算。
     * @param v1
     *        被减数
     * @param v2
     *        减数
     * @return 两个参数的差
     */

    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     * @param v1
     *        被乘数
     * @param v2
     *        乘数
     * @return 两个参数的积
     */

    public static double mul(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 用分隔符连接
     * @param args
     * @param spliter
     * @return
     */
    public static String join(Collection<String> args, String spliter) {
        if (args != null && !args.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Iterator<String> it = args.iterator(); it.hasNext();) {
                sb.append(it.next());
                if (it.hasNext()) {
                    sb.append(spliter);
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    /**
     * 用分隔符连接
     * @param args
     * @param spliter
     * @return
     */
    public static String join(String[] args, String spliter) {
        if (args != null && args.length > 0) {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < args.length; i++) {
                sb.append(args[i]);
                if (i != args.length - 1) {
                    sb.append(spliter);
                }
            }
            return sb.toString();
        } else {
            return null;
        }
    }

    public static final String encryptStr(String str) {
        if (str == null) return "";
        if (str.length() == 0) return "";

        BigInteger bi_passwd = new BigInteger(str.getBytes());

        BigInteger bi_r0 = new BigInteger(SEED);
        BigInteger bi_r1 = bi_r0.xor(bi_passwd);

        return bi_r1.toString(RADIX);
    }


    /**
     * 过滤敏感词汇
     * @param str
     * @return
     */
    public static String filterSensitiveWord(String str) {
        if (!isEmpty(str)) {
            str = str.replace("蜜罐", "").replace("蜜蜂", "").replace("聚信立", "").replace("证通", "");
        }
        return "原因:" + str;
    }

    public static final String decryptStr(String encrypted) {
        if (encrypted == null) return "";
        if (encrypted.length() == 0) return "";

        BigInteger bi_confuse = new BigInteger(SEED);

        BigInteger bi_r1 = new BigInteger(encrypted, RADIX);
        BigInteger bi_r0 = bi_r1.xor(bi_confuse);

        return new String(bi_r0.toByteArray());
    }


    /**
     * 获取当前时间 时间格式为yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String nowTime = sdf.format(now);
        return nowTime;
    }

    /**
     * 时间转化成毫秒
     * @param formateDate
     * @return
     */
    public static Long getMsFromDate(String formateDate) {
        Date parse = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            parse = sdf.parse(formateDate);
            return parse.getTime();
        } catch (ParseException e) {
            logger.error("Convert date to ms occur error", e);
        }
        return -1L;
    }


    /**
     * 判断是否过期
     * @param beginMills
     * @param days
     * @return
     */
    public static boolean isExpired(long beginMills, int days) {
        long now = System.currentTimeMillis();
        return ((now - beginMills) / (24 * 3600 * 1000) >= days);
    }

    /**
     * 判断是否过期
     * @param beginMills
     *        yyyy-MM-dd HH:mm:ss
     * @param days
     * @return
     */
    public static boolean isExpired(String beginTimes, int days) {
        long now = System.currentTimeMillis();
        return ((now - getMsFromDate(beginTimes)) / (24 * 3600 * 1000) >= days);
    }

    public static boolean isJSONArray(String json) {
        Object obj = new JSONTokener(json).nextValue();
        if (obj instanceof net.sf.json.JSONArray) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 将一个数组添加到一个List中
     * @param <T>
     * @param <S>
     * @param lst
     * @param arr
     * @return
     * @since NC6.0
     */
    public static <T, S extends T> List<T> addArrayToList(List<T> lst, S[] arr) {
        if (lst != null && arr != null) {
            for (int i = 0; i < arr.length; i++) {
                lst.add(arr[i]);
            }
        }
        return lst;
    }

    /**
     * 将一个数组添加到一个List中
     * @param <T>
     * @param <S>
     * @param lsta
     * @param lstb
     * @return
     * @author wangxy
     * @since NC6.0
     */
    public static <T, S extends T> List<T> addListToList(List<T> lsta, List<T> lstb) {
        if (lsta != null && lstb != null) {
            for (int i = 0; i < lstb.size(); i++) {
                lsta.add(lstb.get(i));
            }
        }
        return lsta;
    }

    /**
     * 将一个新元素加入数组的末尾。
     * @param <T>
     * @param <S>
     * @param oldData
     * @param o
     * @return
     * @author wangxy
     * @since NC6.0
     */
    public static <T, S extends T> T[] arrayAdd(T[] arr, S obj) {
        T[] newData;
        newData = Toolkit.arrayCapacity(arr, 1);
        newData[arr.length] = obj;
        return newData;
    }

    /**
     * 将新元素加入数组的指定位置。
     * @param <T>
     * @param <S>
     * @param arr
     * @param obj
     * @param index
     * @return
     * @author wangxy
     * @since NC6.0
     */
    public static <T, S extends T> T[] arrayAdd(T[] arr, S obj, int index) {
        int size = arr.length;
        if (index > size || index < 0) throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);

        T[] newData;
        newData = Toolkit.arrayCapacity(arr, 1);
        System.arraycopy(newData, index, newData, index + 1, size - index);
        newData[index] = obj;
        return newData;
    }

    /**
     * 数组容量扩大。 创建日期：(2002-5-30 10:48:40)
     * @param <T>
     * @param arr
     * @param increase
     * @return
     * @since NC6.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] arrayCapacity(T[] arr, int increase) {
        int oldsize = arr.length;
        int size = oldsize + increase;
        T[] newArr = (T[]) Array.newInstance(arr.getClass().getComponentType(), size);

        System.arraycopy(arr, 0, newArr, 0, oldsize);
        return newArr;
    }

    /**
     * <p>
     * 获取数组的长度，如果为空，则长度为0。
     * @param obj
     * @return
     * @since NC6.0
     */
    public static int getArrayLength(Object[] obj) {
        return obj == null ? 0 : obj.length;
    }

    /**
     * <p>
     * 判断是否是空数组。
     * <p>
     * 作者：qbh <br>
     * 日期：2006-1-5
     * @param ss
     * @return
     */
    public static boolean isNull(Object[] array) {
        if (array == null || array.length == 0) {
            return true;
        }

        for (int i = 0; i < array.length; i++) {
            if (array[i] != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>
     * 判断是否是空数组。
     * <p>
     * 作者：liuyga <br>
     * 日期：2011-6-22
     * @param <T>
     * @param ss
     * @return
     */
    public static <T> boolean isNull(List<T> list) {
        if (list == null || list.size() == 0) {
            return true;
        }

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) != null) {
                return false;
            }
        }

        return true;
    }

    /**
     * 合并两个数组，将数组2合并到数组1的后面
     * @param <T>
     * @param <S>
     * @param array1
     * @param array2
     * @return
     * @author wangxy
     * @since NC6.0
     */
    public static <T, S extends T> T[] mergeArray(T[] array1, S[] array2) {
        if (array1 == null && array2 == null) {
            return null;
        }
        if (isNull(array1)) {
            return array2;
        }
        if (isNull(array2)) {
            return array1;
        }

        T[] array = Toolkit.arrayCapacity(array1, array2.length);
        System.arraycopy(array2, 0, array, array1.length, array2.length);

        return array;
    }

    /**
     * 将一个数组压入堆栈。
     * @param stk
     * @param arr
     * @param isOrder
     *        <li>true 表示顺序 -- 按数组下标，最小的在栈顶，最大的在栈底
     *        <li>false 表示倒序 --
     *        最大载栈顶，最小在栈底
     * @return
     * @since NC3.5
     * @see （关联类）
     */
    public static <T, S extends T> Stack<T> pushArrrayToStack(Stack<T> stk, S[] arr, boolean isOrder) {
        if (isOrder) {
            for (int i = arr.length - 1; i >= 0; i--) {
                stk.push(arr[i]);
            }
        } else {
            for (int i = 0; i < arr.length; i++) {
                stk.push(arr[i]);
            }
        }

        return stk;
    }

    /**
     * 数组收缩，取出空值元素
     * @param <T>
     * @param arr
     * @return
     * @author wangxy
     * @since NC6.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] shrinkArray(T[] arr) {
        if (arr == null) {
            return null;
        }
        ArrayList<T> lst = new ArrayList<T>();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                lst.add(arr[i]);
            }
        }
        if (lst.size() == arr.length) {
            return arr;
        }

        T[] shrankArr = arr.getClass() == Object[].class
                ? (T[]) new Object[lst.size()]
                : (T[]) Array.newInstance(arr.getClass().getComponentType(), lst.size());
        return lst.toArray(shrankArr);
    }

    /**
     * 数组收缩，去除数组的前n个元素
     * @param <T>
     * @param arr
     * @param n
     * @return
     * @author wangxy
     * @since NC6.0
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] shrinkArray(T[] arr, int n) {
        if (arr == null) {
            return null;
        }
        if (n > arr.length) {
            throw new ArrayIndexOutOfBoundsException(" n > arr.length");
        }
        T[] array = (T[]) Array.newInstance(arr.getClass().getComponentType(), arr.length - n);
        System.arraycopy(arr, n, array, 0, arr.length - n);
        return array;
    }

    /**
     * 构造传入参数的字符串数组
     * @param str
     * @return
     * @author zhufengg
     * @since NC6.1
     */
    public static String[] strArr(String... str) {
        return str;
    }

    public static String genRandomNum(int pwd_len) {
        // 35是因为数组是从0开始的，26个字母+10个数字
        final int maxNum = 36;
        int i; // 生成的随机数
        int count = 0; // 生成的密码的长度
        char[] str = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};

        StringBuffer pwd = new StringBuffer("");
        Random r = new Random();
        while (count < pwd_len) {
            // 生成随机数，取绝对值，防止生成负数，

            i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1

            if (i >= 0 && i < str.length) {
                pwd.append(str[i]);
                count++;
            }
        }

        return pwd.toString();
    }


    /**
     * 作为流水单
     * 30位transID 17+13
     * @return
     */
    public static String createTransId() {
        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmssSSS");
        return sdf.format(dd) + genRandomNum(13);
    }

    /**
     * AES加密
     * @param encodeRules
     *        miyue
     * @param content
     * @return
     */
    public static String AESEncode(String content, String encodeRules) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] byte_encode = content.getBytes("utf-8");
            byte[] byte_AES = cipher.doFinal(byte_encode);
            String AES_encode = new String(Base64.getEncoder().encode(byte_AES));
            return AES_encode;
        } catch (Exception e) {
        }
        return null;
    }


    /**
     * AES解密
     * @param encodeRules
     *        密钥
     * @param content
     * @return
     */
    public static String AESDecode(String content, String encodeRules) {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecretKey original_key = keygen.generateKey();
            byte[] raw = original_key.getEncoded();
            SecretKey key = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] byte_content = Base64.getDecoder().decode(content);
            byte[] byte_decode = cipher.doFinal(byte_content);
            String AES_decode = new String(byte_decode, "utf-8");
            return AES_decode;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 订单号
     * 30位transID 17+13
     * @return
     */
    public static String createOrderNO() {
        Date dd = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(dd) + genRandomNum(8);
    }


    public static String getSystemVariable(String key) {
        String sysVar = null;
        sysVar = System.getProperty(key);
        return sysVar;
    }

    @SuppressWarnings("rawtypes")
    public static void removeNullValue(Map map) {
        Set set = map.keySet();
        for (Iterator iterator = set.iterator(); iterator.hasNext();) {
            Object obj = (Object) iterator.next();
            Object value = (Object) map.get(obj);
            remove(value, iterator);
        }
    }

    @SuppressWarnings("rawtypes")
    private static void remove(Object obj, Iterator iterator) {
        if (obj instanceof String) {
            String str = (String) obj;
            if (Toolkit.isEmpty(str)) {
                iterator.remove();
            }
        } else if (obj instanceof Collection) {
            Collection col = (Collection) obj;
            if (col == null || col.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Map) {
            Map temp = (Map) obj;
            if (temp == null || temp.isEmpty()) {
                iterator.remove();
            }

        } else if (obj instanceof Object[]) {
            Object[] array = (Object[]) obj;
            if (array == null || array.length <= 0) {
                iterator.remove();
            }
        } else {
            if (obj == null) {
                iterator.remove();
            }
        }
    }

    public static String genAccessKey(String account) {
        return AESEncode(account, "DAISY_##_RISK_##");
    }

}
