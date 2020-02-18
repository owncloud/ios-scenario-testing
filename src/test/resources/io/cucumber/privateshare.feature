Feature: Private Share

  Background: User is logged in
    Given user1 is logged

  Scenario Outline: Correct share
    When user selects <item> to share with <user>
    Then <item> is shared with <user>
    And <user> has <item> in the file list

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |
