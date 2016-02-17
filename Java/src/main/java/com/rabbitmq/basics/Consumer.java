package com.rabbitmq.basics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author : pgajjar
 * @since : 4/29/14 @time: 1:17 AM
 */
public class Consumer {
    private static final Logger logger = Logger.getLogger(Consumer.class.getName());
    private Connection connection = null;
    private Channel channel = null;
    private QueueingConsumer queueingConsumer = null;

    public Consumer(String hostName) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostName);
        connection = connectionFactory.newConnection();
    }

    public void connect(String queueName) throws IOException {
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, true, null);
        queueingConsumer = new QueueingConsumer(channel);
        String consumerTag = channel.basicConsume(queueName, true, queueingConsumer);
        logger.info("Received the consumerTag: " + consumerTag + " from server.");
        logger.info(" [*] Waiting for messages. To exit press CTRL+C");
    }

    public void getMsgs() throws InterruptedException {
        boolean shouldQuit = false;
        while (!shouldQuit) {
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String message = new String(delivery.getBody());
            shouldQuit = Producer.shouldQuit(message);
            logger.info("[x] Received '" + message + "'");
        }
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, InterruptedException, TimeoutException {
        if (args.length != 2) {
            System.out.println("Usage: Consumer <hostname> <queuename>");
            System.exit(-1);
        }

        Consumer consumer = new Consumer(args[0]);
        consumer.connect(args[1]);
        consumer.getMsgs();
        consumer.close();
    }
}
