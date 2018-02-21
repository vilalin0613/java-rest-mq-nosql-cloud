/**
 * 
 */
package com.example.vila.repository;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Host;
import com.datastax.driver.core.Metadata;
import com.datastax.driver.core.ProtocolOptions;
import com.datastax.driver.core.QueryLogger;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.ConstantReconnectionPolicy;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.example.vila.repository.cassandra.ConfirmRetryPolicy;
import com.example.vila.repository.cassandra.LoggingRetryPolicy;

/**
 * Cassandra repository.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public abstract class CassandraRepository implements Repository {

    /**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(CassandraRepository.class);
    
    /**
     * cassandra cluster
     */
    private Cluster cluster;
    
    /**
     * cassandra session
     */ 
    private Session session;
    
    /**
     * cassandra keyspace
     */ 
    private String keyspace;
    
    private Properties properties;
    
    public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
    @Override
    public void connect(Properties properties) throws RepositoryException {

        logger.trace("METHOD connect IN");
        
        this.properties = properties;
        
        // Specifies the keyspace.
        this.keyspace = properties.getProperty("cassandra.keyspace");
        
        // Gets the cassandra cluster.
        this.cluster = Cluster.builder()
            .addContactPoints(properties.getProperty("cassandra.contactpoint").replaceAll(" ", "").split(","))
            .withPort(Integer.parseInt(properties.getProperty("cassandra.port")))
            .withCredentials(properties.getProperty("cassandra.username"), properties.getProperty("cassandra.password"))
            .withLoadBalancingPolicy(DCAwareRoundRobinPolicy.builder().build())
            .withReconnectionPolicy(new ConstantReconnectionPolicy(TimeUnit.SECONDS.toMillis(5)))
            .withRetryPolicy(new LoggingRetryPolicy(new ConfirmRetryPolicy(2)))
            .build();
        
        this.cluster.register(QueryLogger.builder().build());
        this.cluster.getConfiguration()
            .getProtocolOptions()
            .setCompression(ProtocolOptions.Compression.NONE);
        
        Metadata metadata = this.cluster.getMetadata();
        logger.info("Connected to cassandra cluster: {}", metadata.getClusterName());
        
        // Prints hosts to verify the connection is ok.
        for (Host host : metadata.getAllHosts()) {
            logger.info("Datatacenter: {}; Host: {}; Rack: {}", host.getDatacenter(), host.getAddress(), host.getRack());
        }
        
        // Gets the cassandra session.
        this.session = this.cluster.connect();

        
        logger.trace("METHOD connect OUT");
    }

    /* (non-Javadoc)
     * @see com.loftech.juiker.api.repository.Repository#close()
     */
    @Override
    public void close() {

        logger.trace("METHOD close IN");
        
        
        // Closes the cassandra session. 
        this.session.close();
        
        // Closes the cassandra cluster.
        this.cluster.close();
        
        
        logger.trace("METHOD close OUT");
    }

    protected Session getSession() {
        return this.session;
    }
    
    protected String getKeyspace() {
        return this.keyspace;
    }
}
