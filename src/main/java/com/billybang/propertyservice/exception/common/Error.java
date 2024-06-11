package com.billybang.propertyservice.exception.common;

public interface Error {
	String getCode();

	String getMessage(String... values);
}
