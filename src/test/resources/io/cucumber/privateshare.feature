Feature: Private Share

  Background: User is logged in
    Given I am logged

  @SmokeTest
  Scenario Outline: Correct share
    When I select <item> to share with <user>
    Then <item> is shared with <user>
    And <user> has <item> in the file list

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |
