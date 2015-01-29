package com.framework.core.mongo;

import java.net.UnknownHostException;
import java.util.Map;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class MongoDBAccess {

	private Mongo mongoDBAccess = null;
	private DB mongoManager;
	private DBCollection collection;
	private String mongoServerIpAddress;
	private int mongoServerPort;
	private String collectionName;
	private String dbName;

	public String getMongoServerIpAddress() {
		return mongoServerIpAddress;
	}

	public void setMongoServerIpAddress(String mongoServerIpAddress) {
		this.mongoServerIpAddress = mongoServerIpAddress;
	}

	public int getMongoServerPort() {
		return mongoServerPort;
	}

	public void setMongoServerPort(int mongoServerPort) {
		this.mongoServerPort = mongoServerPort;
	}

	public String getCollectionName() {
		return collectionName;
	}

	public void setCollectionName(String collectionName) {
		this.collectionName = collectionName;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	
	public void queryAll() {
	    DBCursor cur = collection.find();
	    while (cur.hasNext()) {
	    	System.out.println(cur.next());
	    }
	}
	
	public int execute(Map<String, Object> map) {
		try {
			DBObject document  = new BasicDBObject();
			for (String key : map.keySet()) {
				document.put(key, map.get(key));
			}
			collection.insert(document);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			
		}
		return 1;
	}
	
	@SuppressWarnings("deprecation")
	public void initDB() {
		try {
			mongoDBAccess = new Mongo(mongoServerIpAddress, mongoServerPort);
			mongoManager = mongoDBAccess.getDB(dbName);
			collection = mongoManager.getCollection(collectionName);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (MongoException e) {
			e.printStackTrace();
		}
	}
}
