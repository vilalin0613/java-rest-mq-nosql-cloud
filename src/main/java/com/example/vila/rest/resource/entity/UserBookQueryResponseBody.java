/**
 * 
 */
package com.example.vila.rest.resource.entity;

import java.util.HashMap;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * REST web service data model.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserBookQueryResponseBody extends ResponseBody{


	/**
     * 
     */
    private List<HashMap<String, String>> books;
  
    public UserBookQueryResponseBody(List<HashMap<String, String>> books){
    	this.books = books;
    }

	public List<HashMap<String, String>> getBooks() {
		return books;
	}

	public void setBooks(List<HashMap<String, String>> books) {
		this.books = books;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((books == null) ? 0 : books.hashCode());
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
		UserBookQueryResponseBody other = (UserBookQueryResponseBody) obj;
		if (books == null) {
			if (other.books != null)
				return false;
		} else if (!books.equals(other.books))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("UserBookQueryResponseBody [books=");
		builder.append(books);
		builder.append("]");
		return builder.toString();
	}
    
    
}
