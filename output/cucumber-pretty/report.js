$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/resources/features/TestCases.feature");
formatter.feature({
  "name": "Invia Travel Automated Test",
  "description": "  Perform test automation for the following scenarios",
  "keyword": "Feature"
});
formatter.scenarioOutline({
  "name": "Search Pauschalreise for a specific region in Homepage",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "I navigate to the Invia Travel website",
  "keyword": "Given "
});
formatter.step({
  "name": "I enter details like \"\u003cplace\u003e\", \"\u003cstartDate\u003e\", \"\u003cendDate\u003e\", \"\u003cguestCount\u003e\" and click offers",
  "keyword": "When "
});
formatter.step({
  "name": "I should see the Hotelauswahl page with offers",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "place",
        "startDate",
        "endDate",
        "guestCount"
      ]
    },
    {
      "cells": [
        "Sizilien",
        "6 Oktober 2019",
        "13 Oktober 2019",
        "2 Erwachsene, 0 Kinder"
      ]
    }
  ]
});
formatter.scenario({
  "name": "Search Pauschalreise for a specific region in Homepage",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "I navigate to the Invia Travel website",
  "keyword": "Given "
});
formatter.match({
  "location": "StepDefinitions.iNavigateToTheInviaTravelWebsite()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I enter details like \"Sizilien\", \"6 Oktober 2019\", \"13 Oktober 2019\", \"2 Erwachsene, 0 Kinder\" and click offers",
  "keyword": "When "
});
formatter.match({
  "location": "StepDefinitions.iEnterDetailsLikeAndClickOffers(String,String,String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should see the Hotelauswahl page with offers",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.iShouldSeeTheHotelauswahlPageWithOffers()"
});
formatter.result({
  "status": "passed"
});
formatter.scenarioOutline({
  "name": "Validate date change, hotel type, review and price sorting in Hotelauswahl page",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "I update the \"\u003cstartDate\u003e\" and \"\u003cendDate\u003e\" dates of the search",
  "keyword": "When "
});
formatter.step({
  "name": "Select hotels with \"\u003crating\u003e\" rating and \"\u003creviewType\u003e\" review",
  "keyword": "And "
});
formatter.step({
  "name": "Sort the results by \"\u003csortParameter\u003e\" parameter",
  "keyword": "And "
});
formatter.step({
  "name": "I should see the sorted result in the Hotelauswahl page",
  "keyword": "Then "
});
formatter.examples({
  "name": "",
  "description": "",
  "keyword": "Examples",
  "rows": [
    {
      "cells": [
        "startDate",
        "endDate",
        "rating",
        "reviewType",
        "sortParameter"
      ]
    },
    {
      "cells": [
        "13 Oktober 2019",
        "20 Oktober 2019",
        "",
        "",
        ""
      ]
    }
  ]
});
formatter.scenario({
  "name": "Validate date change, hotel type, review and price sorting in Hotelauswahl page",
  "description": "",
  "keyword": "Scenario Outline"
});
formatter.step({
  "name": "I update the \"13 Oktober 2019\" and \"20 Oktober 2019\" dates of the search",
  "keyword": "When "
});
formatter.match({
  "location": "StepDefinitions.iUpdateTheAndDatesOfTheSearch(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Select hotels with \"\" rating and \"\" review",
  "keyword": "And "
});
formatter.match({
  "location": "StepDefinitions.selectHotelsWithRatingAndReview(String,String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Sort the results by \"\" parameter",
  "keyword": "And "
});
formatter.match({
  "location": "StepDefinitions.sortTheResultsByParameter(String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "I should see the sorted result in the Hotelauswahl page",
  "keyword": "Then "
});
formatter.match({
  "location": "StepDefinitions.iShouldSeeTheSortedResultInTheHotelauswahlPage()"
});
formatter.result({
  "status": "passed"
});
});