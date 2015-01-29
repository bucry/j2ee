package com.framework.database;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import com.mongodb.DB;
import com.mongodb.DBCursor;
import com.mongodb.Mongo;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class MongoDBTest {

	@Test
	public void saveFile() {
		initData4GridFS();
	}
	
	private static void initData4GridFS()   {  
        long start = new Date().getTime();  
        try {  
            Mongo db = new Mongo("127.0.0.1", 50000);  
            DB mydb = db.getDB("wlb");  
            File f = new File("D://study//document//MySQL5.1参考手册.chm");  
            GridFS myFS = new GridFS(mydb);               
            GridFSInputFile inputFile = myFS.createFile(f);  
            inputFile.save();  
  
            DBCursor cursor = myFS.getFileList();  
            while(cursor.hasNext()){  
                System.out.println(cursor.next());  
            }     
            db.close();  
            long endTime = new Date().getTime();  
            System.out.println(endTime - start);  
            System.out.println((endTime - start) / 10000000);  
        }catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
