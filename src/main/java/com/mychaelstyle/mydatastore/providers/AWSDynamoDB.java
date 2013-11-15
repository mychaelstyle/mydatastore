/**
 * Amazon Web Service DynamoDB datastore provider
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore.providers;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.mychaelstyle.mydatastore.Provider;

/**
 * Amazon Web Service DynamoDB datastore provider
 * @author Masanori Nakashima
 *
 */
public class AWSDynamoDB extends Provider {

	/**
	 * configuration map key for DynamoDB end point
	 */
	public static final String CONFIG_ENDPOINT   = "dynamodb_endpoint";
	/**
	 * configuration map key for Amazon Web Service Access Key
	 */
	public static final String CONFIG_ACCESS_KEY = "aws_access_key";
	/**
	 * configuration map key for Amazon Web Service Access Secret Key
	 */
	public static final String CONFIG_SECRET_KEY = "aws_secret_key";
	/**
	 * Constructor
	 */
	public AWSDynamoDB() {
		super();
	}

	@Override
	public void config(Map<String, String> config) throws Exception {
		this.getClient(config);
	}

	/* (non-Javadoc)
	 * @see com.mychaelstyle.mydatastore.IProvider#batchGet(org.json.JSONObject)
	 */
	@Override
	public JSONArray batchGet(JSONObject conditions) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see com.mychaelstyle.mydatastore.IProvider#batchPut(org.json.JSONObject)
	 */
	@Override
	public void batchPut(JSONObject tableRows) throws Exception {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see com.mychaelstyle.mydatastore.IProvider#batchRemove(org.json.JSONObject)
	 */
	@Override
	public void batchRemove(JSONObject conditions) throws Exception {
		// TODO Auto-generated method stub

	}

	/**
	 * dynamodbv2 client
	 */
	private AmazonDynamoDBClient client;
	/**
	 * get DynamoDB client
	 * @return AmazonDynamoDBClient
	 */
	protected AmazonDynamoDBClient getClient(Map<String,String> config) throws Exception {
		if(null == client){
			AWSCredentials credentials = AWSDynamoDB.getCredentials(config);
	        client = new AmazonDynamoDBClient(credentials);
	        // dynamodb.ap-northeast-1.amazonaws.com
	        String endpoint = config.get(AWSDynamoDB.CONFIG_ENDPOINT);
	        if(null==endpoint) throw new Exception("lost "+AWSDynamoDB.CONFIG_ENDPOINT);
	        client.setEndpoint(endpoint);
		}
        return client;
	}

	/**
	 * get AWSCredentials instance for connection
	 * @return AWSCredentials
	 */
	private static AWSCredentials getCredentials(Map<String,String> config) throws Exception {
		String accessKey = config.get(AWSDynamoDB.CONFIG_ACCESS_KEY);
		String secretKey = config.get(AWSDynamoDB.CONFIG_SECRET_KEY);
		if(accessKey==null) throw new Exception("lost " + AWSDynamoDB.CONFIG_ACCESS_KEY);
		if(secretKey==null) throw new Exception("lost " + AWSDynamoDB.CONFIG_SECRET_KEY);
		return new BasicAWSCredentials(accessKey,secretKey);
	}

}
