@spaces @nooc10
Feature: Spaces

  As a user
  I want to be able to check my available spaces
  so that i can browse through and share my content with other members

  Background: User is logged in
    Given user Alice is logged in

  @createspace
  Rule: Create a space

    Scenario Outline: Create a new space with correct name and subtitle
      When Alice opens the sidebar
      And Alice selects the spaces view
      And Alice selects to create a new space with the following fields
        | <space> | <subtitle> |
      Then Alice should see the following spaces
        | <space> | <subtitle> |
      And Spaces should be created in server with the following fields
        | <space> | <subtitle> |

      Examples:
        | space  | subtitle    |
        | Space1 | First space |

  @editspace
  Rule: Edit an existing space

    Scenario Outline: Edit an existing space with correct name and subtitle
      Given the following spaces have been created in Alice account
        | <space> | <subtitle> |
      When Alice opens the sidebar
      And Alice selects the spaces view
      And Alice selects to edit a space with the following fields
        | <newspace> | <newsubtitle> |
      Then Alice should see the following spaces
        | <newspace> | <newsubtitle> |
      And Spaces should be updated in server with the following fields
        | <newspace> | <newsubtitle> |

      Examples:
        | space  | subtitle     | newspace | newsubtitle           |
        | Space2 | Second space | Space2a  | Second space modified |

  @disablespace
  Rule: Disable an existing space

    Scenario Outline: Disable an existing space
      Given the following spaces have been created in Alice account
        | <space> | <subtitle> |
      When Alice opens the sidebar
      And Alice selects the spaces view
      And Alice selects to disable the following spaces:
        | <space> | <subtitle> |
      Then Alice should not see the following spaces
        | <space> | <subtitle> |
      But Alice should see the following spaces in the list of disabled spaces
        | <space> | <subtitle> |
      And Spaces should be disabled in server with the following fields
        | <space> | <subtitle> |

      Examples:
        | space  | subtitle    |
        | Space3 | Third space |

  Rule: List correct spaces

    @smoke
    Scenario: Create a new space remotelly with correct name and subtitle
      Given the following spaces have been created in Alice account
        | Space1 | First space  |
        | Space2 | Second space |
      When Alice opens the sidebar
      And Alice selects the spaces view
      Then Alice should see the following spaces
        | Space1 | First space  |
        | Space2 | Second space |

    Scenario: Add a new space remotelly with correct name and subtitle
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
    Scenario: Disable a space remotelly
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
