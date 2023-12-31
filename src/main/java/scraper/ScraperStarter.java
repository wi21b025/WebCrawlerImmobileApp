package scraper;

import jakarta.annotation.PostConstruct;
import model.Immobile;
import model.User;
import notification.EmailSender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import scraper.src.WillhabenLinks;
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
        this.userService = userService;
    }

    @PostConstruct
    //@Scheduled(fixedRate = 300000)
    //@Scheduled(fixedDelay = 300000) // 300000 milliseconds = 5 minutes after the last task completed

    public void startScraping()
    {


        List<User> users = userService.getAllUsers();

        for (User user : users)
        {
            try {

                String userSpecificURL = WillhabenLinks.getUrlForUser(user);

                String scrapedData = dataScraper.scrapeData(userSpecificURL);

                logger.warn("Scraped Data: " + scrapedData);

                List<Immobile> newImmobiles = dataProcessor.processData(scrapedData);

                if (!newImmobiles.isEmpty())
                {
                    EmailSender.sendEmail(user.getEmail(), "Neue Immobilienangebote", newImmobiles);
                    logger.info("Email sent to: " + user.getEmail());
                }
            } catch (Exception e) {
                logger.error("Error during scraping process for " + user.getEmail(), e);
            }
        }
    }
}