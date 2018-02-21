/**
 *
 */
package com.example.vila.amqp.rabbitmq.consumer;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.vila.amqp.rabbitmq.RabbitMQConsumer;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.rabbitmq.client.Connection;

/**
 * AMQP consumer.
 *
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
class UserBookConsumer extends RabbitMQConsumer {

    /**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(UserBookConsumer.class);

    /**
     * Constructor
     *
     * @param connection
     * @param queneName
     * @param dataSources
     */
    public UserBookConsumer(Connection connection, String queneName, CassandraDataSource datasource) {
        super(connection, queneName, datasource);
    }

    @Override
    public void process(String message) {

        try {

        	UserBookMessage messageObject = this.getObjectMapper().readValue(message, UserBookMessage.class);

            String userID = messageObject.getUserID();
            String userName = messageObject.getUserName();
            String mqSource = messageObject.getMqSource();

            
            boolean ignore = false;
            
            if(null==mqSource || mqSource.length()==0){
            	ignore = true;
            	logger.info("Abnormal message:{}", message);
            }
    		
            if(!ignore){
            	Map<String, Object> m = new HashMap<String, Object>();
            	m.put("userID", userID);
            	m.put("userName", userName);
            	
            	this.getDataSource().updateUserInformation(m);
            }

        } catch (Exception e) {
            logger.error("Exception: {}", e);
            e.printStackTrace();
        }
    }

}
