@links @nooc10
Feature: Public Links

  As an user
  I want to handle links on my files or folders
  So that the content is accessible for whom i send the link

  Background: User is logged in
    Given user Alice is logged in

  @createlink
  Rule: Create a public link

  @smoke
  Scenario Outline: Create a public link with name
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item> using the <menu> menu
    And Alice creates link on <type> <item> with the following fields
      | permission    | <permission> |
      | password-auto |              |
      | name          | <name>       |
    Then link should be created on <item> with the following fields
      | permission | <permission> |
      | name       | <name>       |

    Examples:
      | type   | item       | permission | menu       | name       |
      | folder | Links1     | Viewer     | Actions    | folderName |
      | file   | Links2.txt | Editor     | Contextual | fileName   |

  Scenario Outline: Create a public link with password created by user
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice creates link on <type> <item> with the following fields
      | permission | Viewer     |
      | password   | <password> |
    Then link should be created on <item> with the following fields
      | password | <password> |

    Examples:
      | type   | item       | password     |
      | folder | Links3     | aa55AA..1111 |
      | file   | Links4.txt | aa55AA..1111 |

  Scenario Outline: Create a public link with password generated automatically
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice creates link on <type> <item> with the following fields
      | permission    | Viewer |
      | password-auto |        |
    Then link should be created on <item> with the following fields
      | password | <password> |

    Examples:
      | type   | item       |
      | folder | Links5     |
      | file   | Links6.txt |

  Scenario Outline: Create a public link with expiration date
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice creates link on <type> <item> with the following fields
      | permission    | Viewer       |
      | expiration    | <expiration> |
      | password-auto |              |
    Then link should be created on <item> with the following fields
      | expiration | <expiration> |

    Examples:
      | type   | item   | expiration |
      | folder | Links7 | 12         |

  Scenario Outline: Create a public link with permissions on a folder
    Given the following items have been created in Alice account
      | folder | <item> |
    When Alice selects to share the folder <item> using the Actions menu
    And Alice creates link on folder <item> with the following fields
      | permission    | <permissions> |
      | password-auto |               |
    Then link should be created on <item> with the following fields
      | permission | <permissions> |

    Examples:
      | item    | permissions |
      | Links8  | Secret      |
      | Links9  | Viewer      |
      | Links10 | Editor      |

  @editlink
  Rule: Edit an existing public link
  @expiration
  Scenario Outline: Edit existing share on a folder, changing permissions
    Given the following items have been created in Alice account
      | folder | <item> |
    And Alice has shared the folder <item> by link
    When Alice selects to share the folder <item> using the Actions menu
    And Alice edits the link on <item> with the following fields
      | permissions | <permissions> |
      | expiration  | 20             |
    Then link should be created on <item> with the following fields
      | permissions | <permissions> |
      | expiration  | 20            |

    Examples:
      | item    | permissions |
      | Links11 | Editor      |
      | Links12 | Secret      |

  Scenario: Edit existing link removing password
    Given the following items have been created in Alice account
      | folder | Links13 |
    And Alice has shared the folder Links13 by link
    When Alice selects to share the folder Links13 using the Contextual menu
    And Alice edits the link on Links13 with the following fields
      | password | "" |
      Then link should be created on Links13 with the following fields
      | password | "" |

  @deletelink
  Rule: Delete a public link

  Scenario Outline: Delete existing link
    Given the following items have been created in Alice account
      | <type> | <item> |
    And Alice has shared the <type> <item> by link
    When Alice selects to share the <type> <item> using the Contextual menu
    And Alice deletes the link on <item>
    Then link on <item> should not exist anymore

    Examples:
      | type   | item    |
      | folder | Links14 |

  @linkshortcut
  Rule: Shortcuts on links

  Scenario Outline: Check shared by link in list
    Given the following items have been created in Alice account
      | <type> | <item> |
    And Alice has shared the folder <item> by link
    When Alice opens the sidebar
    And Alice opens the option shared by link in sidebar
    Then Alice should see <item> in shared by link

    Examples:
      | type | item        |
      | file | Links15.txt |
