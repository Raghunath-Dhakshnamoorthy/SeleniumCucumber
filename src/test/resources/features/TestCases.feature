Feature: Invia Travel Automated Test
  Perform test automation for the following scenarios

  Scenario Outline: Search Pauschalreise for a specific region in Homepage
    Given I navigate to the Invia Travel website
    When I enter details like "<place>", "<startDate>", "<endDate>", "<guestCount>" and click offers
    Then I should see the Hotelauswahl page with offers

    Examples:
      |   place     |      startDate    |      endDate     |       guestCount        |
      |   Sizilien  |   6 Oktober 2019  |  13 Oktober 2019 |  2 Erwachsene, 0 Kinder |


  Scenario Outline: Validate date change, hotel type, review and price sorting in Hotelauswahl page
    When I update the "<startDate>" and "<endDate>" dates of the search
    And Select hotels with "<rating>" rating and "<reviewType>" review and sort results by "<sortParameter>"
    Then I should see the result sorted with "<sortParameter>" in the Hotelauswahl page and select costliest hotel

    ##Please provide rating and review data as per ratingAndReviewLocatorData method in Generic Wrappers
    Examples:
      |      startDate     |      endDate     |  rating    |     reviewType     |  sortParameter |
      |   13 Oktober 2019  |  20 Oktober 2019 |    4Star   |  Excellent Review  |    price_desc  |


  Scenario Outline: Validate time range, arrival date and direct flight options in Hoteldetails page
    When I provide the timings as "<departFrom>" to "<departTill>" in departure range and "<arrivalFrom>" to "<arrivalTill>" in arrival range
    And I select the first option in Anreisedatum field and I count the number of direct flight options available
    Then I should see the first result has flight timings within "<departFrom>" to "<departTill>" and "<arrivalFrom>" to "<arrivalTill>"
    And I select the first offer and in booking page I should see the same hotel I selected in Hotelauswahl page

    Examples:
    |departFrom | departTill  | arrivalFrom | arrivalTill |
    |   04:00   |    21:00    |   00:00     |   12:00     |
