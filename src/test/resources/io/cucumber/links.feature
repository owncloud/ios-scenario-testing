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
    When user selects to share the item <item>
    And user creates link on <item> with the following fields
      | name | <name> |
    Then link should be created on <item> with the following fields
      | name | <name> |

    Examples:
      |  item              |  name    |
      |  Documents         |  link1   |
      #|  textExample.txt   |  link2   |


  Scenario Outline: Create a public link with password
    When user selects to share the item <item>
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
    When user selects to share the item <item>
    And user creates link on <item> with the following fields
      | name            | <name>  |
      | expiration days | <expiration>  |
    Then link should be created on <item> with the following fields
      | name            | <name>        |
      | expiration days | <expiration>  |

    Examples:
      |  item       |  name    | expiration    |
      |  Documents  |  link1   |    7          |

    @ggg
  Scenario Outline: Create a public link with permissions
    When user selects to share the item <item>
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