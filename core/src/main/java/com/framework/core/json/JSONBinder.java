package com.framework.core.json;

import java.io.IOException;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author bfc
 */
public final class JSONBinder<T> {
	
    public static <T> JSONBinder<T> binder(Class<T> beanClass, Class<?>... elementClasses) {
        return new JSONBinder<T>(beanClass, elementClasses);
    }

    private final Class<T> beanClass;

    //private final Class<?>[] elementClasses;

    private JSONBinder(Class<T> beanClass, Class<?>... elementClasses) {
        this.beanClass = beanClass;
        //this.elementClasses = elementClasses;
    }

    @SuppressWarnings("unchecked")
	public T fromJSON(String json) {
    	//TODO List convers
        return (T) JSONObject.toBean(JSONObject.fromObject(json), beanClass);
    }

    public T fromJSONToGeneric(String json) throws IOException {
       return null;
    }

    public String toJSON(T object) {
       return JSONObject.fromObject(object).toString();
    }
    
    /**
     * @Description : 将List集合转换为Json
     * @param list
     * @return
     */
    public String convertListToJson(List<?> list) {
    	return JSONArray.fromObject(list).toString();
    }
}
