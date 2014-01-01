/**
 * MyDataStore test case
 * @author Masanori Nakashima
 */
package com.mychaelstyle.mydatastore;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mychaelstyle.mydatastore.providers.AWSDynamoDB;
import com.mychaelstyle.mydatastore.providers.MySQL;

/**
 * MyDataStore test case
 * This test case requires the following environment valuables.
 * 
 * <ul>
 * <li>AWS_ACCESS_KEY ... your amazon web service access key.</li>
 * <li>AWS_SECRET_KEY ... your amazon web service secret key.</li>
 * <li>AWS_ENDPOINT_DYNAMODB ... your DynamoDB end point.</li>
 * <li>MYDATASTORE_TABLE_TEST ... your test table on DynamoDB.</li>
 * </ul>
 * 
 * @author Masanori Nakashima
 */
public class MyDataStoreTest {

    protected MyDataStore dataStore;
    protected String tableName;
    /**
     * @throws java.lang.Exception
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        this.dataStore = new MyDataStore();
        Map<String,String> config = new HashMap<String,String>();
/*
        // dynamo
        IProvider dynamo = new AWSDynamoDB();
        config.put(AWSDynamoDB.CONFIG_ACCESS_KEY,System.getenv("AWS_ACCESS_KEY"));
        config.put(AWSDynamoDB.CONFIG_SECRET_KEY,System.getenv("AWS_SECRET_KEY"));
        config.put(AWSDynamoDB.CONFIG_ENDPOINT,System.getenv("AWS_ENDPOINT_DYNAMODB"));
        dynamo.config(config);
        this.dataStore.addProvider(dynamo);
        this.tableName = System.getenv("MYDATASTORE_TABLE_TEST");
*/
        // mysql
        IProvider mysql = new MySQL();
        config.put(MySQL.CONFIG_HOST, "localhost");
        config.put(MySQL.CONFIG_PORT, "3306");
        config.put(MySQL.CONFIG_DB, "test");
        config.put(MySQL.CONFIG_USER, "root");
        config.put(MySQL.CONFIG_PASS,"");
        config.put(MySQL.CONFIG_TABLE, "m_datastore");
        mysql.config(config);
        this.dataStore.addProvider(mysql);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSingle() {
        System.out.println("--- Single test");
        // 保存
        JSONObject data = new JSONObject();
        data.put("key","testkey");
        data.put("value","testvalue");
        try {
            this.dataStore.put(this.tableName,data);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        // 取得
        data = new JSONObject();
        data.put("key","testkey");
        try {
            JSONObject result = this.dataStore.get(this.tableName, data);
 System.out.println(result.toString());
            assertNotNull(result);
            String val = result.getString("value");
            assertEquals("testvalue",val);
        } catch (Exception e1) {
            e1.printStackTrace();
            fail(e1.getMessage());
        }

        // 削除
        data = new JSONObject();
        data.put("key","testkey");
        try {
            this.dataStore.delete(this.tableName,data);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }

    @Test
    public void testBatch(){
        
        System.out.println("--- Batch test");
        JSONArray items = new JSONArray();
        JSONObject row = new JSONObject();
        row.put(Provider.FIELD_ACTION, Provider.ACTION_PUT);
        row.put(Provider.FIELD_TABLE, this.tableName);
        JSONObject data = new JSONObject();
        data.put("key","testkey");
        data.put("value","testvalue");
        row.put(Provider.FIELD_DATA,data);
        items.put(row);
System.out.println(row.toString());
        try {
            this.dataStore.batchPut(items);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        // 取得
        items = new JSONArray();
        row = new JSONObject();
        row.put(Provider.FIELD_TABLE, this.tableName);
        data = new JSONObject();
        data.put("key","testkey");
        row.put(Provider.FIELD_DATA,data);
        items.put(row);
        try {
            JSONObject result = this.dataStore.batchGet(items);
            JSONArray rows = result.getJSONArray(this.tableName);
System.out.println(rows.toString());
            assertNotNull(rows);
            assertTrue(rows.length()>0);
        } catch (Exception e1) {
            e1.printStackTrace();
            fail(e1.getMessage());
        }

        // 削除
        items = new JSONArray();
        row = new JSONObject();
        row.put(Provider.FIELD_ACTION, Provider.ACTION_DELETE);
        row.put(Provider.FIELD_TABLE, this.tableName);
        data = new JSONObject();
        data.put("key","testkey");
        row.put(Provider.FIELD_DATA,data);
        items.put(row);
        try {
            this.dataStore.batchDelete(items);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
    }
}
