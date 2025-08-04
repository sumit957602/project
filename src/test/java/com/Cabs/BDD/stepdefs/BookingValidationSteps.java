package com.Cabs.BDD.stepdefs;

import io.cucumber.java.en.*;
import org.junit.Assert;
import com.aventstack.extentreports.Status;
import com.Cabs.BDD.pages.BookingPage;
import com.Cabs.BDD.utils.DriverManager;
import com.Cabs.BDD.utils.WaitUtils;
import com.Cabs.BDD.utils.ExcelReader;
import com.Cabs.BDD.utils.ExtentTestManager;

import java.util.Map;

public class BookingValidationSteps {
    private BookingPage bookingPage;
    private WaitUtils waitUtils;
    private ExcelReader excelReader;
    private String currentScenarioType = "";

    public BookingValidationSteps() {
        this.bookingPage = new BookingPage(DriverManager.driver);
        this.waitUtils = new WaitUtils(DriverManager.driver);
    }

    @When("I enter invalid full name {string}")
    public void i_enter_invalid_full_name(String invalidName) {
        currentScenarioType = "name";
        bookingPage.fillFullName(invalidName);
        if (!invalidName.isEmpty()) {
            fillOtherMandatoryFields();
        }
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing FullName with invalid input: '" + invalidName + "'");
    }

    @When("I enter invalid email {string}")
    public void i_enter_invalid_email(String invalidEmail) {
        currentScenarioType = "email";
        bookingPage.fillEmail(invalidEmail);
        if (!invalidEmail.isEmpty()) {
            fillOtherMandatoryFields();
        }
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing Email with invalid input: '" + invalidEmail + "'");
    }

    @When("I enter invalid phone number {string}")
    public void i_enter_invalid_phone_number(String invalidPhone) {
        currentScenarioType = "phone";
        bookingPage.fillPhoneNumber(invalidPhone);
        if (!invalidPhone.isEmpty()) {
            fillOtherMandatoryFields();
        }
        ExtentTestManager.getTest().log(Status.INFO,
                "🧪 Testing PhoneNumber with invalid input: '" + invalidPhone + "'");
    }

    @When("I leave Select Cab as default empty")
    public void i_leave_select_cab_as_default_empty() {
        currentScenarioType = "cab";
        fillOtherMandatoryFields();
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing CabSelect with empty value");
    }

    @When("I do not select Long Trip or Local Trip")
    public void i_do_not_select_long_trip_or_local_trip() {
        currentScenarioType = "trip";
        fillOtherMandatoryFields();
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing TripSelect with no selection");
    }

    @When("I leave Number of passengers as default empty")
    public void i_leave_number_of_passengers_as_default_empty() {
        currentScenarioType = "passenger";
        fillOtherMandatoryFields();
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing PassengerCount with empty value");
    }

    @When("I enter invalid pickup date")
    public void i_enter_invalid_pickup_date() {
        currentScenarioType = "date";
        fillOtherMandatoryFields();
        bookingPage.clearPickupDate();
        bookingPage.fillPickupDate("");
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing PickupDate with empty value (invalid input)");
    }

    @When("I enter invalid pickup time")
    public void i_enter_invalid_pickup_time() {
        currentScenarioType = "time";
        fillOtherMandatoryFields();
        bookingPage.fillPickupTime("");
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Testing PickupTime with empty value (invalid input)");
    }

    @When("I leave Car Type as default selected")
    public void i_leave_car_type_as_default_selected() {
        currentScenarioType = "carTypeDefault";
        fillOtherMandatoryFieldsExcludingCarType();
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Leaving Car Type as default selected.");
    }

    @When("I do not select One Way or Round Trip")
    public void i_do_not_select_one_way_or_round_trip() {
        currentScenarioType = "tripWayBlank";
        fillOtherMandatoryFields();
        bookingPage.unselectTripWayCheckboxes();
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Not selecting One Way or Round Trip.");
    }

