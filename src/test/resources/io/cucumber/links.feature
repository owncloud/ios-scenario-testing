@links
Feature: Links

  As an user, i want to share my content with other users in the platform
  so that the content is accessible and others can contribute

  Background: User is logged in
    Given user user1 is logged
    And the following items have been created in the account
      |  Documents         |
      |  textExample.txt   |


  Scenario Outline: Create a public link with name
    When user selects to share by link the item <item> using the Actions menu
    And user creates link on <item> with the following fields
      | name | <name> |
    Then link should be created on <item> with the following fields
      | name | <name> |

    Examples:
      |  item              |  name    |
      |  Documents         |  link1   |
      |  textExample.txt   |  link2   |

  Scenario Outline: Create a public link with password
    When user selects to share by link the item <item> using the Actions menu
    And user creates link on <item> with the following fields
      | name     | <name>     |
      | password | <password> |
    Then link should be created on <item> with the following fields
      | name     | <name>     |
      | password | <password> |

    Examples:
      |  item       |  name    | password |
      |  Documents  |  link1   |    a     |

  @expiration
  Scenario Outline: Create a public link with expiration date
    When user selects to share by link the item <item> using the Actions menu
    And user creates link on <item> with the following fields
      | name            | <name>  |
      | expiration days | <expiration>  |
    Then link should be created on <item> with the following fields
      | name            | <name>        |
      | expiration days | <expiration>  |

    Examples:
      |  item       |  name    | expiration    |
      |  Documents  |  link1   |    7          |

  Scenario Outline: Create a public link with name using the contextual menu
    When user selects to share by link the item <item> using the Contextual menu
    And user creates link on <item> with the following fields
      | name | <name> |
    Then link should be created on <item> with the following fields
      | name | <name> |

    Examples:
      |  item              |  name    |
      |  Documents         |  link1   |

  Scenario Outline: Edit existing share, changing permissions
    Given the item <item> has been already shared by link
    When user selects to edit link the item <item> using the Actions menu
    And user edits the link on <item> with the following fields
      | permissions | <permissions> |
      | name        | <name>        |
    Then link should be created on <item> with the following fields
      | permissions | <permissions> |
      | name        | <name>        |

    Examples:
      |  item   |  name    | permissions |
      |  Files  |  downupl |     15      |
      |  Files  |  upload  |     4       |
      |  Files  |  view    |     1       |

  Scenario Outline: Create a public link with permissions
    When user selects to share by link the item <item> using the Actions menu
    And user creates link on <item> with the following fields
      | name       | <name>        |
      | permission | <permissions> |
    Then link should be created on <item> with the following fields
      | name       | <name>        |
      | permission | <permissions> |

    Examples:
      |  item       |  name    | permissions |
      |  Documents  |  link1   |    15       |
      |  Documents  |  link2   |    4        |

  @deletelink
  Scenario: Delete existing link
    Given the item Files has been already shared by link
    When user selects to edit link the item Files using the Actions menu
    And user deletes the link on Files
    Then link on Files should not exist anymore