package com.billybang.propertyservice.model.dto.response;

import com.billybang.propertyservice.model.type.CompanySize;
import com.billybang.propertyservice.model.type.Occupation;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoResponseDto {

    private Occupation occupation;
    private CompanySize companySize;
    private Integer employmentDuration;
    private Integer individualIncome;
    private Integer totalMarriedIncome;
    private Integer childrenCount;
    private Boolean isForeign;
    private Boolean isFirstHouseBuyer;
    private Boolean isMarried;
    private Boolean isNewlyMarried;
    private Boolean hasOtherLoans;
}
