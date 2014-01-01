/**
 * 
 */
package com.mychaelstyle.mydatastore.providers;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mychaelstyle.mydatastore.Provider;

/**
 * @author Masanori
 *
 */
public class Cassandra extends Provider {

    /**
     * 
     */
    public Cassandra() {
        // TODO Auto-generated constructor stub
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#config(java.util.Map)
     */
    @Override
    public void config(Map<String, String> config) throws Exception {
        // TODO Auto-generated method stub

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
