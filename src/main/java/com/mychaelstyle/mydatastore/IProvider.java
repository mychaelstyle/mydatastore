/**
 * Data Store provider interface 
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Data Store provider interface 
 * @author Masanori Nakashima
 */
public interface IProvider {
    /**
     * JSON field name : action
     */
    public static final String FIELD_ACTION = "action";
    /**
     * JSON field name : table
     */
    public static final String FIELD_TABLE  = "table";
    /**
     * JSON field name : data
     */
    public static final String FIELD_DATA   = "data";
    /**
     * JSON field name : key field name
     */
    public static final String FIELD_KEY    = "key";
    /**
     * JSON field action: put
     */
    public static final String ACTION_PUT = "put";
    /**
     * JSON field action : delete
     */
    public static final String ACTION_DELETE = "delete";
    /**
     * configuration
     * @param config
     * @throws Exception
     */
    public void config(Map<String,String> config) throws Exception;
    /**
     * get a row
     * @param table table name
     * @param condition key field name and identified value
     * @return JSONObject for a row field data.
     * @throws Exception
     */
    public JSONObject get(String table, JSONObject condition) throws Exception;
    /**
     * set a row
     * @param table table name
     * @param map field name and value map
     * @throws Exception
     */
    public void put(String table, JSONObject row) throws Exception;
    /**
     * remove a row
     * @param table table name
     * @param condition key field name and identified value
     * @throws Exception
     */
    public void delete(String table, JSONObject condition) throws Exception;
    /**
     * batch get rows
     * @param map
     * @return
     * @throws Exception
     */
    public JSONObject batchGet(JSONArray conditions) throws Exception;
    /**
     * batch put rows
     * @param tableRows
     * @throws Exception
     */
    public void batchPut(JSONArray tableRows) throws Exception;
    /**
     * batch remove rows
     * @param tableRows
     * @throws Exception
     */
    public void batchDelete(JSONArray conditions) throws Exception;
}
