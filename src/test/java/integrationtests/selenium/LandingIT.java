package integrationtests.selenium;

import org.junit.Before;
import org.junit.Test;
import integrationtests.testhelpers.LandingPage;
import com.vdtas.helpers.Routes;
import integrationtests.testhelpers.SeleniumTest;

/**
 * @author vvandertas
 */
public class LandingIT extends SeleniumTest{
    private LandingPage landingPage;
    @Before
    public void setup() {
        landingPage = new LandingPage(driver);
    }

    @Test
    public void landingPage() throws Exception {
        // Load landing page
        landingPage.open();
        landingPage.shouldBeOnPage(Routes.LANDING);

        // Make sure the instructions are there
        landingPage.validateInstructions();

        // and click the start button
        landingPage.clickStartButton();
        landingPage.shouldBeOnPage(Routes.EXPERIMENT);
    }
}
