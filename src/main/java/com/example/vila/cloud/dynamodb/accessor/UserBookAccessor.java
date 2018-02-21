package com.example.vila.cloud.dynamodb.accessor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.BatchGetItemOutcome;
import com.amazonaws.services.dynamodbv2.document.BatchWriteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DeleteItemOutcome;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.TableKeysAndAttributes;
import com.amazonaws.services.dynamodbv2.document.TableWriteItems;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;
import com.example.vila.cloud.DynamoDBService;
import com.example.vila.cloud.object.QueryUserBookItem;

public class UserBookAccessor {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(UserBookAccessor.class);
	private static String TABLENAME = "";;
	private static AmazonDynamoDBClient CLIENT;

	public UserBookAccessor(DynamoDBService service) {
		if (UserBookAccessor.CLIENT == null && service.getConfigProperty("aws.dynamodb.region")!=null && service.getConfigProperty("aws.dynamodb.tablename")!=null) {
			UserBookAccessor.CLIENT = service.getClient(service.getConfigProperty("aws.dynamodb.region"));
			UserBookAccessor.TABLENAME = service.getConfigProperty("aws.dynamodb.tablename");
			
		}
	}
	
	public List<Item> batchGetItem(List<QueryUserBookItem> items){

		List<Item> result = new ArrayList<Item>();
	
		DynamoDB dynamoDB = new DynamoDB(UserBookAccessor.CLIENT);
		
		TableKeysAndAttributes tableKeysAndAttributes = new TableKeysAndAttributes(UserBookAccessor.TABLENAME);
		for(QueryUserBookItem item : items){
			tableKeysAndAttributes.addHashAndRangePrimaryKey("userID", item.getUserID(), "language", item.getLanguage());
		}
		
		BatchGetItemOutcome outcome = dynamoDB.batchGetItem(tableKeysAndAttributes);
		
		Map<String, KeysAndAttributes> unprocessed = null;

		
		try {
			do {
				for (String tableName : outcome.getTableItems().keySet()) {
					result = outcome.getTableItems().get(tableName);
				}

				unprocessed = outcome.getUnprocessedKeys();

				if (unprocessed.isEmpty()) {
					logger.debug("No unprocessed keys found");
					
				} else {
					outcome = dynamoDB.batchGetItemUnprocessed(unprocessed);
				}

			} while (!unprocessed.isEmpty());

		} catch (Exception e) {
			logger.error("Failed to retrieve items:{}",e.getMessage());
		}
		
		return result;
	}
	
	
	public boolean batchPutItem(Collection<Item> items) {

		boolean result = false;
		
		try {

			DynamoDB dynamoDB = new DynamoDB(UserBookAccessor.CLIENT);
			TableWriteItems threadTableWriteItems = new TableWriteItems(UserBookAccessor.TABLENAME);
			
			threadTableWriteItems.withItemsToPut(items);
			BatchWriteItemOutcome outcome = dynamoDB.batchWriteItem(threadTableWriteItems);
			
			do {
				Map<String, List<WriteRequest>> unprocessedItems = outcome.getUnprocessedItems();

				if (outcome.getUnprocessedItems().size() > 0) {
					
					outcome = dynamoDB.batchWriteItemUnprocessed(unprocessedItems);
					
					logger.info("Unprocessed result size : {}", outcome.getBatchWriteItemResult().getUnprocessedItems().size());
				}

			} while (outcome.getUnprocessedItems().size() > 0);

			for (Item writeItem : threadTableWriteItems.getItemsToPut()) {
				logger.info("UpdateItem succeeded {}", writeItem.toJSONPretty());
			}
			
			result = true;
			
		} catch (Exception e) {
			logger.error("Failed to update items: {}", e);
			
		}
		
		return result;
	}
	
	
	public boolean deleteItem(String userID, String language){
	
		boolean result = false;
		
		try {
			DynamoDB dynamoDB = new DynamoDB(UserBookAccessor.CLIENT);
			Table table = dynamoDB.getTable(UserBookAccessor.TABLENAME);
			DeleteItemOutcome outcome = table.deleteItem("userID", userID, "language", language);
    
			logger.info("DeleteItem is successful");
			
			result = true;
			
		} catch (Exception e) {
			logger.error("Failed to delete item: {}", e);
			
		}
		
		logger.trace("METHOD deleteItem OUT");
		
		return result;
	}
}
