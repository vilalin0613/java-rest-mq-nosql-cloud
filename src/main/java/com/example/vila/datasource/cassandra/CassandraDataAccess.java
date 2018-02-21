/**
 *
 */
package com.example.vila.datasource.cassandra;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.datastax.driver.mapping.Result;
import com.example.vila.datasource.cassandra.entity.UserBookAccessor;
import com.example.vila.datasource.cassandra.entity.UserBookEntity;
import com.example.vila.datasource.cassandra.entity.UserInformationAccessor;
import com.example.vila.datasource.cassandra.entity.UserInformationEntity;

/**
 * Cassandra data access.
 *
 * @author Vila Lin <vila0613@gmail.com>
 * @version 0.1.1
 * @since 0.0.1
 */
public class CassandraDataAccess {

	/**
	 * slf4j logger
	 */
	private static final Logger logger = LoggerFactory.getLogger(CassandraDataAccess.class);

	private Mapper<UserBookEntity> userBookMapper;
	private UserBookAccessor userBookAccessor;

	private Mapper<UserInformationEntity> userInformationMapper;
	private UserInformationAccessor userInformationAccessor;

	/**
	 * @param session
	 */
	public CassandraDataAccess(Session session) {

		MappingManager manager = new MappingManager(session);

		this.userBookMapper = manager.mapper(UserBookEntity.class);
		this.userBookAccessor = manager.createAccessor(UserBookAccessor.class);
		
		this.userInformationMapper = manager.mapper(UserInformationEntity.class);
		this.userInformationAccessor = manager.createAccessor(UserInformationAccessor.class);
	}

	public boolean insertUserBook(UserBookEntity userBookEntity) {


		boolean result = false;
		
		try{
			this.userBookMapper.save(userBookEntity);
			result = true;

		}catch(Exception e){
			logger.error("Exception:{}", e);
		}

		return result;
	}

	/**
	 * update user book a part of fields 
	 * @param m
	 * @return
	 */
	public boolean updateUserBook(Map<String, Object> m) {

		boolean result = false;

		Insert stat = QueryBuilder.insertInto(UserBookEntity.KEYSPACE, UserBookEntity.COLUMN_FAMILY);
		
		if (m.containsKey("userID") && m.get("userID")!=null) {
			stat.value("userID", m.get("userID"));
		}
		if (m.containsKey("language") && m.get("language")!=null) {
			stat.value("language", m.get("language"));
		}
		if (m.containsKey("bookID") && m.get("phonebookIDtype")!=null) {
			stat.value("bookID", m.get("bookID"));
		}
		
		try{
			this.userBookMapper.getManager().getSession().execute(stat);
			result = true;

		}catch(Exception e){
			logger.error("Exception:{}", e);
		}

		return result;
	}
	
	public List<UserBookEntity> getUserBooksByUserID(String userID) {

		List<UserBookEntity> result = new ArrayList<UserBookEntity>();
		
		Result<UserBookEntity> userBookEntitys = this.userBookAccessor.getBooksByUserID(userID);

		for (UserBookEntity entity : userBookEntitys) {

			result.add(entity);
		}

		return result;
	}

	public List<UserBookEntity> getUserBooksByUserIDAndLang(String userID, String language) {

		List<UserBookEntity> result = new ArrayList<UserBookEntity>();
		
		Result<UserBookEntity> userBookEntitys = this.userBookAccessor.getBooksByUserIDAndLang(userID, language);

		for (UserBookEntity entity : userBookEntitys) {

			result.add(entity);
		}

		return result;
	}
	
	public UserInformationEntity getUserInformationByUserID(String userID) {

		UserInformationEntity result = null;
				
		result = this.userInformationMapper.get(userID);

		return result;
	}
	
	/**
	 * update user information a part of fields 
	 * @param m
	 * @return
	 */
	public boolean updateUserInformation(Map<String, Object> m) {

		boolean result = false;

		Insert stat = QueryBuilder.insertInto(UserInformationEntity.KEYSPACE, UserInformationEntity.COLUMN_FAMILY);
		
		if (m.containsKey("userID") && m.get("userID")!=null) {
			stat.value("userID", m.get("userID"));
		}
		if (m.containsKey("userName") && m.get("userName")!=null) {
			stat.value("userName", m.get("userName"));
		}
		
		try{
			this.userInformationMapper.getManager().getSession().execute(stat);
			result = true;

		}catch(Exception e){
			logger.error("Exception:{}", e);
		}

		return result;
	}
	
	
}
