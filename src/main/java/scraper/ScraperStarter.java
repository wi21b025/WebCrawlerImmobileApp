package scraper;

import jakarta.annotation.PostConstruct;
import model.Immobile;
import notification.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.UserService;

import java.util.List;

@Component
public class ScraperStarter
{
    private final DataScraper dataScraper;
    private final DataProcessor dataProcessor;
    private final UserService userService;

    private static final Logger logger = LoggerFactory.getLogger(ScraperStarter.class);

    public ScraperStarter(DataScraper dataScraper, DataProcessor dataProcessor, UserService userService) {
        this.dataScraper = dataScraper;
        this.dataProcessor = dataProcessor;
        this.userService = userService; // Initialize UserService
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
                List<String> userEmails = userService.getAllUserEmails(); // Fetch user emails
                logger.info("Number of user emails: " + userEmails.size());
                for (String email : userEmails) {
                    EmailSender.sendEmail(email, "Neue Immobilienangebote", newImmobiles);
                    logger.info("Email notification sent to: " + email);
                }                logger.info("Email notification sent.");
            }
        }
        catch (Exception e)
        {
            logger.error("Error during processing or email sending: " + e.getMessage());
        }
    }
}