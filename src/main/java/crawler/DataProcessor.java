package crawler;

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
import java.util.Map;

@Component
public class DataProcessor
{
    private static final Logger logger = LoggerFactory.getLogger(DataProcessor.class);
    private final ImmobileService immobileService;
    private final DataScraper dataScraper;

    public DataProcessor(ImmobileService immobileService, DataScraper dataScraper)
    {
        this.immobileService = immobileService;
        this.dataScraper = dataScraper;
    }

    public List<Immobile> processData(String scrapedData, Map<String, Object> filter)
    {
        logger.info("Processing data...");

        List<Immobile> cleanedData = cleaningData(scrapedData);
        List<Immobile> newImmobiles = new ArrayList<>();

        String preisSq2Von = (String) filter.get("preis_sq_2_von");
        String preisSq2Bis = (String) filter.get("preis_sq_2_bis");

        int minPricePerSqM = -1;
        int maxPricePerSqM = -1;

        try {
            if (preisSq2Von != null && !preisSq2Von.isEmpty()) {
                minPricePerSqM = Integer.parseInt(preisSq2Von.replaceAll("[^\\d]", ""));
            }
            if (preisSq2Bis != null && !preisSq2Bis.isEmpty()) {
                maxPricePerSqM = Integer.parseInt(preisSq2Bis.replaceAll("[^\\d]", ""));
            }
        } catch (NumberFormatException e) {
            logger.error("Number format exception in parsing filter values", e);
        }

        // Process each Immobile object
        for (Immobile immobile : cleanedData) {
            int pricePerSqM = immobileService.calculatePricePerSqMeter(immobile.getPrice(), immobile.getSize());
            immobile.setPreisProSqMeter(String.valueOf(pricePerSqM));
            // Skip if pricePerSqM is -1 (indicating an error or division by zero)
            if (pricePerSqM == -1) {
                logger.info("Invalid price per square meter, skipping immobile: " + immobile.getTitle());
                continue;
            }

            boolean includeImmobile = true;

            // Check against the minimum and maximum price per square meter, if specified
            if ((minPricePerSqM != -1 && pricePerSqM < minPricePerSqM) ||
                    (maxPricePerSqM != -1 && pricePerSqM > maxPricePerSqM)) {
                includeImmobile = false;
                logger.info("Price per square meter for " + immobile.getTitle() + " is outside the specified range.");
            }

            if (includeImmobile) {
                boolean isNew = immobileService.saveImmobile(immobile);
                if (isNew) {
                    printImmobile(immobile);
                    newImmobiles.add(immobile);
                }
            }
        }

        logger.info("Data processing completed. Total new immobiles added: " + newImmobiles.size());
        return newImmobiles;
    }


    private List<Immobile> cleaningData(String rawData)
    {
        logger.info("Cleaning data...");
        List<Immobile> cleanedData = new ArrayList<>();

        // Parse the raw HTML data using Jsoup
        Document doc = Jsoup.parse(rawData);
        Elements listings = doc.select("a[data-testid^='search-result-entry-header-']");

        for (Element listing : listings)
        {
            Immobile immobile = new Immobile();

            // Extracting title
            Element titleElement = listing.selectFirst("h3.Text-sc-10o2fdq-0");
            immobile.setTitle(titleElement != null ? titleElement.text() : "");

            // Extracting price
            Element priceElement = listing.selectFirst("span[data-testid^='search-result-entry-price']");
            if (priceElement != null)
            {
                String fullPriceText = priceElement.text();
                //String price = fullPriceText.contains("€") ? fullPriceText.substring(fullPriceText.indexOf("€")).trim() : "";
                String price = fullPriceText.replaceAll("[^\\d,.]", "").trim(); // Remove all non-numeric characters except commas and periods

                immobile.setPrice(price);
            }
            else
            {
                immobile.setPrice("");
            }

            // Extracting address
            Element addressElement = listing.selectFirst("span.Text-sc-10o2fdq-0.bFMMYK");
            immobile.setAddress(addressElement != null ? addressElement.text() : "");

            // Extracting view link
            Element viewLinkElement = listing.selectFirst("a[href]");
            if (viewLinkElement != null)
            {
                String viewLinkHref = viewLinkElement.attr("href");
                immobile.setViewLink("https://www.willhaben.at" + viewLinkHref); // Prepend base URL
            }
            else
            {
                immobile.setViewLink("");
            }

            // Extracting size and room
            Elements attributes = listing.select("div[data-testid^='search-result-entry-teaser-attributes-'] span.gTqzpY");

            for (Element attribute : attributes)
            {
                String text = attribute.parent().text();
                if (text.contains("m²"))
                {
                    immobile.setSize(attribute.text());
                }
                else if (text.toLowerCase().contains("zimmer"))
                {
                    immobile.setRoom(attribute.text());
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
    private void printImmobile(Immobile immobile)
    {
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