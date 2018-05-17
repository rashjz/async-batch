package com.example.demo.writer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStream;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
public class AsyncItemWriter<T> implements ItemStreamWriter<Future<T>>, InitializingBean {

    private ItemWriter<T> delegate;

    @Override
    public void afterPropertiesSet() {
        Assert.notNull(delegate, "A delegate ItemWriter must be provided.");
    }

    public void setDelegate(ItemWriter<T> delegate) {
        this.delegate = delegate;
    }

    @Override
    public void write(List<? extends Future<T>> items) throws Exception {
        List<T> list = new ArrayList<>();
        for (Future<T> future : items) {
            try {
                T item = future.get();

                if (item != null) {
                    list.add(item);
                }
            } catch (Exception e) {
                Throwable cause = e.getCause();

                if (cause != null && cause instanceof Exception) { // exception happened in processor
                    log.debug("An exception was thrown while processing an item", e);

                    throw (Exception) cause;
                } else { // exception happened while unwrapping future
                    throw e;
                }
            }
        }
        log.info("writer xxxxxxxxxx " + list.size());

        delegate.write(list);
    }

    @Override
    public void open(ExecutionContext executionContext) {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).open(executionContext);
        }
    }

    @Override
    public void update(ExecutionContext executionContext) {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).update(executionContext);
        }
    }

    @Override
    public void close() {
        if (delegate instanceof ItemStream) {
            ((ItemStream) delegate).close();
        }
    }
}
