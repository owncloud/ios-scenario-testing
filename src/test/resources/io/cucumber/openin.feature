@openin
Feature: Open file in an external application

  As a user
  I want to open a file in an external application,
  so that i will edit or view it there

  Background: User is logged in
    Given user Alice is logged in

  Scenario: Open a file in an external application
    Given the following items have been created in Alice account
      | file | file1.pdf |
    When Alice selects to open in the file file1.pdf using the Actions menu
    Then Alice should see the menu with the options to open the file in an external application

  Scenario: "Open in" not available for folders
    Given the following items have been created in Alice account
      | folder | folder1 |
    When Alice opens the action menu of folder folder1
    Then The Open In option is not available
