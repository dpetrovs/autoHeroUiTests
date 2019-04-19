package com.autohero.bdd.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class WebDriverConfiguration {
    private WebDriver driver;
    private final String APP_URL = getAppUrl();

    protected WebDriverWait waiter(WebDriver webDriver) {
        return new WebDriverWait(webDriver, 5);
    }

    protected String getAppUrl(){
        return getProperties().getProperty("selenium.base.url");
    }

    protected WebDriver getDriver() {
        return driver;
    }

    protected void webDriverSelection() {
        switch (getProperties().getProperty("sys.selenium.browser")){
            case "firefox" :
                setFireFoxPath();
                driver = new FirefoxDriver();
                break;
            case "chrome" :
                setChromePath();
                driver = new ChromeDriver();
                break;
            case "edge" :
                setEdgePath();
                driver = new EdgeDriver();
                break;
            default :
                setChromePath();
                driver = new ChromeDriver();
        }
    }

    private void setChromePath() {
            System.setProperty("webdriver.chrome.driver", chromePath());
    }

    private void setFireFoxPath() {
            System.setProperty("webdriver.gecko.driver", getClass().getResource("/drivers/firefox/").getPath() + "geckodriver.exe");
    }

    private void setEdgePath() {
        System.setProperty("webdriver.edge.driver", getClass().getResource("/drivers/edge/").getPath() + "MicrosoftWebDriver.exe");
    }

    private String chromePath() {
        String folder = getClass().getResource("/drivers/chrome/").getPath();
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            return folder + "chromedriver_win";
        } else if (os.contains("mac")) {
            return folder + "chromedriver_mac";
        } else {
            return folder + "chromedriver_linux";
        }
    }

    protected void driverBaseSteps(WebDriver driver) {
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(3000, TimeUnit.MILLISECONDS);
        driver.get(APP_URL);
    }

    protected Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
