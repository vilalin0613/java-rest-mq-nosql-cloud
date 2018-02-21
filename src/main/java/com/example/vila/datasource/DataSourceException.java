/**
 * 
 */
package com.example.vila.datasource;

/**
 * The DataSource exception
 * 
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class DataSourceException extends Exception {
    
    /**
     * 
     */
    private static final long serialVersionUID = -8531122771979063697L;

    /**
     * 
     * 
     * @param message
     */
    public DataSourceException(String message) {
    	super(message);
    }
    
    /**
     * 
     * 
     * @param cause
     */
    public DataSourceException(Throwable cause) {
        super(cause);
    }
}
