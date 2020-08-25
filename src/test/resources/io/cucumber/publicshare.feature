@publicshare
Feature: Public Share

  As an user, i want to create a direct link to the content in my account
  so that the content is accessible for whom i send the link

  Background: User is logged in
    Given user1 is logged
    And the following items exist in the account
      | Documents        |
      | textExample.txt  |

  Scenario Outline: Create a public link with name
    When user selects the item <item> to share
    And user creates link on <item> with the following fields
        | name | <name> |
    Then link is created on <item> with the following fields
        | name | <name> |

    Examples:
      |  item              |  name    |
      |  Documents         |  link1   |
      |  textExample.txt   |  link2   |

  Scenario Outline: Create a public link with password
    When user selects the item <item> to share
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
    When user selects the item <item> to share
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

    @exp
  Scenario Outline: Create a public link with expiration date
    When user selects the item <item> to share
    And user creates link on <item> with the following fields
      | name            | <name>  |
      | expiration days | <expiration>  |
    Then link is created on <item> with the following fields
      | name            | <name>        |
      | expiration days | <expiration>  |

    Examples:
      |  item       |  name    | expiration  |
      |  Documents  |  link1   |    5        |

  Scenario Outline: Edit existing share, changing permissions
    Given the item <item> is already shared by link
    When user selects the item <item> to share
    And user edits the link on <item> with the following fields
      | permissions | <permissions> |
      | name        | <name>        |
    Then link is created on <item> with the following fields
      | permissions | <permissions> |
      | name        | <name>        |

    Examples:
      |  item   | permissions | name    |
      |  Files  |   15        | downupl |
      |  Files  |   4         | upload  |
      |  Files  |   1         | view    |

  Scenario Outline: Delete existing link
    Given the item <item> is already shared by link
    When user selects the item <item> to share
    And user deletes the link
    Then link on <item> does not exist anymore

    Examples:
      |  item   |
      |  Files  |