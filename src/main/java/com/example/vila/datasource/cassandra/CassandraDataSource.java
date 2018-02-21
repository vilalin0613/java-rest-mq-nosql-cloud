/**
 * 
 */
package com.example.vila.datasource.cassandra;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.vila.datasource.DataSource;
import com.example.vila.datasource.DataSourceException;
import com.example.vila.datasource.cassandra.entity.UserBookEntity;
import com.example.vila.datasource.cassandra.entity.UserInformationEntity;

/**
 * The contact data source.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public interface CassandraDataSource extends DataSource {
	
	/**
	 * get user books
	 * @param userID
	 * @param language
	 * @param bookID
	 * @return
	 * @throws DataSourceException
	 */
	public List<UserBookEntity> getUserBooks(String userID, String language) throws DataSourceException;
	
	/**
	 * get single user information
	 * 
	 * @param userID
	 * @return
	 * @throws DataSourceException
	 */
	public UserInformationEntity getUserInformation(String userID) throws DataSourceException;
	
	/**
	 * update user information a part of fields 
	 * 
	 * @param m
	 * @return
	 * @throws DataSourceException
	 */
    public boolean updateUserInformation(Map<String, Object> m) throws DataSourceException;
    
	/**
	 * update user book a part of fields 
	 * 
	 * @param m
	 * @return
	 * @throws DataSourceException
	 */
    public boolean updateUserBook(Map<String, Object> m) throws DataSourceException;
    
    /**
     * update user books from cloud
     * 
     * @param data
     * @return
     * @throws DataSourceException
     */
    public boolean updateUserBookFromCloud(List<Item> data) throws DataSourceException;
    
}
