@spaces @nooc10
Feature: Spaces

  As a user
  I want to be able to check my available spaces
  so that i can browse through and share my content with other members

  Background: User is logged in
    Given user Alice is logged in

  @createspace
  Rule: Create a space

  Scenario: Create a new space with correct name and subtitle
    When Alice opens the sidebar
    And Alice selects the spaces view
    And Alice selects to create a new space with the following fields
      | name     | Space1      |
      | subtitle | First space |
    Then Alice should see the following spaces
      | name   | subtitle    |
      | Space1 | First space |
    And Spaces should be created in server with the following fields
      | name     | Space1      |
      | subtitle | First space |

  @editspace
  Rule: Edit an existing space

  Scenario: Edit an existing space with correct name and subtitle
    Given the following spaces have been created in Alice account
      | name   | subtitle     |
      | Space2 | Second space |
    When Alice opens the sidebar
    And Alice selects the spaces view
    And Alice selects to edit a space with the following fields
      | name     | Space2a               |
      | subtitle | Second space modified |
    Then Alice should see the following spaces
      | name    | subtitle              |
      | Space2a | Second space modified |
    And Spaces should be updated in server with the following fields
      | name     | Space2a               |
      | subtitle | Second space modified |

  @disablespace
  Rule: Disable an existing space

  Scenario: Disable an existing space
    Given the following spaces have been created in Alice account
      | name   | subtitle    |
      | Space3 | Third space |
    When Alice opens the sidebar
    And Alice selects the spaces view
    And Alice selects to disable the following spaces
      | name   | subtitle    |
      | Space3 | Third space |
    But Alice should see the following spaces in the list of disabled spaces
      | name   | subtitle    |
      | Space3 | Third space |
    And Spaces should be disabled in server with the following fields
      | name     | Space3      |
      | subtitle | Third space |

  @enablespace @ignore
  Scenario: Enable a disabled space
    Given the following spaces have been created in Alice account
      | name   | subtitle     |
      | Space4 | Fourth space |
    And the following spaces have been disabled in Alice account
      | name   | subtitle     |
      | Space4 | Fourth space |
    When Alice opens the sidebar
    And Alice selects the spaces view
    And Alice shows disabled spaces
    And Alice selects to enable the following spaces
      | name   | subtitle     |
      | Space4 | Fourth space |
    Then Alice should see the following spaces
      | name   | subtitle     |
      | Space4 | Fourth space |
    And Spaces should be updated in server with the following fields
      | name     | Space4       |
      | subtitle | Fourth space |

  @remotespace
  Rule: Remote operations on spaces

  Scenario: Create a new space remotely with correct name and subtitle
    Given the following spaces have been created in Alice account
      | name   | subtitle    |
      | Space5 | Fifth space |
      | Space6 | Sixth space |
    When Alice opens the sidebar
    And Alice selects the spaces view
    Then Alice should see the following spaces
      | name   | subtitle    |
      | Space5 | Fifth space |
      | Space6 | Sixth space |

    Scenario: Add a new space remotely with correct name and subtitle
    Given the following spaces have been created in Alice account
      | name   | subtitle      |
      | Space7 | Seventh space |
    And Alice opens the sidebar
    And Alice selects the spaces view
    When the following spaces have been created in Alice account
      | name   | subtitle     |
      | Space8 | Eighth space |
    Then Alice should see the following spaces
      | name   | subtitle      |
      | Space7 | Seventh space |
      | Space8 | Eighth space  |

    Scenario: Disable a space remotely
    Given the following spaces have been created in Alice account
      | name    | subtitle    |
      | Space9  | Ninth space |
      | Space10 | Tenth space |
    And Alice opens the sidebar
    And Alice selects the spaces view
    When the following space is disabled in server
      | name   | subtitle    |
      | Space9 | Ninth space |
    Then Alice should see the following spaces
      | name    | subtitle    |
      | Space10 | Tenth space |
    And Alice should not see the following spaces
      | name   | subtitle    |
      | Space9 | Ninth space |

  @spacemembership
  Rule: Space Membership

    Scenario Outline: Add a member to a space
      Given the following spaces have been created in Alice account
        | name   | subtitle   |
        | <name> | <subtitle> |
      When Alice opens the sidebar
      And Alice selects the spaces view
      And Alice opens the members menu
      And Alice adds Bob to the space <name> with
        | permission | <permissions> |
      Then Bob should be member of the space <name> with
        | permission | <permissions> |

      Examples:
        | name    | subtitle       | permissions |
        | Space10 | Tenth space    | Can view    |
        | Space11 | Eleventh space | Can edit    |
        | Space12 | Twelfth space  | Can manage |

    Scenario Outline: Add a member to a space with expiration date
      Given the following spaces have been created in Alice account
        | name   | subtitle   |
        | <name> | <subtitle> |
      And Alice opens the sidebar
      And Alice selects the spaces view
      And Alice opens the members menu
      And Alice adds Bob to the space <name> with
        | permission     | <permissions>    |
        | expirationDate | <expirationDate> |
      Then Bob should be member of the space <name> with
        | description    | <subtitle>       |
        | permission     | <permissions>    |
        | expirationDate | <expirationDate> |

      Examples:
        | name    | subtitle         | permissions | expirationDate |
        | Space13 | Thirteenth space | Can view    | 25             |

    @editmember
    Scenario Outline: Edit a member
      Given the following spaces have been created in Alice account
        | name   | subtitle   |
        | <name> | <subtitle> |
      And the following users are members of the space <name>
        | user | permission          | expirationDate      |
        | Bob  | <initialPermission> | <initialExpiration> |
      When Alice opens the sidebar
      And Alice selects the spaces view
      And Alice opens the members menu
      And Alice edits Bob from the space <name> with the following fields
        | permission     | <permissions>    |
        | expirationDate | <expirationDate> |
      Then Bob should be member of the space <name> with
        | permission     | <permissions>    |
        | expirationDate | <expirationDate> |

      Examples:
        | name    | subtitle           | initialPermission | initialExpiration | permissions | expirationDate |
        | Space14 | Fourteenth space   | Can view          | 12                | Can edit    | 22             |
        | Space15 | Fifteenth space    | Can manage        |                   | Can view    | 10             |
        | Space16 | Sixteenth space    | Can edit          | 20                | Can manage  |                |

    Scenario: Remove a member from a space
      Given the following spaces have been created in Alice account
        | name    | subtitle          |
        | Space17 | Seventeenth space |
      And the following users are members of the space Space17
        | user    | permission |
        | Bob     | Can view   |
        | Charles | Can edit   |
      When Alice opens the sidebar
      And Alice selects the spaces view
      And Alice opens the members menu
      And Alice removes Bob from the space Space17
      Then Bob should not be member of the space Space17
      And Charles should be member of the space Space17 with
        | permission | Can edit |
