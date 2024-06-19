package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.entity.Area;
import com.billybang.propertyservice.repository.AreaRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

@Service
@AllArgsConstructor
@Transactional
public class AreaStatisticDataSaverService {
    private final AreaRepository repository;

    public void saveAreaStatisticsFromCSV(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            boolean headerSkipped = false;
            while ((line = reader.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length == 3) {

                    Area area = new Area();
                    area.setDistrictId(Long.valueOf(data[0]));
                    area.setAreaName(data[1]);
                    area.setPopulationDensity(Double.parseDouble(data[2]));

                    repository.save(area);
                }
            }
        }
    }
}
