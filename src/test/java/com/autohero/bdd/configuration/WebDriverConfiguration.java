package com.autohero.bdd.configuration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
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
            case "ie" :
                setIEPath();
                InternetExplorerOptions options = new InternetExplorerOptions();
                options.ignoreZoomSettings();
                options.introduceFlakinessByIgnoringSecurityDomains();
                options.requireWindowFocus();
                InternetExplorerDriver ieDriver = new InternetExplorerDriver(options);
                ieDriver.navigate().to("javascript:document.getElementById('overridelink').click()");
                driver = ieDriver;
                break;
            default :
                setChromePath();
                driver = new ChromeDriver();
        }
    }

    private void setChromePath() {
            System.setProperty("webdriver.chrome.driver", chromePath());
    }

    private void setIEPath() {
            System.setProperty("webdriver.ie.driver", getClass().getResource("/drivers/ie/").getPath() + "ie.exe");
    }

    private void setFireFoxPath() {
            System.setProperty("webdriver.gecko.driver", getClass().getResource("/drivers/firefox/").getPath() + "firefoxdriver_win");
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

    private Properties getProperties() {
        Properties properties = new Properties();
        try (InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream("application.properties")) {
            properties.load(resourceAsStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return properties;
    }
}
