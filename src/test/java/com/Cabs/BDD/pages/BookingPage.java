package com.Cabs.BDD.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import com.Cabs.BDD.utils.WaitUtils;

import java.util.List;

public class BookingPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    // --- Input Fields ---
    @FindBy(id = "fullname")
    private WebElement fullNameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "phonenumber")
    private WebElement phoneNumberInput;

    @FindBy(id = "pickupdate")
    private WebElement pickupDateInput;

    @FindBy(id = "pickuptime")
    private WebElement pickupTimeInput;

    // --- Buttons ---
    @FindBy(id = "submitted")
    private WebElement bookNowButton;

    // --- Dropdowns ---
    @FindBy(id = "cabselect") // This is "Select Cab" field
    private WebElement cabSelectDropdown;

    @FindBy(id = "cabType") // This is "Car Type" field
    private WebElement carTypeDropdown;

    @FindBy(id = "passenger")
    private WebElement passengerCountDropdown;


    // --- Radio Buttons (for "Select Trip": Long Trip / Local Trip) ---
    @FindBy(name = "trip")
    private List<WebElement> tripTypeRadioButtons;


    // --- Checkboxes (for "Trip Type: One way / Round Trip") ---
    @FindBy(id = "oneway")
    private WebElement oneWayCheckbox;

    @FindBy(id = "roundtrip")
    private WebElement roundTripCheckbox;


    // --- Error Message Elements (LOCATORS ARE NOW ACCURATE FROM YOUR HTML) ---
    @FindBy(id = "invalidname")
    private WebElement fullNameErrorMsg;

    @FindBy(id = "invalidemail")
    private WebElement emailErrorMsg;

    @FindBy(id = "invalidphno")
    private WebElement phoneNumberErrorMsg;

    @FindBy(id = "invalidcab") // Error for 'Select Cab' dropdown
    private WebElement cabSelectErrorMsg;

    @FindBy(id = "invalidtrip") // Error for 'Select Trip' radio buttons
    private WebElement tripTypeErrorMsg;

    @FindBy(id = "invalidcount")
    private WebElement passengerCountErrorMsg;

    @FindBy(id = "invaliddate")
    private WebElement pickupDateErrorMsg;

    @FindBy(id = "invalidtime")
    private WebElement pickupTimeErrorMsg;

    @FindBy(id = "invalidcabtype") // Error for 'Car Type' dropdown
    private WebElement carTypeErrorMsg;


    // --- Success/Confirmation Elements ---
    @FindBy(id = "confirm")
    private WebElement bookingConfirmationMessage;

    // Found in HTML: <table id="display">
    @FindBy(id = "display") // For the display table that shows booking details
    private WebElement displayTable;

    // Assuming the booking page can be identified by the presence of these elements
    @FindBy(id = "fullname") // Full Name input is a good identifier for the booking page
    private WebElement bookingPageIdentifierElement;


    public BookingPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
        PageFactory.initElements(driver, this);
    }

    // --- Input Field Actions ---
    public void fillFullName(String fullName) {
        waitUtils.waitForElementToBeVisible(fullNameInput);
        fullNameInput.clear();
        fullNameInput.sendKeys(fullName);
    }

    public void fillEmail(String email) {
        waitUtils.waitForElementToBeVisible(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
    }

    public void fillPhoneNumber(String phoneNumber) {
        waitUtils.waitForElementToBeVisible(phoneNumberInput);
        phoneNumberInput.clear();
        phoneNumberInput.sendKeys(phoneNumber);
    }

    public void fillPickupDate(String date) {
        waitUtils.waitForElementToBeVisible(pickupDateInput);
        pickupDateInput.clear();
        pickupDateInput.sendKeys(date);
    }

    public void fillPickupTime(String time) {
        waitUtils.waitForElementToBeVisible(pickupTimeInput);
        pickupTimeInput.clear();
        pickupTimeInput.sendKeys(time);
    }

    public void clearPickupDate() {
        waitUtils.waitForElementToBeVisible(pickupDateInput);
        pickupDateInput.clear();
    }


    // --- Dropdown/Radio/Checkbox Actions ---
    public void selectCab(String cabType) {
        waitUtils.waitForElementToBeClickable(cabSelectDropdown);
        new Select(cabSelectDropdown).selectByValue(cabType.toLowerCase());
    }

    public void selectCarType(String carType) {
        waitUtils.waitForElementToBeClickable(carTypeDropdown);
        new Select(carTypeDropdown).selectByValue(carType.toLowerCase());
    }

    public String getSelectedCarType() {
        waitUtils.waitForElementToBeVisible(carTypeDropdown);
        Select carTypeDropdownSelect = new Select(carTypeDropdown);
        return carTypeDropdownSelect.getFirstSelectedOption().getText().trim();
    }

    public void selectPassengerCount(String count) {
        waitUtils.waitForElementToBeClickable(passengerCountDropdown);
        new Select(passengerCountDropdown).selectByVisibleText(count);
    }

    public void selectTripType(String tripType) {
        boolean selected = false;
        for (WebElement radio : tripTypeRadioButtons) {
            if (radio.getAttribute("value").equalsIgnoreCase(tripType)) {
                waitUtils.waitForElementToBeClickable(radio);
                radio.click();
                selected = true;
                break;
            }
        }
        if (!selected) {
            System.err.println("Warning: Trip Type '" + tripType + "' radio button not found or clickable.");
        }
    }

    public void selectTripWay(String tripWay) {
        WebElement targetCheckbox = null;
        if (tripWay.equalsIgnoreCase("oneway")) {
            targetCheckbox = oneWayCheckbox;
        } else if (tripWay.equalsIgnoreCase("roundtrip")) {
            targetCheckbox = roundTripCheckbox;
        }

        if (targetCheckbox != null) {
            waitUtils.waitForElementToBeClickable(targetCheckbox);
            if (!targetCheckbox.isSelected()) {
                targetCheckbox.click();
            }
        } else {
            System.err.println("Warning: Trip Way checkbox '" + tripWay + "' not found.");
        }
    }

    public void unselectTripWayCheckboxes() {
        if (oneWayCheckbox.isSelected()) {
            waitUtils.waitForElementToBeClickable(oneWayCheckbox);
            oneWayCheckbox.click();
        }
        if (roundTripCheckbox.isSelected()) {
            waitUtils.waitForElementToBeClickable(roundTripCheckbox);
            roundTripCheckbox.click();
        }
    }

    public boolean isOnlyOneTripWaySelected() {
        int selectedCount = 0;
        if (oneWayCheckbox.isSelected()) {
            selectedCount++;
        }
        if (roundTripCheckbox.isSelected()) {
            selectedCount++;
        }
        return selectedCount == 1;
    }


    // --- Button Actions ---
    public void clickBookNow() {
        waitUtils.waitForElementToBeClickable(bookNowButton);
        bookNowButton.click();
    }

    // --- Get Error Message Methods ---
    private String getErrorMessageText(WebElement errorElement) {
        try {
            String text = errorElement.getText().trim();
            if (!text.isEmpty()) {
                return text;
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
            // Element not found, implies no error message is displayed
        } catch (Exception e) {
            System.err.println("Error retrieving error message text for element: " + errorElement + ". " + e.getMessage());
        }
        return "";
    }

    public String getNameErrorMessage() {
        return getErrorMessageText(fullNameErrorMsg);
    }

    public String getEmailErrorMessage() {
        return getErrorMessageText(emailErrorMsg);
    }

    public String getPhoneErrorMessage() {
        return getErrorMessageText(phoneNumberErrorMsg);
    }

    public String getCabErrorMessage() {
        return getErrorMessageText(cabSelectErrorMsg);
    }

    public String getCarTypeErrorMessage() {
        return getErrorMessageText(carTypeErrorMsg);
    }

    public String getTripErrorMessage() {
        return getErrorMessageText(tripTypeErrorMsg);
    }

    public String getCountErrorMessage() {
        return getErrorMessageText(passengerCountErrorMsg);
    }

    public String getDateErrorMessage() {
        return getErrorMessageText(pickupDateErrorMsg);
    }

    public String getTimeErrorMessage() {
        return getErrorMessageText(pickupTimeErrorMsg);
    }

    public String getTripWayErrorMessage() {
        return ""; // As per HTML analysis, no dedicated error element for tripWay.
    }


    // --- Form Submission Status & Page Visibility Methods ---

    public boolean hasErrorMessage() {
        WebElement[] errorMessages = {
            fullNameErrorMsg, emailErrorMsg, phoneNumberErrorMsg,
            cabSelectErrorMsg, carTypeErrorMsg, tripTypeErrorMsg, passengerCountErrorMsg,
            pickupDateErrorMsg, pickupTimeErrorMsg
        };

        for (WebElement errorMsg : errorMessages) {
            if (errorMsg != null && !getErrorMessageText(errorMsg).isEmpty()) {
                return true;
            }
        }
        // getTripWayErrorMessage() returns empty, so it won't be checked here
        return false;
    }

    public boolean isBookingSuccessfulWithoutWait() {
        try {
            if (bookingConfirmationMessage != null && waitUtils.isElementVisible(bookingConfirmationMessage)) {
                return !bookingConfirmationMessage.getText().trim().isEmpty();
            }
        } catch (org.openqa.selenium.NoSuchElementException e) {
        } catch (Exception e) {
            System.err.println("Error checking booking success status: " + e.getMessage());
        }
        return false;
    }

    public boolean isDisplayTableVisible() {
        try {
            return waitUtils.isElementVisible(displayTable);
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }

    public boolean isBookingPageVisible() {
        try {
            return waitUtils.isElementVisible(bookingPageIdentifierElement);
        } catch (org.openqa.selenium.NoSuchElementException | org.openqa.selenium.TimeoutException e) {
            return false;
        }
    }
}