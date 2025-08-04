Feature: Valid booking functionality
  As a user
  I want to make a booking with valid data
  So that I can successfully book a taxi

  Background:
    Given I open the Call Taxi Service application
    And I click on Booking option

  @TC_UI_012 @TC_UI_022
  Scenario: Validate successful booking with all valid inputs
    When I fill all the valid booking data from excel row 1
    And I click on Book Now
    Then all inputs should be accepted as valid
    And booking should be successful
    And confirmation message should be displayed
