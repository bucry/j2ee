package com.framework.core.platform.web.tag;

import java.io.IOException;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import org.springframework.util.StringUtils;

public class PageTag extends TagSupport {

    private static final int STEP = 3;

    private String url;
    
    private Integer pageNo;

    private Integer pageSize;

    private Integer totalRecords;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }

    private Integer getTotalPages() {
        return (totalRecords + pageSize - 1) / pageSize;
    }

    private String createUrl(Integer pageNo) {
        return url + "/" + pageNo + "/" + pageSize;
    }

    private String createOption() {
        if (null == pageSize || pageSize == 0 ) this.pageSize = 10;
        int[] chooses = { 10, 20, 30, 50, 80, 100 };
        StringBuilder options = new StringBuilder();
        options.append("<select id=\"syncPageSize\" name=\"syncPageSize\">");
        for (int choose : chooses) {
            if (choose == pageSize) {
                options.append("<option selected=\"selected\" value=\"" + choose + "\">" + choose + "</option>");
            } else {
                options.append("<option  value=\"" + choose + "\">" + choose + "</option>");
            }
        }
        options.append("</select>");
        return options.toString();
    }

    private String createLink() {
        Integer totalPages = getTotalPages();
        StringBuilder html = new StringBuilder("<p>");
        
        Integer begin = pageNo - STEP;
        Integer pageFrom = begin > 0 ? begin : 1;
        
        Integer end = pageNo + STEP;
        Integer pageTo = end <= totalPages ? end : totalPages;
        
        if (pageFrom != 1) {
            html.append("<a href=\"" + createUrl(1) + "\">1</a>");
            html.append((pageFrom == 2) ? "" : "<span>...</span>");
        }
        for (int i = pageFrom; i <= pageTo; i++) {
            String pageurl = createUrl(i);
            html.append(i == pageNo ? "<strong data-page=\"" + i + "\">" + i + "</strong>" : "<a href=\"" + pageurl + "\">" + i + "</a>");
        }
        if (!pageTo.equals(totalPages)) {
            html.append(pageTo + 1 == totalPages ? "" : "<span>...</span>");
            html.append("<a href=\"" + createUrl(totalPages) + "\">" + totalPages + "</a>");
        }
        html.append("</p>");
        return html.toString();
    }

    public String createPageNav() {
        StringBuilder div = new StringBuilder();
        div.append("<div class=\"pageNav\" id=\"syncPageNav\" data-remote=\"" + url + "\">");
        div.append("Total Records : <span id=\"recordsLen\">" + totalRecords + "</span>");
        div.append(createOption());
        div.append(createLink());
        div.append("</div>");
        return div.toString();
    }

    @Override
    public int doStartTag() throws JspException {
       
        if (!StringUtils.hasText(url))
            return SKIP_BODY;
        try {
            pageContext.getOut().print(createPageNav());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
}