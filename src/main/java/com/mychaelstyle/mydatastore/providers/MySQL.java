/**
 * 
 */
package com.mychaelstyle.mydatastore.providers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mychaelstyle.mydatastore.Provider;

/**
 * DataStore provider for mysql
 * @author Masanori Nakashima
 */
public class MySQL extends Provider {
    public static final String CONFIG_HOST  = "mysql_host";
    public static final String CONFIG_PORT  = "mysql_port";
    public static final String CONFIG_DB    = "mysql_database";
    public static final String CONFIG_TABLE = "mysql_table";
    public static final String CONFIG_USER = "mysql_user";
    public static final String CONFIG_PASS = "mysql_pass";
    private Connection connection;
    /**
     * Constructor
     */
    public MySQL() {
        super();
    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#config(java.util.Map)
     */
    @Override
    public void config(Map<String, String> config) throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
        String url = "jdbc:mysql://"+config.get(CONFIG_HOST)+"/"+config.get(CONFIG_DB);
        this.connection = DriverManager.getConnection(url, config.get(CONFIG_USER), config.get(CONFIG_PASS));
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
        String sql = "";
        PreparedStatement statement = this.connection.prepareStatement(sql);

    }

    /* (non-Javadoc)
     * @see com.mychaelstyle.mydatastore.IProvider#batchDelete(org.json.JSONArray)
     */
    @Override
    public void batchDelete(JSONArray conditions) throws Exception {
        // TODO Auto-generated method stub

    }

}
