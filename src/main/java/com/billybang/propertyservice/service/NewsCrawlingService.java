package com.billybang.propertyservice.service;
import com.billybang.propertyservice.model.news.CrawlingRegion;
import com.billybang.propertyservice.model.news.DistrictNews;
import com.billybang.propertyservice.model.news.News;
import com.billybang.propertyservice.repository.DistrictNewsRepository;
import com.billybang.propertyservice.repository.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
@Transactional
public class NewsCrawlingService {
    private List<CrawlingRegion> regionList;
    private final NewsRepository newsRepository;
    private final DistrictNewsRepository districtNewsRepository;

    @Scheduled(cron = "0 0 0 * * ?")
    public void crawlNewsData() {
        if(regionList.isEmpty()) {
            initialRegoinList();
        }

        LocalDate yesterday = LocalDate.now().minusDays(1);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

        for(CrawlingRegion region: regionList) {
            for(int j=1; j<=5; j++) {
                String url = String.format("https://land.naver.com/news/region.naver?city_no=1100000000&dvsn_no=%s&page=%d", region.getCortarNo(), j);

                try {
                    Document doc = Jsoup.connect(url).get();

                    for (int i = 1; i <= 20; i++) {
                        String imageUrl = "";
                        String headlineText = "";
                        String headlineUrl = "";
                        String newsContent = "";
                        String company = "";
                        String date = "";

                        String cssQuery = String.format("#content > div.section_headline > ul > li:nth-child(%d) > dl > dt.photo > a > img", i);
                        Elements imgUrls = doc.select(cssQuery);
                        for (Element imgUrl : imgUrls) {
                            imageUrl = imgUrl.absUrl("src");
                        }

                        String cssQuery2 = String.format("#content > div.section_headline > ul > li:nth-child(%d) > dl > dt:nth-child(2) > a", i);
                        Elements headlines = doc.select(cssQuery2);

                        for (Element headline : headlines) {
                            headlineText = headline.text();
                            headlineUrl = headline.absUrl("href");
                        }

                        String cssQuery3 = String.format("#content > div.section_headline > ul > li:nth-child(%d) > dl > dd", i);
                        Elements contents = doc.select(cssQuery3);

                        for (Element content : contents) {
                            newsContent = content.ownText();
                            company = content.select("span.writing").text();
                            date = content.select("span.date").text();
                        }

                        if(date.equals(yesterday.format(formatter)) && !headlineUrl.equals("")) {
//                        if(!headlineUrl.equals("")) {
                            News savedNews = newsRepository.save(new News(headlineText, imageUrl, headlineUrl, newsContent, company, date));
                            districtNewsRepository.save(new DistrictNews(region.getDistrictId(), savedNews.getId()));
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void initialRegoinList(){
        regionList.add(new CrawlingRegion("1111000000", "종로구", 1L));
        regionList.add(new CrawlingRegion("1114000000", "중구", 2L));
        regionList.add(new CrawlingRegion("1117000000", "용산구", 3L));
        regionList.add(new CrawlingRegion("1120000000", "성동구", 4L));
        regionList.add(new CrawlingRegion("1121500000", "광진구", 5L));
        regionList.add(new CrawlingRegion("1123000000", "동대문구", 6L));
        regionList.add(new CrawlingRegion("1126000000", "중랑구", 7L));
        regionList.add(new CrawlingRegion("1129000000", "성북구", 8L));
        regionList.add(new CrawlingRegion("1130500000", "강북구", 9L));
        regionList.add(new CrawlingRegion("1132000000", "도봉구", 10L));
        regionList.add(new CrawlingRegion("1135000000", "노원구", 11L));
        regionList.add(new CrawlingRegion("1138000000", "은평구", 12L));
        regionList.add(new CrawlingRegion("1141000000", "서대문구", 13L));
        regionList.add(new CrawlingRegion("1144000000", "마포구", 14L));
        regionList.add(new CrawlingRegion("1147000000", "양천구", 15L));
        regionList.add(new CrawlingRegion("1150000000", "강서구", 16L));
        regionList.add(new CrawlingRegion("1153000000", "구로구", 17L));
        regionList.add(new CrawlingRegion("1154500000", "금천구", 18L));
        regionList.add(new CrawlingRegion("1156000000", "영등포구", 19L));
        regionList.add(new CrawlingRegion("1159000000", "동작구", 20L));
        regionList.add(new CrawlingRegion("1162000000", "관악구", 21L));
        regionList.add(new CrawlingRegion("1165000000", "서초구", 22L));
        regionList.add(new CrawlingRegion("1168000000", "강남구", 23L));
        regionList.add(new CrawlingRegion("1171000000", "송파구", 24L));
        regionList.add(new CrawlingRegion("1174000000", "강동구", 25L));
    }
}
