/**
 * 
 */
package com.example.vila.amqp.rabbitmq;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.amqp.Consumer;
import com.example.vila.amqp.Producer;
import com.example.vila.amqp.rabbitmq.consumer.ConsumerFactory;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.rabbitmq.client.Connection;

/**
 * RabbitMQ AMQP.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class Rabbitmq extends RabbitmqAMQP {

	/**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(Rabbitmq.class);
    
    /**
     * 
     */
    private Map<String, Consumer> consumers;

	/* (non-Javadoc)
	 * @see com.loftech.juiker.api.amqp.AMQP#initialize()
	 */
	@Override
	public void initialize(CassandraDataSource dataSource) {

		this.consumers = new HashMap<String, Consumer>();
		
		Connection connection = this.getConnection();
		RabbitMQConsumer consumer = ConsumerFactory.generateConsumer(connection, this.getQueue(), dataSource);
		
		this.consumers.put(this.getQueue(), consumer);
		
		Thread t = new Thread(consumer);
		t.start();
	}

	/* (non-Javadoc)
	 * @see com.loftech.juiker.api.amqp.AMQP#getConsumer(java.lang.String)
	 */
	@Override
	public Consumer getConsumer(String queue) {
		
		return this.consumers.get(queue);
	}

}
