@spaces @nooc10
Feature: Spaces

  As a user
  I want to be able to check my available spaces
  so that i can browse through and share my content with other members

  Background: User is logged in
    Given user Alice is logged in

  Rule: List correct spaces

    @smoke
    Scenario: Create a new space with correct name and subtitle
      Given the following spaces have been created in Alice account
        | Space1 | First space  |
        | Space2 | Second space |
      When Alice opens the sidebar
      And Alice selects the spaces view
      Then Alice should see the following spaces
        | Space1 | First space  |
        | Space2 | Second space |

    Scenario: Add a new space with correct name and subtitle
      Given the following spaces have been created in Alice account
        | Space3 | Third space |
      And Alice opens the sidebar
      And Alice selects the spaces view
      When the following spaces have been created in Alice account
        | Space4 | Fourth space |
      Then Alice should see the following spaces
        | Space3 | Third space  |
        | Space4 | Fourth space |

    @ignore
    Scenario: Disable a space
      Given the following spaces have been created in Alice account
        | Space5 | Fifth space |
        | Space6 | Sixth space |
      And Alice opens the sidebar
      And Alice selects the spaces view
      When following space is disabled in server
        | Space5 | Fifth space |
      Then Alice should see the following spaces
        | Space6 | Sixth space |
      And Alice should not see the following spaces
        | Space5 | Fifth space |
