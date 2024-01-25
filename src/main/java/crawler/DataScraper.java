package crawler;

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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;

@Component
public class DataScraper
{
    private static final Logger logger = LoggerFactory.getLogger(DataScraper.class);

    private WebDriver driver;
    private String userUrl;

    public DataScraper()
    {
        WebDriverManager.firefoxdriver().setup(); // Automatically download and manage geckodriver
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("--headless");
        this.driver = new FirefoxDriver(options);
    }

    public String scrapeData(String url) {
        this.userUrl = url;
        File file = new File("tmp.txt");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            driver.get(this.userUrl);
            logger.info("Navigating to URL: " + this.userUrl);

            closePopup();

            while (true) {
                String pageContent = savePageContent();
                writer.write(pageContent);
                writer.newLine();

                if (!navigateToNextPage()) {
                    logger.info("Reached the last page");
                    break;
                }
            }
        } catch (IOException e) {
            logger.error("Error writing to file", e);
            return null;
        } finally {
            /*if (driver != null) {
                driver.quit();
            }*/
        }

        return file.getAbsolutePath();
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
    private boolean navigateToNextPage() {
        try {
            // Attempt to find the "Next" button
            WebElement weiterButton = driver.findElement(By.xpath("//a[@data-testid='pagination-top-next-button']"));

            // Check if "Next" button is disabled indicating the last page
            String ariaDisabled = weiterButton.getAttribute("aria-disabled");
            if ("true".equals(ariaDisabled))
            {
                logger.info("Next page button is disabled, reached the last page.");
                return false;
            }

            // Click the button if not disabled
            weiterButton.click();
            logger.info("Navigated to the next page");
            return true;
        } catch (Exception e) {
            // Handle the situation where the "Next" button might not be present
            logger.error("Could not navigate to the next page. It might be the last page or the button is not present");
            return false;
        }
    }

}