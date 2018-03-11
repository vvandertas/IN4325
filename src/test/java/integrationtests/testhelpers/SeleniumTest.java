package integrationtests.testhelpers;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * @author vvandertas
 */
public class SeleniumTest extends IntegrationTest{
    /**
     * Set to true to indicate Chrome will be run in headless mode.
     */
    private static final boolean HEADLESS = false;
    protected static WebDriver driver = null;
    protected static BasePage page;

    @BeforeClass
    public static void seleniumSetUp() {
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setHeadless(HEADLESS);

        // check if this happens to be a Mac with Chrome in the default location
        if(new File("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome").exists()) {
            chromeOptions.setBinary("/Applications/Google Chrome.app/Contents/MacOS/Google Chrome");
        }
        if(!HEADLESS) {
            chromeOptions.addArguments("auto-open-devtools-for-tabs");
        }
        driver = new ChromeDriver(chromeOptions);
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);
        page = new BasePage(driver);
    }

    @AfterClass
    public static void seleniumShutdown() {
        // comment to leave Chrome windows open
        if(driver != null) {
//            driver.quit();
        }
    }
}
