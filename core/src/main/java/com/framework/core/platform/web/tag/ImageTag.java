package com.framework.core.platform.web.tag;

import org.springframework.util.StringUtils;

public class ImageTag extends TagSupport {
    
    private String src;
    
    private String size;

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    @Override
    protected int startTag() throws Exception {
        if (!StringUtils.hasText(src)) {
            pageContext.getOut().print("#");
            return SKIP_BODY;
        }
        boolean isType = false;
        if (!src.contains("_full") || !StringUtils.hasText(size)) {
            return SKIP_BODY; 
        }
        String[] types = {"full", "large", "medium", "thumbnail"};
        for (String type : types) {
            if (size.equals(type)) {
                isType = true;
                break;
            }
        }
        if (!isType) return SKIP_BODY; 
        pageContext.getOut().print(super.constructNFSURL(src.replaceAll("_full", "_" + size)));
        return SKIP_BODY;
    }

}