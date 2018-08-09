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

public class App5 
{
    public static void main( String[] args ) throws Exception
    {
        
    	RedisClient client = RedisClient.create("redis://abc123@localhost:6379/0");       
        StatefulRedisConnection<String, String> connection = client.connect(); 
        
        RedisAsyncCommands<String, String> async = client.connect().async();
        long start =  System.currentTimeMillis();
        
        RedisFuture<String> set = async.set("key", "value2");
        RedisFuture<String> get = async.get("key");
        long end = 0L ;
        //.....
        //set.thenAccept(....)
        if(set.await(1, TimeUnit.SECONDS)) {//阻塞一段时间, 等待服务器的反馈信息
        	end =  System.currentTimeMillis();
        } 
        System.out.println("set gap: " + (end - start));
        
        start =  System.currentTimeMillis();
        if(get.await(1, TimeUnit.SECONDS)){
        	end =  System.currentTimeMillis();
        	System.out.println(get.get());
        }
        System.out.println("get gap: " + (end - start));
        
        //System.out.print(get.get(1, TimeUnit.MINUTES) );
		
        connection.close();                                                    
        client.shutdown();                                                     
        
    }
}
