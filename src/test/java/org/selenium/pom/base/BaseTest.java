package org.selenium.pom.base;



import io.restassured.http.Cookies;
import org.apache.commons.io.FileUtils;
import org.apache.http.impl.io.IdentityOutputStream;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.selenium.pom.constants.DriverType;
import org.selenium.pom.factory.DriverManagerFactory;
import org.selenium.pom.factory.DriverManagerOriginal;
import org.selenium.pom.factory.abstractfactory.DriverManagerAbstract;
import org.selenium.pom.factory.abstractfactory.DriverManagerFactoryAbstract;
import org.selenium.pom.utils.CookieUtils;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class BaseTest {

    private final ThreadLocal<DriverManagerAbstract> driverManager = new ThreadLocal<>();
    private final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public void setDriver(WebDriver driver){
        this.driver.set(driver);
    }

    protected WebDriver getDriver() {
        return this.driver.get();
    }



    private void setDriverManager(DriverManagerAbstract driverManager){
        this.driverManager.set(driverManager);
    }

    protected DriverManagerAbstract getDriverManager() {

        return this.driverManager.get();
    }



    @Parameters("browser")
    @BeforeMethod
    public synchronized void startDriver(@Optional String browser) {
        browser = System.getProperty("browser", browser);
        //if (browser == null )browser = "CHROME";
        //setDriver(new DriverManagerOriginal().initializeDriver(browser));

        //setDriver(DriverManagerFactory.getManager(DriverType.valueOf(browser)).createDriver());
        setDriverManager(DriverManagerFactoryAbstract.getManager(DriverType.valueOf(browser)));

        setDriver(getDriverManager().getDriver());

        System.out.println("Current thread " + Thread.currentThread().getId() + " "
        + " Driver info = " + getDriver());


    }


    @Parameters("browser")

       @AfterMethod(alwaysRun = true)
       public synchronized void quitDriver(@Optional String browser, ITestResult result) throws InterruptedException, IOException {

        Thread.sleep(500);
        System.out.println("Current thread " + Thread.currentThread().getId() + " "
                + " Driver info = " + getDriver());
        //getDriver().quit();
       if (result.getStatus() == ITestResult.FAILURE){

           File destFile = new File("scr" + File.separator + browser + File.separator +
                   result.getTestClass().getRealClass().getSimpleName() + "_"
                   + result.getMethod().getMethodName() + ".png");
           //takeScreenShot(destFile);
           takeScreenShotUsingAshot(destFile);

       }
        getDriverManager().getDriver().quit();
    }

    public void injectCookiesToBrowser(Cookies cookies) {
        List<Cookie> seleniumCookies = new CookieUtils().covertRestAssuredCookiesToSeleniumCookies(cookies);
        for(Cookie cookie : seleniumCookies){
            getDriver().manage().addCookie(cookie);
        }
    }

    private void takeScreenShot(File destFile) throws IOException {
        TakesScreenshot takesScreenshot = (TakesScreenshot) getDriver();
        File srcFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, destFile);
    }

    private void takeScreenShotUsingAshot(File destFile){
        Screenshot screenshot = new AShot()
                .shootingStrategy(ShootingStrategies.viewportPasting(100))
                .takeScreenshot(getDriver());

        try{
            ImageIO.write(screenshot.getImage(), "PNG", destFile);
        } catch (Exception e){
            e.printStackTrace();
        }


    }
}
