Feature: Private Share

  As an user, i want to share my content with other users in the platform
  so that the content is accessible and others can contribute

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | Documents |

  Scenario Outline: Correct share
    When user selects <item> to share with <user>
    Then share is created on <item> with the following fields
      | user | <user> |

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |
