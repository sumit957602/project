Feature: Services page functionality
  As a user
  I want to interact with the services page
  So that I can view available cab types and their details

  Background:
    Given I open the Call Taxi Service application
    And I click on the Services option
    Then the Services page should open

  @TC_UI_003
  Scenario: Validate labels on the Services page
    Then all service types should be listed in correct order:
      | Mini  | 2 Seater |
      | Micro | 3 Seater |
      | Sedan | 4 Seater |
      | Suv   | 7 Seater |

  @TC_UI_004
  Scenario: Validate Mini cab link and its details
    When I click on the Mini link
    Then the Mini cab details page should open

  @TC_UI_005
  Scenario: Validate Micro cab link and its details
    When I click on the Micro link
    Then the Micro cab details page should open

  @TC_UI_006
  Scenario: Validate Sedan cab link and its details
    When I click on the Sedan link
    Then the Sedan cab details page should open

  @TC_UI_007
  Scenario: Validate SUV cab link and its details
    When I click on the Suv link
    Then the SUV cab details page should open

  @TC_UI_008
  Scenario Outline: Validate Back button in cab type description page
    When I click on the "<CabType>" link
    And I click on back link
    Then I should go back to types of cab services page
    Examples:
      | CabType |
      | Mini    |
      | Micro   |
      | Sedan   |
      | Suv     |