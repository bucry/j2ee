package com.framework.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;

import org.junit.Test;

/**
 * 文件过滤器测试
 * @author baifachuan
 *
 */
public class FileFilterTest {
	
	@Test
	public void fileTest() {
		File file = new File(".");
		String[] names = file.list(new MyFileFilter());
		for (String name : names) {
			System.out.println(name);
		}
	}
	
	//测试文件锁
	@Test
	public void fileLock() {
		FileChannel channel = null;
		try {
			channel = new FileOutputStream("").getChannel();
			FileLock lock = channel.tryLock(); 
			//TODO
			lock.release();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

class MyFileFilter implements FilenameFilter {

	@Override
	public boolean accept(File dir, String name) {
		return (name.endsWith(".java") || new File(name).isDirectory());
	}
	
}
