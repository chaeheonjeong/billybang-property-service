package com.billybang.propertyservice.api;

import com.billybang.propertyservice.model.PaginationInfo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class PageResult<T> {

    private T content;
    private PaginationInfo paginationInfo;
//    public static <T> PageResult<T> of(T content, PaginationInfo paginationInfo) {
//        return new PageResult<>(content, paginationInfo);
//    }
}