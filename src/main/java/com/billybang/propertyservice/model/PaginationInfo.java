package com.billybang.propertyservice.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class PaginationInfo {
    private boolean hasNext;
    private int pageNumber;
    private int pageSize;
    private int totalElements;
}
