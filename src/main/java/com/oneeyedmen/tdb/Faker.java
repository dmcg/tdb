
package com.oneeyedmen.tdb;

import com.oneeyedmen.tdb.internal.FieldAccess;
import com.oneeyedmen.tdb.internal.MethodAccess;
import org.jmock.api.Imposteriser;
import org.jmock.api.Invokable;
import org.jmock.internal.ProxiedObjectIdentity;
import org.jmock.lib.legacy.ClassImposteriser;

import java.lang.reflect.ParameterizedType;

public class Faker<T> {

    private static final Imposteriser IMPOSTERISER = ClassImposteriser.INSTANCE;

    @SuppressWarnings("unchecked")
    private final Class<T> type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

    @SuppressWarnings("unchecked")
    public T get() {
        return IMPOSTERISER.imposterise(
                new MyProxiedObjectIdentity(new MethodAccess(this, new FieldAccess(this))),
                type);
    }

    private class MyProxiedObjectIdentity extends ProxiedObjectIdentity{
        public MyProxiedObjectIdentity(Invokable next) {
            super(next);
        }

        @Override
        public String toString() {
            return "A fake " + type.getSimpleName();
        }
    }

}
