package com.common;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @Author xcd
 * @Aata 2019年1月11日
 * @Description
 */
public class Redis {
	private static JedisPool jedisPool;
	
	public static void main(String[] args) {
		/*
		 * note: need add commmons-pool2-2.6.0.jar
		 */
		jedisPool = new JedisPool(new JedisPoolConfig(), "localhost");
		Jedis jedis = jedisPool.getResource();
		jedis.auth("password");
		jedis.lpush("aa", "aaa");//insert data from the front of list
	}
}
