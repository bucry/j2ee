package com.framework.core.platform.web.tag;

import java.util.UUID;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class TokenTag extends TagSupport {
    
    public static final String SYNCH_TOKEN_INPUT_FIELD_NAME = "SYNCH_TOKEN_INPUT_FIELD_NAME";
    
    private String action;

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public int doStartTag() throws JspException {
        try {
            String hiddenArea =  action + "?" + SYNCH_TOKEN_INPUT_FIELD_NAME + "=" + UUID.randomUUID();
            pageContext.getOut().print(hiddenArea);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    /**
     * 遇到结束标签需要执行的内容
     */
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }

}