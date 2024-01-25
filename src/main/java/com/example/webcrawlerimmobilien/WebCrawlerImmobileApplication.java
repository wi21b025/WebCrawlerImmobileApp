package com.example.webcrawlerimmobilien;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"crawler","web.controller","service","config.web"})

public class WebCrawlerImmobileApplication
{
    public static void main(String[] args)
    {
       SpringApplication.run(WebCrawlerImmobileApplication.class, args);
    }
}
