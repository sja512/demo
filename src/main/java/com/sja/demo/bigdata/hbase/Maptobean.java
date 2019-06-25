package com.sja.demo.bigdata.hbase;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName Maptobean
 * @Date 2019/6/17 10:49
 * @describe
 **/
public class Maptobean {
    private final static String STRING_Long = "java.lang.Long";
    private final static String STRING_long = "long";
    private final static String STRING_String = "java.lang.String";
    private final static String STRING_Integer = "java.lang.Integer";
    private final static String STRING_int = "int";
    private final static String STRING_Boolean = "java.lang.Boolean";
    private final static String STRING_boolean = "boolean";
    private final static String STRING_Date = "java.util.Date";
    private final static String STRING_Short = "java.lang.Short";
    private final static String STRING_short = "short";
    private final static String STRING_Float = "java.lang.Float";
    private final static String STRING_float = "float";
    private final static String STRING_double = "double";
    private final static String STRING_Double = "java.lang.Double";


    /**
     * @descript map<String,String> 转化为实体类对象
     */
    public static Object map2JavaBean(Class<?> clazz, Map<String, String> map) throws Exception {
        Object javabean = clazz.newInstance(); // 构建对象
        Method[] methods = clazz.getMethods(); // 获取所有方法
        for (Method method : methods) {
            method.setAccessible(true);
            if (method.getName().startsWith("set")) {

                String field = method.getName(); // 截取属性名
                field = field.substring(field.indexOf("set") + 3);
                field = field.toLowerCase().charAt(0) + field.substring(1);

                if (map.containsKey(field)) {
                    Class[] paramTypes = method.getParameterTypes();
                    for (Class clz : paramTypes) {
                        if (clz == Long.class)
                            method.invoke(javabean, Long.valueOf(map.get(field)));
                        if (clz == Integer.class)
                            method.invoke(javabean, Integer.valueOf(map.get(field)));
                        if (clz == String.class)
                            method.invoke(javabean, map.get(field));
                    }
                }
            }
        }
        return javabean;
    }

    public static Object stringmap2JavaBean(Class<?> clazz, Map<String, String> map) throws Exception {
        Object javabean = clazz.newInstance(); // 构建对象
        Field[] fields= clazz.getDeclaredFields();
        Map<String,String> filedMap = new HashMap();
        for(int i=0;i<fields.length;i++){
            filedMap.put(fields[i].getName(),fields[i].getType().toString());
        }
//        String str = JSONObject.toJSONString(map);
//        javabean = JSONObject.parseObject(str, clazz);
        Method[] methods = clazz.getMethods(); // 获取所有方法
        for (Method method : methods) {
            if (method.getName().startsWith("set")) {
                String field = method.getName(); // 截取属性名
                field = field.substring(field.indexOf("set") + 3);
                field = field.toLowerCase().charAt(0) + field.substring(1);
                String filedType = filedMap.get(field);
                if (map.containsKey(field)) {
                    method.invoke(javabean,getObjectValue(filedType,map.get(field)));
                }
            }
        }
        return javabean;
    }
    public static Object getObjectValue(String type,String val){
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        String filedType = getFieldType(type);
        if(StringUtils.isBlank(val)){
            return null;
        }else {
            switch (filedType){
                case STRING_long:
                    return Long.parseLong(val);
                case STRING_int:
                    return Integer.parseInt(val);
                case STRING_float:
                    return Float.parseFloat(val);
                case STRING_short:
                    return Short.parseShort(val);
                case STRING_boolean:
                    return Boolean.parseBoolean(val);
                case STRING_String:
                    return val;
                case STRING_double:
                    return Double.parseDouble(val);
                case STRING_Date:
                    try{
                        return df.parse(val);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    return null;
                default:
                    return null;
            }
        }
    }
    public static String getFieldType(String type){
        if(type.indexOf(STRING_Long)!= -1 || type.indexOf(STRING_long)!= -1){
            return STRING_long;
        }else if(type.indexOf(STRING_Double)!= -1 || type.indexOf(STRING_double)!= -1){
            return STRING_double;
        } else if(type.indexOf(STRING_Integer)!= -1 || type.indexOf(STRING_int)!= -1){
            return STRING_int;
        }else if(type.indexOf(STRING_Boolean)!= -1 || type.indexOf(STRING_boolean)!= -1){
            return STRING_boolean;
        }else if(type.indexOf(STRING_Short)!= -1 || type.indexOf(STRING_short)!= -1){
            return STRING_short;
        }else if(type.indexOf(STRING_Float)!= -1 || type.indexOf(STRING_float)!= -1){
            return STRING_float;
        }else if(type.indexOf(STRING_Date)!= -1){
            return STRING_Date;
        }else if(type.indexOf(STRING_String)!= -1){
            return STRING_String;
        }
        return null;
    }
}
