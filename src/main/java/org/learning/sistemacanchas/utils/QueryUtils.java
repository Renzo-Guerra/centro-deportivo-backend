package org.learning.sistemacanchas.utils;

import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class QueryUtils {
    public static Sort extractSort(List<String> sortParams) {
        if (sortParams == null || sortParams.isEmpty()) {
            return Sort.unsorted();
        }

        List<Sort.Order> orders = sortParams.stream()
                .filter(param -> param != null && !param.isBlank())
                .map(param -> {
                    String[] parts = param.split(",");
                    String field = parts[0].trim();

                    // Manejo seguro de la dirección
                    Sort.Direction dir = (parts.length > 1 && "desc".equalsIgnoreCase(parts[1].trim()))
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;

                    return new Sort.Order(dir, field);
                })
                .toList();

        return Sort.by(orders);
    }
}
