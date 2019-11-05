Feature: Share

  Background: User is logged in
    Given I am logged


  Scenario Outline: Correct share
    When I select <item> to share with <user>
    Then <item> is shared with <user>
    And <user> sees <item> in the file list

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |

  Scenario Outline: Create a public link with name
    When i select <item> to create link with name <name>
    Then public link is created with the name <name>

    Examples:
      |    item     |   name    |
      |  Documents  |   link1   |