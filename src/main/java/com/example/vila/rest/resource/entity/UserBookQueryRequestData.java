package com.example.vila.rest.resource.entity;

/**
 * 
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public class UserBookQueryRequestData{
	
	private String userID;
	
	private String language;

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
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
		UserBookQueryRequestData other = (UserBookQueryRequestData) obj;
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
		builder.append("UserBookQueryRequestData [userID=");
		builder.append(userID);
		builder.append(", language=");
		builder.append(language);
		builder.append("]");
		return builder.toString();
	}
	
	

	

    
}
