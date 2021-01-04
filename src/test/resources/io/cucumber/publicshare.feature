@publicshare
Feature: Public Share

  As an user, i want to create a direct link to the content in my account
  so that the content is accessible for whom i send the link

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | Documents        |
      | textExample.txt  |

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

  Scenario Outline: Create a public link with password
    When user selects to share the item <item>
    And user creates link on <item> with the following fields
      | name     | <name>     |
      | password | <password> |
    Then link is created on <item> with the following fields
      | name     | <name>     |
      | password | <password> |

    Examples:
      |  item       |  name    | password |
      |  Documents  |  link1   |    a     |

  Scenario Outline: Create a public link with permissions
    When user selects to share the item <item>
    And user creates link on <item> with the following fields
      | name       | <name>        |
      | permission | <permissions> |
    Then link is created on <item> with the following fields
      | name       | <name>        |
      | permission | <permissions> |

    Examples:
      |  item       |  name    | permissions |
      |  Documents  |  link1   |    15       |
      |  Documents  |  link2   |    4        |

  @expiration
  Scenario Outline: Create a public link with expiration date
    When user selects to share the item <item>
    And user creates link on <item> with the following fields
      | name            | <name>  |
      | expiration days | <expiration>  |
    Then link is created on <item> with the following fields
      | name            | <name>        |
      | expiration days | <expiration>  |

    Examples:
      |  item       |  name    | expiration    |
      |  Documents  |  link1   |    4          |

  Scenario Outline: Edit existing share, changing permissions
    Given the item <item> is already shared by link
    When user selects to share the item <item>
    And user edits the link on <item> with the following fields
      | permissions | <permissions> |
      | name        | <name>        |
    Then link is created on <item> with the following fields
      | permissions | <permissions> |
      | name        | <name>        |

    Examples:
      |  item   |  name    | permissions |
      |  Files  |  downupl |     15      |
      |  Files  |  upload  |     4       |
      |  Files  |  view    |     1       |

  Scenario Outline: Delete existing link
    Given the item <item> is already shared by link
    When user selects to share the item <item>
    And user deletes the link
    Then link on <item> does not exist anymore

    Examples:
      |  item   |
      |  Files  |