package com.autohero.bdd.pageObject;

import com.autohero.bdd.enums.PriceOrder;
import com.autohero.bdd.enums.SortOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class SearchPage extends BasePage {

    private final String APP_URL = getAppUrl();
    private static final String FILTER_XPATH = "//div[@data-qa-selector=':filterName']";
    private static final String SELECT_XPATH = "//select[@data-qa-selector='select'][@name=':selectItemName']";
    private static final String SORT_OPTION_XPATH = SELECT_XPATH.replace(":selectItemName", "sort") + "/option[@data-qa-selector-value=':value']";
    private static final String REGISTRATION_YEAR_XPATH = FILTER_XPATH.replace(":filterName", "filter-year");
    private static final String REGISTRATION_YEAR_DROPDOWN_XPATH = SELECT_XPATH.replace(":selectItemName", "yearRange.min");
    private static final String SORT_DROPDOWN_XPATH = SELECT_XPATH.replace(":selectItemName", "sort");
    private static final String SEARCH_RESULTS_TABLE_XPATH = "//div[@data-qa-selector='ad-items']";
    private static final String SEARCH_RESULTS_TABLE_PRICE_XPATH = SEARCH_RESULTS_TABLE_XPATH + "/descendant::div[@data-qa-selector='price']";
    private static final String SEARCH_RESULTS_TABLE_FIRST_REG_DATE_XPATH = SEARCH_RESULTS_TABLE_XPATH + "/descendant::ul[@data-qa-selector='spec-list']/li[:specElementNumber]";
    private List<Double> pricesList = new ArrayList<>();
    private List<Integer> firstRegistrationYearList = new ArrayList<>();

    public void launchBrowser() {
        webDriverSelection();
        driverBaseSteps(getDriver());
    }

    public void openURL() {
        getDriver().get(APP_URL);
    }

    public void driverQuit() {
        getDriver().quit();
    }

    private WebElement getRegistrationYearItem() {
        return getDriver().findElement(By.xpath(REGISTRATION_YEAR_XPATH));
    }

    private Select getRegistrationYearDropDownSelect() {
        return new Select(getRegistrationYearDropDown());
    }

    private WebElement getRegistrationYearDropDown() {
        return getDriver().findElement(By.xpath(REGISTRATION_YEAR_DROPDOWN_XPATH));
    }

    private WebElement getSortDropDown() {
        return getDriver().findElement(By.xpath(SORT_DROPDOWN_XPATH));
    }

    public void selectRegistrationYearByText(Integer value) {
        if (value >= 1960 && value <= Calendar.getInstance().get(Calendar.YEAR)) {
            getRegistrationYearItem().click();
            getRegistrationYearDropDown().click();
            getRegistrationYearDropDownSelect().selectByVisibleText(value.toString());
        }
    }

    public void selectSortItemByTest(SortOptions value) {
        getSortDropDown().click();
        switch (value) {
            case PUBLISHED_AT_DESC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "publishedAt.desc"))).click();
                break;
            case OFFER_PRICE_AMOUNT_MINOR_UNITS_ASC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "offerPrice.amountMinorUnits.asc"))).click();
                break;
            case OFFER_PRICE_AMOUNT_MINOR_UNITS_DESC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "offerPrice.amountMinorUnits.desc"))).click();
                break;
            case MILEAGE_DISTANCE_ASC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "mileage.distance.asc"))).click();
                break;
            case MILEAGE_DISTANCE_DESC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "mileage.distance.desc"))).click();
                break;
            case MANUFACTURER_ASC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "manufacturer.asc"))).click();
                break;
            case MANUFACTURER_DESC:
                getDriver().findElement(By.xpath(SORT_OPTION_XPATH.replace(":value", "manufacturer.desc"))).click();
                break;
        }
    }

    private List<Integer> collectFirstRegistrationData() {
        List<WebElement> resultElements = getDriver().findElements(By.xpath(SEARCH_RESULTS_TABLE_FIRST_REG_DATE_XPATH.replace(":specElementNumber", "1")));
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < resultElements.size(); i++) {
            int date = Integer.parseInt(resultElements.get(i).getAttribute("innerText").substring(resultElements.get(i).getAttribute("innerText").indexOf("/") + 1, resultElements.get(i).getAttribute("innerText").length()));
            result.add(i, date);
        }
        return result;
    }

    private List<Double> collectPrices() {
        waitUntilContentLoaded();
        List<Double> pricesList1 = new ArrayList<>();
        List<WebElement> searchResultsList = getDriver().findElements(By.xpath(SEARCH_RESULTS_TABLE_PRICE_XPATH));
        for (int i = 0; i < searchResultsList.size(); i++) {
            pricesList1.add(i, Double.parseDouble(searchResultsList.get(i).getAttribute("innerHTML").substring(0, searchResultsList.get(i).getAttribute("innerHTML").indexOf("&"))));
        }
        return pricesList1;
    }

    public void collectSearchElementsData() {
        boolean hasMore = true;
        while (hasMore) {
            List<Double> priceListNext = collectPrices();
            List<Integer> specListNext = collectFirstRegistrationData();
            pricesList.addAll(priceListNext);
            firstRegistrationYearList.addAll(specListNext);
            priceListNext.clear();
            specListNext.clear();
            hasMore = isPageNumberAvailable(getCurrentPageNumber() + 1);
            if (hasMore) {
                goToNextPage();
                waitLoadingBanner();
            }
        }
    }

    public boolean validatePriceOrder(PriceOrder priceOrder) {
        boolean result = true;
        switch (priceOrder) {
            case DESCENDING:
                for (int i = 0; i < pricesList.size() - 1; i++) {
                    if (pricesList.get(i + 1) > pricesList.get(i)) {
                        result = false;
                    }
                }
                break;
            case ASCENDING:
                for (int i = 0; i < pricesList.size() - 1; i++) {
                    if (pricesList.get(i + 1) < pricesList.get(i)) {
                        result = false;
                    }
                }
                break;
        }
        return result;
    }

    public boolean validateFirstRegistrationYear(int year) {
        for (Integer e : firstRegistrationYearList) {
            if (e < year) {
                return false;
            }
        }
        return true;
    }

    public void goToPage(int pageNumber) {
        getPaging().goToPage(pageNumber);
    }

    public void goToNextPage() {
        getPaging().goToNextPage();
    }

    public void goToPreviousPage() {
        getPaging().goToPreviousPage();
    }

    public boolean isPageNumberAvailable(int pageNumber) {
        return getPaging().isPageAvailable(pageNumber);
    }

    public int getCurrentPageNumber() {
        return getPaging().getCurrentPageNumber();
    }

    protected Paging getPaging() {
        return new Paging(getDriver().findElement(By.xpath("//ul[@class='pagination']")));
    }

    private void waitUntilContentLoaded() {
        if (getProperties().getProperty("sys.selenium.browser").equals("firefox")) {
            try {
                Sleeper.SYSTEM_SLEEPER.sleep(Duration.ofSeconds(1));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            waiter(getDriver()).until(ExpectedConditions.not(ExpectedConditions.stalenessOf(getDriver().findElements(By.xpath(SEARCH_RESULTS_TABLE_PRICE_XPATH)).get(0))));
            waiter(getDriver()).until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElements(By.xpath(SEARCH_RESULTS_TABLE_PRICE_XPATH)).get(0), "innerText"));
        }

        if (getProperties().getProperty("sys.selenium.browser").equals("chrome") || getProperties().getProperty("sys.selenium.browser").equals("edge")) {
            waiter(getDriver()).until(ExpectedConditions.attributeToBeNotEmpty(getDriver().findElements(By.xpath(SEARCH_RESULTS_TABLE_PRICE_XPATH)).get(0), "innerText"));
        }
    }

    @Override
    public void waitPageLoaded() {
        waiter(getDriver()).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//h1[text() = 'Autosuche']")));
    }
}
