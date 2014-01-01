/**
 * 
 */
package com.mychaelstyle.mydatastore.providers;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.MongoClient;
import com.mychaelstyle.mydatastore.Provider;

/**
 * @author masanori
 *
 */
public class MongoDB extends Provider {

    public static final String CONFIG_HOST = "mongo_host";
    public static final String CONFIG_PORT = "mongo_port";
    private MongoClient client;
    /**
     * 
     */
    public MongoDB() {
        super();
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#config(java.util.Map)
     */
    @Override
    public void config(Map<String, String> config) throws Exception {
        this.client = new MongoClient(config.get(CONFIG_HOST), Integer.parseInt(config.get(CONFIG_PORT)));

    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchGet(org.json.JSONArray)
     */
    @Override
    public JSONObject batchGet(JSONArray conditions) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchPut(org.json.JSONArray)
     */
    @Override
    public void batchPut(JSONArray tableRows) throws Exception {
        // TODO Auto-generated method stub

    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchDelete(org.json.JSONArray)
     */
    @Override
    public void batchDelete(JSONArray conditions) throws Exception {
        // TODO Auto-generated method stub

    }

}
