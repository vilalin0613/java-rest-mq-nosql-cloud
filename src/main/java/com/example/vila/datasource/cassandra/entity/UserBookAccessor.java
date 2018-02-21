/**
 * 
 */
package com.example.vila.datasource.cassandra.entity;

import com.datastax.driver.mapping.Result;
import com.datastax.driver.mapping.annotations.Accessor;
import com.datastax.driver.mapping.annotations.Param;
import com.datastax.driver.mapping.annotations.Query;

/**
 * Cassandra data accessor
 * 
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@Accessor
public interface UserBookAccessor {
    
	@Query("SELECT * FROM demo.userbook")
    public Result<UserBookEntity> getAllUserBook();
	
	@Query("SELECT * FROM demo.userbook WHERE userID = :userID")
    public Result<UserBookEntity> getBooksByUserID(@Param("userID") String userID);

	@Query("SELECT * FROM demo.userbook WHERE userID = :userID AND language = :language")
    public Result<UserBookEntity> getBooksByUserIDAndLang(@Param("userID") String userID, @Param("language") String language);
	
}
