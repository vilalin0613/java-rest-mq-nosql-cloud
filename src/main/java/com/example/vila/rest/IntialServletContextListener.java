package com.example.vila.rest;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Properties;
import java.util.Timer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import com.example.vila.amqp.AMQP;
import com.example.vila.amqp.AMQPException;
import com.example.vila.amqp.rabbitmq.Rabbitmq;
import com.example.vila.cloud.CloudService;
import com.example.vila.cloud.CloudServiceImpl;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.example.vila.datasource.cassandra.CassandraDataSourceImpl;
import com.example.vila.datasource.mysql.MySqlDataSource;
import com.example.vila.repository.CassandraRepository;
import com.example.vila.repository.MysqlRepository;
import com.example.vila.repository.Repository;
import com.example.vila.repository.RepositoryException;

/**
 *   
 * 
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@WebListener
public class IntialServletContextListener implements ServletContextListener {
    
    /**
     * slf4j logger
     */
    private static final Logger logger = LoggerFactory.getLogger(IntialServletContextListener.class);
    
    /**
     * @param dataSource
     * @param properties
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws RepositoryException 
     * @return the repository, or null
     */
    private Repository initialRepository(String dataSource, Properties properties) throws InstantiationException, IllegalAccessException, ClassNotFoundException, RepositoryException {

    	Repository repository = null;
    	
    	
        String dataSourceName = properties.getProperty(dataSource);
    	
        if ((dataSourceName != null) && (!dataSourceName.equals(""))) {
     
        	repository = (Repository) Class.forName(dataSource + "DataSource").newInstance();
        	
        	if (repository != null) {
        		
        		repository.connect(properties);
        		
                repository.initialize();
            }
        }
        
        return repository;
    }
    
    /**
     * @param amqpName
     * @param properties
     * @param dataSources
     * @param cloudService
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws ClassNotFoundException
     * @throws AMQPException 
     * @return the repository, or null
     */
    private AMQP initialAMQP(Properties properties, CassandraDataSource dataSource) throws InstantiationException, IllegalAccessException, ClassNotFoundException, AMQPException {
    	AMQP amqp = new Rabbitmq();
    	amqp.connect(properties);
        amqp.initialize(dataSource);
        
        return amqp;
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        
		// logger
		MDC.put("userID", "NA");
		MDC.put("transID", "NA");
		MDC.put("requestURI", "NA");
		MDC.put("direction", "NA");
		MDC.put("remoteIP", "NA");
		MDC.put("userAgent", "NA");
		MDC.put("statusCode", "NA");
        
        final String configPath = System.getProperty("user.dir")+"/config.properties";
        FileReader fr = null;
        try {
        	
            ServletContext context = contextEvent.getServletContext();

            logger.info("user.dir :{}", System.getProperty("user.dir"));
        	
        	// Gets properties
            Properties properties = new Properties();
        	File configF = new File(configPath);
        	if(configF.exists()){
        		fr = new FileReader(configF);
	        	properties.load(fr);
	            
        	}else{
        		throw new IOException(configPath + " is not found.");
        	}
        	context.setAttribute("properties", properties);
            
            //Set cloud service
            CloudService cloudService = new CloudServiceImpl(properties);
            context.setAttribute("cloudService", cloudService);

            // Sets repository
        	context.setAttribute("mysqlRepository", this.initialRepository("MysqlRepository", properties));
        	context.setAttribute("cassandraRepository", this.initialRepository("CassandraRepository", properties));
            
            //Set cahced executor pool
            ExecutorService executors = Executors.newCachedThreadPool();
            context.setAttribute("executors", executors);
            
            // Sets amqp
    		AMQP amqp = this.initialAMQP(properties, (CassandraDataSource) context.getAttribute("cassandraRepository"));
            context.setAttribute("amqp", amqp);
            
        } catch (IOException ioe) {
            logger.error("IOException: {}", ioe);
            ioe.printStackTrace();
        } catch (InstantiationException ie) {
            logger.error("InstantiationException: {}", ie);
            ie.printStackTrace();
        } catch (IllegalAccessException ile) {
            logger.error("IllegalAccessException: {}", ile);
            ile.printStackTrace();
        } catch (ClassNotFoundException cnfe) {
            logger.error("ClassNotFoundException: {}", cnfe);
            cnfe.printStackTrace();
        } catch (RepositoryException re) {
            logger.error("RepositoryException: {}", re);
            re.printStackTrace();
        } catch (Exception e) {
            logger.error("Exception: {}", e);
            e.printStackTrace();
        } finally{
            if(null != fr){
                try
                {
                    fr.close();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.ServletContextEvent)
     */
    @Override
    public void contextDestroyed(ServletContextEvent contextEvent) {
        ServletContext context = contextEvent.getServletContext();
        ((Repository)context.getAttribute("mysqlRepository")).close();
        ((Repository)context.getAttribute("cassandraRepository")).close();
        ((AMQP)context.getAttribute("amqp")).close();
    }

    
    
}
