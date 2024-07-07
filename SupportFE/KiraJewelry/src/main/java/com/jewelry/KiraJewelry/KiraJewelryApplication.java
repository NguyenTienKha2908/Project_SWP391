package com.jewelry.KiraJewelry;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.jewelry.KiraJewelry.crawler.CrawlerService;

@SpringBootApplication
public class KiraJewelryApplication {

	@Autowired
    private CrawlerService crawlerService; // This will be injected by Spring Boot


	public static void main(String[] args) {

		

		SpringApplication.run(KiraJewelryApplication.class, args);
		// crawlerService.crawlAndSaveData();
	}

}
