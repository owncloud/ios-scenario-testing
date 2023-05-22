@links
Feature: Public Links

  As an user
  I want to handle links on my files or folders
  So that the content is accessible for whom i send the link

  Background: User is logged in
    Given user Alice is logged in

    @createlink
    Rule: Create a public link

      @smoke
      Scenario Outline: Create a public link
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share the <type> <item> using the <menu> menu
        And Alice creates link on <type> <item> with the following fields
          | permission | <permission> |
        Then link should be created on <item> with the following fields
          | permission | <permission> |

        Examples:
          | type   | item       | permission | menu       |
          | folder | Links1     | Viewer     | Actions    |
          | file   | Links2.txt | Viewer     | Contextual |

      Scenario Outline: Create a public link with password
        Given the following items have been created in the account
            | <type>  | <item>  |
        When Alice selects to share the <type> <item> using the Actions menu
        And Alice creates link on <type> <item> with the following fields
          | permission | Viewer     |
          | password   | <password> |
        Then link should be created on <item> with the following fields
          | password | <password> |

        Examples:
          | type   | item       | password |
          | folder | Links3     | a        |
          | file   | Links4.txt | a        |

      Scenario Outline: Create a public link with expiration date
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share the <type> <item> using the Actions menu
        And Alice creates link on <type> <item> with the following fields
          | permission | Viewer       |
          | expiration | <expiration> |
        Then link should be created on <item> with the following fields
          | expiration | <expiration> |

        Examples:
          | type   | item   | expiration |
          | folder | Links5 | 1          |

      Scenario Outline: Create a public link with permissions on a folder
        Given the following items have been created in the account
          | folder  | <item>  |
        When Alice selects to share the folder <item> using the Actions menu
        And Alice creates link on folder <item> with the following fields
          | permission | <permissions> |
        Then link should be created on <item> with the following fields
          | permission | <permissions> |

        Examples:
          | item   | permissions |
          | Links6 | Uploader    |
          | Links7 | Contributor |
          | Links8 | Editor      |

    @editlink
    Rule: Edit a public link

      Scenario Outline: Edit existing share on a folder, changing permissions
        Given the following items have been created in the account
          | folder  | <item>  |
        And Alice has shared the folder <item> by link
        When Alice selects to share the folder <item> using the Actions menu
        And Alice edits the link on <item> with the following fields
          | permissions | <permissions> |
        Then link should be created on <item> with the following fields
          | permissions | <permissions> |

        Examples:
          | item    | permissions |
          | Links9  | Editor      |
          | Links10 | Contributor |
          | Links11 | Uploader    |

    @deletelink
    Rule: Delete a public link

      Scenario Outline: Delete existing link
        Given the following items have been created in the account
          | <type>  | <item>  |
        And Alice has shared the <type> <item> by link
        When Alice selects to share the <type> <item> using the Contextual menu
        And Alice deletes the link on <item>
        Then link on <item> should not exist anymore

        Examples:
          | type   | item    |
          | folder | Links12 |