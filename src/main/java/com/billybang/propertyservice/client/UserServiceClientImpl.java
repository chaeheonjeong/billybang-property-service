package com.billybang.propertyservice.client;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.model.dto.response.UserResponseDto;
import com.billybang.propertyservice.model.dto.response.ValidateTokenResponseDto;
import org.springframework.stereotype.Service;

@Service
public class UserServiceClientImpl implements UserServiceClient {

    @Override
    public ApiResult<UserResponseDto> getUserInfo() {
        return null;
    }

    @Override
    public ApiResult<ValidateTokenResponseDto> validateToken() {
        return null;
    }
}
