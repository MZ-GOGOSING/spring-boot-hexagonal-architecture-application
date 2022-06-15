package me.gogosing.support.converter;

import org.springframework.lang.Nullable;

@FunctionalInterface
public interface BiConverter<T, U, R> {

    @Nullable
    R convert(T t, U u);
}
