/**
 * MyDataStore object class 
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * MyDataStore object class 
 * 
 * This library can use data store service easy via JSON.<br>
 * And you can put data to some data store services at once. for example, can put to MySQL table, Amazon DynamoDB,
 * Memcached, Redis ... etc.<br>
 * <br>
 * e.g)
 * <pre>
 * MyDataStore dataStore = new MyDataStore();
 * IProvider dynamodb = new AWSDynamoDB();
 * dynamodb.config(configMap);
 * dataStore.addProvider(dynamodb);
 * IProvider memcached = new Memcached();
 * memcached.config(configMap);
 * dataStore.addProvider(memcached);
 * </pre>
 * <br>
 * <br>
 * you can put item using the following JSON string.<br>
 * <br>
 * <pre>
 * String str = "
 * {
 *   "action" : "put",
 *   "table"  : "foo_table",
 *   "data"   : {
 *     "key" : "keyvalue1",
 *     "value: "hogehoge",
 *     ...
 *   }
 * }
 * ";
 * </pre>
 * 
 * <pre>
 * JSONObject data = new JSONObject(str);
 * dataStore.put(tableName,data);
 * </pre>
 * <br>
 * you can get items like the followings.<br>
 * <pre>
 * String cond = "
 * {
 *   "key" : "keyvalue"
 * }";
 * JSONObject result = dataStore.get(tableName, cond);
 * </pre>
 * The result JSON format.
 * <pre>
 * {
 *   "key" : "keyvalue",
 *   "field1" : "field1",
 *   ...
 * }
 * </pre>
 * 
 * @author Masanori Nakashima
 * @link https://github.com/mychaelstyle/mydatastore
 */
public class MyDataStore {

    /**
     * key value store providers list
     */
    private List<IProvider> providers = new ArrayList<IProvider>();

    /**
     * Constructor
     */
    public MyDataStore() {
        super();
    }

    /**
     * add IProvider instance
     * @param provider
     */
    public void addProvider(IProvider provider) {
        this.providers.add(provider);
    }

    /**
     * get a row
     * @param table table name
     * @param condition key field name and identified value
     * @return JSONObject for a row field data.
     * @throws Exception
     */
    public JSONObject get(String table, JSONObject condition) throws Exception {
        Exception lastException = null;
        for(IProvider provider : this.providers) {
            try {
                JSONObject result = provider.get(table, condition);
                if(null!=result) {
                    return result;
                }
            } catch(Exception e) {
                lastException = e;
        	}
        }
        throw lastException;
    }
    /**
     * set a row
     * @param table table name
     * @param map field name and value map
     * @throws Exception
     */
    public void put(String table, JSONObject row) throws Exception {
        for(IProvider provider : this.providers) {
            provider.put(table, row);
        }
    }
    /**
     * remove a row
     * @param table table name
     * @param condition key field name and identified value
     * @throws Exception
     */
    public void delete(String table, JSONObject condition) throws Exception {
        for(IProvider provider : this.providers) {
            provider.delete(table, condition);
        }
    }
    /**
     * batch get rows
     * @param map
     * @return
     * @throws Exception
     */
    public JSONObject batchGet(JSONArray conditions) throws Exception {
        Exception lastException = null;
        for(IProvider provider : this.providers) {
            try {
                JSONObject result = provider.batchGet(conditions);
                if(null!=result) {
                    return result;
                }
            } catch(Exception e) {
                lastException = e;
            }
        }
        throw lastException;		
    }

    /**
     * batch put rows
     * @param tableRows
     * @throws Exception
     */
    public void batchPut(JSONArray tableRows) throws Exception {
        for(IProvider provider : this.providers) {
            provider.batchPut(tableRows);
        }
    }

    /**
     * batch remove rows
     * @param tableRows
     * @throws Exception
     */
    public void batchDelete(JSONArray conditions) throws Exception {
        for(IProvider provider : this.providers) {
            provider.batchDelete(conditions);
        }
    }

}
