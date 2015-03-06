package com.framework.core.utils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BeanUtils {

	public static <T> boolean hasBean(T obj) {
		return !(obj == null);
	}

	public static void copyProperties(Object objectA, Object objectB) {

		Field[] fields = objectA.getClass().getDeclaredFields();

		Method methodA = null;
		Method methodB = null;

		String methodAName = null;
		String methodBName = null;

		for (Field field : fields) {

			String name = field.getName();
			int length = name.length();

			methodAName = new StringBuffer("get")
					.append(name.substring(0, 1).toUpperCase())
					.append(name.substring(1, length)).toString();

			methodBName = new StringBuffer("set")
					.append(name.substring(0, 1).toUpperCase())
					.append(name.substring(1, length)).toString();

			try {
				methodA = objectA.getClass().getDeclaredMethod(methodAName);

				methodB = objectB.getClass().getDeclaredMethod(methodBName,
						Class.forName("java.lang.String"));

				String methodBParam = (String) methodA.invoke(objectA);

				methodB.invoke(objectB, methodBParam);

			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
