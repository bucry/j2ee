package com.framework.core.platform;

public class ClassUtils {
    public static String getOriginalClassName(Class<?> targetClass) {
        String className = targetClass.getName();
        int cglibNameIndex = className.indexOf("$$EnhancerByCGLIB");
        if (cglibNameIndex > -1) {
            return className.substring(0, cglibNameIndex);
        }
        return className;
    }

    public static String getOriginalClassName(Object object) {
        return getOriginalClassName(object.getClass());
    }

    public static String getSimpleOriginalClassName(Object object) {
        String fullClassName = getOriginalClassName(object);
        int lastNamespace = fullClassName.lastIndexOf('.');
        if (lastNamespace > -1) {
            return fullClassName.substring(lastNamespace + 1);
        }
        return fullClassName;
    }
}
