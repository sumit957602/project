Feature: Booking form validation
  As a user
  I want to ensure booking form fields are validated correctly
  So that invalid bookings are prevented

  Background:
    Given I open the Call Taxi Service application
    And I click on Booking option

  # TC_BOOK_013: Full Name invalid/empty (Manual Status: Fail)
  # Expected: Error Message, No Submission (Automation will now FAIL if app submits or no error)
  @TC_BOOK_013_BLANK
  Scenario: Verify the Full Name field with empty input
    When I enter invalid full name ""
    And I submit the form
    Then error message should be shown for name field
    And the form should not be submitted

  @TC_BOOK_013_INVALID_DATA
  Scenario Outline: Verify form rejects invalid name data (e.g., numbers, symbols)
    When I enter invalid full name "<invalidName>"
    And I submit the form
    Then error message should be shown for name field
    And the form should not be submitted
    Examples:
      | invalidName |
      | 123         |
      | @#$%        |

  # TC_BOOK_014: Email invalid/empty (Manual Status: Fail)
  # Expected: Error Message, No Submission (Automation will now FAIL if app submits or no error)
  @TC_BOOK_014_BLANK
  Scenario: Verify the Email field with empty input
    When I enter invalid email ""
    And I submit the form
    Then error message should be shown for email field
    And the form should not be submitted

  @TC_BOOK_014_INVALID_DATA
  Scenario Outline: Verify form rejects invalid email data
    When I enter invalid email "<invalidEmail>"
    And I submit the form
    Then error message should be shown for email field
    And the form should not be submitted
    Examples:
      | invalidEmail   |
      | invalid-email  |
      | test@          |
      | @domain.com    |

  # TC_BOOK_015: Phone Number invalid/empty (Manual Status: Fail)
  # Expected: Error Message, No Submission (Automation will now FAIL if app submits or no error)
  @TC_BOOK_015_BLANK
  Scenario: Verify the Phone Number field with empty input
    When I enter invalid phone number ""
    And I submit the form
    Then error message should be shown for phone field
    And the form should not be submitted

  @TC_BOOK_015_INVALID_DATA
  Scenario Outline: Verify form rejects invalid phone data
    When I enter invalid phone number "<invalidPhone>"
    And I submit the form
    Then error message should be shown for phone field
    And the form should not be submitted
    Examples:
      | invalidPhone   |
      | 123            |
      | abcdefghij     |

  # TC_BOOK_016: Pick Up Date invalid (Manual Status: Fail)
  # Automation will now FAIL this as it expects an error message and non-submission.
  @TC_BOOK_016
  Scenario: Verify the Pick Up Date field with invalid input
    When I enter invalid pickup date
    And I submit the form
    Then error message should be shown for date field
    And the form should not be submitted

  # TC_BOOK_017: Pick Up Time invalid (Manual Status: Fail)
  # Automation will now FAIL this as it expects an error message and non-submission.
  @TC_BOOK_017
  Scenario: Verify the Pick Up Time field with invalid input
    When I enter invalid pickup time
    And I submit the form
    Then error message should be shown for time field
    And the form should not be submitted

  # TC_BOOK_018: Select Cab empty (Manual Status: Pass - form not submitting)
  # Automation will PASS if app blocks it as expected.
  @TC_BOOK_018
  Scenario: Verify the Select Cab field with blank input
    When I leave Select Cab as default empty
    And I submit the form
    Then error message should be shown for cab selection
    And the form should not be submitted

  # TC_BOOK_019: Car Type empty (Manual Status: Pass - "The car type is already selected")
  # Automation will PASS this if the default "AC" is selected.
  @TC_BOOK_019
  Scenario: Verify the Car Type field with default selected
    When I leave Car Type as default selected
    And I submit the form
    Then car type should be automatically selected
    And booking should be successful

  # TC_BOOK_020: Select Trip empty (Manual Status: Pass - error raised, form not submitting)
  # Automation will PASS if app blocks it as expected.
  @TC_BOOK_020
  Scenario: Verify the Select Trip option with blank input
    When I do not select Long Trip or Local Trip
    And I submit the form
    Then error message should be shown for trip selection
    And the form should not be submitted

  # TC_BOOK_021: Trip Type empty (One Way/Round Trip) (Manual Status: Fail)
  # Automation will now FAIL this as it expects an error message (which won't appear per HTML)
  # and non-submission (which will fail if the form submits).
  @TC_BOOK_021
  Scenario: Verify the Trip Type field with blank input (One Way/Round Trip)
    When I do not select One Way or Round Trip
    And I submit the form
    Then error message should be shown for trip type selection
    And the form should not be submitted

  # TC_UI_024: Verify that only one "Trip Type" option can be selected (Manual Status: Fail)
  # Automation will now FAIL this if the app allows selecting both checkboxes.
  # This scenario directly highlights the bug of checkboxes not behaving as radio buttons.
  @TC_UI_024
  Scenario: Verify only one Trip Type (One Way/Round Trip) option can be selected
    When I attempt to select both "One Way" and "Round Trip" options
    Then only one Trip Type option should be selected