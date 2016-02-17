package com.rabbitmq.basics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author: pgajjar @date: 4/29/14 @time: 12:47 AM
 */
public class Producer {
    private static final Logger logger = Logger.getLogger(Producer.class.getName());

    private Connection connection = null;
    private Channel channel = null;

    public static boolean shouldQuit(String message) {
        return message.equalsIgnoreCase("quit") || message.equalsIgnoreCase("exit") || message.equalsIgnoreCase("end");
    }

    public Producer(String hostName) throws IOException, TimeoutException {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(hostName);
        connection = connectionFactory.newConnection();
    }

    public void sendMsg(String queueName, String message) throws IOException {
        channel = connection.createChannel();
        channel.queueDeclare(queueName, false, false, true, null);
        channel.basicPublish("", queueName, null, message.getBytes());
        logger.info(" [x] Sent '" + message + "'");
    }

    public void close() throws IOException, TimeoutException {
        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        if (args.length != 3) {
            System.out.println("Usage: Producer <hostname> <queuename> <message>");
            System.exit(-1);
        }

        Producer producer = new Producer(args[0]);
        producer.sendMsg(args[1], args[2]);
        producer.close();
    }
}
