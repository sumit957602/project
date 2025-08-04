package com.Cabs.BDD.stepdefs;

import io.cucumber.java.en.*;
import org.junit.Assert;
import com.Cabs.BDD.pages.HomePage;
import com.Cabs.BDD.pages.ServicesPage;
import com.Cabs.BDD.pages.BookingPage;
import com.Cabs.BDD.utils.DriverManager;
import com.Cabs.BDD.utils.ExtentTestManager;
import com.aventstack.extentreports.Status;

public class NavigationSteps {
    private HomePage homePage;
    private ServicesPage servicesPage;
    private BookingPage bookingPage;

    public NavigationSteps() {
        homePage = new HomePage(DriverManager.driver);
        servicesPage = new ServicesPage(DriverManager.driver);
        bookingPage = new BookingPage(DriverManager.driver);
    }

    @Given("I open the Call Taxi Service application")
    public void i_open_the_call_taxi_service_application() {
        ExtentTestManager.getTest().log(Status.INFO, "🌐 Opening application URL...");
        DriverManager.driver.get("https://webapps.tekstac.com/SeleniumApp2/CallTaxiService/Home.html");
        ExtentTestManager.getTest().log(Status.INFO, "✅ Application opened successfully");
    }

    @Then("the website should load successfully")
    public void the_website_should_load_successfully() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying website load status...");
        Assert.assertTrue("Homepage should be loaded successfully", homePage.isPageLoaded());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Website verification completed");
    }

    @Then("all modules should be visible")
    public void all_modules_should_be_visible() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying all homepage modules visibility...");
        Assert.assertTrue("All homepage modules should be visible", homePage.areAllModulesVisible());
        ExtentTestManager.getTest().log(Status.PASS, "✅ All modules visibility verified");
    }

    @When("I click on the Services option")
    public void i_click_on_the_services_option() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Services option");
        homePage.clickServicesLink();
    }

    @Then("the Services section should be visible")
    public void the_services_section_should_be_visible() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Services page visibility.");
        Assert.assertTrue("Services page should be visible", servicesPage.isServicesPageDisplayed());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Services page verification completed");
    }

    @When("I click on Booking option")
    public void i_click_on_booking_option() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Booking option");
        homePage.clickBookingLink();
    }

    @Then("the Booking page should be visible")
    public void the_booking_page_should_be_visible() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Booking page visibility.");
        Assert.assertTrue("Booking page should be visible", bookingPage.isBookingPageVisible());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Booking page verification completed");
    }

    @When("I click on About option")
    public void i_click_on_about_option() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on About option");
        homePage.clickAboutLink();
    }

    @Then("the About page should be visible")
    public void the_about_page_should_be_visible() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying About page visibility.");
        // Placeholder: Implement actual verification for the About page
        // You'd need to add elements and a method like isAboutPageDisplayed() in HomePage or a dedicated AboutPage class.
        // Example: Assert.assertTrue("About page should be visible", homePage.isElementVisible(driver.findElement(By.id("aboutPageUniqueElement"))));
        ExtentTestManager.getTest().log(Status.PASS, "✅ About page verification completed (placeholder).");
    }

    @When("I click on Contact option")
    public void i_click_on_contact_option() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Contact option");
        homePage.clickContactLink();
    }

    @Then("the Contact page should be visible")
    public void the_contact_page_should_be_visible() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Contact page visibility.");
        // Placeholder: Implement actual verification for the Contact page
        // Example: Assert.assertTrue("Contact page should be visible", homePage.isElementVisible(driver.findElement(By.id("contactPageUniqueElement"))));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Contact page verification completed (placeholder).");
    }
}