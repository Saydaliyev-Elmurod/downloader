package org.example.notification.security;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ListUtils {
    
    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException("List must contain exactly one element");
                    }
                    return list.get(0);
                }
        );
    }
}
