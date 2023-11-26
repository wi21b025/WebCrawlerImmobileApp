package scraper;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class DataScraper
{
    private static final Logger logger = LoggerFactory.getLogger(DataScraper.class);

    private WebDriver driver;

    public DataScraper()
    {
        // Set up the WebDriver (Firefox in this example)
        WebDriverManager.firefoxdriver().setup(); // Automatically download and manage geckodriver

        // Uncomment below lines to use headless Firefox
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        this.driver = new FirefoxDriver(options);


        // Comment below line to use headless Firefox
        //driver = new FirefoxDriver();
    }

    public String scrapeData()
    {
        StringBuilder concatenatedContent = new StringBuilder();

        try
        {

            // Navigate to the URL
            logger.info("Navigating to the URL");
            driver.get("https://www.willhaben.at/iad/immobilien/ferienimmobilien-mieten/ferienimmobilien-angebote?sfId=d936001c-1c1a-4cd1-b7e1-1fc29703078a&isNavigation=true&page=1&rows=90");
            closePopup(); // Add this line to close popups

            while (true)
            {
                // Extract the content from the current page and append it to the concatenatedContent StringBuilder
                String pageContent = savePageContent();
                concatenatedContent.append(pageContent);
                logger.info("Content from current page appended");

                // Navigate to the next page
                if (!navigateToNextPage())
                {
                    logger.info("Reached the last page");
                    break;
                }
            }
        }
        finally
        {
            // Quit the WebDriver when done
           /* logger.info("Quitting the WebDriver");
            if (driver != null) {
                driver.quit();
                this.driver = null;
            }*/
        }

        return concatenatedContent.toString();
    }


    private void closePopup()
    {
        try
        {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("didomi-notice-agree-button"))).click();
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("didomi-notice-agree-button")));
            logger.info("Popup closed");
        }
        catch (Exception e)
        {
            logger.error("Popup handling error: " + e.getMessage());
        }
    }

    private String savePageContent()
    {
        WebElement divContent = driver.findElement(By.id("skip-to-resultlist"));
        return divContent.getAttribute("outerHTML");
    }
    private boolean navigateToNextPage()
    {
        WebElement weiterButton = driver.findElement(By.xpath("//a[@data-testid='pagination-top-next-button']"));
        weiterButton.click();
        logger.info("Navigated to the next page");
        if (weiterButton.getAttribute("aria-disabled").equals("true"))
        {
            logger.info("Next page button disabled");
            return false;
        }

        return true;
    }



    /*private boolean navigateToNextPage() {
        WebElement weiterButton = driver.findElement(By.xpath("//a[@data-testid='pagination-top-next-button']"));

        if (weiterButton.getAttribute("aria-disabled").equals("true")) {
            logger.info("Next page button disabled");
            return false;
        }

        weiterButton.click();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            logger.error("Error while waiting: " + e.getMessage());
        }

        logger.info("Navigated to the next page");
        return true;
    }

     */
}
