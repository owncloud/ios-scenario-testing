Feature: Rename an item

  Background: User is logged in
    Given user1 is logged


  Scenario: Rename an item
    When user selects the item AL1234 to rename
    And user sets BM4567 as name
    Then user sees BM4567 in the file list
    And user does not see AL1234 in the file list