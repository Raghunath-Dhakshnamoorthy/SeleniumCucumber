Feature: Invia Travel Automated Test
  Perform test automation for the following scenarios

#  @Current
  Scenario Outline: Search Pauschalreise for a specific region in Homepage
    Given I navigate to the Invia Travel website
    When I enter details like "<place>", "<startDate>", "<endDate>", "<guestCount>" and click offers
    Then I should see the Hotelauswahl page with offers

    Examples:
      |   place     |      startDate    |      endDate     |       guestCount        |
      |   Sizilien  |   6 Oktober 2019  |  13 Oktober 2019 |  2 Erwachsene, 0 Kinder |


  Scenario Outline: Validate date change, hotel type, review and price sorting in Hotelauswahl page
    When I update the "<startDate>" and "<endDate>" dates of the search
    And Select hotels with "<rating>" rating and "<reviewType>" review
    And Sort the results by "<sortParameter>" parameter
    Then I should see the sorted result in the Hotelauswahl page

    Examples:
      |      startDate     |      endDate     |  rating    |   reviewType  |   sortParameter |
      |   13 Oktober 2019  |  20 Oktober 2019 |            |               |                 |