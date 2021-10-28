package com.xxxx.fanout.send;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 发布订阅-消息生产者
 */
public class Send {

    //定义交换机名称
    private final static String EXCHANGE_NAME = "exchange_fanout";

    public static void main(String[] argv) throws Exception {
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.93.14");
        factory.setUsername("admin");
        factory.setVirtualHost("/");
        factory.setPassword("123456");
        factory.setPort(5672);

        try (
                //连接工厂创建连接
                Connection connection = factory.newConnection();
                //创建信道
             Channel channel = connection.createChannel()) {
         channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String message = "Hello World!";
            //发送消息
            channel.basicPublish(EXCHANGE_NAME, "",null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
            }
        }
 }
