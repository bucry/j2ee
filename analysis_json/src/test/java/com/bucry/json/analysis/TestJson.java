package com.bucry.json.analysis;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bucry.json.response.CommonResponse;
import com.bucry.json.utils.StringUtils;

public class TestJson {

    enum JSONType {
        JSONARRAY,
        JSONOBJECT;
    }

    private  Object translateFromJson(JSONObject jsonObject) throws Exception {

        JSONType jsonType = JSONType.JSONOBJECT;
        Class<?> baseClass = Class.forName(jsonObject.getString("className"));
        Object object = baseClass.newInstance();
        Field[] fields = baseClass.getDeclaredFields();

        for (Field filed : fields) {

            Class<?> filedType = filed.getType();
            Object filedValue = null;
            if ("serialVersionUID".equals(filed.getName())) {
            	continue;
            }
            
            if (filedType.getCanonicalName().contains("int") || filedType.getCanonicalName().contains("Integer")) {
                filedValue = jsonObject.getInt(filed.getName());
            } else if (filedType.getCanonicalName().contains("String")) {
                filedValue = jsonObject.getString(filed.getName());
            } else if (filedType.getCanonicalName().contains("List")) {
                jsonType = JSONType.JSONARRAY;
                filedValue = jsonObject.getJSONArray(filed.getName());
            } else if (filedType.getCanonicalName().contains("Long") || filedType.getCanonicalName().contains("long")) {
            	filedValue = jsonObject.getLong(filed.getName());
            } else if (filedType.getCanonicalName().contains("Double") || filedType.getCanonicalName().contains("double")) {
           	 	filedValue = jsonObject.getDouble(filed.getName());
            } else if (filedType.getCanonicalName().contains("Boolean") || filedType.getCanonicalName().contains("boolean")) {
           	 	filedValue = jsonObject.getBoolean(filed.getName());
            } else {
                jsonType = JSONType.JSONOBJECT;
                filedValue = jsonObject.getJSONObject(filed.getName());
            }

            if (filedValue == null || filedValue.toString().length() == 0) {
            	continue;
            }
            
            if (!filedValue.toString().contains("[{") && !filedValue.toString().contains("]}") && !filedValue.toString().contains("className")) {
                String firstMethodNameChar = filed.getName().substring(0, 1);
                String methodName = "set" + firstMethodNameChar.toUpperCase() + filed.getName().substring(1, filed.getName().length());
                Method method = baseClass.getMethod(methodName, filed.getType());
                method.invoke(object, filedValue);
            } else if (filedValue.toString().contains("className")) {
                Object subClassObject = null;
                switch (jsonType) {
                    case JSONARRAY:
                        subClassObject = translateFromJson((JSONArray)filedValue);
                        break;
                    case JSONOBJECT:
                        subClassObject = translateFromJson((JSONObject)filedValue);
                        break;
                }
                String firstMethodNameChar = filed.getName().substring(0, 1);
                String methodName = "set" + firstMethodNameChar.toUpperCase() + filed.getName().substring(1, filed.getName().length());
                Method method = baseClass.getMethod(methodName, filed.getType());
                method.invoke(object, subClassObject);

            } else {
                Object subClassObject = null;
                switch (jsonType) {
                    case JSONARRAY:
                        subClassObject = translateFromJson((JSONArray)filedValue);
                        break;
                    case JSONOBJECT:
                        subClassObject = translateFromJson((JSONObject)filedValue);
                        break;
                }
                String firstMethodNameChar = filed.getName().substring(0, 1);
                String methodName = "set" + firstMethodNameChar.toUpperCase() + filed.getName().substring(1, filed.getName().length());
                Method method = baseClass.getMethod(methodName, filed.getType());
                method.invoke(object, subClassObject);
            }
        }

        return object;
    }


    private  Object translateFromJson(JSONArray jsonObject) throws Exception {
        List<Object> outputStringList = new LinkedList<Object>();
        for(int i=0; i<jsonObject.length(); i++){
            String filedValue = jsonObject.get(i).toString();

            if (filedValue.contains("className")) {
                JSONObject jsonObject1 = new JSONObject(filedValue);
                outputStringList.add(translateFromJson(jsonObject1));
            } else {
                outputStringList.add(filedValue);
            }
        }
        return outputStringList;
    }


    public static void main(String args[]) {
        try {
           String json ="{\"pageSize\":5,\"pageNo\":1,\"table\":\"DimCallType\",\"operation\":\"update\",\"className\":\"com.bucry.json.response.CommonResponse\","
           		+ "\"result\":[{\"id\":1,\"className\":\"com.bucry.json.response.RowResponse\","
           		+ "\"results\":["
           		+ "{\"key\":\"call_reason_id\",\"value\":\"100001\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"CallReasonID\",\"value\":\"1\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"call_reason_hc\",\"value\":\"解冻或销户\",\"className\":\"com.bucry.json.response.RowDetails\"}]},"
           		+ "{\"id\":2,\"className\":\"com.bucry.json.response.RowResponse\",\""
           		+ "results\":[{\"key\":\"call_reason_id\",\"value\":\"100002\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"CallReasonID\",\"value\":\"2\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"call_reason_hc\",\"value\":\"解停用\",\"className\":\"com.bucry.json.response.RowDetails\"}]},"
           		+ "{\"id\":3,\"className\":\"com.bucry.json.response.RowResponse\","
           		+ "\"results\":[{\"key\":\"call_reason_id\",\"value\":\"100003\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"CallReasonID\",\"value\":\"3\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"call_reason_hc\",\"value\":\"营销活动\",\"className\":\"com.bucry.json.response.RowDetails\"}]},"
           		+ "{\"id\":4,\"className\":\"com.bucry.json.response.RowResponse\","
           		+ "\"results\":[{\"key\":\"call_reason_id\",\"value\":\"100004\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"CallReasonID\",\"value\":\"4\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"call_reason_hc\",\"value\":\"投诉分行\",\"className\":\"com.bucry.json.response.RowDetails\"}]},"
           		+ "{\"id\":5,\"className\":\"com.bucry.json.response.RowResponse\","
           		+ "\"results\":[{\"key\":\"call_reason_id\",\"value\":\"100005\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"CallReasonID\",\"value\":\"5\",\"className\":\"com.bucry.json.response.RowDetails\"},"
           		+ "{\"key\":\"call_reason_hc\",\"value\":\"分行增额\",\"className\":\"com.bucry.json.response.RowDetails\"}]}]}";
            JSONObject o = new JSONObject(json);
            CommonResponse c = (CommonResponse)new TestJson().translateFromJson(o);
            System.out.println(c.getResult().get(0).getResults().get(0).getValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
