/**
 * 
 */
package com.example.vila.repository;

import java.util.Properties;

/**
 * The Repository interface.
 * All the repositories MUST inherit this interface.
 * 
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public interface Repository {
    
    /**
     * Connects repository.
     * 
     * @param properties the application properties.
     * @throws RepositoryException 
     */
    public void connect(Properties properties) throws RepositoryException;
    
    /**
     * Initializes repository. Doing some initializations after connecting to repository.
     */
    public void initialize();
    
    /**
     * Closes repository.
     */
    public void close();
}
