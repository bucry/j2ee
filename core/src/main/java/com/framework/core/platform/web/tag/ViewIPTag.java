package com.framework.core.platform.web.tag;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.Tag;

public class ViewIPTag implements Tag {

    //接收传递进来的PageContext对象
    private PageContext pageContext;
    
    @Override
    public int doEndTag() throws JspException {
        System.out.println("调用doEndTag()方法");
        return 0;
    }

    @Override
    public int doStartTag() throws JspException {
        System.out.println("调用doStartTag()方法");
        HttpServletRequest request =(HttpServletRequest) pageContext.getRequest();
        JspWriter out = pageContext.getOut();
        String ip = request.getRemoteAddr();
        try {
            //这里输出的时候会抛出IOException异常
            out.write(ip);
        } catch (IOException e) {
            //捕获IOException异常后继续抛出
            throw new RuntimeException(e);
        }
        return 0;
    }

    @Override
    public Tag getParent() {
        return null;
    }

    @Override
    public void release() {
        System.out.println("调用release()方法");
    }

    @Override
    public void setPageContext(PageContext pageContext) {
        System.out.println("setPageContext(PageContext pageContext)");
        this.pageContext = pageContext;
    }

    @Override
    public void setParent(Tag arg0) {

    }

}