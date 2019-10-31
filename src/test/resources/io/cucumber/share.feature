Feature: Share

  @Current
  Scenario Outline: Correct share
    Given I am logged
    When I select <item> to share with <user>
    Then <item> is shared with <user>
    And <user> sees <item> in the file list

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |