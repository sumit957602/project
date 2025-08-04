package com.Cabs.BDD.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.*;
import org.junit.Assert;

import com.Cabs.BDD.pages.CabDetailsPage;
import com.Cabs.BDD.pages.ServicesPage;

import com.Cabs.BDD.utils.DriverManager;
import com.aventstack.extentreports.Status;
import com.Cabs.BDD.utils.ExtentTestManager;

import java.util.List;
import java.util.Map;


public class ServicesSteps {
    private ServicesPage servicesPage;
    private CabDetailsPage cabDetailsPage; // NEW: Declare CabDetailsPage

    public ServicesSteps() {
        servicesPage = new ServicesPage(DriverManager.driver);
        // cabDetailsPage is initialized when navigating to detail pages
    }

    @Then("the Services page should open")
    public void the_services_page_should_open() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Services page is open.");
        Assert.assertTrue("Services page is not visible", servicesPage.isServicesPageDisplayed());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Services page opened successfully.");
    }

    @Then("all service types should be listed in correct order:")
    public void all_service_types_should_be_listed_in_correct_order(DataTable dataTable) {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying service types and order.");
        List<List<String>> expectedServices = dataTable.asLists(String.class);
        ExtentTestManager.getTest().log(Status.INFO, "Expected service types from data table: " + expectedServices);

        Assert.assertTrue("Service types are not in correct order or not visible",
                servicesPage.areServiceTypesInCorrectOrder(expectedServices));
        ExtentTestManager.getTest().log(Status.PASS, "✅ All service types listed in correct order.");
    }

    @When("I click on the Mini link")
    public void i_click_on_the_mini_link() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Mini cab link.");
        servicesPage.clickMiniLink();
        cabDetailsPage = new CabDetailsPage(DriverManager.driver); // Initialize CabDetailsPage here
    }

    @When("I click on the Micro link")
    public void i_click_on_the_micro_link() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Micro cab link.");
        servicesPage.clickMicroLink();
        cabDetailsPage = new CabDetailsPage(DriverManager.driver);
    }

    @When("I click on the Sedan link")
    public void i_click_on_the_sedan_link() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Sedan cab link.");
        servicesPage.clickSedanLink();
        cabDetailsPage = new CabDetailsPage(DriverManager.driver);
    }

    @When("I click on the Suv link")
    public void i_click_on_the_suv_link() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Suv cab link.");
        servicesPage.clickSuvLink();
        cabDetailsPage = new CabDetailsPage(DriverManager.driver);
    }

    @Then("the Mini cab details page should open")
    public void the_mini_cab_details_page_should_open() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Mini cab details page is open.");
        Assert.assertTrue("Mini cab details page did not open", cabDetailsPage.isMiniPageLoaded());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Mini cab details page opened.");
    }

    @Then("the Micro cab details page should open")
    public void the_micro_cab_details_page_should_open() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Micro cab details page is open.");
        Assert.assertTrue("Micro cab details page did not open", cabDetailsPage.isMicroPageLoaded());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Micro cab details page opened.");
    }

    @Then("the Sedan cab details page should open")
    public void the_sedan_cab_details_page_should_open() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying Sedan cab details page is open.");
        Assert.assertTrue("Sedan cab details page did not open", cabDetailsPage.isSedanPageLoaded());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Sedan cab details page opened.");
    }

    @Then("the SUV cab details page should open")
    public void the_suv_cab_details_page_should_open() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying SUV cab details page is open.");
        Assert.assertTrue("SUV cab details page did not open", cabDetailsPage.isSuvPageLoaded());
        ExtentTestManager.getTest().log(Status.PASS, "✅ SUV cab details page opened.");
    }

    @When("I click on the {string} link")
    public void i_click_on_the_link(String cabType) {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on '" + cabType + "' link.");
        servicesPage.clickServiceLink(cabType);
        cabDetailsPage = new CabDetailsPage(DriverManager.driver); // Initialize CabDetailsPage here
    }

    @When("I click on back link")
    public void i_click_on_back_link() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking on Back link.");
        cabDetailsPage.clickBackLink(); // Now calls CabDetailsPage for back button
    }

    @Then("I should go back to types of cab services page")
    public void i_should_go_back_to_types_of_cab_services_page() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying return to Services page.");
        Assert.assertTrue("Did not return to services page",
                DriverManager.driver.getCurrentUrl().contains("services.html"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Returned to services page.");
    }
}