package com.dreamteam.yamblz.tradecat.data;


@SuppressWarnings("WeakerAccess") // methods with custom message can be used later
public class Preconditions {

    private static final String MESSAGE_OBJECTS_NULL = "All objects must not be non null";

    private static final String FALSE_CONDITION = "False condition";


    public static void nonNull(final Object... objects) {
        nonNull(MESSAGE_OBJECTS_NULL, objects);
    }

    public static void nonNull(String exceptionMessage, final Object... objects) {
        for (Object object : objects) {
            if (object == null) {
                throw new NullPointerException(exceptionMessage);
            }
        }
    }

    public static void assertFalse(boolean condition) {
        assertFalse(FALSE_CONDITION, condition);
    }

    public static void assertFalse(String exceptionMessage, boolean condition) {
        if (condition) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

    public static void assertTrue(boolean condition) {
        assertTrue(FALSE_CONDITION, condition);
    }

    public static void assertTrue(String exceptionMessage, boolean condition) {
        if (!condition) {
            throw new IllegalStateException(exceptionMessage);
        }
    }

}