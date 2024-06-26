package com.rapid7.integration.util;

@FunctionalInterface
public interface ThrowingBiConsumer<T, U> {

    void accept(T t, U u) throws Exception;
}
