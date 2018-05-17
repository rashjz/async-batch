package com.example.demo.interfaces;

import java.util.List;

@FunctionalInterface
public interface InitialListProvider<T> {
    List<T> getInitialList() throws Exception;
}
