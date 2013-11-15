/**
 * Provider abstract object class
 * 
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Provider abstract object class
 * 
 * @author Masanori Nakashima
 */
public abstract class Provider implements IProvider {

	/* (non-Javadoc)
	 * @see com.mychaelstyle.mydatastore.IProvider#get(java.lang.String, org.json.JSONObject)
	 */
	@Override
	public JSONObject get(String table, JSONObject condition) throws Exception {
		JSONObject conditions = new JSONObject();
		JSONArray rows = new JSONArray();
		rows.put(condition);
		conditions.put(table, rows);
		JSONArray result = this.batchGet(conditions);
		return result.getJSONObject(0);
	}

	/* (non-Javadoc)
	 * @see com.mychaelstyle.mydatastore.IProvider#put(java.lang.String, org.json.JSONObject)
	 */
	@Override
	public void put(String table, JSONObject row) throws Exception {
		JSONObject tableRows = new JSONObject();
		JSONArray rows = new JSONArray();
		rows.put(row);
		tableRows.put(table, rows);
		this.batchPut(tableRows);
	}

	/* (non-Javadoc)
	 * @see com.mychaelstyle.mydatastore.IProvider#remove(java.lang.String, org.json.JSONObject)
	 */
	@Override
	public void remove(String table, JSONObject condition) throws Exception {
		JSONObject conditions = new JSONObject();
		JSONArray rows = new JSONArray();
		rows.put(condition);
		conditions.put(table, rows);
		this.batchRemove(conditions);
	}

}
