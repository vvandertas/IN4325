package integrationtests.testhelpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static integrationtests.testhelpers.IntegrationTest.HOST;

/**
 * @author vvandertas
 */
public class BasePage {

    public WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    /**
     * @return
     * @see <a href="https://stackoverflow.com/a/33349203/52057"/>
     */
    protected boolean waitForAjax() {
        WebDriverWait wait = new WebDriverWait(driver, 30);

        // wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            try {
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState === \"complete\" && (jQuery === 'undefined' || jQuery.active === 0)");
            } catch(Exception e) {
                // no jQuery present
                return true;
            }
        };
        return wait.until(jQueryLoad);
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
     * Retrieves the specified page while not verifying for errors.
     *
     * @param path
     */
    public void getWithError(String path) {
        driver.get(HOST + path);
    }

    /**
     * Verifies the driver is on the specified page without an error
     *
     * @param path path
     */
    public void shouldBeOnPage(String path) {
        assertEquals(HOST + path, driver.getCurrentUrl());
        assertFalse(driver.getTitle().contains("Error: "));
    }
}