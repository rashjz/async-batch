package com.example.demo.reader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JUnit4.class)
public class UserItemReaderTest {

    @Test
    public void doRead_nullProvider_iteratorInitialized() throws Exception {
        // Setup
        UserItemReader<Object> reader = new UserItemReader<>(() -> null);

        // Execute
        reader.doOpen();
        Object readerValue = reader.doRead();

        //Validate
        assertThat(readerValue).isNull();

        // Finish
        reader.doClose();
    }

    @Test
    public void doRead_emptyListProvider_readedValueIsNull() throws Exception {
        // Setup
        UserItemReader<Object> reader = new UserItemReader<>(Collections::emptyList);

        // Execute
        reader.doOpen();
        Object readedValue = reader.doRead();

        //Validate
        assertThat(readedValue).isNull();

        // Finish
        reader.doClose();
    }

    @Test
    public void doRead_threeStringsProvider_threeStringsReaded() throws Exception {
        // Setup
        final String one = "one";
        final String two = "two";
        final String three = "three";

        UserItemReader<String> reader = new UserItemReader<>(() -> Arrays.asList(one, two, three));

        // Execute
        reader.doOpen();

        String readedValue1 = reader.doRead();
        String readedValue2 = reader.doRead();
        String readedValue3 = reader.doRead();
        String readedValue4 = reader.doRead();

        //Validate
        assertThat(readedValue1).isEqualTo(one);
        assertThat(readedValue2).isEqualTo(two);
        assertThat(readedValue3).isEqualTo(three);
        assertThat(readedValue4).isNull();

        // Finish
        reader.doClose();
    }
}
