package com.autohero.bdd.steps;

import com.autohero.bdd.enums.PriceOrder;
import com.autohero.bdd.enums.SortOptions;
import com.autohero.bdd.pageObject.SearchPage;
import cucumber.api.PendingException;
import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebElement;

import java.util.List;


public class FilterStepDefs {
    SearchPage searchPage = new SearchPage();

    @Before
    public void before(Scenario scenario){
        searchPage.launchBrowser();
    }

    @After
    public void after(Scenario scenario){
        searchPage.driverQuit();
    }


    @Given("^user is navigated to AutoHero site$")
    public void userIsNavigatedToAutoHeroSite() {
        searchPage.openURL();
        searchPage.waitPageLoaded();
    }

    @When("^user select (\\d+) First Registration year$")
    public void userSelectFirstRegistrationYear(int firstRegistrationYear) {
        searchPage.selectRegistrationYearByText(firstRegistrationYear);
        searchPage.waitLoadingBanner();
    }

    @And("^user select \"([^\"]*)\" option from sort drop-down list$")
    public void userSelectOptionFromSortDropDownList(SortOptions selectOption) throws Throwable {
       searchPage.selectSortItemByTest(selectOption);
       searchPage.waitLoadingBanner();
    }

    @And("^all cars are sorted by price \"([^\"]*)\"$")
    public void allCarsAreSortedByPrice(PriceOrder priceOorder) throws Throwable {
        Assert.assertTrue(searchPage.validatePriceOrder(priceOorder));

    }

    @Then("^all cars are filtered by first registration since (\\d+)$")
    public void allCarsAreFilteredByFirstRegistrationSince(int firstRegistrationYear) throws Throwable {
        Assert.assertTrue(searchPage.validateFirstRegistrationYear(firstRegistrationYear));
    }

    @And("^user goes through all filtered pages$")
    public void userGoesThroughAllFilteredPages() throws Throwable {
        searchPage.collectSearchElementsData();
    }
}
