package com.billybang.propertyservice;

import com.billybang.propertyservice.service.PropertyDataSaverService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class PropertyServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PropertyServiceApplication.class, args);
    }

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

}
