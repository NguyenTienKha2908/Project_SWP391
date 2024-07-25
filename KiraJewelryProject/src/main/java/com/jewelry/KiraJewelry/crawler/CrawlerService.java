package com.jewelry.KiraJewelry.crawler;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jewelry.KiraJewelry.models.Material;
import com.jewelry.KiraJewelry.models.MaterialPriceList;
import com.jewelry.KiraJewelry.service.MaterialPriceListService;
import com.jewelry.KiraJewelry.service.MaterialService;

import jakarta.annotation.PostConstruct;

@Service
public class CrawlerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CrawlerService.class);

    @Autowired
    private CrawlerRepository CrawlerRepository;

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialPriceListService materialPriceListService;

    @PostConstruct // This method will be called after dependency injection
    public void initialCrawl() {
        crawlAndSaveData();
    }

    @Scheduled(fixedRate = 1000*60*60) // Run every hour
    public void crawlAndSaveData() {
        String url = "https://www.pnj.com.vn/blog/gia-vang/";

        try {
            Document document = Jsoup.connect(url).get();
            Elements goldPrices = document.select("#content-price tr");

            System.out.println("==========================");
            System.out.println("Gold Price - All Gold Price");
            System.out.println("==========================");

            int mId = 1;
            for (Element row : goldPrices) {
                String title = row.select("td").first().text();
                Elements prices = row.select("span");

                if (prices.size() >= 2) {
                    String priceString = prices.get(1).text();
                    String priceWithoutComma = priceString.replace(",", ""); // Remove comma before parsing
                    double price = Double.parseDouble(priceWithoutComma); // VND Million
                    DecimalFormat df = new DecimalFormat("#.00");
                    double USPrice = (price * 1000) / 25455;
                    String formattedUSPrice = df.format(USPrice);

                    MaterialPriceList materialPriceList = materialPriceListService.getLatestPriceByMaterialId(mId);
                    if (materialPriceList != null) {
                        materialPriceList.setPrice(Double.parseDouble(formattedUSPrice));
                        materialPriceList.setEff_Date(LocalDateTime.now());
                        materialPriceListService.saveMaterialPriceList(materialPriceList);
                    } else {
                        MaterialPriceList newMaterialPriceList = new MaterialPriceList();
                        Material material = materialService.getMaterialById(mId);
                        newMaterialPriceList.setPrice(Double.parseDouble(formattedUSPrice));
                        newMaterialPriceList.setMaterial(material);
                        newMaterialPriceList.setEff_Date(LocalDateTime.now());
                        materialPriceListService.saveMaterialPriceList(newMaterialPriceList);
                    }
                    
                    
                    mId++;
                    System.out.println("Title: " + title + " - Second Price: " + price);
                } else {
                    System.out.println("Title: " + title + " - Not enough prices available");
                }
            }
        } catch (IOException ex) {
            LOGGER.error("Error fetching data from website:", ex);
        }
    }
}