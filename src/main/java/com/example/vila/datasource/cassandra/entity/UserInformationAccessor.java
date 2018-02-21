/**
 * 
 */
package com.example.vila.datasource.cassandra.entity;

import com.datastax.driver.core.ResultSet;
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
public interface UserInformationAccessor {
    
	@Query("SELECT * FROM demo.userinformation")
    public Result<UserInformationEntity> getAllUserInformation();
	
	@Query("SELECT * FROM demo.userinformation WHERE userid = :userid")
    public Result<UserInformationEntity> getUserInformationByUserID(@Param("userid") String userid);
	
	@Query("UPDATE demo.userinformation SET username = :username WHERE userid = :userid")
    public ResultSet updateUserName(@Param("username") String username, @Param("userid") String userid);
	
	@Query("DELETE FROM demo.userinformation WHERE userid = :userid")
    public ResultSet deleteUserInformation(@Param("userid") String userid);
	
}
