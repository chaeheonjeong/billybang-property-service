package com.billybang.propertyservice.crawling;

import org.apache.tomcat.util.json.JSONParser;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;

public class DataSaver {
    public void readJsonFiles(String folderPath) throws Exception{
        File folder = new File(folderPath);
        File[] subFolderPathList = folder.listFiles();

        for(File subFolderPath: subFolderPathList){
            File subFolder = new File(String.valueOf(subFolderPath));
            File[] fileList = subFolder.listFiles();

            for(File file: fileList){
                Reader reader = new FileReader(file);
                JSONParser jsonParser = new JSONParser(reader);
                Object json = jsonParser.parse();
                System.out.println(json);
            }


        }
    }

    public static void main(String[] args) throws Exception {
        DataSaver dataSaver = new DataSaver();
        try {
            dataSaver.readJsonFiles("src/main/resources/data/원룸·투룸");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
