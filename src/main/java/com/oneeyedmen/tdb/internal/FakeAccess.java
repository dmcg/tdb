package com.oneeyedmen.tdb.internal;

import com.oneeyedmen.tdb.Faker;
import org.jmock.api.Invocation;
import org.jmock.api.Invokable;

import java.lang.reflect.Method;


public class FakeAccess implements Invokable {

    private static final Boolean DEFAULT_BOOLEAN = Boolean.FALSE;
    private static final Byte DEFAULT_BYTE = (byte) 0x07;
    private static final Short DEFAULT_SHORT = (short) 42;
    private static final Character DEFAULT_CHAR = '?';
    private static final Integer DEFAULT_INTEGER = 42;
    private static final Double DEFAULT_DOUBLE = Math.PI;
    private static final Float DEFAULT_FLOAT = (float) Math.PI;

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        if (invocation.getParameterCount() == 0) {
            return resultFor(invocation);
        }
        else {
            throw new NoSuchMethodError(invocation.getInvokedMethod().getName());
        }
    }

    private Object resultFor(Invocation invocation) {
        Method invokedMethod = invocation.getInvokedMethod();
        Class<?> returnType = invokedMethod.getReturnType();
        if (returnType == Void.TYPE)
            throw new NoSuchMethodError(invocation.getInvokedMethod().getName());
        if (returnType == String.class)
            return FieldAccess.fieldNameForAccessor(invokedMethod);
        if (returnType == Boolean.TYPE)
            return DEFAULT_BOOLEAN;
        if (returnType == Integer.TYPE || returnType == Long.TYPE)
            return DEFAULT_INTEGER;
        if (returnType == Float.TYPE)
            return DEFAULT_FLOAT;
        if (returnType == Double.TYPE)
            return DEFAULT_DOUBLE;
        if (returnType == Character.TYPE)
            return DEFAULT_CHAR;
        if (returnType == Byte.TYPE)
            return DEFAULT_BYTE;
        if (returnType == Short.TYPE)
            return DEFAULT_SHORT;
        return Faker.fakeA(returnType);
    }
}
