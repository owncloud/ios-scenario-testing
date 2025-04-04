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
  Scenario Outline: Correct share with user and expiration
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item> using the <menu> menu
    And Alice selects the following user as sharee with the following fields
      | sharee      | <sharee>      |
      | permissions | <permissions> |
      | expiration  | <expiration>  |
    Then user <sharee> should have access to <item>
    And share should be created on <item> with the following fields
      | sharee      | <sharee>      |
      | permissions | <permissions> |
      | expiration  | <expiration>  |

    Examples:
      | type   | item       | permissions | sharee | menu       | expiration |
      | file   | Share1.txt | Viewer      | Bob    | Actions    | yes        |
      | folder | Share2     | Editor      | Bob    | Contextual | no         |
      | folder | Share3     | Upload      | Bob    | Actions    | yes        |

  @smoke
  Scenario Outline: Correct share with group
    Given the following items have been created in Alice account
      | <type> | <item> |
    When Alice selects to share the <type> <item> using the <menu> menu
    And Alice selects the following group as sharee with the following fields
      | group       | <group>       |
      | permissions | <permissions> |
      | expiration  | <expiration>  |
    Then share should be created on <item> with the following fields
      | group       | <group>       |
      | permissions | <permissions> |
      | expiration  | <expiration>  |
    And group test should have access to <item>

    Examples:
      | type   | item       | group | permissions | menu       | expiration |
      | file   | Share4.txt | test  | Viewer      | Contextual | yes        |
      | folder | Share5     | test  | Editor      | Actions    | no         |
      | folder | Share6     | test  | Upload      | Actions    | yes          |

  @editshare
  Rule: Edit an existing share

  Scenario Outline: Edit existing share on a folder, removing permissions
    Given the following items have been created in Alice account
      | folder | <item> |
    And Alice has shared <type> <item> with user <sharee> with Viewer permissions
    When Alice selects to share the folder <item> using the Actions menu
    And Alice edits the share with the following fields
      | sharee      | <sharee>      |
      | permissions | <permissions> |
      | expiration  | <expiration>  |
    Then user <sharee> should have access to <item>
    And share should be edited on <item> with the following fields
      | sharee      | <sharee>      |
      | permissions | <permissions> |
      | expiration  | <expiration>  |

    Examples:
      | type   | item       | sharee | permissions | expiration |
      | file   | Share7.txt | Bob    | Editor      | yes        |
      | folder | Share8     | Bob    | Upload      | no         |

  @deleteshare
  Rule: Delete an existing share

  Scenario Outline: Delete existing share on folder
    Given the following items have been created in Alice account
      | <type> | <item> |
    And Alice has shared <type> <item> with <shareetype> <sharee> with <permission> permissions
    When Alice selects to share the <type> <item> using the Actions menu
    And Alice deletes the share with
      | <sharee> |
    Then user <sharee> should not have access to <item>
    And <item> should not be shared anymore with
      | <sharee> |

    Examples:
      | type   | item   | shareetype | sharee | permission |
      | folder | Share9 | user       | Bob    | Viewer     |

  @sharesortcut
  Rule: Shortcuts

  Scenario Outline: Check shared file with me in list
  Given the following items have been created in Bob account
    | <type> | <item> |
  And Bob has shared <type> <item> with user Alice with Viewer permissions
  When Alice opens the sidebar
  And Alice opens the option shared with me in sidebar
  Then Alice should see <item> in shared with me

  Examples:
    | type | item        |
    | file | Share10.txt |

  Scenario Outline: Check shared folder by me in list
    Given the following items have been created in Alice account
      | <type> | <item> |
    And Alice has shared <type> <item> with user Bob with Viewer permissions
    When Alice opens the sidebar
    And Alice opens the option shared by me in sidebar
    Then Alice should see <item> in shared by me

    Examples:
      | type   | item    |
      | folder | Share11 |
