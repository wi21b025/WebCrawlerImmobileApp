package crawler;

import crawler.notification.EmailSender;
import crawler.util.WillhabenLinks;
import jakarta.annotation.PostConstruct;
import model.Immobile;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import service.UserService;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ScraperStarter {
    private final DataScraper dataScraper;
    private final DataProcessor dataProcessor;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(ScraperStarter.class);

    private final Map<String, Long> lastProcessedUrls = new ConcurrentHashMap<>();

    public ScraperStarter(DataScraper dataScraper, DataProcessor dataProcessor, UserService userService) {
        this.dataScraper = dataScraper;
        this.dataProcessor = dataProcessor;
        this.userService = userService;
    }

    @PostConstruct
    public void startScraping() {
        scrapeDataForAllUsers();
    }

    private void scrapeDataForAllUsers() {
        List<User> users = userService.getAllUsers();

        try {
            for (User user : users) {
                for (Map<String, Object> filter : user.getFilters()) {
                    processUserFilter(user, filter);
                }
            }
        } catch (Exception e) {
            logger.error("Error during scraping process: " + e.getMessage());
        }
    }

    private void processUserFilter(User user, Map<String, Object> filter) {
        String userSpecificURL = WillhabenLinks.getUrlForFilter(filter);
        long currentTime = System.currentTimeMillis();
        long lastProcessedTime = lastProcessedUrls.getOrDefault(userSpecificURL, 0L);

        if (currentTime - lastProcessedTime >= 300000) { // 300,000 ms = 5 minutes
            try {
                String filePath = dataScraper.scrapeData(userSpecificURL);
                String fileContent = readFileContent(filePath);

                logger.warn("Scraped Data: " + fileContent);
                List<Immobile> newImmobiles = dataProcessor.processData(fileContent, filter);

                if (!newImmobiles.isEmpty()) {
                    EmailSender.sendEmail(user.getEmail(), "Neue Immobilienangebote", newImmobiles);
                    logger.info("Email sent to: " + user.getEmail() + " for filter: " + filter);
                }

                lastProcessedUrls.put(userSpecificURL, currentTime);
            } catch (Exception e) {
                logger.error("Error during scraping process for filter " + filter + " and user " + user.getEmail(), e);
            }
        } else {
            logger.info("URL recently processed, skipping: " + userSpecificURL);
        }
    }

    private String readFileContent(String filePath) throws IOException {
        StringBuilder fileContentBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(Paths.get(filePath).toFile()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                fileContentBuilder.append(line).append("\n");
            }
        }
        return fileContentBuilder.toString();
    }

    @Scheduled(fixedDelay = 300000) // 300,000 milliseconds = 5 minutes
    public void restartScraping() {
        scrapeDataForAllUsers();
    }
}