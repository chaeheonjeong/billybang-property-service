package com.billybang.propertyservice.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FeignErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    @Override
    public Exception decode(String methodKey, Response response) {

        try {
            if (response.status() == 400) {
                return new FeignException.BadRequest(
                        response.reason(),
                        response.request(),
                        response.body().asInputStream().readAllBytes(),
                        response.headers());
            } else if (response.status() == 401) {
                return new FeignException.Unauthorized(
                        response.reason(),
                        response.request(),
                        response.body().asInputStream().readAllBytes(),
                        response.headers());
            } else if (response.status() == 403) {
                return new FeignException.Forbidden(
                        response.reason(),
                        response.request(),
                        response.body().asInputStream().readAllBytes(),
                        response.headers());
            } else {
                return new FeignException.InternalServerError(
                        response.reason(),
                        response.request(),
                        response.body().asInputStream().readAllBytes(),
                        response.headers());
            }
        } catch (Exception e) {
            return e;
        }
    }
}
