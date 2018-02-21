/**
 * 
 */
package com.example.vila.amqp.rabbitmq;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.amqp.Consumer;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

/**
 * AMQP consumer interface.
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public abstract class RabbitMQConsumer implements Runnable, Consumer {
	
	/**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitMQConsumer.class);

    /**
	 * AMQP connection
	 */
	private Connection connection;
	
	/**
	 * AMQP channel
	 */
	private Channel channel;
	
	/**
	 * Queue name
	 */
	private String queneName;
	
	/**
	 * Data source
	 */
	private CassandraDataSource dataSource;
	
	/**
	 * JSON object mapper
	 */
	private ObjectMapper objectMapper;

	/**
	 * @param connection
	 * @param queneName
	 * @param dataSources
	 */
	public RabbitMQConsumer(Connection connection, String queneName, CassandraDataSource dataSource) {
		this.connection = connection;
		try {
			this.channel = this.connection.createChannel();
		} catch (IOException ioe) {
			logger.error("IOException: {}", ioe);
            ioe.printStackTrace();
		}
		this.queneName = queneName;
		this.dataSource = dataSource;
		this.objectMapper = new ObjectMapper();
	}
	
	/**
	 * @return
	 */
	protected CassandraDataSource getDataSource() {
		return this.dataSource;
	}
	
	/**
	 * @return
	 */
	protected ObjectMapper getObjectMapper() {
		return this.objectMapper;
	}

	@Override
	public void run() {
		
		try {

		    this.channel.basicQos(1);
	
		    this.channel.basicConsume(this.queneName, false, new DefaultConsumer(channel) {
		    	
		    	@Override
		    	public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
			        
		    		String message = new String(body, "UTF-8");
		    		
			        try {
			        	process(message);
			        	
			        }catch(Exception e){
                        logger.info("Exception :{}", e);
                        
                    }finally {
			        	channel.basicAck(envelope.getDeliveryTag(), false);
			        }
			    }
		    });
	    
		} catch (IOException ioe) {
			logger.error("IOException: {}", ioe);
            ioe.printStackTrace();
		}
		
	}
}
