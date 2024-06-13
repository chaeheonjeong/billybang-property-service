package com.billybang.propertyservice.service;

import com.billybang.propertyservice.model.Property;
import com.billybang.propertyservice.repository.PropertyDataSaveRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@AllArgsConstructor
public class PropertyDataSaverService {
    private final PropertyDataSaveRepository propertyDataSaveRepository;

    public void saveProperties(List<Property> properties) {
        System.out.println(properties.get(0));
        for (Property property : properties) {
            try {
                propertyDataSaveRepository.save(property);
            } catch (DataIntegrityViolationException e) {
                continue;
            }
        }
    }

    public void readJsonFiles(String folderPath) throws Exception{
        File folder = new File(folderPath);
        File[] subFolderPathList = folder.listFiles();
        List<Property> properties = new ArrayList<>();

        for(File subFolderPath: subFolderPathList){
            File subFolder = new File(String.valueOf(subFolderPath));
            if(subFolder.getName().equals(".DS_Store")) continue;
            File[] fileList = subFolder.listFiles();

            for(File file: fileList){
                Reader reader = new FileReader(file);
                JSONParser jsonParser = new JSONParser();
                JSONArray jsonArray = (JSONArray) jsonParser.parse(reader);

                for(Object obj: jsonArray){
                    JSONObject json = (JSONObject) obj;
                    Property property = new Property();
                    property.setId(Long.parseLong(json.get("articleNo").toString()));
                    property.setArticleName(json.get("articleName").toString());
                    property.setBuildingName(json.get("buildingName").toString());
                    property.setRealEstateType(json.get("realEstateTypeCode").toString());

                    if(json.get("tradeTypeName").equals("매매") || json.get("tradeTypeName").equals("전세")) {
                        property.setTradeType(toTradeTypeCode(json.get("tradeTypeName").toString()));
                    } else {
                        continue;
                    }

                    property.setArticleFeatureDesc(getStringValue(json, "articleFeatureDesc"));
                    property.setArticleConfirmYmd(json.get("articleConfirmYmd").toString());
                    property.setAreaName(getStringValue(json, "areaName"));
                    property.setArea1(Integer.parseInt(json.get("area1").toString()));
                    property.setArea2(Integer.parseInt(json.get("area2").toString()));
                    property.setFloorInfo(json.get("floorInfo").toString());

                    if(json.get("dealOrWarrantPrc") == null) continue;
                    property.setPrice(toIntPrice(json.get("dealOrWarrantPrc").toString()));

                    property.setLatitude(Double.parseDouble(json.get("latitude").toString()));
                    property.setLongitude(Double.parseDouble(json.get("longitude").toString()));
                    property.setArticleUrl(json.get("cpPcArticleUrl").toString());
                    property.setRepresentativeImgUrl(getStringValue(json, "representativeImgUrl"));
                    property.setSameAddrCnt(Integer.parseInt(json.get("sameAddrCnt").toString()));
                    property.setRealtorName(json.get("realtorName").toString());
                    property.setCpName(json.get("cpName").toString());

                    properties.add(property);
                }
            }
        }

        saveProperties(properties);
    }

    private String getStringValue(JSONObject json, String key) {
        return json.containsKey(key) ? json.get(key).toString() : null;
    }

    public String toTradeTypeCode(String tradeTypeName){
        if(tradeTypeName.equals("매매")){
            return "DEAL";
        }

        return "LEASE";
    }

    public int toIntPrice(String price){
        int result = 0;
        price = price.replaceAll(",", "");

        if(price.contains("억")){
            String[] parts = price.split("억");

            result += Integer.parseInt(parts[0].trim()) * 100;
            if(parts.length > 1){
                result += Integer.parseInt(parts[1].trim()) / 100;
            }
        } else{
            result += Integer.parseInt(price.trim()) / 100;
        }
        return result;
    }
}
