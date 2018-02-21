/**
 * 
 */
package com.example.vila.amqp.rabbitmq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.amqp.Producer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**
 * AMQP producer interface.
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public abstract class RabbitMQProducer implements Producer {
	
	/**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQProducer.class);

    /**
	 * AMQP connection
	 */
	private Connection connection;
	
	/**
	 * AMQP channel
	 */
	private Channel channel;
	
	/**
	 * Exchange name
	 */
	private String exchangeName;
	
	/**
	 * JSON object mapper
	 */
	private ObjectMapper objectMapper;
	
	/**
	 * @param connection
	 * @param queneName
	 */
	public RabbitMQProducer(Connection connection, String exchangeName) {
		this.connection = connection;
		try {
			this.channel = this.connection.createChannel();
		} catch (IOException ioe) {
			logger.error("IOException: {}", ioe);
            ioe.printStackTrace();
		}
		this.exchangeName = exchangeName;
		this.objectMapper = new ObjectMapper();
	}
	
	/**
	 * @return
	 */
	protected ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}
	
	protected void publish(String message) {
		
		try {
			
			this.channel.basicPublish(this.exchangeName, "", null, message.getBytes());
		
		} catch (IOException ioe) {
			logger.error("IOException: {}", ioe);
            ioe.printStackTrace();
		}
	}
}
