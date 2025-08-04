Feature: Navigation functionality
  As a user
  I want to navigate through different pages
  So that I can access various services

  Background:
    Given I open the Call Taxi Service application

  @TC_UI_001
  Scenario: Verify homepage opens correctly
    Then the website should load successfully
    And all modules should be visible

  @TC_UI_002
  Scenario: Check if the Services link works
    When I click on the Services option
    Then the Services section should be visible

  @TC_UI_009
  Scenario: Validate that the Booking link is working
    When I click on Booking option
    Then the Booking page should be visible

  @TC_UI_010
  Scenario: Validate that the About link is working
    When I click on About option
    Then the About page should be visible

  @TC_UI_011
  Scenario: Validate that the Contact link is working
    When I click on Contact option
    Then the Contact page should be visible
