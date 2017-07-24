package com.mycloud.redis;

import redis.clients.jedis.ShardedJedis;

public interface JedisDataSource {

	public ShardedJedis getRedisClient();

	public void returnResource(ShardedJedis shardedJedis);

	public void returnResource(ShardedJedis shardedJedis, boolean broken);
}
