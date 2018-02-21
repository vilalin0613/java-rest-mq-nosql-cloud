/**
 * 
 */
package com.example.vila.amqp.rabbitmq;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeoutException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.amqp.AMQP;
import com.example.vila.amqp.AMQPException;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public abstract class RabbitmqAMQP implements AMQP {

	/**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(RabbitmqAMQP.class);
    
    /**
     * amqp connection
     */ 
    private Connection connection;
    
    /**
     * amqp exchange
     */ 
    private String exchange;
    
    /**
     * amqp queue
     */ 
    private String queue;
	
	/* (non-Javadoc)
	 * @see com.loftech.juiker.api.amqp.AMQP#connect(java.util.Properties)
	 */
	@Override
	public void connect(Properties properties) throws AMQPException {
		
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost(properties.getProperty("mq.hostname"));
        factory.setPort(Integer.parseInt(properties.getProperty("mq.port")));
        factory.setUsername(properties.getProperty("mq.username"));
        factory.setPassword(properties.getProperty("mq.password"));
        factory.setVirtualHost(properties.getProperty("mq.virtualhost"));
        factory.setRequestedHeartbeat(10);
        factory.setAutomaticRecoveryEnabled(true);
        factory.setNetworkRecoveryInterval(10000);
        
        try {
        	
			this.connection = factory.newConnection();
			this.exchange = properties.getProperty("mq.exchange");
			this.queue = properties.getProperty("mq.queues");		
			
			logger.info("Connected to RabbitMQ");
			
		} catch (IOException ioe) {
			logger.error("IOException: {}", ioe);
            
		} catch (TimeoutException te) {
			logger.error("TimeoutException: {}", te);
            
		}
		
	}

	/* (non-Javadoc)
	 * @see com.loftech.juiker.api.amqp.AMQP#close()
	 */
	@Override
	public void close() {
		try {
			this.connection.close();
		} catch (IOException ioe) {
			logger.error("IOException: {}", ioe);
		}

	}
	
	/**
	 * @return
	 */
	protected Connection getConnection() {
        return this.connection;
    }
	
	/**
	 * @return
	 */
	protected String getExchange() {
        return this.exchange;
    }
	
	/**
	 * @return
	 */
	protected String getQueue() {
        return this.queue;
    }
}
