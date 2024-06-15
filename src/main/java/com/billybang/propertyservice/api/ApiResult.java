package com.billybang.propertyservice.api;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ApiResult<T> {

    boolean success;
    T response;
}
