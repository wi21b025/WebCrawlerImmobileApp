package scraper;

import model.Immobile;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import service.ImmobileService;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataProcessor {
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    private final ImmobileService immobileService;
    private final DataScraper dataScraper;

    public DataProcessor(ImmobileService immobileService, DataScraper dataScraper) {
        this.immobileService = immobileService;
        this.dataScraper = dataScraper;
    }

    /*public void init() {
        String scrapedData = dataScraper.scrapeData(); // Scraping data
        processData(scrapedData); // Processing data
    }*/


    //  AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(ImmobileService.class);
    // ImmobileService immobileService = context.getBean(ImmobileService.class);

    public List<Immobile> processData(String scrapedData) {
        logger.info("Processing data...");

        List<Immobile> cleanedData = cleaningData(scrapedData);
        List<Immobile> newImmobiles = new ArrayList<>();

        // Print and save each Immobile object
        for (Immobile immobile : cleanedData) {
            printImmobile(immobile);
            boolean isNew = immobileService.saveImmobile(immobile);
            if (isNew) {
                newImmobiles.add(immobile);
            }
        }

        logger.info("Data processing completed.");
        return newImmobiles;
    }

    private List<Immobile> cleaningData(String rawData) {
        logger.info("Cleaning data...");
        List<Immobile> cleanedData = new ArrayList<>();

        // Parse the raw HTML data using Jsoup
        Document doc = Jsoup.parse(rawData);
        Elements listings = doc.select("a[data-testid^='search-result-entry-header-']");
        logger.info("Number of listings found: " + listings.size());

        for (Element listing : listings) {
            Immobile immobile = new Immobile();

            // Extracting title
            Element titleElement = listing.selectFirst("h3.iPrWBD");
            immobile.setTitle(titleElement != null ? titleElement.text() : "");

            // Extracting price
            Element priceElement = listing.selectFirst("span[data-testid^='search-result-entry-price']");
            if (priceElement != null) {
                String fullPriceText = priceElement.text();
                String price = fullPriceText.contains("€") ? fullPriceText.substring(fullPriceText.indexOf("€")).trim() : "";
                immobile.setPrice(price);
            } else {
                immobile.setPrice("");
            }

            // Extracting address
            Element addressElement = listing.selectFirst("span.kmXElp");
            immobile.setAddress(addressElement != null ? addressElement.text() : "");

            // Extracting view link
            Element viewLinkElement = listing.selectFirst("a[href]");
            if (viewLinkElement != null) {
                String viewLinkHref = viewLinkElement.attr("href");
                immobile.setViewLink("https://www.willhaben.at" + viewLinkHref); // Prepend base URL
            } else {
                immobile.setViewLink("");
            }

            // Extracting size and room
           Elements attributes = listing.select("div[data-testid^='search-result-entry-teaser-attributes-'] span.jWysWP");
            for (Element attribute : attributes) {
                String text = attribute.parent().text();
                if (text.contains("m²")) {
                    immobile.setSize(attribute.text() + " m²");
                } else if (text.toLowerCase().contains("zimmer")) {
                    immobile.setRoom(attribute.text() + " Zimmer");
                }
            }


            // Extracting image URL
            Element imageElement = listing.selectFirst("img[src]");
            immobile.setImageUrl(imageElement != null ? imageElement.attr("src") : "");

            cleanedData.add(immobile);
        }

        logger.info("Data cleaning completed. Total listings processed: " + cleanedData.size());
        return cleanedData;
    }

    // Helper method to print an Immobile object
    private void printImmobile(Immobile immobile) {
        logger.info("Title: " + immobile.getTitle());
        logger.info("Price: " + immobile.getPrice());
        logger.info("Address: " + immobile.getAddress());
        logger.info("Size: " + immobile.getSize());
        logger.info("Image URL: " + immobile.getImageUrl());
        logger.info("Rooms: " + immobile.getRoom());
        logger.info("PreisProSqMeter: " + immobile.getPreisProSqMeter());
        logger.info("viewLink: " + immobile.getViewLink());
        logger.info("");
    }
}
