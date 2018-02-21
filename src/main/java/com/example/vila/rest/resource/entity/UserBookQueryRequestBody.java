package com.example.vila.rest.resource.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserBookQueryRequestBody extends AppRequestBody {
	
	private List<UserBookQueryRequestData> data;
	
	
	/**
	 * Constructor. Each field MUST be initialized a default value here. 
	 * 
	 */
	public UserBookQueryRequestBody() {
	    super();
        this.data = null;
	}


	public List<UserBookQueryRequestData> getData() {
		return data;
	}


	public void setData(List<UserBookQueryRequestData> data) {
		this.data = data;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((data == null) ? 0 : data.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBookQueryRequestBody other = (UserBookQueryRequestBody) obj;
		if (data == null) {
			if (other.data != null)
				return false;
		} else if (!data.equals(other.data))
			return false;
		return true;
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserBookQueryRequestBody [data=");
		builder.append(data);
		builder.append("]");
		return builder.toString();
	}
	
	
    
}
