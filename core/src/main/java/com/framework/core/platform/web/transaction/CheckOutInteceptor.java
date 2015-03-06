package com.framework.core.platform.web.transaction;

import java.lang.annotation.Annotation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.framework.core.collection.Key;
import com.framework.core.platform.SpringObjectFactory;
import com.framework.core.platform.web.cookie.CookieContext;
import com.framework.core.platform.web.session.SessionInterceptor;
import com.framework.core.platform.web.site.SiteHelper;
import com.framework.core.platform.web.site.security.AuthSettings;
import com.framework.core.platform.web.transaction.provider.MemcachedTransactionProvider;
import com.framework.core.platform.web.transaction.provider.TransactionProvider;

public class CheckOutInteceptor extends HandlerInterceptorAdapter {
    
    private final Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    private static final String ATTRIBUTE_CONTEXT_INITIALIZED = CheckOutInteceptor.class.getName() + ".CONTEXT_INITIALIZED";
    private static final String BEAN_NAME_TRANSACTION_PROVIDER = "transactionProvider";

    private CookieContext cookieContext;
    
    private UserTransactionContext userTransactionContext;

    private TransactionProvider transactionProvider;
    private SpringObjectFactory springObjectFactory;
    
    private AuthSettings  authSettings;

    @PostConstruct
    public void initialize() {
        springObjectFactory.registerSingletonBean(BEAN_NAME_TRANSACTION_PROVIDER, MemcachedTransactionProvider.class);
        transactionProvider = springObjectFactory.getBean(TransactionProvider.class);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // only site controller may need session
        if (!SiteHelper.isSiteController(handler))
            return true;
        
        if (handler instanceof HandlerMethod) {
            Boolean initialized = (Boolean) request.getAttribute(ATTRIBUTE_CONTEXT_INITIALIZED);
            // only process non-forwarded request, to make sure only init once per
            // request
            
            CkeckOutProcess ckeckOutProcess = findAnnotation((HandlerMethod) handler, CkeckOutProcess.class);
            if (!Boolean.TRUE.equals(initialized) && null != ckeckOutProcess) {
                loadTransaction(userTransactionContext, authSettings.getKeyForTransaction());
                request.setAttribute(ATTRIBUTE_CONTEXT_INITIALIZED, Boolean.TRUE);
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        saveTransaction(userTransactionContext);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        saveTransaction(userTransactionContext);
    }

    private void loadTransaction(UserTransactionContext userTransactionContext, Key<String> transactionIdCookieKey) {
        String transactionId  = cookieContext.getCookie(transactionIdCookieKey); //从cookie中跟据key获取transactionId
        if (transactionId != null) {
            String transactionData = transactionProvider.getAndRefreshTransaction(transactionId);
            if (transactionData != null) {
                userTransactionContext.setId(transactionId);
                userTransactionContext.loadTransactionData(transactionData);
            } else {
                logger.debug("can not find transaction, generate new transactionId to replace old one");
                userTransactionContext.requireNewTransactionId();
            }
        }
    }

    private void saveTransaction(UserTransactionContext userTransactionContext) {
        if (userTransactionContext.changed()) {
            if (userTransactionContext.invalidated()) {
                deleteTransaction(userTransactionContext);
            } else {
                persistTransaction(userTransactionContext);
            } 
            userTransactionContext.saved();
        }
    }

    private void deleteTransaction(UserTransactionContext userTransactionContext) {
        String transactionId = userTransactionContext.getId();
        if (transactionId == null) transactionId = cookieContext.getCookie(authSettings.getKeyForTransaction());
        transactionProvider.clearTransaction(transactionId);
    }

    private void persistTransaction(final UserTransactionContext userTransactionContext) {
        String transactionId = userTransactionContext.getId();
        if (transactionId == null) transactionId = cookieContext.getCookie(authSettings.getKeyForTransaction());
        transactionProvider.saveTransaction(transactionId, userTransactionContext.getTransactionData());
    }
    
    private <T extends Annotation> T findAnnotation(HandlerMethod handler, Class<T> annotationType) {
        T annotation = handler.getBeanType().getAnnotation(annotationType);
        if (annotation != null) return annotation;
        return handler.getMethodAnnotation(annotationType);
    }

    @Inject
    public void setUserTransactionContext(UserTransactionContext userTransactionContext) {
        this.userTransactionContext = userTransactionContext;
    }

    @Inject
    public void setCookieContext(CookieContext cookieContext) {
        this.cookieContext = cookieContext;
    }

    @Inject
    public void setSpringObjectFactory(SpringObjectFactory springObjectFactory) {
        this.springObjectFactory = springObjectFactory;
    }

    @Inject
    public void setAuthSettings(AuthSettings authSettings) {
        this.authSettings = authSettings;
    }
   
}