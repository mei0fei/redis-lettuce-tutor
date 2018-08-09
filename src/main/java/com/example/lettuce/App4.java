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

public class App4 
{
    public static void main( String[] args ) throws Exception
    {
        
    	RedisClient client = RedisClient.create("redis://abc123@localhost:6379/0");      
        StatefulRedisConnection<String, String> connection = client.connect(); 
        
        RedisAsyncCommands<String, String> commands = client.connect().async();
        
        Executor sharedExecutor = Executors.newFixedThreadPool(2);
		RedisFuture<String> future = commands.get("key");

		future.thenAcceptAsync(new Consumer<String>() {
		    @Override
		    public void accept(String value) {
		        System.out.println(value);
		    }
		}, sharedExecutor);
        
		future.await(1L, TimeUnit.MINUTES);
		//Thread.sleep(1000);//等待子线程完成
		
        connection.close();                                                    
        client.shutdown();                                                     
        
    }
}
