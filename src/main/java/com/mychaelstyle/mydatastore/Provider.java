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
        JSONArray conditions = new JSONArray();
        JSONObject cond = new JSONObject();
        cond.put(FIELD_TABLE, table);
        cond.put(FIELD_DATA,condition);
        conditions.put(cond);
        JSONObject result = this.batchGet(conditions);
        JSONArray arr = result.getJSONArray(table);
        if(arr.length()>0){
            return arr.getJSONObject(0);
        }
        return null;
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#put(java.lang.String, org.json.JSONObject)
     */
    @Override
    public void put(String table, JSONObject row) throws Exception {
        JSONArray rows = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put(FIELD_ACTION, ACTION_PUT);
        obj.put(FIELD_TABLE, table);
        obj.put(FIELD_DATA, row);
        rows.put(obj);
        this.batchPut(rows);
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#remove(java.lang.String, org.json.JSONObject)
     */
    @Override
    public void delete(String table, JSONObject condition) throws Exception {
        JSONArray rows = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put(FIELD_ACTION, ACTION_DELETE);
        obj.put(FIELD_TABLE, table);
        obj.put(FIELD_DATA, condition);
        rows.put(obj);
        this.batchDelete(rows);
    }

}
