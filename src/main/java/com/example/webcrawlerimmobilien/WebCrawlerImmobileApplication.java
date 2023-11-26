package com.example.webcrawlerimmobilien;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication

//@ComponentScan(basePackages = "notification")
@ComponentScan(basePackages = {"scraper", "service"})
//@ComponentScan(basePackages = {"notification", ""})

public class WebCrawlerImmobileApplication
{

    public static void main(String[] args) {


        SpringApplication.run(WebCrawlerImmobileApplication.class, args);
    }

}
