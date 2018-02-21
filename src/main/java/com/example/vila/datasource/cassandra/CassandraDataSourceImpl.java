/**
 *
 */
package com.example.vila.datasource.cassandra;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.datastax.driver.core.BatchStatement;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.example.vila.datasource.DataSourceException;
import com.example.vila.datasource.cassandra.entity.UserBookEntity;
import com.example.vila.datasource.cassandra.entity.UserInformationEntity;
import com.example.vila.repository.CassandraRepository;

/**
 * Cassandra data source.
 *
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public class CassandraDataSourceImpl extends CassandraRepository implements CassandraDataSource {

	/**
	 * slf4j logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CassandraDataSourceImpl.class);

	/**
	 *
	 */
	public static final String KEYSPACE_DEMO = "demo";
	
	/**
	 *
	 */
	public static final String COLUMNFAMILY_USERINFORMATION = "userinformation";
	
	/**
	 *
	 */
	public static final String COLUMNFAMILY_USERBOOK = "userbook";
	
	/**
	 *
	 */
	private CassandraDataAccess dataAccess;

	
    public CassandraDataAccess getDataAccess() {
        return dataAccess;
    }

   
	@Override
	public void initialize() {

		this.dataAccess = new CassandraDataAccess(this.getSession());
	}
	
    @Override
	public List<UserBookEntity> getUserBooks(String userID, String language) throws DataSourceException {

		return this.dataAccess.getUserBooksByUserIDAndLang(userID, language);
	}
    
	@Override
	public UserInformationEntity getUserInformation(String userID) throws DataSourceException {
		
		return this.dataAccess.getUserInformationByUserID(userID);
	}
	
	@Override
	public boolean updateUserBookFromCloud(List<Item> data) throws DataSourceException {
		
		boolean result = false;
		
		try{
			BatchStatement batchInsertUserBooks = new BatchStatement();
			
			for(Item item : data){
				Insert insertUserBooksStat = QueryBuilder.insertInto(UserBookEntity.KEYSPACE, UserBookEntity.COLUMN_FAMILY);
				insertUserBooksStat.value(UserBookEntity.COLUMN_USERID, item.getString("userID"));
				insertUserBooksStat.value(UserBookEntity.COLUMN_LANGUAGE, item.getString("language"));
				insertUserBooksStat.value(UserBookEntity.COLUMN_BOOKID, item.getString("bookID"));
		        
		        batchInsertUserBooks.add(insertUserBooksStat);
			}
			
			this.getSession().executeAsync(batchInsertUserBooks);

			result = true;
			
		}catch(Exception e){
			logger.error("Exception:{}", e);
		}

		return result;
		
	}

	@Override
	public boolean updateUserInformation(Map<String, Object> m) throws DataSourceException {
		boolean result = false;
		
		try{
			this.dataAccess.updateUserInformation(m);
			
			result = true;
			
		}catch(Exception e){
			logger.error("Exception:{}", e);
		}

		return result;
	}	
	
	@Override
	public boolean updateUserBook(Map<String, Object> m) throws DataSourceException {
		boolean result = false;
		
		try{
			this.dataAccess.updateUserBook(m);
			
			result = true;
			
		}catch(Exception e){
			logger.error("Exception:{}", e);
		}

		return result;
	}	


}