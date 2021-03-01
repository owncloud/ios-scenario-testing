@links
Feature: Links

  As an user, i want to share my content with other users in the platform
  so that the content is accessible and others can contribute

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | Documents |

  @smoke
  Scenario Outline: Create a public link with name
    When user selects to share the item <item>
    And user creates link on <item> with the following fields
      | name | <name> |
    Then link is created on <item> with the following fields
      | name | <name> |

    Examples:
      |  item              |  name    |
      |  Documents         |  link1   |
      |  textExample.txt   |  link2   |