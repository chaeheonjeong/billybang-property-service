package com.billybang.propertyservice;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.json.JSONArray;
import org.json.JSONObject;


public class ReverseGeocoding {
    private static String clientId;
    private static String clientSecret;

    public Map<String, String> getAddress(double latitude, double longitude) {
        Map<String, String> addressMap = new HashMap<>();

        try {
            String API_URL = String.format("https://naveropenapi.apigw.ntruss.com/map-reversegeocode/v2/gc?coords=%f,%f&output=json&orders=roadaddr,addr", longitude, latitude);

            Properties properties = new Properties();
            properties.load(new FileInputStream("config.properties"));
            clientId = properties.getProperty("CLIENT_ID");
            clientSecret = properties.getProperty("CLIENT_SECRET");

            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("GET");
            conn.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            conn.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // JSON 파싱
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray results = jsonResponse.getJSONArray("results");

            String roadAddress = null;
            String jibeonAddress = null;

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String name = result.getString("name");

                String area1 = result.getJSONObject("region").getJSONObject("area1").getString("name");
                String area2 = result.getJSONObject("region").getJSONObject("area2").getString("name");
                String area3 = result.getJSONObject("region").getJSONObject("area3").getString("name");

                if (name.equals("roadaddr")) {
                    String roadName = result.getJSONObject("land").getString("name");
                    String number1 = result.getJSONObject("land").getString("number1");
                    String number2 = result.getJSONObject("land").optString("number2", "");

                    if (!number2.isEmpty()) {
                        roadAddress = area1 + " " + area2 + " " + area3 + " " + roadName + " " + number1 + "-" + number2;
                    } else {
                        roadAddress = area1 + " " + area2 + " " + area3 + " " + roadName + " " + number1;
                    }
                } else if (name.equals("addr")) {
                    String number1 = result.getJSONObject("land").getString("number1");
                    String number2 = result.getJSONObject("land").optString("number2", "");

                    if (!number2.isEmpty()) {
                        jibeonAddress = area1 + " " + area2 + " " + area3 + " " + number1 + "-" + number2;
                    } else {
                        jibeonAddress = area1 + " " + area2 + " " + area3 + " " + number1;
                    }
                }
            }

            addressMap.put("roadAddress", roadAddress);
            addressMap.put("jibeonAddress", jibeonAddress);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return addressMap;
    }
}
