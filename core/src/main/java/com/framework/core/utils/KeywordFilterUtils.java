package com.framework.core.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 敏感词过滤
 * @author Administrator
 *
 */
public class KeywordFilterUtils {
	
	private KeywordFilterUtils() {}
	private List<String> keywords = new ArrayList<String>();
	private volatile static KeywordFilterUtils keywordFilterUtils = null;
	private static String FILE_PATH = null;
	
	
	public static String getReplaceAllWord(String mseeage) {
		StringBuilder sb = new StringBuilder();
		for (int index = 0; index < mseeage.length(); index++) {
			sb.append("*");
		}
		return sb.toString();
	}
	
	/**
	 * 初始化
	 * @return
	 */
	public synchronized static KeywordFilterUtils getInstance() {
		
		if (keywordFilterUtils == null) {
			
			String os = System.getProperty("os.name");  
	        if (os != null && os.startsWith("Windows")) { 
	        	FILE_PATH = "E:/游戏敏感词/keyword.txt";
	        } else {
	        	FILE_PATH = "/opt/u1wan/keyword.txt";
	        }
			
			
			keywordFilterUtils = new KeywordFilterUtils();
			keywordFilterUtils.addKeywords(keywordFilterUtils.listKeyWords());
		}
		return keywordFilterUtils;
	}
	
	/**
	 * 重置
	 */
	public synchronized static void clearInstance() {
		keywordFilterUtils.clearKeywords();
		keywordFilterUtils = null;
	}
	
	
	private List<String> listKeyWords() {
		readTxtFile(FILE_PATH);
		return keywords;
	}
	
	
	private void readTxtFile(String filePath) {
		try {
			String encoding = "UTF-8";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { 
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					keywords.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}

	}
	
	
	/** 直接禁止的 */
	private HashMap keysMap = new HashMap();
	
	private int matchType = 1; // 1:最小长度匹配 2：最大长度匹配

	private void addKeywords(List<String> keywords) {
		for (int i = 0; i < keywords.size(); i++) {
			String key = keywords.get(i).trim();
			HashMap nowhash = null;
			nowhash = keysMap;
			for (int j = 0; j < key.length(); j++) {
				char word = key.charAt(j);
				Object wordMap = nowhash.get(word);
				if (wordMap != null) {
					nowhash = (HashMap) wordMap;
				} else {
					HashMap<String, String> newWordHash = new HashMap<String, String>();
					newWordHash.put("isEnd", "0");
					nowhash.put(word, newWordHash);
					nowhash = newWordHash;
				}
				if (j == key.length() - 1) {
					nowhash.put("isEnd", "1");
				}
			}
		}
	}

	/**
	 * 重置关键词
	 */
	private void clearKeywords() {
		keysMap.clear();
	}

	/**
	 * 检查一个字符串从begin位置起开始是否有keyword符合， 如果有符合的keyword值，返回值为匹配keyword的长度，否则返回零
	 * flag 1:最小长度匹配 2：最大长度匹配
	 */
	private int checkKeyWords(String txt, int begin, int flag) {
		HashMap nowhash = null;
		nowhash = keysMap;
		int maxMatchRes = 0;
		int res = 0;
		int l = txt.length();
		char word = 0;
		for (int i = begin; i < l; i++) {
			word = txt.charAt(i);
			Object wordMap = nowhash.get(word);
			if (wordMap != null) {
				res++;
				nowhash = (HashMap) wordMap;
				if (((String) nowhash.get("isEnd")).equals("1")) {
					if (flag == 1) {
						wordMap = null;
						nowhash = null;
						txt = null;
						return res;
					} else {
						maxMatchRes = res;
					}
				}
			} else {
				txt = null;
				nowhash = null;
				return maxMatchRes;
			}
		}
		txt = null;
		nowhash = null;
		return maxMatchRes;
	}

	/**
	 * 返回txt中关键字的列表
	 */
	public Set<String> getTxtKeyWords(String txt) {
		Set<String> set = new HashSet<String>();
		int l = txt.length();
		for (int i = 0; i < l;) {
			int len = checkKeyWords(txt, i, matchType);
			if (len > 0) {
				set.add(txt.substring(i, i + len));
				i += len;
			} else {
				i++;
			}
		}
		txt = null;
		return set;
	}

	/**
	 * 仅判断txt中是否有关键字
	 */
	public boolean isContentKeyWords(String txt) {
		for (int i = 0; i < txt.length(); i++) {
			int len = checkKeyWords(txt, i, 1);
			if (len > 0) {
				return true;
			}
		}
		txt = null;
		return false;
	}

	public int getMatchType() {
		return matchType;
	}

	public void setMatchType(int matchType) {
		this.matchType = matchType;
	}


}
