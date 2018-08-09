package com.example.talk;

import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

public class SubScriber {
	
	StatefulRedisPubSubConnection<String, String> connection ;
	RedisPubSubCommands<String, String> sync_sub ;
	
	public SubScriber(StatefulRedisPubSubConnection<String, String> connection){
		this.connection = connection;
		sync_sub = this.connection.sync();
	}
	
	public void subscribe(String txt){
		sync_sub.subscribe(txt);
	}
}
