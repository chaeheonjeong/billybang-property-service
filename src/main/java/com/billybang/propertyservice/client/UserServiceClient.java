package com.billybang.propertyservice.client;

import com.billybang.propertyservice.model.dto.response.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "user-service", path = "/users")
public interface UserServiceClient {

    @GetMapping("/user-info")
    UserResponseDto getUserInfo();

}
