/**
 * Data Store provider for memcached
 * 
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore.providers;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.spy.memcached.MemcachedClient;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mychaelstyle.mydatastore.Provider;

/**
 * Data Store provider for memcached
 * 
 * This provider requires net.spy.spymemcached.
 * 
 * @author Masanori Nakashima
 *
 */
public class Memcached extends Provider {
    public static final String CONFIG_HOSTS = "memcached_hosts";
    public static final String CONFIG_EXPIRE = "memcached_expire";
    private MemcachedClient client;
    private int expire = 3600;
    /**
     * Constructor
     */
    public Memcached() {
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#config(java.util.Map)
     */
    @Override
    public void config(Map<String, String> config) throws Exception {
        List<InetSocketAddress> hosts = new ArrayList<InetSocketAddress>();
        String[] hostStringArray = config.get(CONFIG_HOSTS).split(",");
        for(String str : hostStringArray){
            String[] info = str.trim().split(":");
            String host;
            Integer port;
            if(null==info || info.length==0) {
                continue;
            }
            host = info[0];
            if(info.length>1){
                port = Integer.parseInt(info[1]);
            } else {
                port = 11211;
            }
            InetSocketAddress addr = new InetSocketAddress(host,port);
            hosts.add(addr);
        }
        this.client = new MemcachedClient(hosts);
        if(config.containsKey(CONFIG_EXPIRE)) {
            this.expire = Integer.parseInt(config.get(CONFIG_EXPIRE));
        }
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchGet(org.json.JSONArray)
     */
    @Override
    public JSONObject batchGet(JSONArray conditions) throws Exception {
        JSONObject ret = new JSONObject();
        for(int num=0; num<conditions.length();num++){
            JSONObject obj = conditions.getJSONObject(num);
            String table = obj.getString(FIELD_TABLE);
            String fieldName = "key";
            if(obj.has(FIELD_KEY)){
                fieldName = obj.getString(FIELD_KEY);
            }
            JSONObject data = obj.getJSONObject(FIELD_DATA);
            String key = data.getString(fieldName);
            String regKey = Memcached.regKey(table, key);
            String result = (String) this.client.get(regKey);
            JSONObject res = new JSONObject(result);
            JSONArray resArray = new JSONArray();
            if(ret.has(table)){
                resArray = ret.getJSONArray(table);
            }
            resArray.put(res);
            ret.put(table, resArray);
        }
        return ret;
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchPut(org.json.JSONArray)
     */
    @Override
    public void batchPut(JSONArray tableRows) throws Exception {
        for(int num=0; num<tableRows.length();num++){
            JSONObject obj = tableRows.getJSONObject(num);
            String table = obj.getString(FIELD_TABLE);
            String fieldName = "key";
            if(obj.has(FIELD_KEY)){
                fieldName = obj.getString(FIELD_KEY);
            }
            JSONObject data = obj.getJSONObject(FIELD_DATA);
            String key = data.getString(fieldName);
            String regKey = Memcached.regKey(table, key);
            this.client.set(regKey, this.expire, data.toString());
        }
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchDelete(org.json.JSONArray)
     */
    @Override
    public void batchDelete(JSONArray conditions) throws Exception {
        for(int num=0; num<conditions.length();num++){
            JSONObject obj = conditions.getJSONObject(num);
            String table = obj.getString(FIELD_TABLE);
            String fieldName = "key";
            if(obj.has(FIELD_KEY)){
                fieldName = obj.getString(FIELD_KEY);
            }
            JSONObject data = obj.getJSONObject(FIELD_DATA);
            String key = data.getString(fieldName);
            String regKey = Memcached.regKey(table, key);
            this.client.delete(regKey);
        }
    }

    /**
     * get register key string
     * @param table
     * @param key
     * @return
     */
    private static String regKey(String table, String key) {
        return new StringBuffer(table).append("_").append(key).toString();
    }
}
