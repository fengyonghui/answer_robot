package cn.mchina.robot.web.util;

import cn.mchina.robot.common.util.JSONUtil;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-1-16
 * Time: 上午11:34
 * To change this template use File | Settings | File Templates.
 */

@Component
public class GsonHttpMessageConverter extends MappingJacksonHttpMessageConverter {
    public static final Charset DEFAULT_CHARSET = Charset.forName("UTF-8");

    @Override
    protected Object readInternal(Class<? extends Object> clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        try {
//            Gson gson = JSONUtil.getGson();
            return JSONUtil.fromJson(StringUtils.convertStreamToString(inputMessage.getBody()), clazz);
        } catch (JsonParseException e) {
            throw new HttpMessageNotReadableException("Could not read JSON: " + e.getMessage(), e);
        }
    }

    @Override
    protected void writeInternal(Object o, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        Type genericType = TypeToken.get(o.getClass()).getType();

        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputMessage.getBody(), DEFAULT_CHARSET));
        try {
            writer.append(JSONUtil.toJson(o, genericType));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            writer.flush();
            writer.close();
        }
    }
}
