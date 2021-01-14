@privateshare
Feature: Private Share

  As an user, i want to share my content with other users in the platform
  so that the content is accessible and others can contribute

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | Documents |

  @smoke
  Scenario Outline: Correct share with user
    When user selects <item> to share with <user>
    Then user <user> has access to <item>
    And share is created on <item> with the following fields
      | user | <user> |

    Examples:
      |  item   |   user    |
      |  Files  |   user2   |

  @smoke
  Scenario Outline: Correct share with group
    When user selects <item> to share with <group>
    Then group including <user_in_group> has access to <item>
    And share is created on <item> with the following fields
      | group | <group> |

    Examples:
      |  item   |   group    |  user_in_group |
      |  Files  |   test     |     user2      |

  Scenario Outline: Edit existing share, removing permissions
    Given the item <item> is already shared with <user>
    When user selects to share the item <item>
    And user edits the share on <item> with permissions <permissions>
    Then user <user> has access to <item>
    Then share is created on <item> with the following fields
      | user        |  <user>        |
      | permissions |  <permissions> |

#Permissions
    # READ -> 1
    # UPDATE -> 2
    # CREATE -> 4
    # DELETE -> 8
    # SHARE -> 16

    Examples:
      |  item   |   user    | permissions |
      |  Files  |   user2   |   1         |
      |  Files  |   user2   |   9         |
      |  Files  |   user2   |   13        |
      |  Files  |   user2   |   17        |

  Scenario Outline: Delete existing share
    Given the item <item> is already shared with <user>
    When user selects to share the item <item>
    And user deletes the share
    Then user <user> does not have access to <item>
    And <item> is not shared anymore with <user>

    Examples:
      |  item     |   user    |
      |  Files    |   user2   |