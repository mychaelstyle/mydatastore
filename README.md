mydatastore
===========

Mychael style DataStroe wrapper easy to use.

This library can use data store service easy via JSON.
And you can put data to some data store services at once. for example, can put to MySQL table, Amazon DynamoDB,
Memcached, Redis ... etc.

e.g)

  MyDataStore dataStore = new MyDataStore();
  IProvider dynamodb = new AWSDynamoDB();
  dynamodb.config(configMap);
  dataStore.addProvider(dynamodb);
  IProvider memcached = new Memcached();
  memcached.config(configMap);
  dataStore.addProvider(memcached);
  </pre>

you can put item using the following JSON string.

  String str = "
  {
    "action" : "put",
    "table"  : "foo_table",
    "data"   : {
      "key" : "keyvalue1",
      "value: "hogehoge",
      ...
    }
  }
  ";

  JSONObject data = new JSONObject(str);
  dataStore.put(tableName,data);

you can get items like the followings.

  String cond = "
  {
    "key" : "keyvalue"
  }";
  JSONObject result = dataStore.get(tableName, cond);

The result JSON format.
  {
    "key" : "keyvalue",
    "field1" : "field1",
    ...
  }

you can delete item.

  String cond "
  {
    "key": "keyvalue"
  }
  ";
  dataStore.delete(tableName,cond);

  