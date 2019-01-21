package cn.mchina.robot.redis;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.io.*;

/**
 * Created with IntelliJ IDEA.
 * User: yonghui.feng
 * Date: 14-2-12
 * Time: 下午2:02
 * To change this template use File | Settings | File Templates.
 */
public class RedisService {
    @Resource
    private RedisTemplate<Serializable, Serializable> redisTemplate;

    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 向redis里面添加key-value格式的数据
     *
     * @param key   key
     * @param value value
     */

    public void set(final Serializable key, final Object value) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] key_ = RedisService.getBytesFromObject(key);
                byte[] value_ = RedisService.getBytesFromObject(value);
                connection.set(key_, value_);
                return true;
            }
        });
    }

    /**
     * 根据key从redis里面取出value
     *
     * @param key   key
     */
    public Serializable get(final Serializable key) {
        return redisTemplate.execute(new RedisCallback<Serializable>() {
            @Override
            public Serializable doInRedis(RedisConnection connection) throws DataAccessException {

                byte[] keyBytes = RedisService.getBytesFromObject(key);
                byte[] bytes = connection.get(keyBytes);
                return (Serializable) RedisService.getObjectFromBytes(bytes);
            }
        });
    }

    public void remove(final Serializable key) {
        redisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection) throws DataAccessException {
                byte[] keyBytes = RedisService.getBytesFromObject(key);
                connection.del(keyBytes);
                return true;
            }
        });
    }

    public static byte[] getBytesFromObject(Object obj) {
        if (obj == null) {
            return null;
        }
        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        ObjectOutputStream oo;
        try {
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return bo.toByteArray();
    }

    public static Object getObjectFromBytes(byte[] objBytes) {
        if (objBytes == null || objBytes.length == 0) {
            return null;
        }
        ByteArrayInputStream bi = new ByteArrayInputStream(objBytes);
        ObjectInputStream oi = null;
        try {
            oi = new ObjectInputStream(bi);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {

            return oi==null?null:oi.readObject();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static final String getRedisKey(Class c, Number key){
        return c.getName() + ":" + key;
    }
}
