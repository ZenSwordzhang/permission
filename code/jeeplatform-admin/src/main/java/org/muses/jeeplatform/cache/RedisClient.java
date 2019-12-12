package org.muses.jeeplatform.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;

@Component
public class RedisClient {

    @Autowired
    private RedisTemplate redisTemplate;

    public void setValue(String key, String value)throws Exception{
        redisTemplate.opsForValue().set(key, value);
    }

    public String getValue(String key)throws Exception{
        return redisTemplate.opsForValue().get(key).toString();
    }


}
