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
    Then Alice should not see the following spaces
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

  Rule: Membership