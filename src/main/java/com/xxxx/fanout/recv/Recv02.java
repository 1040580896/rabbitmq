package com.xxxx.fanout.recv;

import com.rabbitmq.client.*;

/**
 * 发布订阅-消息消费者2
 */
public class Recv02 {

    //定义交换机
    private final static String EXCHANGE_NAME = "exchange_fanout";
    public static void main(String[] argv) throws Exception {
        //创建连接工厂
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("192.168.93.14");
        factory.setUsername("admin");
        factory.setVirtualHost("/");
        factory.setPassword("123456");
        factory.setPort(5672);
        //连接工厂创建连接
        Connection connection = factory.newConnection();
        //创建信道
        Channel channel = connection.createChannel();


        //绑定交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //获取队列(排他队列)
        String queueName = channel.queueDeclare().getQueue();
        //绑定队列和交换机
        channel.queueBind(queueName, EXCHANGE_NAME, "");
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        //监听队列消费消息
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> {
        });
    }
}