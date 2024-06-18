package com.billybang.propertyservice.model.dto.response;

import com.billybang.propertyservice.model.statistic.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class StatisticResponseDto {
    private List<PopulationDensity> populationDensity;
    private List<IndividualIncome> individualIncome;
    private List<PopulationCount> populationCount;
    private List<CrimeCount> crimeCount;
    private List<SeoulAverage> seoulAverage;
}
