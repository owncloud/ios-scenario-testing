Feature: Public Share

  Background: User is logged in
    Given user1 is logged

  Scenario Outline: Create a public link with name
    When user selects <item> to create link with name <name>
    Then public link is created on <item> with the name <name>

    Examples:
      |        item        |   name    |
      |  Documents         |   link1   |
      |  San Francisco.jpg |   link2   |