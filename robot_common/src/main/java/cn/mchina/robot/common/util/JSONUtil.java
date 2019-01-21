package cn.mchina.robot.common.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
import java.sql.Timestamp;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-1-15
 * Time: 下午6:27
 * To change this template use File | Settings | File Templates.
 */
public final class JSONUtil {
    private static Gson gson;

    private JSONUtil(){}

    private static Gson _getGson(){
        if (gson == null){
            gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").registerTypeAdapter(Timestamp.class, new GsonTimestampTypeAdapter()).registerTypeAdapter(java.sql.Date.class, new GsonSqlDateTypeAdapter()).create();
        }
       return gson;
    }

    public static <T> T fromJson(String json, Class<T> tClass){
         return _getGson().fromJson(json, tClass);
    }

    public static String toJson(Object o, Type type){
        return _getGson().toJson(o, type);
    }

    public static String toJson(Object o){
        return _getGson().toJson(o);
    }
}
