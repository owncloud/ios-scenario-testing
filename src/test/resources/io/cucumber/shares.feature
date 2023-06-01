@shares
Feature: Private Share

  As a user
  I want to share my content with other users in the platform
  So that the content is accessible and others can contribute

  Background: User is logged in
    Given user Alice is logged in

    @createshare
    Rule: Create a share

      @smoke
      Scenario Outline: Correct share with user
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share the <type> <item> using the <menu> menu
        And Alice selects the following user as sharee with <permissions> permissions
          | sharee      | <sharee>      |
          | email       | <email>       |
          | permissions | <permissions> |
        Then user <sharee> should have access to <item>
        And share should be created on <item> with the following fields
          | sharee      | <sharee> (<email>) |
          | permissions | <permissions>      |

        Examples:
          | type   | item       | permissions | sharee  | email          | menu       |
          | file   | Share1.txt | Viewer      | Bob     | bob@own.com    | Actions    |
          | folder | Share2     | Editor      | Charles | charly@own.com | Contextual |

      @smoke
      Scenario Outline: Correct share with group
        Given the following items have been created in the account
          | <type>  | <item>  |
        When Alice selects to share the <type> <item> using the <menu> menu
        And Alice selects the following group as sharee with <permissions> permissions
          | group | <group> |
        Then share should be created on <item> with the following fields
          | group       | <group>       |
          | permissions | <permissions> |
        And group test should have access to <item>

        Examples:
          | type   | item       | group | permissions | menu       |
          | file   | Share3.txt | test  | Viewer      | Contextual |
          | folder | Share4     | test  | Editor      | Actions    |

    @editshare
    Rule: Edit an existing share

      Scenario Outline: Edit existing share on a folder, removing permissions
        Given the following items have been created in the account
          | folder | <item> |
        And Alice has shared <type> <item> with user <sharee> with Viewer permissions
        When Alice selects to share the folder <item> using the Actions menu
        And Alice edits the share with the following fields
          | sharee      | <sharee> (<email>) |
          | permissions | <permissions>      |
        Then user <sharee> should have access to <item>
        And share should be created on <item> with the following fields
          | sharee      | <sharee> (<email>) |
          | permissions | <permissions>      |

        Examples:
          | type   | item   | sharee | email       | permissions |
          | folder | Share5 | Bob    | bob@own.com | Editor      |
          | folder | Share6 | Bob    | bob@own.com | Custom      |

    @resharing
    Rule: Resharing

      Scenario Outline: Resharing allowed
        Given the following items have been created in the account
          | <type> | <item> |
        When Alice selects to share the <type> <item> using the Actions menu
        And Alice selects the following user as sharee with Viewer permissions
          | sharee | <sharee> |
          | email  | <email>  |
        And Bob has reshared <type> <item> with <shareetype> <sharee2> with Viewer permissions
        Then user <sharee> should have access to <item>
        And user <sharee2> should have access to <item>
        And share should be created on <item> with the following fields
          | sharee | <sharee> (<email>)   |
          | sharee | <sharee2> (<email2>) |

        Examples:
          | type   | item       | shareetype | sharee | email       | sharee2 | email2         |
          | file   | Share7.txt | user       | Bob    | bob@own.com | Charles | charly@own.com |
          | folder | Share8     | user       | Bob    | bob@own.com | Charles | charly@own.com |

      Scenario Outline: Resharing not allowed
        Given the following items have been created in the account
          | <type> | <item> |
        When Alice selects to share the <type> <item> using the Actions menu
        And Alice selects the following user as sharee without sharing permission
          | sharee | <sharee> |
        And <sharee> has reshared <type> <item> with user <sharee2> with Viewer permissions
        Then user <sharee> should have access to <item>
        But user <sharee2> should not have access to <item>

        Examples:
          | type | item       | sharee | sharee2 |
          | file | Share9.txt | Bob    | Charles |

    @deleteshare
    Rule: Delete an existing share

      Scenario Outline: Delete existing share on folder
        Given the following items have been created in the account
          | <type> | <item> |
        And Alice has shared <type> <item> with <shareetype> <sharee> with <permission> permissions
        When Alice selects to share the <type> <item> using the Actions menu
        And Alice deletes the share with
          | <sharee> (<email>) |
        Then user <sharee> should not have access to <item>
        And <item> should not be shared anymore with
          | <sharee> (<email>) |

        Examples:
          | type   | item    | shareetype | sharee | email       | permission |
          | folder | Share10 | user       | Bob    | bob@own.com | Viewer     |

