package org.rococoa.tdb.internal;

import org.jmock.api.Invocation;
import org.jmock.api.Invokable;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class FieldAccess implements Invokable {

    private final Object object;

    public FieldAccess(Object object) {
        this.object = object;
    }

    @Override
    public Object invoke(Invocation invocation) throws Throwable {
        Method method = invocation.getInvokedMethod();
        if (method.getParameterTypes().length == 0) {
            Field field = object.getClass().getDeclaredField(fieldNameForAccessor(method));
            field.setAccessible(true);
            return field.get(object);
        } else {
            Field field = object.getClass().getDeclaredField(fieldNameForMutator(method));
            field.setAccessible(true);
            field.set(object, invocation.getParametersAsArray()[0]);
            return null;
        }
    }

    private String fieldNameForMutator(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith("set"))
            return Character.toLowerCase(methodName.charAt(3)) + methodName.substring(4);
        else
            return methodName;
    }

    private String fieldNameForAccessor(Method method) {
        String methodName = method.getName();
        if (methodName.startsWith("get")) {
            return removePrefixAnduncapitalise(methodName, 3);
        }
        else if (methodName.startsWith("is"))
            return removePrefixAnduncapitalise(methodName, 2);
        else
            return methodName;
    }

    private String removePrefixAnduncapitalise(String methodName, int index) {
        return Character.toLowerCase(methodName.charAt(index)) + methodName.substring(index + 1);
    }
}
