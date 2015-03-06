package com.framework.core.platform;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopeMetadata;
import org.springframework.context.annotation.ScopeMetadataResolver;
import org.springframework.context.annotation.ScopedProxyMode;

import java.util.Map;

/**
 * Make scanned component as prototype by default
 * <p/>
 * port from org.springframework.context.annotation.AnnotationScopeMetadataResolver
 *
 * @author bfc
 */
public class DefaultAnnotationScopeResolver implements ScopeMetadataResolver {
    public ScopeMetadata resolveScopeMetadata(BeanDefinition definition) {
        ScopeMetadata metadata = new ScopeMetadata();
        metadata.setScopeName(BeanDefinition.SCOPE_PROTOTYPE);
        if (definition instanceof AnnotatedBeanDefinition) {
            resolveScopeAnnotation((AnnotatedBeanDefinition) definition, metadata);
        }
        return metadata;
    }

    private void resolveScopeAnnotation(AnnotatedBeanDefinition definition, ScopeMetadata metadata) {
        Map<String, Object> attributes = definition.getMetadata().getAnnotationAttributes(Scope.class.getName());
        if (attributes != null) {
            metadata.setScopeName((String) attributes.get("value"));
            ScopedProxyMode proxyMode = (ScopedProxyMode) attributes.get("proxyMode");
            if (proxyMode == null || proxyMode == ScopedProxyMode.DEFAULT) {
                proxyMode = ScopedProxyMode.NO;
            }
            metadata.setScopedProxyMode(proxyMode);
        }
    }
}

