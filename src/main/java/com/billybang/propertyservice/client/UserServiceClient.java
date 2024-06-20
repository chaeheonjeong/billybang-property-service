package com.billybang.propertyservice.client;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.model.dto.response.UserResponseDto;
import com.billybang.propertyservice.model.dto.response.ValidateTokenResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", path = "/users")
public interface UserServiceClient {

    @GetMapping("/user-info")
    ApiResult<UserResponseDto> getUserInfo();

    @GetMapping("/validate-token")
    ApiResult<ValidateTokenResponseDto> validateToken();
}
