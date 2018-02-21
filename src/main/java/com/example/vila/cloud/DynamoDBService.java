package com.example.vila.cloud;

import java.util.Properties;

import org.slf4j.LoggerFactory;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.RegionUtils;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

/**
 *
 * @author Vila <vila@loftechs.com>
 */
public class DynamoDBService {

	/**
	 * slf4j logger
	 */
	private static final org.slf4j.Logger logger = LoggerFactory.getLogger(DynamoDBService.class);
	private static BasicAWSCredentials credential = null;
	private static AmazonDynamoDBClient client = null;
	private Properties configProps;

	public DynamoDBService(Properties configProps) {
		this.configProps = configProps;
	}

	public Properties getConfigProps() {
		return configProps;
	}
	
	public String getConfigProperty(String key) {
		return (String) configProps.get(key);
	}

	public synchronized AWSCredentials getCredential() {
		
		logger.trace("METHOD getCredential IN");

		if (credential == null) {
			credential = new BasicAWSCredentials(configProps.getProperty("aws.accesskey"),
					configProps.getProperty("aws.secretkey"));
		}

		logger.trace("METHOD getCredential OUT");

		return credential;
	}

	public synchronized AmazonDynamoDBClient getClient(String regionName) {
		
		logger.trace("METHOD getClient IN");

		if (client == null) {
			Region apRegion = RegionUtils.getRegion(regionName);
			client = new AmazonDynamoDBClient(getCredential());
			client.setRegion(apRegion);
		}

		logger.trace("METHOD getClient OUT");

		return client;
	}

}
