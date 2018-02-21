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
@UDT(keyspace = CassandraDataSourceImpl.KEYSPACE_DEMO, name = CassandraDataSourceImpl.COLUMNFAMILY_USERBOOK)
public class UserBookEntity {

	public static final String KEYSPACE = CassandraDataSourceImpl.KEYSPACE_DEMO;
	
	public static final String COLUMN_FAMILY = CassandraDataSourceImpl.COLUMNFAMILY_USERBOOK;
	
	/**
     * 
     */
    public static final String COLUMN_USERID = "userID";
	
    /**
     * 
     */
    public static final String COLUMN_LANGUAGE = "language";

    /**
     * 
     */
    public static final String COLUMN_BOOKID = "bookID";
    
    /**
     * 
     */
    @Field(name = UserBookEntity.COLUMN_USERID)
    private String userID;
    
    /**
     * 
     */
    @Field(name = UserBookEntity.COLUMN_LANGUAGE)
    private String language;
    
    /**
     * 
     */
    @Field(name = UserBookEntity.COLUMN_BOOKID)
    private String bookID;

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bookID == null) ? 0 : bookID.hashCode());
		result = prime * result + ((language == null) ? 0 : language.hashCode());
		result = prime * result + ((userID == null) ? 0 : userID.hashCode());
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
		UserBookEntity other = (UserBookEntity) obj;
		if (bookID == null) {
			if (other.bookID != null)
				return false;
		} else if (!bookID.equals(other.bookID))
			return false;
		if (language == null) {
			if (other.language != null)
				return false;
		} else if (!language.equals(other.language))
			return false;
		if (userID == null) {
			if (other.userID != null)
				return false;
		} else if (!userID.equals(other.userID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserBookEntity [userID=");
		builder.append(userID);
		builder.append(", language=");
		builder.append(language);
		builder.append(", bookID=");
		builder.append(bookID);
		builder.append("]");
		return builder.toString();
	}
    
   


	
	
    
}
