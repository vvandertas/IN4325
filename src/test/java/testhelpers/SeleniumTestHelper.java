package testhelpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static testhelpers.IntegrationTest.HOST;

/**
 * @author vvandertas
 */
public class SeleniumTestHelper {

    public WebDriver driver;

    public SeleniumTestHelper(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * @return
     * @see <a href="https://stackoverflow.com/a/33349203/52057"/>
     */
    public boolean waitForAjax() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        Function<WebDriver, Boolean> jQueryLoad = driver -> {
            try {
                return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
            } catch(Exception e) {
                // no jQuery present
                return true;
            }
        };

        // wait for Javascript to load
        Function<WebDriver, Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState")
                .toString().equals("complete");

        return wait.until(jQueryLoad) && wait.until(jsLoad);
    }

    /**
     * @return true on success, exception on failure
     */
    public boolean waitForPageChange(final String newUrl) {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        return wait.until((driver) -> driver.getCurrentUrl().equals(newUrl));
    }

    /**
     * Retrieves the provided path
     *
     * @param path
     */
    public void get(String path) {
        driver.get(HOST + path);
        shouldBeOnPage(path);
    }

    /**
     * Retrieves the specified helper while not verifying for errors.
     *
     * @param path
     */
    public void getWithError(String path) {
        driver.get(HOST + path);
    }

    /**
     * Verifies the driver is on the specified helper without an error
     *
     * @param path path
     */
    public void shouldBeOnPage(String path) {
        assertEquals(HOST + path, driver.getCurrentUrl());
        assertFalse(driver.getTitle().contains("Error: "));
    }
}