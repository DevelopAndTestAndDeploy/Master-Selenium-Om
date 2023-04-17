package org.selenium.pom.factory;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.pom.constants.DriverType;

public class DriverManagerOriginal {

    public WebDriver initializeDriver(String browser) {
        WebDriver driver;
        //String localBrowser;
        //For mvn

        //For testng xml
        //localBrowser = browser;
        switch (DriverType.valueOf(browser)){
            case CHROME :
                WebDriverManager.chromedriver().cachePath("Drivers").setup();
                ChromeOptions co = new ChromeOptions();
                co.addArguments("--remote-allow-origins=*");
                driver = new ChromeDriver(co);
                break;

            case FIREFOX :
                WebDriverManager.firefoxdriver().cachePath("Drivers").setup();
                driver = new FirefoxDriver();
                break;


            default:
                throw new IllegalStateException("Invalid browser name " + browser);
        }

        driver.manage().window().maximize();

//        driver.manage().window().maximize();

        //driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        return driver;
    }




}
