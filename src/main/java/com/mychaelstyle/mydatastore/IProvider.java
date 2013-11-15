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
	public void remove(String table, JSONObject condition) throws Exception;
	/**
	 * batch get rows
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONArray batchGet(JSONObject conditions) throws Exception;
	/**
	 * batch put rows
	 * @param tableRows
	 * @throws Exception
	 */
	public void batchPut(JSONObject tableRows) throws Exception;
	/**
	 * batch remove rows
	 * @param tableRows
	 * @throws Exception
	 */
	public void batchRemove(JSONObject conditions) throws Exception;
}
