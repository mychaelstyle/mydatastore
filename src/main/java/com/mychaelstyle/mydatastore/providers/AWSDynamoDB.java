/**
 * Amazon Web Service DynamoDB datastore provider
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore.providers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONObject;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemRequest;
import com.amazonaws.services.dynamodbv2.model.BatchGetItemResult;
import com.amazonaws.services.dynamodbv2.model.BatchWriteItemRequest;
import com.amazonaws.services.dynamodbv2.model.DeleteRequest;
import com.amazonaws.services.dynamodbv2.model.KeysAndAttributes;
import com.amazonaws.services.dynamodbv2.model.PutRequest;
import com.amazonaws.services.dynamodbv2.model.WriteRequest;

/**
 * Amazon Web Service DynamoDB datastore provider
 * @author Masanori Nakashima
 *
 */
public class AWSDynamoDB extends AWS {

    /**
     * configuration map key for DynamoDB end point
     */
    public static final String CONFIG_ENDPOINT   = "dynamodb_endpoint";

    /**
     * dynamodbv2 client
     */
    private AmazonDynamoDBClient client;

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
    @SuppressWarnings("unchecked")
    @Override
    public JSONObject batchGet(JSONArray conditions) throws Exception {
        BatchGetItemRequest request = new BatchGetItemRequest();
        for(int num=0; num<conditions.length(); num++){
            JSONObject condition = conditions.getJSONObject(num);
            Map<String,AttributeValue> map = new HashMap<String,AttributeValue>();
            String table = condition.getString(FIELD_TABLE);
            JSONObject cond = condition.getJSONObject(FIELD_DATA);
            Set<Object> fields = cond.keySet();
            for(Object field : fields){
                String fname = (String) field;
                Object value = cond.get(fname);
                if(value instanceof Integer){
                    map.put(fname, new AttributeValue().withN(value.toString()));
                } else {
                    map.put(fname, new AttributeValue().withS(value.toString()));
                }
            }
            KeysAndAttributes keys = new KeysAndAttributes().withKeys(map);
            request.addRequestItemsEntry(table, keys);
        }
        BatchGetItemResult result = this.client.batchGetItem(request);
        Map<String,List<Map<String,AttributeValue>>> resMap = result.getResponses();
        JSONObject retJson = new JSONObject();
        for(String table : resMap.keySet()){
            List<Map<String,AttributeValue>> rows = resMap.get(table);
            JSONArray jsonRows = new JSONArray();
            for(Map<String,AttributeValue> row : rows){
                JSONObject obj = new JSONObject();
                for(String field : row.keySet()){
                    obj.put(field, row.get(field).getS());
                }
                jsonRows.put(obj);
            }
            retJson.put(table, jsonRows);
        }
        return retJson;
    }


    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchPut(org.json.JSONObject)
     */
    @Override
    public void batchPut(JSONArray tableRows) throws Exception {
        JSONArray rows = new JSONArray();
        for(int num=0; num<tableRows.length(); num++){
            JSONObject obj = (JSONObject) tableRows.getJSONObject(num);
            obj.put(FIELD_ACTION, ACTION_PUT);
            rows.put(obj);
        }
        this.batchWrite(rows);
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchRemove(org.json.JSONObject)
     */
    @Override
    public void batchDelete(JSONArray conditions) throws Exception {
        JSONArray rows = new JSONArray();
        for(int num=0; num<conditions.length(); num++){
            JSONObject obj = (JSONObject) conditions.getJSONObject(num);
            obj.put(FIELD_ACTION, ACTION_DELETE);
            rows.put(obj);
        }
        this.batchWrite(rows);
    }

    /**
     * get DynamoDB client
     * @return AmazonDynamoDBClient
     */
    protected AmazonDynamoDBClient getClient(Map<String,String> config) throws Exception {
        if(null == client){
            AWSCredentials credentials = AWS.getCredentials(config);
            client = new AmazonDynamoDBClient(credentials);
            // dynamodb.ap-northeast-1.amazonaws.com
            String endpoint = config.get(AWSDynamoDB.CONFIG_ENDPOINT);
            if(null==endpoint) throw new Exception("require "+AWSDynamoDB.CONFIG_ENDPOINT);
            client.setEndpoint(endpoint);
        }
        return client;
    }

    /**
     * batch write
     * @param jsonArray
     */
    private void batchWrite(JSONArray jsonArray){
        Map<String,List<WriteRequest>> itemsMap = new HashMap<String,List<WriteRequest>>();
        for(int num=0; num<jsonArray.length(); num++){
            JSONObject json = jsonArray.getJSONObject(num);
            String table  = json.getString(FIELD_TABLE);
            WriteRequest item = createWriteRequest(json);
            List<WriteRequest> trRequests = new ArrayList<WriteRequest>();
            if(itemsMap.containsKey(table)){
                trRequests = itemsMap.get(table);
            }
            trRequests.add(item);
            itemsMap.put(table, trRequests);
        }
        BatchWriteItemRequest request = new BatchWriteItemRequest();
        request.setRequestItems(itemsMap);
        this.client.batchWriteItem(request);
    }

    /**
     * create WriteRequest for batchWrite
     * @param json
     * @return
     */
    private static WriteRequest createWriteRequest(JSONObject json){
        String action = json.getString(FIELD_ACTION);
        JSONObject data   = json.getJSONObject(FIELD_DATA);
        if(ACTION_DELETE.equalsIgnoreCase(action)){
            DeleteRequest delReq = new DeleteRequest();
            for(Object ko : data.keySet()){
                String k = (String) ko;
                Object v = data.get(k);
                if(v instanceof Integer){
                    delReq.addKeyEntry(k, new AttributeValue().withN(((Integer)v).toString()));
                } else if(v instanceof String){
                    delReq.addKeyEntry(k, new AttributeValue().withS((String)v));
                } else {
                    String str = JSONObject.valueToString(v);
                    delReq.addKeyEntry(k, new AttributeValue().withS(str));
                }
            }
            return new WriteRequest(delReq);
        } else {
            PutRequest putReq = new PutRequest();
            for(Object ko : data.keySet()){
                String k = (String) ko;
                Object v = data.get(k);
                if(v instanceof Integer){
                    putReq.addItemEntry(k, new AttributeValue().withN(((Integer)v).toString()));
                } else if(v instanceof String){
                    putReq.addItemEntry(k, new AttributeValue().withS((String)v));
                } else {
                    String str = JSONObject.valueToString(v);
                    putReq.addItemEntry(k, new AttributeValue().withS(str));
                }
            }
            return new WriteRequest(putReq);
        }
    }
}