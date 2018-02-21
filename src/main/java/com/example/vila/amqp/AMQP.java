/**
 * 
 */
package com.example.vila.amqp;

import java.util.Properties;

import com.example.vila.datasource.cassandra.CassandraDataSource;

/**
 * The AMQP interface.
 * All the AMQPs MUST inherit this interface.
 * 
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public interface AMQP {
	
	/**
     * Connects AMQP.
     * 
     * @param properties the application properties.
     * @throws AMQPException 
     */
    public void connect(Properties properties) throws AMQPException;
    
    /**
     * Initializes AMQP. Doing some initializations after connecting to repository.
     */
    public void initialize(CassandraDataSource dataSource);
    
    /**
     * Gets consumer, or null if not found.
     * 
     * @param queue
     * @return
     */
    public Consumer getConsumer(String queue);
    
    /**
     * Closes AMQP.
     */
    public void close();
}
