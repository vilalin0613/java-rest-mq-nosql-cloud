/**
 * 
 */
package com.example.vila.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.tomcat.jdbc.pool.DataSource;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public abstract class MysqlRepository implements Repository {

    /**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(MysqlRepository.class);
    
    /**
     * mysql data source
     */
    private DataSource datasource;
    
    /* (non-Javadoc)
     * @see com.loftech.juiker.api.repository.Repository#connect(java.util.Properties)
     */
    @Override
    public void connect(Properties properties) throws RepositoryException {
        
        logger.trace("METHOD connect IN");
        
        PoolProperties pp = new PoolProperties();
        
        pp.setDriverClassName(properties.getProperty("mysql.classname"));
        pp.setUrl(properties.getProperty("mysql.url"));
        pp.setUsername(properties.getProperty("mysql.username"));
        pp.setPassword(properties.getProperty("mysql.password"));
        
        pp.setInitialSize(Integer.parseInt(properties.getProperty("mysql.connectionpool.initial")));
        pp.setMaxActive(Integer.parseInt(properties.getProperty("mysql.connectionpool.maxactive")));
        pp.setMinIdle(Integer.parseInt(properties.getProperty("mysql.connectionpool.minidle")));
        pp.setMaxIdle(Integer.parseInt(properties.getProperty("mysql.connectionpool.maxidle")));
        
        pp.setMaxWait(Integer.parseInt(properties.getProperty("mysql.connectionpool.maxwait")));
        
        pp.setTestWhileIdle(true);
        pp.setTestOnBorrow(true);
        pp.setValidationQuery("SELECT 1");
        pp.setTestOnReturn(false);
        pp.setValidationInterval(30000);
        pp.setTimeBetweenEvictionRunsMillis(15000);
        pp.setMinEvictableIdleTimeMillis(30000);

        pp.setRemoveAbandoned(true);
        pp.setRemoveAbandonedTimeout(60);
        pp.setLogAbandoned(true);

        pp.setJmxEnabled(true);
        
        pp.setJdbcInterceptors(
                "org.apache.tomcat.jdbc.pool.interceptor.ConnectionState;"+
                "org.apache.tomcat.jdbc.pool.interceptor.StatementFinalizer");
    
        this.datasource = new DataSource();
        this.datasource.setPoolProperties(pp); 
        
        logger.info("Connected to mysql url: {}", pp.getUrl());

        
        logger.trace("METHOD connect OUT");

    }

    /* (non-Javadoc)
     * @see com.loftech.juiker.api.repository.Repository#close()
     */
    @Override
    public void close() {

        logger.trace("METHOD close IN");
        
        
        this.datasource.close();

        
        logger.trace("METHOD close OUT");

    }
    
    protected Connection getConnection() throws SQLException {
        return this.datasource.getConnection();
    }

}
