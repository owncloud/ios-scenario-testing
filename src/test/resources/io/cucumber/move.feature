Feature: Move item

  As an user, i want to be able to move content to other place in the account
  so that the files and folders are place where i want

  Background: User is logged in
    Given user1 is logged

  Scenario: Move an existent folder to another location
    When user selects the item moveMe to move
    And user selects Documents as target folder
    Then user does not see moveMe in the file list
    And user sees moveMe inside the folder Documents
