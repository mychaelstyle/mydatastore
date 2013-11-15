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
 * @author Masanori Nakashima
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
	public void remove(String table, JSONObject condition) throws Exception {
		for(IProvider provider : this.providers) {
			provider.remove(table, condition);
		}
	}
	/**
	 * batch get rows
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public JSONArray batchGet(JSONObject conditions) throws Exception {
		Exception lastException = null;
		for(IProvider provider : this.providers) {
			try {
				JSONArray result = provider.batchGet(conditions);
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
	public void batchPut(JSONObject tableRows) throws Exception {
		for(IProvider provider : this.providers) {
			provider.batchPut(tableRows);
		}
	}

	/**
	 * batch remove rows
	 * @param tableRows
	 * @throws Exception
	 */
	public void batchRemove(JSONObject conditions) throws Exception {
		for(IProvider provider : this.providers) {
			provider.batchRemove(conditions);
		}
	}

}
