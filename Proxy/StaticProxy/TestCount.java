package com.leo.staticinvoke;

public class TestCount {  
    public static void main(String[] args) {  
        Count countImpl = new CountImpl();  
        Count countProxy = new CountProxy(countImpl);  
        countProxy.updateCount();  
        countProxy.queryCount();  
    }  
}  