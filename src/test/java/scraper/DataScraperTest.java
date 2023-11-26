package scraper;

import org.springframework.boot.test.context.SpringBootTest;
import org.testng.annotations.Test;

@SpringBootTest
public class DataScraperTest {

    @Test
    public void testScrapeData() {
        DataScraper dataScraper = new DataScraper();
        String scrapedData = dataScraper.scrapeData();

        // Add assertions or print the scraped data for testing purposes
        System.out.println(scrapedData);
    }
}
