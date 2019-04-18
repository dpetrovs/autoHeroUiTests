package com.autohero.bdd.configuration;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public abstract class BasePage {

    public static WebDriver driver;
    public WebDriverWait getWaiter(WebDriver webDriver) {
        return new WebDriverWait(webDriver, 5);
    }
    public void waitLoadingBanner() {
        getWaiter(driver).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-qa-selector='loading-banner'][@class='root___31FXK']")));
    }
    public void waitPageLoaded(){}
}
