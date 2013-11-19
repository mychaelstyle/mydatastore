/**
 * 
 */
package com.mychaelstyle.mydatastore.providers;

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

import com.mychaelstyle.mydatastore.Provider;

/**
 * Data store provider AWSDynamoDB test case
 * 
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
 *
 */
public class AWSDynamoDBTest {
    /**
     * AWSDynamoDB provider instance
     */
    protected AWSDynamoDB dynamodb;
    /**
     * test table name
     */
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
        this.dynamodb = new AWSDynamoDB();
        Map<String,String> map = new HashMap<String,String>();
        map.put(AWSDynamoDB.CONFIG_ACCESS_KEY, System.getenv("AWS_ACCESS_KEY"));
        map.put(AWSDynamoDB.CONFIG_SECRET_KEY, System.getenv("AWS_SECRET_KEY"));
        map.put(AWSDynamoDB.CONFIG_ENDPOINT, System.getenv("AWS_ENDPOINT_DYNAMODB"));
        try {
            this.dynamodb.config(map);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }
        this.tableName = System.getenv("MYDATASTORE_TABLE_TEST");
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
    }

    /**
     * Test method for {@link com.mychaelstyle.mydatastore.providers.AWSDynamoDB#batchPut(org.json.JSONArray)}.
     * Test method for {@link com.mychaelstyle.mydatastore.providers.AWSDynamoDB#batchGet(org.json.JSONArray)}.
     * Test method for {@link com.mychaelstyle.mydatastore.providers.AWSDynamoDB#batchRemove(org.json.JSONArray)}.
     */
    @Test
    public void testBatch() {
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
            this.dynamodb.batchPut(items);
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
            JSONObject result = this.dynamodb.batchGet(items);
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
            this.dynamodb.batchDelete(items);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    /**
     * Test method for {@link com.mychaelstyle.mydatastore.Provider#put(java.lang.String, org.json.JSONObject)}.
     * Test method for {@link com.mychaelstyle.mydatastore.Provider#get(java.lang.String, org.json.JSONObject)}.
     * Test method for {@link com.mychaelstyle.mydatastore.Provider#remove(java.lang.String, org.json.JSONObject)}.
     */
    @Test
    public void testSingle() {
System.out.println("--- Single test");
        // 保存
        JSONObject data = new JSONObject();
        data.put("key","testkey");
        data.put("value","testvalue");
        try {
            this.dynamodb.put(this.tableName,data);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

        // 取得
        data = new JSONObject();
        data.put("key","testkey");
        try {
            JSONObject result = this.dynamodb.get(this.tableName, data);
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
            this.dynamodb.delete(this.tableName,data);
        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

}
