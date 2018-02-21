/**
 * 
 */
package com.example.vila.datasource.cassandra.entity;

import com.datastax.driver.mapping.annotations.Field;
import com.datastax.driver.mapping.annotations.UDT;
import com.example.vila.datasource.cassandra.CassandraDataSourceImpl;

/**
 * Cassandra data model.
 * 
 * @author  Vila Lin <vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@UDT(keyspace = CassandraDataSourceImpl.KEYSPACE_DEMO, name = CassandraDataSourceImpl.COLUMNFAMILY_USERINFORMATION)
public class UserInformationEntity {

	public static final String KEYSPACE = CassandraDataSourceImpl.KEYSPACE_DEMO;
	
	public static final String COLUMN_FAMILY = CassandraDataSourceImpl.COLUMNFAMILY_USERINFORMATION;
	
	/**
     * 
     */
    public static final String COLUMN_USERID = "userID";
	
    /**
     * 
     */
    public static final String COLUMN_USERNAME = "username";

    
    /**
     * 
     */
    @Field(name = UserInformationEntity.COLUMN_USERID)
    private String userID;
    
    /**
     * 
     */
    @Field(name = UserInformationEntity.COLUMN_USERNAME)
    private String userName;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserInformationEntity other = (UserInformationEntity) obj;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserInformationEntity [userID=");
		builder.append(userID);
		builder.append(", userName=");
		builder.append(userName);
		builder.append("]");
		return builder.toString();
	}
    
    
	


	
	
    
}
