package com.framework.core.utils;

import java.util.regex.Pattern;

public final class AssertUtils {
    public static void assertNull(Object target, String message) {
        if (target != null) throw new AssertionException(message);
    }

    public static void assertNotNull(Object target, String message) {
        if (target == null) throw new AssertionException(message);
    }

    public static void assertTrue(boolean target, String message) {
        if (!target) throw new AssertionException(message);
    }

    public static void assertFalse(boolean target, String message) {
        assertTrue(!target, message);
    }

    public static void assertHasText(String target, String message) {
        if (!StringUtils.hasText(target)) throw new AssertionException(message);
    }

    public static void assertMatches(String target, Pattern pattern, String message) {
        if (!pattern.matcher(target).matches()) throw new AssertionException(message);
    }

    public static class AssertionException extends RuntimeException {
        private static final long serialVersionUID = -1023297548643274521L;

        public AssertionException(String message) {
            super(message);
        }
    }

    private AssertUtils() {
    }
}
