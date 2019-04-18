package com.autohero.bdd.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class Paging {
    private static final String ACTIVE_PAGE_XPATH = "//ul[@class='pagination']/li[contains(@class, 'active')]/a";
    private static final String BY_NUMBER_PAGE_XPATH = "//ul[@class='pagination']/li/a[text()=':pageNumber']";
    private static final String NEXT_PAGE_ITEM_XPATH = "//ul[@class='pagination']/li/a/span[@aria-label='Next']";
    private static final String PREVIOUS_PAGE_ITEM_XPATH = "//ul[@class='pagination']/li/a/span[@aria-label='Previous']";

    private WebElement webElement;

    public Paging(WebElement webElement) {
        this.webElement = webElement;
    }

    public int getCurrentPageNumber() {
        return Integer.parseInt(webElement.findElement(By.xpath(ACTIVE_PAGE_XPATH)).getAttribute("text"));
    }

    public boolean isPageAvailable(int number) {
        return !webElement.findElements(By.xpath(BY_NUMBER_PAGE_XPATH.replace(":pageNumber", Integer.toString(number)))).isEmpty();
    }

    public void goToNextPage() {
        webElement.findElement(By.xpath(NEXT_PAGE_ITEM_XPATH)).click();
    }

    public void goToPreviousPage() {
        webElement.findElement(By.xpath(PREVIOUS_PAGE_ITEM_XPATH)).click();
    }

    public void goToPage(int pageNumber) {
        webElement.findElement(By.xpath(BY_NUMBER_PAGE_XPATH.replace(":pageNumber", Integer.toString(pageNumber)))).click();
    }
}
