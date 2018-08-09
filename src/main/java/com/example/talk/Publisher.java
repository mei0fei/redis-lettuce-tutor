package com.example.talk;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class Publisher {
	
	StatefulRedisPubSubConnection<String, String> connection ;
	RedisPubSubCommands<String, String> sync_pub ;
	
	
	public Publisher(StatefulRedisPubSubConnection<String, String> connection){
		this.connection = connection;
		sync_pub = this.connection.sync();
	}
	
	public void publish(String txt){
		String[] strs = txt.split(":");
		sync_pub.publish(strs[0], strs[1]);
	}
}
