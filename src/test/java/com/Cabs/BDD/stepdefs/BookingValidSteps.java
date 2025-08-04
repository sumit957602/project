package com.Cabs.BDD.stepdefs;

import io.cucumber.java.en.*;
import org.junit.Assert;
import com.aventstack.extentreports.Status;
import com.Cabs.BDD.pages.BookingPage;
import com.Cabs.BDD.utils.DriverManager;
import com.Cabs.BDD.utils.ExcelReader;
import com.Cabs.BDD.utils.ExtentTestManager;
import com.Cabs.BDD.utils.WaitUtils;

import java.util.Map;

public class BookingValidSteps {
    private BookingPage bookingPage;
    private ExcelReader excelReader;
    private WaitUtils waitUtils;

    public BookingValidSteps() {
        bookingPage = new BookingPage(DriverManager.driver);
        excelReader = new ExcelReader("src/test/java/com/Cabs/BDD/testdata/booking_data.xlsx");
        waitUtils = new WaitUtils(DriverManager.driver);
    }

    @When("I fill all the valid booking data from excel row {int}")
    public void i_fill_all_the_valid_booking_data_from_excel_row(Integer rowNum) {
        ExtentTestManager.getTest().log(Status.INFO, "📋 Filling booking data from Excel row: " + rowNum);
        Map<String, String> data = excelReader.getTestData("ValidData", rowNum);

        bookingPage.fillFullName(data.get("FullName"));
        bookingPage.fillEmail(data.get("Email"));
        bookingPage.fillPhoneNumber(data.get("PhoneNumber"));
        bookingPage.selectTripType(data.get("TripType"));
        bookingPage.selectCab(data.get("CabType"));
        bookingPage.selectCarType(data.get("CarType"));
        bookingPage.selectPassengerCount(data.get("PassengerCount"));
        bookingPage.fillPickupDate(data.get("PickupDate"));
        bookingPage.fillPickupTime(data.get("PickupTime"));
        bookingPage.selectTripWay(data.get("TripWay"));
        ExtentTestManager.getTest().log(Status.INFO, "✅ Booking data filled.");
    }

    @When("I click on Book Now")
    public void i_click_on_book_now() {
        ExtentTestManager.getTest().log(Status.INFO, "👆 Clicking 'Book Now'.");
        bookingPage.clickBookNow();
        waitUtils.waitForSeconds(2);
    }

    @Then("all inputs should be accepted as valid")
    public void all_inputs_should_be_accepted_as_valid() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying inputs accepted as valid.");
        Assert.assertFalse("No validation errors should be displayed for valid inputs", bookingPage.hasErrorMessage());
        ExtentTestManager.getTest().log(Status.PASS, "✅ No validation errors found for valid inputs.");
    }

    @Then("booking should be successful")
    public void booking_should_be_successful() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying booking success.");
        Assert.assertTrue("Booking should be successful (confirmation message expected)", bookingPage.isBookingSuccessfulWithoutWait());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Booking successful.");
    }

    @Then("confirmation message should be displayed")
    public void confirmation_message_should_be_displayed() {
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Verifying confirmation message visibility.");
        Assert.assertTrue("Display table with booking details should be visible", bookingPage.isDisplayTableVisible());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Confirmation message and details table displayed.");
    }
}