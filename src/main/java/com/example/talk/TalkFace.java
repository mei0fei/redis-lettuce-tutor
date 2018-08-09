package com.example.talk;

import io.lettuce.core.RedisClient;
import io.lettuce.core.pubsub.RedisPubSubListener;
import io.lettuce.core.pubsub.StatefulRedisPubSubConnection;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

//https://blog.csdn.net/xietansheng/article/details/72814531
//https://blog.csdn.net/bcbobo21cn/article/details/52540831
public class TalkFace extends JFrame {
	RedisClient client ;     
    StatefulRedisPubSubConnection<String, String> connection_sub ;
    StatefulRedisPubSubConnection<String, String> connection_pub ;
    
	//JFrame f;
	List<String> sub_names ;
	Publisher pub;
	SubScriber sub;
	
	JTextArea area;
	
	public TalkFace(){
		//client = RedisClient.create("redis://abc123@localhost:6379/0");
		client = RedisClient.create("redis://localhost:6379/0");
	    connection_sub = client.connectPubSub();
	    
	    //添加sub监听事件
  		connection_sub.addListener(new RedisPubSubListener<String, String>() {

  			//当对方给通道发送了消息, 该方法会被调用
  			@Override
  			public void message(String channel, String message) {
  				//在终端打印来之通道的消息
  				//System.out.println(channel + "  : "+ message);
  				area.append(channel + "  : "+ message + "\n");
  			}

  			@Override
  			public void message(String pattern, String channel, String message) {
  				// TODO Auto-generated method stub
  				
  			}

  			//当订阅一个通道时, 这个方法被调用
  			@Override
  			public void subscribed(String channel, long count) {
  				// TODO Auto-generated method stub
  				//System.out.println(channel + "  count : "+ count);
  			}

  			@Override
  			public void psubscribed(String pattern, long count) {
  				// TODO Auto-generated method stub
  				
  			}

  			//取消订阅时, 该方法被调用
  			@Override
  			public void unsubscribed(String channel, long count) {
  				// TODO Auto-generated method stub
  				
  			}

  			@Override
  			public void punsubscribed(String pattern, long count) {
  				// TODO Auto-generated method stub
  				
  			}
  			
  		});
	    
	    connection_pub = client.connectPubSub();
	    
		drawFace();
		sub_names = new ArrayList();
		pub = new Publisher(connection_pub);
		sub = new SubScriber(connection_sub);
	}
	
	ActionListener listener_sub = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// 显示输入对话框, 返回输入的内容
            String inputContent = JOptionPane.showInputDialog(
            		TalkFace.this,
                    "订阅的通道名称:",
                    ""
            );
            System.out.println("输入的内容: " + inputContent);
            sub_names.add(inputContent);
            sub.subscribe(inputContent);
		}
		
	};
	
	ActionListener listener_pub = new ActionListener(){
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// 显示输入对话框, 返回输入的内容
            String inputContent = JOptionPane.showInputDialog(
            		TalkFace.this,
                    "发布消息的通道名称:消息",
                    ""
            );
            System.out.println("输入的内容: " + inputContent);
            pub.publish(inputContent);
			
		}
		
	};
	
	public void drawFace(){
		//this = new JFrame("聊天器");
		this.setTitle("聊天器");
		this.addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosed(WindowEvent arg0) {
				
				System.out.println("-------close...---------");
				connection_sub.close();
				connection_pub.close();
				client.shutdown();
				super.windowClosed(arg0);
			} 
			
		});
		
        this.setSize(400, 350);
        this.setLocation(200, 200);
        this.setLayout(null);
        
        area= new JTextArea();
        area.setBackground(new Color(100, 150, 100));
        area.setBounds(5, 5, 300, 200);
        
        JTextField text = new JTextField();
        text.setBounds(5, 210, 300, 20);
        
        JButton btn_sub =new JButton();
        btn_sub.setText("订阅");
        btn_sub.setBounds(5, 235, 60, 30);
        btn_sub.addActionListener(listener_sub);
        
        JButton btn_pub =new JButton();
        btn_pub.setText("发布");
        btn_pub.setBounds(75, 235, 60, 30);
        btn_pub.addActionListener(listener_pub);
        
        this.add(area);
        this.add(text);
        this.add(btn_sub);
        this.add(btn_pub);        
        
        //this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        this.setVisible(true);
	} 
	

	
	public static void main(String[] args) {
		TalkFace tf = new TalkFace();
		System.out.println("-------end---------");
    }
}
