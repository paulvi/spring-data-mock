package com.mmnaseri.utils.spring.data.proxy.impl.adapters;

import com.mmnaseri.utils.spring.data.domain.impl.ImmutableInvocation;
import org.springframework.data.domain.Slice;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isIn;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Mohammad Milad Naseri (m.m.naseri@gmail.com)
 * @since 1.0 (4/10/16)
 */
public class IterableSliceResultAdapterTest {

    private interface Sample {

        Slice findSlice();

        Object findOther();

    }

    @Test
    public void testAccepting() throws Exception {
        final IterableSliceResultAdapter adapter = new IterableSliceResultAdapter();
        assertThat(adapter.accepts(null, null), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findOther"), new Object[]{}), new Object()), is(false));
        assertThat(adapter.accepts(new ImmutableInvocation(Sample.class.getMethod("findSlice"), new Object[]{}), new Object()), is(true));
    }

    @Test
    public void testAdapting() throws Exception {
        final IterableSliceResultAdapter adapter = new IterableSliceResultAdapter();
        final List<Object> originalValue = Arrays.<Object>asList(1, 2, 3, 4);
        final Slice slice = adapter.adapt(new ImmutableInvocation(Sample.class.getMethod("findSlice"), new Object[]{}), originalValue);
        assertThat(slice, is(notNullValue()));
        assertThat(slice.getNumberOfElements(), is(originalValue.size()));
        final List content = slice.getContent();
        for (Object item : content) {
            assertThat(item, isIn(originalValue));
        }
    }
}