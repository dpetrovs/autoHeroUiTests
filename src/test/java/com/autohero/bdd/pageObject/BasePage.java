package com.autohero.bdd.pageObject;
import com.autohero.bdd.configuration.WebDriverConfiguration;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;

public abstract class BasePage extends WebDriverConfiguration{

    public void waitLoadingBanner() {
        waiter(getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@data-qa-selector='loading-banner'][@class='root___31FXK']")));
    }
    public void waitPageLoaded(){}
}
