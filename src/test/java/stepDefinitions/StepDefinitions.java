package stepDefinitions;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.HotelSelectionPage;
import pageObjects.HomePage;

import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class StepDefinitions {
    static WebDriver driver;
    HomePage homePage;
    static HotelSelectionPage hotelSelectionPage;
    ResourceBundle config = ResourceBundle.getBundle("config");

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
//        homePage.searchPauschalreise(place, startDate, endDate, guestCount);
    }

    @Then("^I should see the Hotelauswahl page with offers$")
    public void iShouldSeeTheHotelauswahlPageWithOffers() {
        System.out.println("Then");
    }

    @When("^I update the \"([^\"]*)\" and \"([^\"]*)\" dates of the search$")
    public void iUpdateTheAndDatesOfTheSearch(String newStartDate, String newEndDate){
//        hotelSelectionPage = new HotelSelectionPage(driver);
        hotelSelectionPage.selectNewSearchDates(newStartDate, newEndDate);
    }

    @And("^Select hotels with \"([^\"]*)\" rating and \"([^\"]*)\" review$")
    public void selectHotelsWithRatingAndReview(String arg0, String arg1) {
        System.out.println("And");
    }

    @And("^Sort the results by \"([^\"]*)\" parameter$")
    public void sortTheResultsByParameter(String arg0) {
        System.out.println("And");
    }

    @Then("^I should see the sorted result in the Hotelauswahl page$")
    public void iShouldSeeTheSortedResultInTheHotelauswahlPage() {
        System.out.println("Then2");
    }
}
