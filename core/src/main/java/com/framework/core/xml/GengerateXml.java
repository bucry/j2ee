package com.framework.core.xml;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class GengerateXml {
	
	public static void generateXml(String outputPath, DocumentEntity docList) {
		try {
			Document doc = generateXmlValues(docList);// 生成XML文件
			outputXml(doc, outputPath);// 将文件输出到指定的路径
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("出现异常");
		}
	}
	
	

	/**
	 * 将XML文件输出到指定的路径
	 * @param doc
	 * @param fileName
	 * @throws Exception
	 */
	private static void outputXml(Document doc, String fileName) throws Exception {
		try {
			
			File file = new File(fileName);
			if (file.exists()) {
				System.out.println("已经存在----" + fileName);
				return;
			}
			
			TransformerFactory tf = TransformerFactory.newInstance();
			Transformer transformer = tf.newTransformer();
			DOMSource source = new DOMSource(doc);
			transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");// 设置文档的换行与缩进
			PrintWriter pw = new PrintWriter(new FileOutputStream(fileName));
			StreamResult result = new StreamResult(pw);
			transformer.transform(source, result);
			System.out.println("生成XML文件成功!");
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 生成XML文件
	 * 
	 * @param list
	 * @return
	 */
	public static Document generateXmlValues(DocumentEntity entity) {
		Document doc = null;
		Element root = null;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
			root = doc.createElement("Entity");
			doc.appendChild(root);
		} catch (Exception e) {
			e.printStackTrace();
			return null;// 如果出现异常，则不再往下执行
		}

		Element element = doc.createElement(entity.getMethodName());
		for (ParmsEntity value : entity.getParmsList()) {
			String key = value.getParmsName();
			element.setAttribute(key + "length", value.getLength() + "");
			element.setAttribute(key + "ofset", value.getOffset() + "");
			root.appendChild(element);
		}
		return doc;
	}

}
