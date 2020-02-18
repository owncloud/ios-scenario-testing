Feature: Delete item

  As an user, i want to be able to delete content from my list
  so that i can get rid of the content i do not need anymore

  Background: User is logged in
    Given user1 is logged

  Scenario: Delete an existent folder
    When user selects the item deleteMe to delete
    And user accepts the deletion
    Then user does not see deleteMe in the file list
