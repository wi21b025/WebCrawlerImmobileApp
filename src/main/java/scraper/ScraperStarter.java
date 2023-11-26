package scraper;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ScraperStarter
{
    private final DataScraper dataScraper;
    private final DataProcessor dataProcessor;
    private static final Logger logger = LoggerFactory.getLogger(DataScraper.class);

    public ScraperStarter(DataScraper dataScraper, DataProcessor dataProcessor)
    {
        this.dataScraper = dataScraper;
        this.dataProcessor = dataProcessor;
    }

    @PostConstruct
    public void startScraping()
    {
        // Start the scraping process
        String scrapedData = dataScraper.scrapeData();

        logger.warn(scrapedData);
        //System.out.println(scrapedData);
        // Process the scraped data
        dataProcessor.processData(scrapedData);
    }
}
