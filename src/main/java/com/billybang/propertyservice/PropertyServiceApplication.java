package com.billybang.propertyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
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
//
//
//            for(String folderPath: folderPathList) {
//                propertyDataSaverService.readJsonFiles(folderPath);
//            }
//        };
//    }

//    @Bean
//    public CommandLineRunner test(PropertyDataSaverService propertyDataSaverService) {
//        return (args) -> {
//            propertyDataSaverService.saveAddress();
//            System.out.println("도로명, 지번 주소 저장 완료");
//        };
//    }


//    @Bean
//    public CommandLineRunner test(PropertyDataSaverService propertyDataSaverService) {
//        return (args) -> {
//            propertyDataSaverService.saveDistrictAreaId();
//            System.out.println("매물 구와 동 코드 저장 완료");
//        };
//    }

//    @Bean
//    public CommandLineRunner importStatistics(DistrictStatisticDataSaverService districtStatisticDataSaverService, AreaStatisticDataSaverService areaStatisticDataSaverService) {
//        return args -> {
//            String districtCsvFilePath = "data/구별_통계.csv";
//            String AreaCsvFilePath = "data/동별_통계.csv";
//
//            Resource disResource = new ClassPathResource(districtCsvFilePath);
//            Resource areaResource = new ClassPathResource(AreaCsvFilePath);
//            try (InputStream disInputStream = disResource.getInputStream();
//                 InputStream areaInputStream = areaResource.getInputStream()) {
//
//                districtStatisticDataSaverService.saveDistrictStatisticsFromCSV(disInputStream);
//                areaStatisticDataSaverService.saveAreaStatisticsFromCSV(areaInputStream);
//                System.out.println("CSV data imported successfully!");
//            } catch (IOException e) {
//                System.out.println("Failed to import CSV data: " + e.getMessage());
//            }
//        };
//    }
}
