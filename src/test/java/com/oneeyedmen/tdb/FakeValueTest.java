package com.oneeyedmen.tdb;

import org.junit.Test;

import static org.junit.Assert.*;

@SuppressWarnings("UnusedDeclaration")
public class FakeValueTest {

    public abstract class ValueToBeFaked {
        public abstract String name();
        public abstract String operation(int arg);
        public abstract String getProperty();
        public abstract void setProperty(String value);
        public abstract boolean isSomething();
        public abstract void setSomething(boolean value);
    }

    @Test public void use_a_field_to_fake_an_accessor() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {
            String name = "bill";
        }.get();

        assertEquals("bill", fakeValue.name());
    }

    @Test public void use_a_method_to_fake_a_method() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {
            String operation(int arg) {
                return String.valueOf(arg);
            }
        }.get();

        assertEquals("42", fakeValue.operation(42));
    }

    @Test public void use_a_field_to_fake_a_property() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {
            String property = "bill";
        }.get();

        assertEquals("bill", fakeValue.getProperty());

        fakeValue.setProperty("fred");
        assertEquals("fred", fakeValue.getProperty());
    }

    @Test public void use_a_field_to_fake_an_is_property() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {
            boolean something = false;
        }.get();

        assertEquals(false, fakeValue.isSomething());

        fakeValue.setSomething(true);
        assertEquals(true, fakeValue.isSomething());
    }

    @Test public void equals_only_considers_identity() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {}.get();
        ValueToBeFaked fakeValue2 = new FakeValue<ValueToBeFaked>() {}.get();
        assertEquals(fakeValue, fakeValue);
        assertNotEquals(fakeValue, fakeValue2);
    }

    @Test public void to_string() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {}.get();
        assertEquals("A fake ValueToBeFaked", fakeValue.toString());
    }

    @Test public void throws_RuntimeException_on_no_resolution() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {}.get();

        try {
            fakeValue.name();
            fail();
        } catch (RuntimeException x) {
            assertTrue(x.getCause() instanceof NoSuchFieldException);
        }
    }

    @Test public void throws_ClassCastException_on_wrong_type() {
        ValueToBeFaked fakeValue = new FakeValue<ValueToBeFaked>() {
            int name = 42;
        }.get();

        try {
            fakeValue.name();
            fail();
        } catch (RuntimeException x) {
            assertTrue(x instanceof ClassCastException);
        }
    }
}
