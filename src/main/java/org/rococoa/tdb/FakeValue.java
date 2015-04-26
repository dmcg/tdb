
package org.rococoa.tdb;

import org.jmock.api.Imposteriser;
import org.jmock.api.Invokable;
import org.jmock.internal.ProxiedObjectIdentity;
import org.jmock.lib.legacy.ClassImposteriser;
import org.rococoa.tdb.internal.FieldAccess;
import org.rococoa.tdb.internal.MethodAccess;

import java.lang.reflect.ParameterizedType;

public class FakeValue<T> {

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
