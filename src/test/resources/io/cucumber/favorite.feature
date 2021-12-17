@favorite
Feature: Set an item as favorite (starred)

  As a user, i want to set a file or folder as favorite
  so that it will be quickly accessed by shortcut

  Background: User is logged in
    Given user Alice is logged in

  Scenario: Favorite an item using the Actions menu
    Given the following items have been created in the account
      | item   | fav1  |
    When Alice sets as favorite the item fav1 using the Actions menu
    And Alice closes the Actions menu
    Then item fav1 should be set as favorite

  Scenario: Favorite an item using the Contextual menu
    Given the following items have been created in the account
      | item   | fav2  |
    When Alice sets as favorite the item fav2 using the Contextual menu
    Then item fav2 should be set as favorite

