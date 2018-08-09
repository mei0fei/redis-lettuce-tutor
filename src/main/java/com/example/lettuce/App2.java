package com.example.lettuce;

import java.util.concurrent.CompletableFuture;

/**
 * Hello world!
 *
 */


import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
//https://github.com/lettuce-io/lettuce-core/wiki/Asynchronous-API
public class App2 
{
    public static void main( String[] args )
    {
        
    	RedisClient client = RedisClient.create("redis://abc123@localhost:6379/0");      
        StatefulRedisConnection<String, String> connection = client.connect(); 
        RedisCommands<String, String> commands = connection.sync();   
        final CompletableFuture<String> future = new CompletableFuture<String>();
        future.thenRun(new Runnable() {
            public void run() {
                try {
                	Thread.sleep(2000);
                    System.out.println("Got value: " + future.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });
        System.out.println("Current state: " + future.isDone());
        future.complete("my value"); // complete 阻塞主线程
        System.out.println("Current state: " + future.isDone());
        connection.close();                                                    
        client.shutdown();                                                     
        
    }
}
