package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.*;
import com.billybang.propertyservice.model.dto.request.SearchStatisticRequestDto;
import com.billybang.propertyservice.model.dto.response.SearchStatisticResponseDto;
import com.billybang.propertyservice.repository.AreaRepository;
import com.billybang.propertyservice.repository.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class InfoService {

    private static final Map<Long, Map<String, Double>> populationDensities = new HashMap<>();
    private static final Map<String, Float> individualIncomes = new HashMap<>();
    private static final Map<Long, HashMap<String, Integer>> populationCounts = new HashMap<>();
    private static final Map<String, Integer> crimeCounts = new HashMap<>();
    private static final Map<String, Number> averageMap = new HashMap<>();

    private final AreaRepository areaRepository;
    private final DistrictRepository districtRepository;

    public SearchStatisticResponseDto findStatisticInfo(SearchStatisticRequestDto searchStatisticRequestDto) {
        Long districtId = searchStatisticRequestDto.getDistrictId();

        if(populationDensities.isEmpty() || individualIncomes.isEmpty() || populationCounts.isEmpty() || crimeCounts.isEmpty()) {
            List<District> districts = districtRepository.findAll();
            initializeMaps(districts);
        }

        return new SearchStatisticResponseDto(getPopulationDensity(districtId), getIndividualIncome(), getPopulationCount(districtId), getCrimeCount(), getAverage());
    }

    private void initializeMaps(List<District> districts){
        initializeDensityMap();
        initializeIncomeMap(districts);
        initializeCrimeCountMap(districts);
        initializePopulationCountMap(districts);
    }

    private void initializeDensityMap() {
        for(int i=1; i<= 25; i++){
            List<Area> areas = areaRepository.findByDistrictId((long)i);

            HashMap<String, Double> areaDensities = new HashMap<>();
            for(Area area: areas) {
                areaDensities.put(area.getAreaName(), area.getPopulationDensity());
            }

            populationDensities.put((long) i, areaDensities);
        }
    }

    private void initializeIncomeMap(List<District> districts) {
        float sum = 0;
        for(District district: districts){
            individualIncomes.put(district.getDistrictName(), district.getIndividualIncome());
            sum += district.getIndividualIncome();
        }

        BigDecimal average = new BigDecimal(sum / 25).setScale(1, RoundingMode.DOWN);
        averageMap.put("income", average.floatValue());
    }

    private void initializeCrimeCountMap(List<District> districts) {
        int sum = 0;
        for(District district: districts){
            crimeCounts.put(district.getDistrictName(), district.getCrimeCount());
            sum += district.getCrimeCount();
        }

        averageMap.put("crime", sum / 25);
    }

    private void initializePopulationCountMap(List<District> districts) {
        for(District district: districts){
            HashMap<String, Integer> populations = new HashMap<>();
            populations.put("0s", district.getPopulation0s());
            populations.put("10s", district.getPopulation10s());
            populations.put("20s", district.getPopulation20s());
            populations.put("30s", district.getPopulation30s());
            populations.put("40s", district.getPopulation40s());
            populations.put("50s", district.getPopulation50s());
            populations.put("60s", district.getPopulation60s());
            populations.put("70over", district.getPopulation70Over());


            populationCounts.put(district.getId(), populations);
        }
    }

    private List<PopulationDensity> getPopulationDensity(Long districtId) {
        Map<String, Double> areaDensities = populationDensities.get(districtId);
        List<PopulationDensity> densityList = new ArrayList<>();
        for(String areaName: areaDensities.keySet()){
            densityList.add(new PopulationDensity(areaName, areaDensities.get(areaName)));
        }

        return densityList;
    }

    private List<IndividualIncome> getIndividualIncome() {
        List<IndividualIncome> incomeList = new ArrayList<>();
        for(String districtName : individualIncomes.keySet()){
            incomeList.add(new IndividualIncome(districtName, individualIncomes.get(districtName)));
        }

        return incomeList;
    }

    private List<PopulationCount> getPopulationCount(Long districtId) {
        Map<String, Integer> popCounts = populationCounts.get(districtId);
        List<PopulationCount> countList = new ArrayList<>();
        for(String popAge: popCounts.keySet()){
            countList.add(new PopulationCount(popAge,popCounts.get(popAge)));
        }

        return countList;
    }

    private List<CrimeCount> getCrimeCount() {
        List<CrimeCount> crimeCountList = new ArrayList<>();
        for(String districtName: crimeCounts.keySet()){
            crimeCountList.add(new CrimeCount(districtName, crimeCounts.get(districtName)));
        }

        return crimeCountList;
    }

    private List<SeoulAverage> getAverage() {
        List<SeoulAverage> seoulAverage = new ArrayList<>();
        seoulAverage.add(new SeoulAverage("crimeAverage", averageMap.get("crime")));
        seoulAverage.add(new SeoulAverage("incomeAverage", averageMap.get("income")));
        return seoulAverage;
    }
}
