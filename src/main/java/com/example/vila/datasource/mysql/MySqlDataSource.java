/**
 * 
 */
package com.example.vila.datasource.mysql;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import com.example.vila.datasource.DataSource;
import com.example.vila.datasource.DataSourceException;

/**
 * The user data source.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public interface MySqlDataSource extends DataSource {
    
	/**
     * Basic authentication.
     * 
     * @param username the phone number. It MUST not be null or empty for better performance.
     * @param password the uuid. It MUST not be null or empty for better performance.
     * @return the auth result
     * @throws DataSourceException
     */
    public boolean authBasic(String username, String password) throws DataSourceException;

    /**
     * Gets userid from phoneNumber.
     * 
     * @param phoneNumber the phone number. It MUST not be null or empty for better performance.
     * @param brandID the brand id
     * @return the userid, or null if not found.
     * @throws DataSourceException 
     */
    public String getUserID(String phoneNumber) throws DataSourceException;
}
