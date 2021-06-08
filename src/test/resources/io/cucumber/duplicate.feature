@duplicate
Feature: Duplicate item

  As a user
  I want to duplicate items in my account
  So that i will have a copy in the same place to perform modifications

  Background: User is logged in
    Given user user1 is logged

  Scenario: Duplicate an existent folder using the Actions menu
    Given the folder dupl has been created in the account
    When user selects to duplicate the folder dupl using the Actions menu
    Then user should see dupl in the filelist
    And user should see dupl 2 in the filelist

  Scenario: Duplicate an existent folder using the Contextual menu
    Given the folder duplbis has been created in the account
    When user selects to duplicate the folder duplbis using the Contextual menu
    Then user should see duplbis in the filelist
    And user should see duplbis 2 in the filelist