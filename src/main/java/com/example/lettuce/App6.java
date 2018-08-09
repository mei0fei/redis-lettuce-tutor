package com.example.lettuce;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Hello world!
 *
 */


import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.async.RedisAsyncCommands;
import io.lettuce.core.api.sync.RedisCommands;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;
import io.lettuce.core.pubsub.api.sync.RedisPubSubCommands;

//https://github.com/lettuce-io/lettuce-core/wiki/Pub-Sub
public class App6 
{
    public static void main( String[] args ) throws Exception
    {
        
    	RedisClient client = RedisClient.create("redis://abc123@localhost:6379/0");     
        
        
        StatefulRedisPubSubConnection<String, String> connection = client.connectPubSub();
		connection.addListener(new RedisPubSubListener<String, String>() {

			@Override
			public void message(String channel, String message) {
				// TODO Auto-generated method stub
				System.out.println(channel + " rec : "+ message);
			}

			@Override
			public void message(String pattern, String channel, String message) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void subscribed(String channel, long count) {
				// TODO Auto-generated method stub
				System.out.println(channel + "  count : "+ count);
			}

			@Override
			public void psubscribed(String pattern, long count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void unsubscribed(String channel, long count) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void punsubscribed(String pattern, long count) {
				// TODO Auto-generated method stub
				
			}
			
		});

		RedisPubSubCommands<String, String> sync = connection.sync();
		sync.subscribe("channel");
		int i=0;
        while(true) {
        	Thread.sleep(500);
        	i++;
        	if(i==100) break;
        }
		
        connection.close();                                                    
        client.shutdown();                                                     
        
    }
}