    @When("I attempt to select both {string} and {string} options")
    public void i_attempt_to_select_both_options(String option1, String option2) {
        currentScenarioType = "tripWayBoth";
        fillOtherMandatoryFields();
        bookingPage.selectTripWay(option1);
        bookingPage.selectTripWay(option2);
        ExtentTestManager.getTest().log(Status.INFO, "🧪 Attempting to select both '" + option1 + "' and '" + option2 + "'.");
    }


    @When("I submit the form")
    public void i_submit_the_form() {
        bookingPage.clickBookNow();
        waitUtils.waitForSeconds(2);
    }

    private void fillOtherMandatoryFields() {
        if (excelReader == null) {
            excelReader = new ExcelReader("src/test/java/com/Cabs/BDD/testdata/booking_data.xlsx");
        }
        Map<String, String> validData = excelReader.getTestData("ValidData", 1);

        if (!"name".equals(currentScenarioType)) { bookingPage.fillFullName(validData.get("FullName")); }
        if (!"phone".equals(currentScenarioType)) { bookingPage.fillPhoneNumber(validData.get("PhoneNumber")); }
        if (!"email".equals(currentScenarioType)) { bookingPage.fillEmail(validData.get("Email")); }
        if (!"trip".equals(currentScenarioType)) { bookingPage.selectTripType(validData.get("TripType")); }
        if (!"cab".equals(currentScenarioType)) { bookingPage.selectCab(validData.get("CabType")); }
        if (!"passenger".equals(currentScenarioType)) { bookingPage.selectPassengerCount(validData.get("PassengerCount")); }
        if (!"date".equals(currentScenarioType)) { bookingPage.fillPickupDate(validData.get("PickupDate")); }
        if (!"time".equals(currentScenarioType)) { bookingPage.fillPickupTime(validData.get("PickupTime")); }

        if (!"carTypeDefault".equals(currentScenarioType)) {
             bookingPage.selectCarType(validData.get("CarType"));
        }
        if (!"tripWayBlank".equals(currentScenarioType) && !"tripWayBoth".equals(currentScenarioType)) {
             bookingPage.selectTripWay(validData.get("TripWay"));
        }
    }

    private void fillOtherMandatoryFieldsExcludingCarType() {
        if (excelReader == null) {
            excelReader = new ExcelReader("src/test/java/com/Cabs/BDD/testdata/booking_data.xlsx");
        }
        Map<String, String> validData = excelReader.getTestData("ValidData", 1);

        bookingPage.fillFullName(validData.get("FullName"));
        bookingPage.fillPhoneNumber(validData.get("PhoneNumber"));
        bookingPage.fillEmail(validData.get("Email"));
        bookingPage.selectTripType(validData.get("TripType"));
        bookingPage.selectCab(validData.get("CabType"));
        bookingPage.selectPassengerCount(validData.get("PassengerCount"));
        bookingPage.fillPickupDate(validData.get("PickupDate"));
        bookingPage.fillPickupTime(validData.get("PickupTime"));
        bookingPage.selectTripWay(validData.get("TripWay"));
    }

