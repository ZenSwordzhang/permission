package com.zsx.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component("redisCache")
public class RedisCache {
	
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 从Redis缓存获取数据
	 * @param redisKey
	 * @return
	 */
	public Object getDataFromRedis(String redisKey){
		return redisTemplate.opsForValue().get(redisKey);
	}
	
	/**
	 * 保存数据到Redis
	 * @param redisKey
	 */
	public boolean saveDataToRedis(String redisKey,Object obj){
		return redisTemplate.opsForValue().setIfAbsent(redisKey, obj);
	}
	

}
