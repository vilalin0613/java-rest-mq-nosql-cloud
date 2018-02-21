package com.example.vila.rest.resource.cloud;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.LoggerFactory;

import com.amazonaws.services.dynamodbv2.document.Item;
import com.example.vila.cloud.CloudService;
import com.example.vila.cloud.object.QueryUserBookItem;
import com.example.vila.datasource.DataSourceException;
import com.example.vila.datasource.cassandra.CassandraDataSource;
import com.example.vila.rest.resource.entity.UserBookQueryRequestData;

public class Syncer implements Callable<Boolean>{

	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(Syncer.class);
	
	private CassandraDataSource cds;
	private com.example.vila.cloud.CloudService cloudService;
	private List<UserBookQueryRequestData> data;
    
	public Syncer(CloudService cloudService, CassandraDataSource cds, List<UserBookQueryRequestData> data){
		this.cloudService = cloudService;
		this.cds = cds;
		this.data = data;
	}
	
	public Boolean call() {

    	ExecutorService executor = Executors.newCachedThreadPool();
    	Collection<BatchGetItemCallable> collection = new ArrayList<BatchGetItemCallable>();
    	
    	List<QueryUserBookItem> queryList = new ArrayList<QueryUserBookItem>();
    	for (UserBookQueryRequestData subData : this.data) {
    			
    		QueryUserBookItem queryItem = new QueryUserBookItem();
			queryItem.setUserID(subData.getUserID());
			queryItem.setLanguage(subData.getLanguage());
			
			queryList.add(queryItem);
			collection.add(new BatchGetItemCallable(cloudService, queryList));
			queryList = new ArrayList<QueryUserBookItem>();
		}
    	
		try {
			List<Future<List<Item>>> futures = executor.invokeAll(collection);
			
			for(Future<List<Item>> f : futures){
				
				try {
					cds.updateUserBookFromCloud(f.get());
					
				} catch (DataSourceException e) {
					logger.error("Exception:{}", e);
				}
				
			}
		} catch (Exception e) {
			logger.error("Exception:{}", e);

		} finally {
			executor.shutdown();
		}
	    	
    	return true;
	}
	
	
}
