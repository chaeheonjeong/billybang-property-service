package com.billybang.propertyservice;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ChangeDatabaseName {
    public static void main(String[] args) {
        String inputFilePath = "/Users/chaeheon/billybang-property-service-study/src/main/resources/data/properties.txt";
        String outputFilePath = "/Users/chaeheon/billybang-property-service-study/src/main/resources/data/properties.sql"; // 변경된 내용을 저장할 파일

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFilePath));
             BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {

            String line;
            while ((line = reader.readLine()) != null) {
                // 'billybangdb'를 'billybang_study'로 변경
                line = line.replace("billybangdb", "billybang_study");
                writer.write(line);
                writer.newLine();
            }

            System.out.println("변경이 완료되었습니다. 새로운 파일: " + outputFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
