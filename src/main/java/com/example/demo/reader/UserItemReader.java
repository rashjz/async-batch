package com.example.demo.reader;

import com.example.demo.interfaces.InitialListProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.support.AbstractItemCountingItemStreamItemReader;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Slf4j
public class UserItemReader<T> extends AbstractItemCountingItemStreamItemReader<T> {

    private final InitialListProvider<T> initialListProvider;

    private Iterator<T> iterator;

    public UserItemReader(final InitialListProvider<T> initialListProvider) {
        this.initialListProvider = initialListProvider;
    }

    @Override
    protected void doOpen() throws Exception {
        List<T> initialList = initialListProvider.getInitialList();

        log.info("reader xxxxxxxxxx " + initialList.size());

        if (initialList == null) {
            log.debug("List returned from InitialListProvider {} is null.", initialListProvider);

            this.iterator = Collections.emptyIterator();
            return;
        }

        this.iterator = initialList.iterator();
    }

    @Override
    protected T doRead() {
        if (iterator.hasNext()) {
            return iterator.next();
        }

        return null;
    }

    @Override
    protected void doClose() {
        // NO-OP
    }
}
