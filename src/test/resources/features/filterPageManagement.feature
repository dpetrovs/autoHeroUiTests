Feature: Filter functionality for AutoHero site

  @FunctionalTests
  Scenario Outline: Verify First Registration filter functionality
    Given user is navigated to AutoHero site
    When user select <First Registration Year> First Registration year
    And user select "OFFER_PRICE_AMOUNT_MINOR_UNITS_DESC" option from sort drop-down list
    And user goes through all filtered pages
    Then all cars are filtered by first registration since <First Registration Year>
    And all cars are sorted by price "DESCENDING"
    Examples:
    |First Registration Year|
    |      2015             |
