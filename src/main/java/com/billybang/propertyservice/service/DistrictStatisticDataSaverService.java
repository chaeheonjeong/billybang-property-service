package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.entity.District;
import com.billybang.propertyservice.repository.DistrictRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@AllArgsConstructor
@Transactional
public class DistrictStatisticDataSaverService {

    private final DistrictRepository repository;

    public void saveDistrictStatisticsFromCSV(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length == 12) {

                    District district = new District();
                    district.setId(Long.valueOf(data[0]));
                    district.setDistrictName(data[1]);
                    district.setIndividualIncome(Float.parseFloat(data[2]));
                    district.setPopulation0s(Integer.parseInt(data[3]));
                    district.setPopulation10s(Integer.parseInt(data[4]));
                    district.setPopulation20s(Integer.parseInt(data[5]));
                    district.setPopulation30s(Integer.parseInt(data[6]));
                    district.setPopulation40s(Integer.parseInt(data[7]));
                    district.setPopulation50s(Integer.parseInt(data[8]));
                    district.setPopulation60s(Integer.parseInt(data[9]));
                    district.setPopulation70Over(Integer.parseInt(data[10]));
                    district.setCrimeCount(Integer.parseInt(data[11]));

                    repository.save(district);
                }
            }
        }
    }
}
