package com.leo.staticinvoke;

public class CountProxy implements Count {  
    private Count count;  
  
    /** 
     * 覆盖默认构造器 
     *  
     * @param countImpl 
     */  
    public CountProxy(Count count) {  
        this.count = count;  
    }  
  
    @Override  
    public void queryCount() {  
        System.out.println("事务处理之前");  
        // 调用委托类的方法;  
        count.queryCount();  
        System.out.println("事务处理之后");  
    }  
  
    @Override  
    public void updateCount() {  
        System.out.println("事务处理之前");  
        // 调用委托类的方法;  
        count.updateCount();  
        System.out.println("事务处理之后");  
  
    }  
  
}  