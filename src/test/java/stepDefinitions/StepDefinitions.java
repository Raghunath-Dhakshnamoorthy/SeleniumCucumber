package stepDefinitions;

import cucumber.api.Scenario;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.BookingPage;
import pageObjects.HolidayPackagePage;
import pageObjects.HotelSelectionPage;
import pageObjects.HomePage;
import wrappers.GenericWrappers;

import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class StepDefinitions {
    static WebDriver driver;
    HomePage homePage;
    static HotelSelectionPage hotelSelectionPage;
    static HolidayPackagePage holidayPackagePage;
    static BookingPage bookingPage;
    private Scenario scenario;
    ResourceBundle config = ResourceBundle.getBundle("config");

    @Before
    public void before(Scenario scenario) {
        this.scenario = scenario;
    }

    @Given("^I navigate to the Invia Travel website$")
    public void iNavigateToTheInviaTravelWebsite() {

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.get(config.getString("test.url"));
    }

    @When("^I enter details like \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\" and click offers$")
    public void iEnterDetailsLikeAndClickOffers(String place, String startDate, String endDate, String guestCount) {

        homePage = new HomePage(driver);
        hotelSelectionPage = homePage.searchPauschalreise(place, startDate, endDate, guestCount);
    }

    @Then("^I should see the Hotelauswahl page with offers$")
    public void iShouldSeeTheHotelauswahlPageWithOffers() {
        scenario.write("Navigated successfully to Hotelauswahl page");
    }

    @When("^I update the \"([^\"]*)\" and \"([^\"]*)\" dates of the search$")
    public void iUpdateTheAndDatesOfTheSearch(String newStartDate, String newEndDate){

        hotelSelectionPage.selectNewSearchDates(newStartDate, newEndDate);
    }

    @And("^Select hotels with \"([^\"]*)\" rating and \"([^\"]*)\" review and sort results by \"([^\"]*)\"$")
    public void selectHotelsWithRatingReviewAndSort(String rating, String review, String sortParam) {

        hotelSelectionPage.selectRatingReviewAndSort(rating, review, sortParam);
    }

    @Then("^I should see the result sorted with \"([^\"]*)\" in the Hotelauswahl page and select costliest hotel$")
    public void iShouldSeeTheSortedResultInTheHotelauswahlPage(String sortParameter) {

        boolean isHotelSorted = hotelSelectionPage.verifyPriceDescSortedResult();
        Assert.assertTrue("Hotel results are not sorted", isHotelSorted);
        scenario.write("Hotel results are sorted with :"+sortParameter+" parameter");
        holidayPackagePage = hotelSelectionPage.selectCostliestHotel();
    }


    @When("^I provide the timings as \"([^\"]*)\" to \"([^\"]*)\" in departure range and \"([^\"]*)\" to \"([^\"]*)\" in arrival range$")
    public void iProvideTheTimingsAsToInDepartureRangeAndToInArrivalRange(String departFrom, String departTill, String arrivalFrom, String arrivalTill) {

        //Calculate offset values and move the slider
        List<Integer> departOffsetValues = GenericWrappers.getSliderTimeRange(departFrom, departTill, "Offset");
        int departFromOffset = departOffsetValues.get(0);
        int departTillOffset = departOffsetValues.get(1);
        List<Integer> arrivalOffsetValues = GenericWrappers.getSliderTimeRange(arrivalFrom, arrivalTill, "Offset");
        int arrivalFromOffset = arrivalOffsetValues.get(0);
        int arrivalTillOffset = arrivalOffsetValues.get(1);
        holidayPackagePage.moveTimeRangeSlider(departFromOffset, departTillOffset, arrivalFromOffset, arrivalTillOffset);
    }

    @And("^I select the first option in Anreisedatum field and I count the number of direct flight options available$")
    public void iSelectTheFirstOptionInAnreisedatumFieldAndICountTheNumberOfDirectFlightOptionsAvailable() {

        int directFlights = holidayPackagePage.countDirectFlightOptions();
        scenario.write("Number of direct flight options available is: "+Integer.toString(directFlights));
    }

    @Then("^I should see the first result has flight timings within \"([^\"]*)\" to \"([^\"]*)\" and \"([^\"]*)\" to \"([^\"]*)\"$")
    public void iShouldSeeTheFirstResultHasFlightTimingsWithinToAndTo(String departFrom, String departTill, String arrivalFrom, String arrivalTill) {

        //Retrieve the time range values and verify its within range
        List<Integer> departTimeValues = GenericWrappers.getSliderTimeRange(departFrom, departTill, "TimeRange");
        int filterDepartFrom = departTimeValues.get(0);
        int filterDepartTill = departTimeValues.get(1);
        List<Integer> arrivalTimeValues = GenericWrappers.getSliderTimeRange(arrivalFrom, arrivalTill, "TimeRange");
        int filterArrivalFrom = arrivalTimeValues.get(0);
        int filterArrivalTill = arrivalTimeValues.get(1);
        boolean verifyTimeRange = holidayPackagePage.verifyFlightsWithinTimeRange(filterDepartFrom, filterDepartTill, filterArrivalFrom, filterArrivalTill);
        Assert.assertTrue("First flight result is not within filtered time range", verifyTimeRange);
        scenario.write("Verified that flight timings of first result is withing the filtered ranges");
    }

    @And("^I select the first offer and in booking page I should see the same hotel I selected in Hotelauswahl page$")
    public void iSelectTheFirstOfferAndInBookingPageIShouldSeeTheSameHotelISelectedInHotelauswahlPage() {

        bookingPage = holidayPackagePage.selectTheFirstOfferForBooking();
        boolean verifyHotelName = bookingPage.verifyBookedHotelName(HotelSelectionPage.selectedHotelName);
        Assert.assertTrue("Hotel name is not same as in Hotelauswahl page", verifyHotelName);
        scenario.write("Hotel Name verified");
        bookingPage.quitBrowser();
    }
}
