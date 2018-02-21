package com.example.vila.cloud;

import java.util.List;
import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.vila.cloud.dynamodb.accessor.UserBookAccessor;
import com.example.vila.cloud.object.QueryUserBookItem;

public class CloudServiceImpl implements CloudService {

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(CloudServiceImpl.class);

	private static CloudServiceImpl instance = null;
	private UserBookAccessor userBookAccessor = null;

	
	public static CloudServiceImpl getInstance(Properties configProps) {

		if (instance == null) {
			instance = new CloudServiceImpl(configProps);
		}

		return instance;
	}

	public CloudServiceImpl(Properties configProps) {
		
		DynamoDBService service = new DynamoDBService(configProps);
		this.userBookAccessor = new UserBookAccessor(service);

	}

	@Override
	public List<Item> queryUserBook(List<QueryUserBookItem> items) {
		return this.userBookAccessor.batchGetItem(items);
	}


}
