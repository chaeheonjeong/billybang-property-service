package com.billybang.propertyservice.client;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.model.dto.response.UserResponseDto;
import com.billybang.propertyservice.model.dto.response.ValidateTokenResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

public interface UserServiceClient {

    ApiResult<UserResponseDto> getUserInfo();

    ApiResult<ValidateTokenResponseDto> validateToken();
}
