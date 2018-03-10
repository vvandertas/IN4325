package integrationtests.testhelpers;

import com.vdtas.App;
import org.junit.AfterClass;
import org.junit.BeforeClass;

/**
 * @author vvandertas
 */
public abstract class IntegrationTest {
    public static final String HOST = "http://localhost:9090";
    protected static App app;

    @BeforeClass
    public static void appStartup() {
        app = new App();
        System.setProperty("application.env", "test");
        try {
            app.start("server.join=false");
        } catch(Exception e) {
            System.exit(1);
        }
    }

    @AfterClass
    public static void appShutdown() {
        app.stop();
    }
}
