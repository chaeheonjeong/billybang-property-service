package com.billybang.propertyservice.service;

import com.billybang.propertyservice.ReverseGeocoding;
import com.billybang.propertyservice.model.entity.Property;
import com.billybang.propertyservice.repository.PropertyDataSaverRepository;
import jakarta.transaction.Transactional;
import org.json.simple.parser.JSONParser;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PropertyDataSaverService {
    private final PropertyDataSaverRepository propertyDataSaverRepository;
    private Map<String, Map<String, String>> districtAreaMap = new HashMap<>();

    public PropertyDataSaverService(PropertyDataSaverRepository propertyDataSaverRepository) {
        this.propertyDataSaverRepository = propertyDataSaverRepository;
    }

    public void init() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/data/지역위경도.csv"))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] columns = line.split(",");
                String districtName = columns[2];
                String areaName = columns[3];
                String code = columns[0];

                districtAreaMap
                        .computeIfAbsent(districtName, k -> new HashMap<>())
                        .put(areaName, code);
            }
        }
    }

    @Transactional
    public void saveDistrictAreaId() throws IOException {
        init();
        //List<Property> properties = propertyDataSaverRepository.findAll();
        List<Property> properties = propertyDataSaverRepository.findByDistrictIdIsNull();
        for(Property property: properties){
            String address = property.getJibeonAddress();
            String[] addArr = address.split(" ");

            String districtName = addArr[1];
            String areaName = addArr[2];
            if (districtAreaMap.containsKey(districtName) && districtAreaMap.get(districtName).containsKey(areaName)) {
                String code = districtAreaMap.get(districtName).get(areaName);
                String districtId = code.substring(0, 5);
                String areaId = code.substring(5);

                property.setDistrictId(Long.valueOf(districtId));
                property.setAreaId(Long.valueOf(areaId));

                propertyDataSaverRepository.save(property);
            }
        }
    }

//    @Transactional
//    public void saveAddress(){
////        List<Property> properties = propertyDataSaverRepository.findAll();
//
//        ReverseGeocoding reverseGeocoding = new ReverseGeocoding();
//        for(Property property: properties) {
//            Map<String, String> addressMap = reverseGeocoding.getAddress(property.getLatitude(), property.getLongitude());
//
//            String roadAddress = addressMap.get("roadAddress");
//            String jibeonAddress = addressMap.get("jibeonAddress");
//
//            if (roadAddress != null) {
//                property.setRoadAddress(roadAddress);
//            }
//
//            if (jibeonAddress != null) {
//                property.setJibeonAddress(jibeonAddress);
//            }
//
//            propertyDataSaverRepository.save(property);
//        }
//    }

    @Transactional
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
                    property.setDirection(getStringValue(json, "direction"));

                    Object tagListObj = json.get("tagList");
                    JSONArray tagList;
                    if (tagListObj instanceof JSONArray) {
                        tagList = (JSONArray) tagListObj;
                    } else {
                        tagList = new JSONArray();
                    }
                    StringBuilder tagsBuilder = new StringBuilder();
                    if (tagList != null) {
                        for (int i = 0; i < tagList.size(); i++) {
                            tagsBuilder.append(tagList.get(i));
                            if (i < tagList.size() - 1) {
                                tagsBuilder.append(",");
                            }
                        }
                    }
                    String tags = tagsBuilder.toString();
                    property.setTags(tags);
                    properties.add(property);
                }
            }
        }
        saveProperties(properties);
    }

    public String getStringValue(JSONObject json, String key) {
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

    public void saveProperties(List<Property> properties) {
        System.out.println(properties.get(0));
        for (Property property : properties) {
            try {
                propertyDataSaverRepository.save(property);
            } catch (DataIntegrityViolationException e) {
                continue;
            }
        }
    }
}
