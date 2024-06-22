package com.billybang.propertyservice;

import com.billybang.propertyservice.service.AreaStatisticDataSaverService;
import com.billybang.propertyservice.service.DistrictStatisticDataSaverService;
import com.billybang.propertyservice.service.NewsCrawlingService;
import com.billybang.propertyservice.service.PropertyDataSaverService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.IOException;
import java.io.InputStream;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@EnableScheduling
public class PropertyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyServiceApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner test(NewsCrawlingService newsCrawlingService) {
//        return (args) -> {
//            newsCrawlingService.crawlNewsData();
//            System.out.println("뉴스 크롤링 완료");
//        };
//    }

//    @Bean
//    public CommandLineRunner test(PropertyDataSaverService propertyDataSaverService) {
//        return (args) -> {
//            String[] folderPathList = {"src/main/resources/data/원룸·투룸", "src/main/resources/data/아파트·오피스텔",
//                    "src/main/resources/data/빌라·주택"};
//
//            for(String folderPath: folderPathList) {
//                propertyDataSaverService.readJsonFiles(folderPath);
//            }
//        };
//    }

    @Bean
    public CommandLineRunner importStatistics(DistrictStatisticDataSaverService districtStatisticDataSaverService, AreaStatisticDataSaverService areaStatisticDataSaverService) {
        return args -> {
            String districtCsvFilePath = "data/구별_통계.csv";
            String AreaCsvFilePath = "data/동별_통계.csv";

            Resource disResource = new ClassPathResource(districtCsvFilePath);
            Resource areaResource = new ClassPathResource(AreaCsvFilePath);
            try (InputStream disInputStream = disResource.getInputStream();
                 InputStream areaInputStream = areaResource.getInputStream()) {

                districtStatisticDataSaverService.saveDistrictStatisticsFromCSV(disInputStream);
                areaStatisticDataSaverService.saveAreaStatisticsFromCSV(areaInputStream);
                System.out.println("CSV data imported successfully!");
            } catch (IOException e) {
                System.out.println("Failed to import CSV data: " + e.getMessage());
            }
        };
    }
}
