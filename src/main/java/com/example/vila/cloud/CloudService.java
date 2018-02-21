
package com.example.vila.cloud;

import java.util.List;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.vila.cloud.object.QueryUserBookItem;

/**
 * The contact data source.
 * 
 * @author  Vila Lin<vila0613@gmail.com>
 * @version 0.0.1
 * @since   0.0.1
 */
public interface CloudService {

	/**
	 * Batch get items from federated cloud basic infomation table (maximum : 100 records per time)
	 * 
	 * @param items
	 * @return Item list
	 */
	public List<Item> queryUserBook(List<QueryUserBookItem> items);
}
