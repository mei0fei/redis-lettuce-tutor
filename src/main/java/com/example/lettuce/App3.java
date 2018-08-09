package com.example.lettuce;

import java.util.concurrent.CompletableFuture;
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

public class App3 
{
    public static void main( String[] args )
    {
        
    	RedisClient client = RedisClient.create("redis://abc123@localhost:6379/0");      
        StatefulRedisConnection<String, String> connection = client.connect(); 
        
        RedisAsyncCommands<String, String> commands = client.connect().async();
        try {
	        //consuming a futures means obtaining the value.
	        RedisFuture<String> future = commands.get("key");
	        
	        /*future.thenAccept(new Consumer<String>() {
	            public void accept(String value) {
	                System.out.println(value);
	            }
	        });*/
	        //与上面的代码功能相等
	        future.thenAccept(System.out::println);
	        
	        //future.await(1L, TimeUnit.SECONDS);
	        //Thread.sleep(1000);//等待子线程完成
	        
        }catch(Exception e) {
        	e.printStackTrace();
        }
        connection.close();                                                    
        client.shutdown();                                                     
        
    }
}
