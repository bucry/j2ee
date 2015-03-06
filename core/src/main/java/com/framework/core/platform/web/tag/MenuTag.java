package com.framework.core.platform.web.tag;

import org.springframework.util.StringUtils;
import com.framework.core.platform.web.session.SessionContext;

public class MenuTag  extends TagSupport {

    @Override
    protected int startTag() throws Exception {
        SessionContext sessionContext = (SessionContext) ctx.getBean("sessionContext");
        String menus = sessionContext.get(authSettings.getKeyForMenus());
        pageContext.getOut().print(StringUtils.hasText(menus) ? menus : "");
        return SKIP_BODY;
    }

}
