Feature: Rename an item

  Background: User is logged in
    Given I am logged


  Scenario: Rename an item
    When I select the folder AL1234 to rename
    And I set BM4567 as name
    Then I see BM4567 in my file list
    And I do not see AL1234 in my file list