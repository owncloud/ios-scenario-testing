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
      Scenario Outline: Create a public link with name
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share by link the <type> <item> using the Actions menu
        And Alice creates link on <type> <item> with the following fields
          | name | <name> |
        Then link should be created on <item> with the following fields
          | name | <name> |

        Examples:
          |  type     |  item          |  name    |
          |  folder   |  Links1        |  link1   |
          |  file     |  Links2.txt    |  link2   |

      Scenario Outline: Create a public link with password
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share by link the <type> <item> using the Actions menu
        And Alice creates link on <type> <item> with the following fields
          | name     | <name>     |
          | password | <password> |
        Then link should be created on <item> with the following fields
          | name     | <name>     |
          | password | <password> |

        Examples:
          |  type     |  item        |  name    | password |
          |  folder   |  Links3      |  link3   |    a     |
          |  file     |  Links4.txt  |  link4   |    a     |

      Scenario Outline: Create a public link with expiration date
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share by link the <type> <item> using the Actions menu
        And Alice creates link on <type> <item> with the following fields
          | name            | <name>        |
          | expiration days | <expiration>  |
        Then link should be created on <item> with the following fields
          | name            | <name>        |
          | expiration days | <expiration>  |

        Examples:
          |  type    |  item         |  name    | expiration   |
          |  folder  |  Links5       |  link5   |    7         |
          |  file    |  Links6.txt   |  link6   |    17        |

      Scenario Outline: Create a public link with permissions on a folder
        Given the following items have been created in the account
          | folder  | <item>  |
        When Alice selects to share by link the folder <item> using the Actions menu
        And Alice creates link on folder <item> with the following fields
          | name       | <name>        |
          | permission | <permissions> |
        Then link should be created on <item> with the following fields
          | name       | <name>        |
          | permission | <permissions> |

        Examples:
          |  item         |  name    | permissions | description
          |  Links7       |  link7   |    15       | Download / View / Upload
          |  Links8       |  link8   |    4        | Upload Only (File drop)
          |  Links9       |  link9   |    1        | Download / View

      Scenario Outline: Create a public link with name using the contextual menu
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share by link the <type> <item> using the Contextual menu
        And Alice creates link on <type> <item> with the following fields
          | name | <name> |
        Then link should be created on <item> with the following fields
          | name | <name> |

        Examples:
          |  type   |  item         |  name     |
          |  folder |  Links10      |  link10   |
          |  file   |  Links11.txt  |  link11   |

    @editlink
    Rule: Edit a public link

      Scenario Outline: Edit existing share on a folder, changing permissions
        Given the following items have been created in the account
          | folder  | <item>  |
        And Alice has shared the folder <item> by link
        When Alice selects to edit link the folder <item> using the Actions menu
        And Alice edits the link on <item> with the following fields
          | permissions | <permissions> |
          | name        | <name>        |
        Then link should be created on <item> with the following fields
          | permissions | <permissions> |
          | name        | <name>        |

        Examples:
          |  item     |  name    | permissions | description
          |  Links12  |  link12  |     15      | Download / View / Upload
          |  Links13  |  link13  |     4       | Upload Only (File drop)
          |  Links14  |  link14  |     1       | Download / View

    @deletelink
    Rule: Delete a public link

      Scenario Outline: Delete existing link
        Given the following items have been created in the account
          | <type>  | <item>  |
        And Alice has shared the <type> <item> by link
        When Alice selects to edit link the <type> <item> using the Actions menu
        And Alice deletes the link on <item>
        Then link on <item> should not exist anymore

        Examples:
          |  type   |  item         |
          |  folder |  Links15      |
          |  file   |  Links16.txt  |