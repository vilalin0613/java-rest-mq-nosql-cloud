package com.example.vila.rest.resource.cloud;

import java.util.List;
import java.util.concurrent.Callable;

import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.vila.cloud.CloudService;
import com.example.vila.cloud.object.QueryUserBookItem;

public class BatchGetItemCallable implements Callable<List<Item>>{

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(BatchGetItemCallable.class);
	private CloudService cloudService;
	private List<QueryUserBookItem> queryList;
	
	public BatchGetItemCallable(CloudService cloudService, List<QueryUserBookItem> queryList){
		this.cloudService = cloudService;
		this.queryList = queryList;
	}

	@Override
	public List<Item> call() throws Exception {
		
		return this.cloudService.queryUserBook(this.queryList);
	}
	
}