    @Then("error message should be shown for name field")
    public void error_message_should_be_shown_for_name_field() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getNameErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for name validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Name error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Name error message should contain expected text",
                errorMessage.contains("Please enter the name"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Name validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for email field")
    public void error_message_should_be_shown_for_email_field() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getEmailErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for email validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Email error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Email error message should contain expected text",
                errorMessage.contains("Please enter the email"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Email validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for phone field")
    public void error_message_should_be_shown_for_phone_field() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getPhoneErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for phone validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Phone error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Phone error message should contain expected text",
                errorMessage.contains("Please enter the Phone number"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Phone validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for cab selection")
    public void error_message_should_be_shown_for_cab_selection() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getCabErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for cab selection validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Cab selection error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Cab error message should contain expected text",
                errorMessage.contains("Please Select the Cab Type"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Cab selection validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for trip selection")
    public void error_message_should_be_shown_for_trip_selection() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getTripErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for trip selection validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Trip selection error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Trip error message should contain expected text",
                errorMessage.contains("Please Select the Trip"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Trip selection validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for passenger count")
    public void error_message_should_be_shown_for_passenger_count() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getCountErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for passenger count validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Passenger count error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Passenger count error message should contain expected text",
                errorMessage.contains("Please Select the number of passengers"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Passenger count validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for date field")
    public void error_message_should_be_shown_for_date_field() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getDateErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for pickup date validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Pickup Date error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Pickup Date error message should contain expected text",
                errorMessage.contains("Please enter the date"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Pickup Date validation error displayed as expected: " + errorMessage);
    }

    @Then("error message should be shown for time field")
    public void error_message_should_be_shown_for_time_field() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getTimeErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for pickup time validation error. Found: '" + errorMessage + "'");
        Assert.assertFalse("Pickup Time error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        Assert.assertTrue("Pickup Time error message should contain expected text",
                errorMessage.contains("Please enter the time"));
        ExtentTestManager.getTest().log(Status.PASS, "✅ Pickup Time validation error displayed as expected: " + errorMessage);
    }

    @Then("car type should be automatically selected")
    public void car_type_should_be_automatically_selected() {
        waitUtils.waitForSeconds(1);
        String selectedCarType = bookingPage.getSelectedCarType();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking if Car Type is automatically selected. Found: '" + selectedCarType + "'");
        Assert.assertFalse("Car Type should not be empty (Expected: auto-selected)", selectedCarType.isEmpty());
        Assert.assertEquals("Car Type should be 'AC' by default", "AC", selectedCarType);
        ExtentTestManager.getTest().log(Status.PASS, "✅ Car Type '" + selectedCarType + "' is automatically selected as expected.");
    }

    @Then("error message should be shown for trip type selection")
    public void error_message_should_be_shown_for_trip_type_selection() {
        waitUtils.waitForSeconds(1);
        String errorMessage = bookingPage.getTripWayErrorMessage();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking for Trip Type (One Way/Round Trip) validation error. Found: '" + errorMessage + "'");

        Assert.assertFalse("Trip Type (One Way/Round Trip) error message should be displayed (Expected: non-empty)", errorMessage.isEmpty());
        ExtentTestManager.getTest().log(Status.PASS, "✅ Trip Type (One Way/Round Trip) validation error displayed as expected: " + errorMessage);
    }

    @Then("only one Trip Type option should be selected")
    public void only_one_trip_type_option_should_be_selected() {
        waitUtils.waitForSeconds(1);
        boolean onlyOneSelected = bookingPage.isOnlyOneTripWaySelected();
        ExtentTestManager.getTest().log(Status.INFO, "🔍 Checking if only one Trip Type (One Way/Round Trip) option is selected. Result: " + onlyOneSelected);

        Assert.assertTrue("Only one Trip Type (One Way/Round Trip) option should be selected (Expected: true)", onlyOneSelected);
        ExtentTestManager.getTest().log(Status.PASS, "✅ Only one Trip Type (One Way/Round Trip) option is selected as expected.");
    }

    @Then("the form should not be submitted")
    public void the_form_should_not_be_submitted() {
        boolean hasErrors = bookingPage.hasErrorMessage();
        boolean formSubmitted = bookingPage.isBookingSuccessfulWithoutWait();

        ExtentTestManager.getTest().log(Status.INFO,
                "🔍 Form submission check - HasErrors: " + hasErrors + ", FormSubmitted (should be false): " + formSubmitted);

        Assert.assertTrue("Validation error should be present (Expected: true)", hasErrors);
        Assert.assertFalse("Form should not submit with validation errors (Expected: false)", formSubmitted);

        ExtentTestManager.getTest().log(Status.PASS,
                "✅ Form correctly blocked with validation errors for " + currentScenarioType + " scenario.");
    }
}