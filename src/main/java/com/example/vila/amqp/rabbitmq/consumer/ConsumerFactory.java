/**
 * 
 */
package com.example.vila.amqp.rabbitmq.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.amqp.rabbitmq.RabbitMQConsumer;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.rabbitmq.client.Connection;

/**
 * AMQP consumer factory.
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public class ConsumerFactory {
	
	/**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(ConsumerFactory.class);

	/**
	 * @param name
	 * @param connection
	 * @return
	 */
	public static RabbitMQConsumer generateConsumer(Connection connection, String queueName, CassandraDataSource dataSource) {
		
		logger.trace("METHOD generateConsumer IN");
		
		RabbitMQConsumer result = null;
		
		if (queueName.equals("user_information")) {
			result = new UserBookConsumer(connection, queueName, dataSource);
		}else {
			throw new RuntimeException("Unknown consumer name.");
		}
		
		logger.trace("METHOD generateConsumer OUT");
		
		return result;
	}
}
