Feature: Private Share

  As an user, i want to share my content with other users in the platform
  so that the content is accessible and others can contribute

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | Documents |

  Scenario Outline: Correct share
    When user selects <item> to share with <user>
    Then <user> has access to <item>
    And share is created on <item> with the following fields
      | user | <user> |

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |

  Scenario Outline: Edit existing share, removing permissions
    Given the item <item> is already shared with <user>
    When user selects the item <item> to share
    And user edits the share on <item> with permissions <permissions>
    Then <user> has access to <item>
    And share is created on <item> with the following fields
      | user        |  <user>        |
      | permissions |  <permissions> |

    Examples:
      |    item     |   user    | permissions |
      |  Documents  |   user2   |   1         |

  Scenario Outline: Delete existing share
    Given the item <item> is already shared with <user>
    When user selects the item <item> to share
    And user deletes the share
    Then <user> does not have access to <item>
    And <item> is not shared anymore with <user>

    Examples:
      |    item     |   user    |
      |  Documents  |   user2   |