package scraper;

import jakarta.annotation.PostConstruct;
import model.Immobile;
import notification.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.List;

@Component
public class ScraperStarter
{
    private final DataScraper dataScraper;
    private final DataProcessor dataProcessor;
    private static final Logger logger = LoggerFactory.getLogger(ScraperStarter.class);

    public ScraperStarter(DataScraper dataScraper, DataProcessor dataProcessor) {
        this.dataScraper = dataScraper;
        this.dataProcessor = dataProcessor;
    }

    @PostConstruct
    //@Scheduled(fixedRate = 300000)
    @Scheduled(fixedDelay = 300000) // 300000 milliseconds = 5 minutes after the last task completed

    public void startScraping()
    {
        // Start the scraping process
        String scrapedData = dataScraper.scrapeData();
        logger.warn("Scraped Data: " + scrapedData);

        try {
            // Process the scraped data
            List<Immobile> newImmobiles = dataProcessor.processData(scrapedData);
            logger.info("Number of new immobiles: " + newImmobiles.size());

            // Send email notification if there are new listings
            if (!newImmobiles.isEmpty())
            {
                EmailSender.sendEmail("kevinxhunga1@gmail.com", "New Listings Available", newImmobiles);
                logger.info("Email notification sent.");
            }
        }
        catch (Exception e)
        {
            logger.error("Error during processing or email sending: " + e.getMessage());
            // Further exception handling logic if necessary
        }
    }
}