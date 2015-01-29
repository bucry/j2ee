package com.framework.core.platform.web.tag;

import org.springframework.util.StringUtils;

public class ImgTag extends TagSupport {
    
    private String id;
    
    private String type;
    
    private String src;
    
    private String size;

    private String alt;
    
    private String cssclass;
    
    private String title;

    private Integer width;
    
    private Integer height;
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCssclass() {
        return cssclass;
    }

    public void setCssclass(String cssclass) {
        this.cssclass = cssclass;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getAlt() {
        return alt;
    }
    
    public void setAlt(String alt) {
        this.alt = alt;
    }

    @Override
    protected int startTag() throws Exception {        
        boolean isType = false;
        if (!StringUtils.hasText(src) || !src.contains("_full")) {
            return SKIP_BODY; 
        }
        
        final String[] types = {"full", "large", "medium", "thumbnail"};
        if (StringUtils.hasText(size)) {
            for (String type : types) {
                if (size.equals(type)) {
                    isType = true;
                    break;
                }
            }
            if (!isType) return SKIP_BODY; 
        } else {
            size = "full";
        }
        
        final StringBuilder img = new StringBuilder("");
        final StringBuilder imgSize = new StringBuilder("");

        if (null != width && null != height) {
            Integer[] imgArea = super.compress(src, width, height);
            if (!"jsonsize".equals(type)) {
                imgSize.append("width='" + imgArea[0] + "' height='" + imgArea[1] + "'");
            } else {
                imgSize.append("width:" + imgArea[0] + ", height:" + imgArea[1]);
            }
        }

        if (!"jsonsize".equals(type)) {
            img.append("<img");
            img.append(" src='" + super.constructNFSURL(src.replaceAll("_full", "_" + size)) + "'");
            img.append(" " + imgSize);
        } else {
            img.append( imgSize );
        }

        if (StringUtils.hasText(id)) img.append(" id='" + id + "'");
        if (StringUtils.hasText(alt)) img.append(" alt='" + alt + "'");
        if (StringUtils.hasText(title)) img.append(" alt='" + title + "'");
        if (StringUtils.hasText(cssclass)) img.append(" class='" + cssclass + "'");
        if (!"jsonsize".equals(type)) {
            img.append(" />");
        }
        
        pageContext.getOut().print(img.toString());
        return SKIP_BODY;
    }
}