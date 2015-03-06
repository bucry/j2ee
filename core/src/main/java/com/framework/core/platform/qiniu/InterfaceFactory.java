package com.framework.core.platform.qiniu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.codec.EncoderException;
import org.json.JSONException;

import com.framework.core.platform.qiniu.config.FrameConfig;
import com.qiniu.api.auth.AuthException;
import com.qiniu.api.auth.digest.Mac;
import com.qiniu.api.config.Config;
import com.qiniu.api.io.IoApi;
import com.qiniu.api.io.PutExtra;
import com.qiniu.api.io.PutRet;
import com.qiniu.api.resumableio.ResumeableIoApi;
import com.qiniu.api.rs.Entry;
import com.qiniu.api.rs.GetPolicy;
import com.qiniu.api.rs.PutPolicy;
import com.qiniu.api.rs.RSClient;
import com.qiniu.api.rs.URLUtils;

/**
 * 七牛文件上传接口
 * @author Administrator
 *
 */
public class InterfaceFactory {
	
	private static Lock lock = new ReentrantLock();
	private static InterfaceFactory instance = null;
	private InterfaceFactory() {
		Config.ACCESS_KEY = FrameConfig.ACCESS_KEY;
		Config.SECRET_KEY = FrameConfig.SECRET_KEY;
	}
	
	public static InterfaceFactory getInstance() {
		if (instance == null) {
			try {
				lock.lock();
				if (instance == null) {
					instance = new InterfaceFactory();
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
		return instance;
	}
	
	public Mac getQiniuMac() {
		Mac mac = null;
		try {
			mac = new Mac(Config.ACCESS_KEY, Config.SECRET_KEY);
		} catch (Exception e) {
			e.printStackTrace();
		}
        return mac;
	}
	
	/**
	 * 获取私有库文件下载链接
	 * @param key 上传文件配置key
	 * @return 带token的url
	 */
	public String getPrivateBucketFileUrl(String key) {
		String downloadUrl = null;
		try {
			String baseUrl = URLUtils.makeBaseUrl(FrameConfig.PRIVATE_DOMAIN, key);
			GetPolicy getPolicy = new GetPolicy();
			downloadUrl = getPolicy.makeRequest(baseUrl, getQiniuMac());
		} catch (EncoderException e) {
			downloadUrl = null;
			e.printStackTrace();
		} catch (AuthException e) {
			downloadUrl = null;
			e.printStackTrace();
		}
		return downloadUrl;
	}
	
	/**
	 * 非断点续传文件
	 * 上传本地文件到远程仓库
	 * @param localFilePath 本地文件位置
	 * @param key 远程服务器key
	 * @param isPrivateBucket 是否是私有仓库  true 是 false 否
	 * @return json状态  例如：{"hash":"Fr4tIIilWs4NhffPvzk9w8Zzgf4R","key":"a"}  出现异常返回null
	 */
	public String LocalFileNotBreakpointUploadToBucket(String localFilePath, String key, boolean isPrivateBucket) {
		try {
			PutPolicy putPolicy = new PutPolicy(isPrivateBucket ? FrameConfig.PRIVATE_BUCKET_NAME : FrameConfig.PUBLIC_BUCKET_NAME);
			String uptoken = putPolicy.token(getQiniuMac());
			PutExtra extra = new PutExtra();
			PutRet ret = IoApi.putFile(uptoken, key, localFilePath, extra);
			return ret.response;
		} catch (AuthException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 断点续传文件
	 * 上传本地文件到远程仓库
	 * @param localFilePath 本地文件位置
	 * @param key 远程服务器key
	 * @param isPrivateBucket 是否是私有仓库  true 是 false 否
	 * @parm mimeType 文件类型 可为null
	 * @return json状态  例如：{"hash":"Fr4tIIilWs4NhffPvzk9w8Zzgf4R","key":"a"}  出现异常返回null
	 */
	public String LocalFileBreakpointUploadToBucket(String localFilePath, String key, boolean isPrivateBucket, String mimeType) {
		try {
			PutPolicy putPolicy = new PutPolicy(isPrivateBucket ? FrameConfig.PRIVATE_BUCKET_NAME : FrameConfig.PUBLIC_BUCKET_NAME);
			putPolicy.returnBody = "{\"key\": $(key), \"hash\": $(etag),\"mimeType\": $(mimeType)}";
			String uptoken = putPolicy.token(getQiniuMac());
			File file = new File(localFilePath);
			PutRet ret = ResumeableIoApi.put(file, uptoken, key, mimeType);
			return ret.response;
		} catch (AuthException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 使用流上传本地文件
	 * 上传本地文件到远程仓库
	 * @param localFilePath 本地文件位置
	 * @param key 远程服务器key
	 * @param isPrivateBucket 是否是私有仓库  true 是 false 否
	 * @parm mimeType 文件类型 可为null
	 * @return json状态  例如：{"hash":"Fr4tIIilWs4NhffPvzk9w8Zzgf4R","key":"a"}  出现异常返回null
	 */
	public String LocalFileStreamToBucket(String localFilePath, String key, boolean isPrivateBucket, String mimeType) {
		try {
			PutPolicy putPolicy = new PutPolicy(isPrivateBucket ? FrameConfig.PRIVATE_BUCKET_NAME : FrameConfig.PUBLIC_BUCKET_NAME);
			putPolicy.returnBody = "{\"key\": $(key), \"hash\": $(etag),\"mimeType\": $(mimeType)}";
			String uptoken = putPolicy.token(getQiniuMac());
			File file = new File(localFilePath);
			FileInputStream fis = new FileInputStream(file);
			PutRet ret = ResumeableIoApi.put(fis, uptoken, key, mimeType);
			return ret.response;
		} catch (AuthException e) {
			e.printStackTrace();
			return null;
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 删除远程文件
	 * @param isPrivateBucket 是否是私有仓库  true 是 false 否
	 * @param key
	 * @return
	 */
	public boolean deleteRemoteFile(boolean isPrivateBucket, String key) {
        try {
			RSClient client = new RSClient(getQiniuMac());
			client.delete(isPrivateBucket ? FrameConfig.PRIVATE_BUCKET_NAME : FrameConfig.PUBLIC_BUCKET_NAME, key);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 获取远程文件属性
	 * @param isPrivateBucket
	 * @param key
	 * @return
	 */
	public Entry getFileInfo(boolean isPrivateBucket, String key) {
        try {
			RSClient client = new RSClient(getQiniuMac());
			return client.stat(isPrivateBucket ? FrameConfig.PRIVATE_BUCKET_NAME : FrameConfig.PUBLIC_BUCKET_NAME, key);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
