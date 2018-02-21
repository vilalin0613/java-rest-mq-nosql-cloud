/**
 * 
 */
package com.example.vila.repository;

/**
 * The DataSource exception
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class RepositoryException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 7692228516225257975L;

    /**
     * 
     * 
     * @param cause
     */
    public RepositoryException(Throwable cause) {
        super(cause);
    }
}
