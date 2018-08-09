package com.example.lettuce;

/**
 * Hello world!
 *
 */

import io.lettuce.core.*;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class App 
{
    public static void main( String[] args )
    {
        
    	RedisClient client = RedisClient.create("redis://abc123@localhost:6379/0");    

        StatefulRedisConnection<String, String> connection = client.connect(); 

        RedisCommands<String, String> commands = connection.sync();   
        
        
        commands.set("foo", "hello");

        String value = commands.get("foo");                                   
        System.out.println(value);

        connection.close();                                                    

        client.shutdown();                                                     
        
    }
}
