@duplicate
Feature: Duplicate item

  As a user
  I want to duplicate items in my account
  So that i will have a copy in the same place to perform modifications

  Background: User is logged in
    Given user Alice is logged in

  @smoke
  Scenario: Duplicate an existent folder using the Actions menu
    Given the following items have been created in Alice account
      | type   | name  |
      | folder | dupl1 |
    When Alice selects to duplicate the folder dupl1 using the Actions menu
    Then Alice should see dupl1 in the filelist
    And Alice should see 'dupl1 2' in the filelist

  Scenario: Duplicate an existent folder using the Contextual menu
    Given the following items have been created in Alice account
      | type   | name    |
      | folder | duplbis |
    When Alice selects to duplicate the folder duplbis using the Contextual menu
    Then Alice should see duplbis in the filelist
    And Alice should see 'duplbis 2' in the filelist