package com.billybang.propertyservice.client;

import com.billybang.propertyservice.api.ApiResult;
import com.billybang.propertyservice.api.ApiUtils;
import com.billybang.propertyservice.model.dto.response.UserInfoResponseDto;
import com.billybang.propertyservice.model.dto.response.UserResponseDto;
import com.billybang.propertyservice.model.dto.response.ValidateTokenResponseDto;
import com.billybang.propertyservice.model.type.CompanySize;
import com.billybang.propertyservice.model.type.Occupation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class UserServiceClientImpl implements UserServiceClient {

    @Override
    public ApiResult<UserResponseDto> getUserInfo() {
        return ApiUtils.success(UserResponseDto.builder()
                    .userId(1234L)
                    .email("1234@naver.com")
                    .birthDate(LocalDate.of(1990, 5, 15))
                    .nickname("감자")
                    .userInfo(UserInfoResponseDto.builder()
                            .occupation(Occupation.IT).companySize(CompanySize.LARGE)
                            .employmentDuration(10).individualIncome(4000).totalMarriedIncome(4000)
                            .childrenCount(2).isFirstHouseBuyer(true).isForeign(false).hasOtherLoans(false)
                            .isNewlyMarried(false).isMarried(false).build())
                    .build());
    }

    @Override
    public ApiResult<ValidateTokenResponseDto> validateToken() {
        return ApiUtils.success(ValidateTokenResponseDto.builder().isValid(true).build());
    }
}
